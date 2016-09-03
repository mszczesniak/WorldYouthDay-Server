package pl.kielce.tu.worldyouthday.phone.validator;

import com.google.common.base.Strings;
import org.springframework.stereotype.Component;
import pl.kielce.tu.worldyouthday.phone.resource.UpdatePhoneDetailsResource;
import pl.kielce.tu.worldyouthday.utils.exception.ValidationException;
import pl.kielce.tu.worldyouthday.utils.validator.Validator;

@Component
public class UpdatePhoneDetailsValidator implements Validator<UpdatePhoneDetailsResource> {
    @Override
    public void validate(UpdatePhoneDetailsResource resource) throws ValidationException {
        if (resource == null) {
            throw new ValidationException("Null phone details");
        }

        if (Strings.isNullOrEmpty(resource.getDescription())) {
            throw new ValidationException("Empty description");
        }

        if (Strings.isNullOrEmpty(resource.getOwner())) {
            throw new ValidationException("Empty owner");
        }

        if (resource.getVersion() == null) {
            throw new ValidationException("Null version");
        }
    }
}
