package pl.kielce.tu.worldyouthday.pointofinterest.category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Łukasz Wesołowski on 10.04.2016.
 */

@Repository
public interface PointOfInterestCategoryRepository extends JpaRepository<PointOfInterestCategory, String> {
}
