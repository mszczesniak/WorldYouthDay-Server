package pl.kielce.tu.worldyouthday.pointofinterest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import pl.kielce.tu.worldyouthday.cities.CityRepository;
import pl.kielce.tu.worldyouthday.pointofinterest.category.PointOfInterestCategoryRepository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Optional;

/**
 * Created by Łukasz Wesołowski on 14.05.2016.
 */

@Component
public class PointOfInterestSpecification {
    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private PointOfInterestCategoryRepository pointOfInterestCategoryRepository;

    public Specification<PointOfInterest> hasCity(Optional<String> cityId) {

        return (Root<PointOfInterest> root,
                CriteriaQuery<?> query,
                CriteriaBuilder cb) ->
                cityId.isPresent() ?
                        cb.equal(root.get(PointOfInterest_.city), cityRepository.findOne(cityId.get())) : null;
    }

    public Specification<PointOfInterest> hasCategory(Optional<String> categoryId) {

        return (Root<PointOfInterest> root,
                CriteriaQuery<?> query,
                CriteriaBuilder cb) ->
                categoryId.isPresent() ?
                        cb.equal(root.get(PointOfInterest_.category), pointOfInterestCategoryRepository.findOne(categoryId.get())) : null;
    }
}
