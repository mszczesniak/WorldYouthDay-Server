package pl.kielce.tu.worldyouthday.news.validator;

import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.kielce.tu.worldyouthday.news.resource.NewsDetailsResource;
import pl.kielce.tu.worldyouthday.utils.exception.ValidationException;
import pl.kielce.tu.worldyouthday.utils.validator.Validator;

/**
 * Created by Łukasz Wesołowski on 01.05.2016.
 */

@Component
public class NewsDetailsValidator implements Validator<NewsDetailsResource> {
    @Autowired
    private NewsTextValidator newsTextValidator;

    @Override
    public void validate(NewsDetailsResource var1) throws ValidationException {
        if (Strings.isNullOrEmpty(var1.getTitle())) {
            throw new ValidationException("Invalid title");
        }

        if (Strings.isNullOrEmpty(var1.getText())) {
            throw new ValidationException("Invalid text");
        }

        newsTextValidator.validate(var1.getText());
    }
}
