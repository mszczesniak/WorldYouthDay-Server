package pl.kielce.tu.worldyouthday.pointofinterest.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.kielce.tu.worldyouthday.language.Language;
import pl.kielce.tu.worldyouthday.pointofinterest.category.converter.PointOfInterestCategoryToResourceConverter;
import pl.kielce.tu.worldyouthday.pointofinterest.category.resource.NewPointOfInterestCategoryResource;
import pl.kielce.tu.worldyouthday.pointofinterest.category.resource.PointOfInterestCategoryDetailsResource;
import pl.kielce.tu.worldyouthday.pointofinterest.category.resource.PointOfInterestCategoryResource;
import pl.kielce.tu.worldyouthday.pointofinterest.category.resource.UpdatePointOfInterestCategoryResource;
import pl.kielce.tu.worldyouthday.pointofinterest.category.validator.NewPointOfInterestCategoryValidator;
import pl.kielce.tu.worldyouthday.pointofinterest.category.validator.UpdatePointOfInterestCategoryValidator;
import pl.kielce.tu.worldyouthday.utils.exception.ValidationException;
import pl.kielce.tu.worldyouthday.version.VersionTable;
import pl.kielce.tu.worldyouthday.version.Versionable;

import java.util.*;

/**
 * Created by Łukasz Wesołowski on 10.04.2016.
 */

@Service
public class PointOfInterestCategoryService {
    @Autowired
    private PointOfInterestCategoryRepository pointOfInterestCategoryRepository;

    @Autowired
    private PointOfInterestCategoryToResourceConverter pointOfInterestCategoryToResourceConverter;

    @Autowired
    private NewPointOfInterestCategoryValidator newPointOfInterestCategoryValidator;

    @Autowired
    private UpdatePointOfInterestCategoryValidator updatePointOfInterestCategoryValidator;

    public List<PointOfInterestCategoryResource> findAllPointOfInterestCategories(Language language) {
        return pointOfInterestCategoryToResourceConverter.convert(pointOfInterestCategoryRepository.findAll(), language);
    }

    public List<PointOfInterestCategoryResource> findAll(PointOfInterestCategorySearchCriteria searchCriteria) {
        Optional<Language> language = searchCriteria.getLanguage();
        if (language.isPresent()) {
            return pointOfInterestCategoryToResourceConverter.convert(pointOfInterestCategoryRepository.findAll(), language.get());
        } else {
            return pointOfInterestCategoryToResourceConverter.convert(pointOfInterestCategoryRepository.findAll());
        }
    }

    public PointOfInterestCategoryResource findById(String id) {
        return pointOfInterestCategoryToResourceConverter.convert(pointOfInterestCategoryRepository.findOne(id));
    }

    @Versionable(table = VersionTable.POI_CATEGORY)
    public PointOfInterestCategoryResource addPointOfInterestCategory(NewPointOfInterestCategoryResource resource) throws ValidationException {
        newPointOfInterestCategoryValidator.validate(resource);

        String categoryId = UUID.randomUUID().toString();

        HashMap<Language, PointOfInterestCategoryDetails> details = new HashMap<>();

        for (Map.Entry<Language, PointOfInterestCategoryDetailsResource> entry : resource.getDetails().entrySet()) {
            details.put(entry.getKey(), new PointOfInterestCategoryDetails(
                    categoryId,
                    entry.getKey(),
                    entry.getValue().getName()
            ));
        }

        PointOfInterestCategory newCategory = new PointOfInterestCategory(
                categoryId,
                0L,
                details
        );

        return pointOfInterestCategoryToResourceConverter.convert(pointOfInterestCategoryRepository.saveAndFlush(newCategory));
    }

    @Versionable(table = VersionTable.POI_CATEGORY)
    public PointOfInterestCategoryResource updatePointOfInterestCategory(String id, UpdatePointOfInterestCategoryResource resource) throws ValidationException {
        updatePointOfInterestCategoryValidator.validate(id, resource);

        HashMap<Language, PointOfInterestCategoryDetails> details = new HashMap<>();

        for (Map.Entry<Language, PointOfInterestCategoryDetailsResource> entry : resource.getDetails().entrySet()) {
            details.put(entry.getKey(), new PointOfInterestCategoryDetails(
                    id,
                    entry.getKey(),
                    entry.getValue().getName()
            ));
        }

        PointOfInterestCategory updatedCategory = new PointOfInterestCategory(
                id,
                resource.getVersion(),
                details
        );

        return pointOfInterestCategoryToResourceConverter.convert(pointOfInterestCategoryRepository.saveAndFlush(updatedCategory));
    }

    @Versionable(table = VersionTable.POI_CATEGORY)
    public void removePointOfInterestCategory(String id) throws ValidationException {
        PointOfInterestCategory category = pointOfInterestCategoryRepository.findOne(id);
        if (category == null) {
            throw new ValidationException(String.format("Category with id: %s doesn't exist", id));
        }

        pointOfInterestCategoryRepository.delete(category);
    }
}
