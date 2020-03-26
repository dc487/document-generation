package org.rha.services.document_generation.core.docx;

import org.rha.services.document_generation.core.AbstractDocumentGenerator;
import org.rha.services.document_generation.core.DocumentFormatConverter;
import org.rha.services.document_generation.core.DocumentTemplater;

import javax.inject.Inject;
import javax.inject.Named;

public final class DocxDocumentGenerator extends AbstractDocumentGenerator {
    @Inject
    public DocxDocumentGenerator(
            @Named("docx4j") DocumentTemplater docxDocumentTemplater,
            @Named("noop-converter") DocumentFormatConverter docxToDocxFormatConverter,
            @Named("docx-to-pdf-converter") DocumentFormatConverter docxToPdfFormatConverter
    ) {
        super(
                docxDocumentTemplater,
                docxToDocxFormatConverter,
                docxToPdfFormatConverter
        );
    }
}
