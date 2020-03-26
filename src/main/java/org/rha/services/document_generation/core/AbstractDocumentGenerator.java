package org.rha.services.document_generation.core;

import org.rha.services.document_generation.core.docx.DocxDocumentGenerator;
import org.rha.services.document_generation.core.model.DocumentGenerationRequest;
import org.rha.services.document_generation.core.model.exceptions.DocumentConversionException;
import org.rha.services.document_generation.core.model.exceptions.DocumentTemplatingException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.function.Consumer;

public abstract class AbstractDocumentGenerator implements DocumentGenerator {
    private final DocumentTemplater documentTemplater;
    private final DocumentFormatConverter toDocxFormatConverter;
    private final DocumentFormatConverter toPdfFormatConverter;

    protected AbstractDocumentGenerator(
            DocumentTemplater documentTemplater,
            DocumentFormatConverter toDocxFormatConverter,
            DocumentFormatConverter toPdfFormatConverter
    ) {
        this.documentTemplater = documentTemplater;
        this.toDocxFormatConverter = toDocxFormatConverter;
        this.toPdfFormatConverter = toPdfFormatConverter;
    }

    @Override
    public void generateDocument(DocumentGenerationRequest documentGenerationRequest)
            throws DocumentConversionException, DocumentTemplatingException {
        final ByteArrayOutputStream templatedOutput = new ByteArrayOutputStream();
        this.documentTemplater.populateDocumentTemplate(
                documentGenerationRequest.getDocumentTemplate(),
                documentGenerationRequest.getDocumentContent(),
                templatedOutput
        );
        final byte[] templatedOutputBytes = templatedOutput.toByteArray();

        documentGenerationRequest.getOutputDocuments().entrySet().parallelStream()
                .forEach(rethrowConsumer(f -> {
                    final InputStream inputStream = new ByteArrayInputStream(templatedOutputBytes);
                    switch (f.getKey()) {
                        case DOCX:
                            this.toDocxFormatConverter.convert(inputStream, f.getValue());
                            break;
                        case PDF:
                            this.toPdfFormatConverter.convert(inputStream, f.getValue());
                            break;
                        default:
                            throw new DocumentConversionException("Unsupported output document format: " +
                                    f.getKey().name());
                    }
                }));
    }

    @FunctionalInterface
    private interface Consumer_WithExceptions<T, E extends Exception> {
        void accept(T t) throws E;
    }

    private static <T, E extends Exception> Consumer<T> rethrowConsumer(Consumer_WithExceptions<T, E> consumer) throws E {
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
