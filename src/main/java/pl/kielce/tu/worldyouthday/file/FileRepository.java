package pl.kielce.tu.worldyouthday.file;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Łukasz Wesołowski on 10.05.2016.
 */

@Repository
public interface FileRepository extends JpaRepository<File, String> {
}
