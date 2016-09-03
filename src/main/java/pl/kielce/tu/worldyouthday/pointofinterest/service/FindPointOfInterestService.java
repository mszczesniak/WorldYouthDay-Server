package pl.kielce.tu.worldyouthday.pointofinterest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import pl.kielce.tu.worldyouthday.language.Language;
import pl.kielce.tu.worldyouthday.pointofinterest.PointOfInterest;
import pl.kielce.tu.worldyouthday.pointofinterest.PointOfInterestRepository;
import pl.kielce.tu.worldyouthday.pointofinterest.PointOfInterestSearchCriteria;
import pl.kielce.tu.worldyouthday.pointofinterest.PointOfInterestSpecification;
import pl.kielce.tu.worldyouthday.pointofinterest.converter.PointOfInterestToResourceConverter;
import pl.kielce.tu.worldyouthday.pointofinterest.resource.PointOfInterestResource;

import java.util.List;
import java.util.Optional;

/**
 * Created by Łukasz Wesołowski on 18.05.2016.
 */

@Service
public class FindPointOfInterestService {
    @Autowired
    private PointOfInterestRepository pointOfInterestRepository;

    @Autowired
    private PointOfInterestToResourceConverter pointOfInterestToResourceConverter;

    @Autowired
    private PointOfInterestSpecification pointOfInterestSpecification;

    public List<PointOfInterestResource> findAllPointsOfInterest(PointOfInterestSearchCriteria criteria) {
        Specification<PointOfInterest> specification = Specifications
                .where(pointOfInterestSpecification.hasCategory(criteria.getCategoryId()))
                .and(pointOfInterestSpecification.hasCity(criteria.getCityId()));

        Optional<Language> language = criteria.getLanguage();
        List<PointOfInterest> pointsOfInterest = pointOfInterestRepository.findAll(specification);

        if (language.isPresent()) {
            return pointOfInterestToResourceConverter.convert(pointsOfInterest, language.get());
        } else {
            return pointOfInterestToResourceConverter.convert(pointsOfInterest);
        }
    }

    public PointOfInterestResource findPointOfInterest(String id, Language language) {
        return pointOfInterestToResourceConverter.convert(pointOfInterestRepository.findOne(id), language);
    }

    public PointOfInterestResource findPointOfInterestInAllLanguages(String id) {
        return pointOfInterestToResourceConverter.convert(pointOfInterestRepository.findOne(id));
    }
}
