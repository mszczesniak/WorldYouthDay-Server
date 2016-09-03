package pl.kielce.tu.worldyouthday.news.converter;

import org.springframework.stereotype.Component;
import pl.kielce.tu.worldyouthday.language.Language;
import pl.kielce.tu.worldyouthday.news.News;
import pl.kielce.tu.worldyouthday.news.NewsDetails;
import pl.kielce.tu.worldyouthday.news.resource.NewsDetailsResource;
import pl.kielce.tu.worldyouthday.news.resource.NewsResource;
import pl.kielce.tu.worldyouthday.utils.converter.ToLanguageResourceConverter;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Łukasz Wesołowski on 01.05.2016.
 */

@Component
public class NewsToResourceConverter extends ToLanguageResourceConverter<News, NewsResource, NewsDetailsResource> {
    @Override
    public NewsResource convert(News news) {
        if (news == null) {
            return null;
        }

        Map<Language, NewsDetailsResource> details = new HashMap<>();

        for (HashMap.Entry<Language, NewsDetails> entry : news.getDetails().entrySet()) {
            details.put(entry.getKey(), convertDetails(news, entry.getKey()));
        }

        return NewsResource.newBuilder()
                .withId(news.getId())
                .withCityId(news.getCity().getId())
                .withDetails(details)
                .withCreationAuthorId(news.getCreationAuthor().getId())
                .withCreationDate(news.getCreationDate())
                .withModificationAuthorId(news.getModificationAuthor() == null ? null : news.getModificationAuthor().getId())
                .withModificationDate(news.getModificationDate())
                .withVersion(news.getVersion())
                .build();
    }

    @Override
    public NewsResource convert(News news, Language language) {
        if (news == null) {
            return null;
        }

        if (language == null) {
            return convert(news);
        }

        return NewsResource.newBuilder()
                .withId(news.getId())
                .withCityId(news.getCity().getId())
                .withDetails(news.getDetails(language).getId().getLanguage(), NewsDetailsResource
                        .newBuilder()
                        .withTitle(news.getDetails(language).getTitle())
                        .withText(news.getDetails(language).getText())
                        .build()
                )
                .withCreationAuthorId(news.getCreationAuthor().getId())
                .withCreationDate(news.getCreationDate())
                .withModificationAuthorId(news.getModificationAuthor() == null ? null : news.getModificationAuthor().getId())
                .withModificationDate(news.getModificationDate())
                .withVersion(news.getVersion())
                .build();
    }

    @Override
    public NewsDetailsResource convertDetails(News news, Language language) {
        return NewsDetailsResource
                .newBuilder()
                .withTitle(news.getDetails(language).getTitle())
                .withText(news.getDetails(language).getText())
                .build();
    }
}
