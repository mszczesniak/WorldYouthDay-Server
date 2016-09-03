package pl.kielce.tu.worldyouthday.news;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * Created by Łukasz Wesołowski on 28.04.2016.
 */

@Repository
public interface NewsRepository extends JpaRepository<News, String>, JpaSpecificationExecutor<News> {
}
