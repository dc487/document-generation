package org.rha.services.document_generation.core.docx;

import org.docx4j.Docx4J;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.rha.services.document_generation.core.DocumentFormatConverter;
import org.rha.services.document_generation.core.DocumentGenerator;
import org.rha.services.document_generation.core.model.DocumentGenerationRequest;
import org.rha.services.document_generation.core.model.exceptions.DocumentConversionException;
import org.rha.services.document_generation.core.model.exceptions.DocumentTemplatingException;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.function.Consumer;

public final class DocxDocumentGenerator implements DocumentGenerator {
    private final DocumentFormatConverter docxToDocxFormatConverter;
    private final DocumentFormatConverter docxToPdfFormatConverter;

    @Inject
    public DocxDocumentGenerator(
            @Named("noop-converter") DocumentFormatConverter docxToDocxFormatConverter,
            @Named("docx-to-pdf-converter") DocumentFormatConverter docxToPdfFormatConverter
    ) {
        this.docxToDocxFormatConverter = docxToDocxFormatConverter;
        this.docxToPdfFormatConverter = docxToPdfFormatConverter;
    }

    @Override
    public void generateDocument(DocumentGenerationRequest documentGenerationRequest)
            throws DocumentConversionException, DocumentTemplatingException {
        final ByteArrayOutputStream templatedOutput = new ByteArrayOutputStream();
        try {
            final WordprocessingMLPackage template = Docx4J.load(documentGenerationRequest.getDocumentTemplate());
            Docx4J.bind(template, documentGenerationRequest.getDocumentContent(), Docx4J.FLAG_NONE);
            Docx4J.save(template, templatedOutput);
        } catch (Docx4JException e) {
            throw new DocumentTemplatingException(e);
        }
        final byte[] templatedOutputBytes = templatedOutput.toByteArray();

        documentGenerationRequest.getOutputDocuments().entrySet().parallelStream()
                .forEach(rethrowConsumer(f -> {
                    final InputStream inputStream = new ByteArrayInputStream(templatedOutputBytes);
                    switch (f.getKey()) {
                        case DOCX:
                            docxToDocxFormatConverter.convert(inputStream, f.getValue());
                            break;
                        case PDF:
                            docxToPdfFormatConverter.convert(inputStream, f.getValue());
                            break;
                        default:
                            throw new DocumentConversionException("Unsupported document format: " +
                                    f.getKey().name());
                    }
                }));
    }

    @FunctionalInterface
    public interface Consumer_WithExceptions<T, E extends Exception> {
        void accept(T t) throws E;
    }

    public static <T, E extends Exception> Consumer<T> rethrowConsumer(Consumer_WithExceptions<T, E> consumer) throws E {
        return t -> {
            try {
                consumer.accept(t);
            } catch (Exception exception) {
                throwAsUnchecked(exception);
            }
        };
    }

    @SuppressWarnings("unchecked")
    private static <E extends Throwable> void throwAsUnchecked(Exception exception) throws E {
        throw (E) exception;
    }
}
