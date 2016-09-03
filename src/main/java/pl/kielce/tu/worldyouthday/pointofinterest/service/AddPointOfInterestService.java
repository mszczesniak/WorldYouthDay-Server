package pl.kielce.tu.worldyouthday.pointofinterest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.kielce.tu.worldyouthday.cities.City;
import pl.kielce.tu.worldyouthday.cities.CityRepository;
import pl.kielce.tu.worldyouthday.language.Language;
import pl.kielce.tu.worldyouthday.pointofinterest.PointOfInterest;
import pl.kielce.tu.worldyouthday.pointofinterest.PointOfInterestDetails;
import pl.kielce.tu.worldyouthday.pointofinterest.PointOfInterestRepository;
import pl.kielce.tu.worldyouthday.pointofinterest.category.PointOfInterestCategory;
import pl.kielce.tu.worldyouthday.pointofinterest.category.PointOfInterestCategoryRepository;
import pl.kielce.tu.worldyouthday.pointofinterest.converter.PointOfInterestToResourceConverter;
import pl.kielce.tu.worldyouthday.pointofinterest.resource.NewPointOfInterestResource;
import pl.kielce.tu.worldyouthday.pointofinterest.resource.PointOfInterestDetailsResource;
import pl.kielce.tu.worldyouthday.pointofinterest.resource.PointOfInterestResource;
import pl.kielce.tu.worldyouthday.pointofinterest.validator.NewPointOfInterestDetailsValidator;
import pl.kielce.tu.worldyouthday.pointofinterest.validator.NewPointOfInterestValidator;
import pl.kielce.tu.worldyouthday.utils.exception.ValidationException;
import pl.kielce.tu.worldyouthday.version.VersionTable;
import pl.kielce.tu.worldyouthday.version.Versionable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

/**
 * Created by Łukasz Wesołowski on 18.05.2016.
 */

@Service
public class AddPointOfInterestService {
    @Autowired
    private PointOfInterestRepository pointOfInterestRepository;

    @Autowired
    private PointOfInterestCategoryRepository pointOfInterestCategoryRepository;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private NewPointOfInterestValidator newPointOfInterestValidator;

    @Autowired
    private NewPointOfInterestDetailsValidator newPointOfInterestDetailsValidator;

    @Autowired
    private PointOfInterestToResourceConverter pointOfInterestToResourceConverter;


    @Transactional
    @Versionable(table = VersionTable.POI)
    public PointOfInterestResource addPointOfInterest(NewPointOfInterestResource resource) throws ValidationException {
        newPointOfInterestValidator.validate(resource);

        City city = cityRepository.findOne(resource.getCityId());
        PointOfInterestCategory category = pointOfInterestCategoryRepository.findOne(resource.getCategoryId());
        HashMap<Language, PointOfInterestDetails> details = new HashMap<>();
        String newPointOfInterestId = UUID.randomUUID().toString();

        for (HashMap.Entry<Language, PointOfInterestDetailsResource> entry : resource.getDetails().entrySet()) {
            newPointOfInterestDetailsValidator.validate(entry.getValue());
            details.put(entry.getKey(), new PointOfInterestDetails(
                    newPointOfInterestId,
                    entry.getKey(),
                    entry.getValue().getName(),
                    entry.getValue().getDescription()
            ));
        }

        PointOfInterest pointOfInterest = new PointOfInterest(
                newPointOfInterestId,
                resource.getLatitude(),
                resource.getLongitude(),
                0L,
                category,
                details,
                city,
                new ArrayList<>()
        );

        return pointOfInterestToResourceConverter.convert(pointOfInterestRepository.saveAndFlush(pointOfInterest));
    }
}
