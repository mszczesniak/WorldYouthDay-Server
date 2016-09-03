package pl.kielce.tu.worldyouthday.news.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.kielce.tu.worldyouthday.cities.City;
import pl.kielce.tu.worldyouthday.cities.CityRepository;
import pl.kielce.tu.worldyouthday.language.Language;
import pl.kielce.tu.worldyouthday.news.News;
import pl.kielce.tu.worldyouthday.news.NewsDetails;
import pl.kielce.tu.worldyouthday.news.NewsRepository;
import pl.kielce.tu.worldyouthday.news.converter.NewsToResourceConverter;
import pl.kielce.tu.worldyouthday.news.resource.NewNewsResource;
import pl.kielce.tu.worldyouthday.news.resource.NewsDetailsResource;
import pl.kielce.tu.worldyouthday.news.resource.NewsResource;
import pl.kielce.tu.worldyouthday.news.validator.NewNewsValidator;
import pl.kielce.tu.worldyouthday.news.validator.NewsDetailsValidator;
import pl.kielce.tu.worldyouthday.user.User;
import pl.kielce.tu.worldyouthday.user.UserRepository;
import pl.kielce.tu.worldyouthday.utils.exception.ValidationException;
import pl.kielce.tu.worldyouthday.version.VersionTable;
import pl.kielce.tu.worldyouthday.version.Versionable;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Łukasz Wesołowski on 24.05.2016.
 */

@Service
public class AddNewsService {
    @Autowired
    private NewsRepository newsRepository;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NewNewsValidator newNewsValidator;

    @Autowired
    private NewsToResourceConverter newsToResourceConverter;

    @Autowired
    private NewsDetailsValidator newsDetailsValidator;

    @Transactional
    @Versionable(table = VersionTable.NEWS)
    public NewsResource addNews(NewNewsResource resource) throws ValidationException {
        newNewsValidator.validate(resource);

        City city = cityRepository.findOne(resource.getCityId());
        User author = userRepository.findOne(resource.getAuthorId());

        String newsId = UUID.randomUUID().toString();
        Map<Language, NewsDetails> details = new HashMap<>();

        for (Map.Entry<Language, NewsDetailsResource> e : resource.getDetails().entrySet()) {
            newsDetailsValidator.validate(e.getValue());
            details.put(e.getKey(), new NewsDetails(
                    newsId,
                    e.getKey(),
                    e.getValue().getTitle(),
                    e.getValue().getText()
            ));
        }

        News news = new News(
                newsId,
                new Date(),
                author,
                null,
                null,
                0L,
                details,
                city
        );

        return newsToResourceConverter.convert(newsRepository.saveAndFlush(news));

    }
}
