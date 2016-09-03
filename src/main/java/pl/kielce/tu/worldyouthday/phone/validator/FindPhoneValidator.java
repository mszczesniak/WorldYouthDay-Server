package pl.kielce.tu.worldyouthday.phone.validator;

import com.google.common.base.Strings;
import org.springframework.stereotype.Component;
import pl.kielce.tu.worldyouthday.utils.exception.ValidationException;
import pl.kielce.tu.worldyouthday.utils.validator.Validator;

@Component
public class FindPhoneValidator implements Validator<String> {


    @Override
    public void validate(String id) throws ValidationException {

        if (Strings.isNullOrEmpty(id)) {
            throw new ValidationException("Empty Id");
        }

    }
}
