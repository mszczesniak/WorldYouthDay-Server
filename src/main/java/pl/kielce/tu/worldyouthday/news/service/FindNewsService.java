package pl.kielce.tu.worldyouthday.news.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import pl.kielce.tu.worldyouthday.language.Language;
import pl.kielce.tu.worldyouthday.news.News;
import pl.kielce.tu.worldyouthday.news.NewsRepository;
import pl.kielce.tu.worldyouthday.news.NewsSearchCriteria;
import pl.kielce.tu.worldyouthday.news.NewsSpecification;
import pl.kielce.tu.worldyouthday.news.converter.NewsToResourceConverter;
import pl.kielce.tu.worldyouthday.news.resource.NewsResource;

import java.util.List;
import java.util.Optional;

/**
 * Created by Łukasz Wesołowski on 24.05.2016.
 */

@Service
public class FindNewsService {
    @Autowired
    private NewsRepository newsRepository;

    @Autowired
    private NewsToResourceConverter newsToResourceConverter;

    @Autowired
    private NewsSpecification newsSpecification;

    public List<NewsResource> findAllNews(NewsSearchCriteria criteria) {
        Specification<News> specification = Specifications
                .where(newsSpecification.hasCity(criteria.getCityId()));

        Optional<Language> language = criteria.getLanguage();
        List<News> newses = newsRepository.findAll(specification);

        if (language.isPresent()) {
            return newsToResourceConverter.convert(newses, language.get());
        } else {
            return newsToResourceConverter.convert(newses);
        }
    }

    public NewsResource findNews(String id) {
        return newsToResourceConverter.convert(newsRepository.findOne(id));
    }
}
