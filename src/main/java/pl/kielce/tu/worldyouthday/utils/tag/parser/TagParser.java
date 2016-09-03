package pl.kielce.tu.worldyouthday.utils.tag.parser;

import pl.kielce.tu.worldyouthday.utils.tag.Tag;

import java.util.List;

/**
 * Created by Łukasz Wesołowski on 04.05.2016.
 */
public interface TagParser<T extends Tag> {
    List<T> parse(String text);
}
