package pl.kielce.tu.worldyouthday.utils.converter;

import pl.kielce.tu.worldyouthday.language.Language;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class ToLanguageResourceConverter<Entity, Resource, DetailsResource> {

    public abstract Resource convert(Entity var1);

    public abstract Resource convert(Entity var1, Language language);

    public abstract DetailsResource convertDetails(Entity var1, Language language);

    public List<Resource> convert(List<Entity> var1, Language language) {
        List<Resource> list = new ArrayList<>();
        var1.stream().forEach(var -> list.add(convert(var, language)));
        return list;
    }

    public List<Resource> convert(List<Entity> entities) {
        return entities.stream().map(a -> convert(a)).collect(Collectors.toList());
    }
}
