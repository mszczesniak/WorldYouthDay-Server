package pl.kielce.tu.worldyouthday.prayer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrayerRepository extends JpaRepository<Prayer, String> {
}
