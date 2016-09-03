package pl.kielce.tu.worldyouthday.utils.converter;

import java.util.ArrayList;
import java.util.List;

public abstract class ToResourceConverter<S, T> {

    public abstract T convert(S var1);

    public List<T> convert(List<S> var1) {
        List<T> list = new ArrayList<>();
        var1.stream().forEach(var -> list.add(convert(var)));
        return list;
    }
}
