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
import pl.kielce.tu.worldyouthday.news.resource.NewsDetailsResource;
import pl.kielce.tu.worldyouthday.news.resource.NewsResource;
import pl.kielce.tu.worldyouthday.news.resource.UpdateNewsResource;
import pl.kielce.tu.worldyouthday.news.validator.NewsDetailsValidator;
import pl.kielce.tu.worldyouthday.news.validator.UpdateNewsValidator;
import pl.kielce.tu.worldyouthday.user.User;
import pl.kielce.tu.worldyouthday.user.UserRepository;
import pl.kielce.tu.worldyouthday.utils.exception.ValidationException;
import pl.kielce.tu.worldyouthday.version.VersionTable;
import pl.kielce.tu.worldyouthday.version.Versionable;

import java.util.Date;
import java.util.HashMap;

/**
 * Created by Łukasz Wesołowski on 24.05.2016.
 */

@Service
public class UpdateNewsService {
    @Autowired
    private NewsRepository newsRepository;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UpdateNewsValidator updateNewsValidator;

    @Autowired
    private NewsToResourceConverter newsToResourceConverter;

    @Autowired
    private NewsDetailsValidator newsDetailsValidator;

    @Transactional
    @Versionable(table = VersionTable.NEWS)
    public NewsResource updateNews(String newsId, UpdateNewsResource resource) throws ValidationException {
        updateNewsValidator.validate(newsId, resource);

        City city = cityRepository.findOne(resource.getCityId());
        User author = userRepository.findOne(resource.getAuthorId());
        News news = newsRepository.findOne(newsId);

        HashMap<Language, NewsDetails> details = new HashMap<>();

        for (HashMap.Entry<Language, NewsDetailsResource> entry : resource.getDetails().entrySet()) {
            newsDetailsValidator.validate(entry.getValue());
            details.put(entry.getKey(), new NewsDetails(
                    newsId,
                    entry.getKey(),
                    entry.getValue().getTitle(),
                    entry.getValue().getText()
            ));
        }


        News updatedNews = new News(
                newsId,
                news.getCreationDate(),
                news.getCreationAuthor(),
                new Date(),
                author,
                resource.getVersion(),
                details,
                city
        );

        return newsToResourceConverter.convert(newsRepository.saveAndFlush(updatedNews));
    }
}
