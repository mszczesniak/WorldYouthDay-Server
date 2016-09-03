package pl.kielce.tu.worldyouthday.news;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import pl.kielce.tu.worldyouthday.cities.CityRepository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Optional;

/**
 * Created by Łukasz Wesołowski on 14.05.2016.
 */

@Component
public class NewsSpecification {
    @Autowired
    private CityRepository cityRepository;

    public Specification<News> hasCity(Optional<String> cityId) {

        return (Root<News> root,
                CriteriaQuery<?> query,
                CriteriaBuilder cb) ->
                cityId.isPresent() ?
                        cb.equal(root.get(News_.city), cityRepository.findOne(cityId.get())) : null;
    }
}
