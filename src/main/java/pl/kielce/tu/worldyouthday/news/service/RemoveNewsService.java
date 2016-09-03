package pl.kielce.tu.worldyouthday.news.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.kielce.tu.worldyouthday.news.News;
import pl.kielce.tu.worldyouthday.news.NewsRepository;
import pl.kielce.tu.worldyouthday.utils.exception.ValidationException;
import pl.kielce.tu.worldyouthday.version.VersionTable;
import pl.kielce.tu.worldyouthday.version.Versionable;

/**
 * Created by Łukasz Wesołowski on 24.05.2016.
 */

@Service
public class RemoveNewsService {
    @Autowired
    private NewsRepository newsRepository;

    @Versionable(table = VersionTable.NEWS)
    public void removeNews(String id) throws ValidationException {
        News news = newsRepository.findOne(id);
        if (news == null) {
            throw new ValidationException(String.format("News with id: %s doesn't exist", id));
        }

        newsRepository.delete(news);
    }
}
