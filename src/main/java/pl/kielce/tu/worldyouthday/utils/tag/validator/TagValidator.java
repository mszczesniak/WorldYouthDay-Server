package pl.kielce.tu.worldyouthday.utils.tag.validator;

import pl.kielce.tu.worldyouthday.utils.exception.ValidationException;

/**
 * Created by Łukasz Wesołowski on 04.05.2016.
 */
public interface TagValidator {
    void validate(String text) throws ValidationException;
}
