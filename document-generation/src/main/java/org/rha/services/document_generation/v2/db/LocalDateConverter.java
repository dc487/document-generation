package org.rha.services.document_generation.v2.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.sql.Date;
import java.time.LocalDate;

@Converter(autoApply = true)
public class LocalDateConverter implements AttributeConverter<LocalDate, Date> {
    Logger logger = LoggerFactory.getLogger(LocalDateConverter.class);

    @Override
    public Date convertToDatabaseColumn(LocalDate localDate) {
        logger.info("Attempting to convert localdate to date");
        return Date.valueOf(localDate);
    }

    @Override
    public LocalDate convertToEntityAttribute(Date date) {
        return date.toLocalDate();
    }
}
