package pl.kielce.tu.worldyouthday.pointofinterest.validator;

import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.kielce.tu.worldyouthday.news.NewsDetailsRepository;
import pl.kielce.tu.worldyouthday.pointofinterest.PointOfInterestRepository;
import pl.kielce.tu.worldyouthday.utils.exception.ValidationException;
import pl.kielce.tu.worldyouthday.utils.validator.Validator;


@Component
public class RemovePointOfInterestValidator implements Validator<String> {
    @Autowired
    private PointOfInterestRepository pointOfInterestRepository;

    @Autowired
    private NewsDetailsRepository newsDetailsRepository;

    @Override
    public void validate(String id) throws ValidationException {
        if (Strings.isNullOrEmpty(id)) {
            throw new ValidationException("Invalid id");
        }

        if (!pointOfInterestRepository.exists(id)) {
            throw new ValidationException(String.format("POI with id %s not exist", id));
        }

        if (!newsDetailsRepository.findByTextLike("%<poi%id=\"" + id + "\">%").isEmpty()) {
            throw new ValidationException("Remove news with refers to the poi");
        }
    }
}
