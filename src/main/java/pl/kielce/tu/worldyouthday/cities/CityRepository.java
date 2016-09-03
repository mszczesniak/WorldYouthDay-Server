package pl.kielce.tu.worldyouthday.cities;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CityRepository extends JpaRepository<City, String> {
    Optional<City> findOneByName(String name);

    List<City> findByIdIn(List<String> idList);
}
