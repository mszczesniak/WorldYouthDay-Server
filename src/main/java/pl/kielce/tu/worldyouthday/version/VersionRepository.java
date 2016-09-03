package pl.kielce.tu.worldyouthday.version;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VersionRepository extends JpaRepository<Version, String> {
    Optional<Version> findOneByTable(VersionTable table);
}
