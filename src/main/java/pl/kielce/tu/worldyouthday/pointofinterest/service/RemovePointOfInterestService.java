package pl.kielce.tu.worldyouthday.pointofinterest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.kielce.tu.worldyouthday.pointofinterest.PointOfInterest;
import pl.kielce.tu.worldyouthday.pointofinterest.PointOfInterestRepository;
import pl.kielce.tu.worldyouthday.pointofinterest.validator.RemovePointOfInterestValidator;
import pl.kielce.tu.worldyouthday.utils.exception.ValidationException;
import pl.kielce.tu.worldyouthday.version.VersionTable;
import pl.kielce.tu.worldyouthday.version.Versionable;

/**
 * Created by Łukasz Wesołowski on 18.05.2016.
 */

@Service
public class RemovePointOfInterestService {
    @Autowired
    private PointOfInterestRepository pointOfInterestRepository;

    @Autowired
    private RemovePointOfInterestValidator removePointOfInterestValidator;

    @Transactional
    @Versionable(table = VersionTable.POI)
    public void removePointOfInterest(String id) throws ValidationException {
        removePointOfInterestValidator.validate(id);
        PointOfInterest pointOfInterest = pointOfInterestRepository.findOne(id);
        pointOfInterestRepository.delete(pointOfInterest);
    }
}
