package pl.kielce.tu.worldyouthday.scheduler;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface SchedulerRepository extends JpaRepository<Event, String>, JpaSpecificationExecutor<Event> {
}
