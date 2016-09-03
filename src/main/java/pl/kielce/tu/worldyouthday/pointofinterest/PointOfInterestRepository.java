package pl.kielce.tu.worldyouthday.pointofinterest;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by vooka on 31.03.2016.
 */

@Repository
public interface PointOfInterestRepository extends JpaRepository<PointOfInterest, String>, JpaSpecificationExecutor<PointOfInterest> {
    List<PointOfInterest> findByCityId(String cityId);
}
