package templating;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.ValidationException;
import javax.validation.Validator;
import java.util.Set;

@ApplicationScoped
public class TemplateMessageValidator {

    @Inject
    Validator validator;

    public void validateMessage(TemplateDocumentMessage message) {

        Set<ConstraintViolation<TemplateDocumentMessage>> constraintViolations = validator.validate(message);
        if (!constraintViolations.isEmpty()) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Message validation failed! The following validation errors were found : ");

            constraintViolations.forEach(
                    constraintViolation -> stringBuilder
                            .append("PROPERTY - ")
                            .append(constraintViolation.getPropertyPath().toString())
                            .append(" , VIOLATION - ")
                            .append(constraintViolation.getMessage())
                            .append("    :    ")
            );

            throw new ValidationException(stringBuilder.toString());
        }
    }
}
