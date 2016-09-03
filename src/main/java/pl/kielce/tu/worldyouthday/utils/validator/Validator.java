package pl.kielce.tu.worldyouthday.utils.validator;

import pl.kielce.tu.worldyouthday.utils.exception.ValidationException;

public interface Validator<T> {

    void validate(T var1) throws ValidationException;
}
