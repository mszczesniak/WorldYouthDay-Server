package pl.kielce.tu.worldyouthday.news;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewsDetailsRepository extends JpaRepository<NewsDetails, String> {
    List<News> findByTextLike(String text);
}
