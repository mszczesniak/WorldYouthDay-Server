package pl.kielce.tu.worldyouthday.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import pl.kielce.tu.worldyouthday.cities.CityRepository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Date;
import java.util.Optional;

@Component
public final class SchedulerSpecifications {

    @Autowired
    private CityRepository cityRepository;

    public Specification<Event> hasCity(Optional<String> cityId) {

        return (Root<Event> root,
                CriteriaQuery<?> query,
                CriteriaBuilder cb) ->
                cityId.isPresent() ?
                        cb.equal(root.get(Event_.city), cityRepository.findOne(cityId.get())) : null;
    }

    public Specification<Event> dateFrom(Optional<Date> date) {

        return (Root<Event> root,
                CriteriaQuery<?> query,
                CriteriaBuilder cb) ->
                date.isPresent() ?
                        cb.greaterThanOrEqualTo(root.get(Event_.startDate), date.get()) : null;
    }

    public Specification<Event> dateTo(Optional<Date> date) {

        return (Root<Event> root,
                CriteriaQuery<?> query,
                CriteriaBuilder cb) ->
                date.isPresent() ?
                        cb.lessThanOrEqualTo(root.get(Event_.endDate), date.get()) : null;
    }


}
