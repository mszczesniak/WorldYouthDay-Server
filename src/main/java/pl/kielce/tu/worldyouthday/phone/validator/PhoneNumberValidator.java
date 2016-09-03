package pl.kielce.tu.worldyouthday.phone.validator;

import com.google.common.base.Strings;
import org.springframework.stereotype.Component;
import pl.kielce.tu.worldyouthday.utils.exception.ValidationException;
import pl.kielce.tu.worldyouthday.utils.validator.Validator;

@Component
public class PhoneNumberValidator implements Validator<String>{
    @Override
    public void validate(String var1) throws ValidationException {
        if(Strings.isNullOrEmpty(var1)) {
            throw new ValidationException("Empty phone number");
        }
    }
}
