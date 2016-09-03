package pl.kielce.tu.worldyouthday.file.validator;

import com.google.common.io.Files;
import org.springframework.stereotype.Component;
import pl.kielce.tu.worldyouthday.file.resource.NewFileResource;
import pl.kielce.tu.worldyouthday.utils.exception.ValidationException;
import pl.kielce.tu.worldyouthday.utils.validator.Validator;

/**
 * Created by Łukasz Wesołowski on 17.05.2016.
 */

@Component
public class NewFileValidator implements Validator<NewFileResource> {


    @Override
    public void validate(NewFileResource var1) throws ValidationException {
        if (var1.getFile() == null) {
            throw new ValidationException("File is empty");
        }

        if (var1.getType() == null) {
            throw new ValidationException("Invalid type");
        }

        if (!var1.getType().containsExtension(Files.getFileExtension(var1.getFile().getOriginalFilename()))) {
            throw new ValidationException("Invalid extension");
        }
    }
}
