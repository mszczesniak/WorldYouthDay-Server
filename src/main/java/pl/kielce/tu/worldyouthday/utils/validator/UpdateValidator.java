package pl.kielce.tu.worldyouthday.utils.validator;

import pl.kielce.tu.worldyouthday.utils.exception.ValidationException;

public interface UpdateValidator<T> {

    void validate(String id, T var1) throws ValidationException;
}
