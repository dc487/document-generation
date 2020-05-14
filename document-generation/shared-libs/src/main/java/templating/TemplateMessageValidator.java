package templating;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.HashSet;
import java.util.Set;

@ApplicationScoped
public class TemplateMessageValidator {

    @Inject
    Validator validator;

    public Set<String> validateMessage(TemplateDocumentMessage message) {
        Set<String> violationsList = new HashSet<>();

        Set<ConstraintViolation<TemplateDocumentMessage>> constraintViolations = validator.validate(message);
        if (!constraintViolations.isEmpty()) {
            constraintViolations.forEach(
                    constraintViolation -> violationsList.add(constraintViolation.getMessage())
            );
        }
        return violationsList;
    }
}
