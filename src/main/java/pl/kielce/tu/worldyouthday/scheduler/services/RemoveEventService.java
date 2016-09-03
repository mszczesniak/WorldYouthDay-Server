package pl.kielce.tu.worldyouthday.scheduler.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.kielce.tu.worldyouthday.scheduler.Event;
import pl.kielce.tu.worldyouthday.scheduler.SchedulerRepository;
import pl.kielce.tu.worldyouthday.scheduler.validator.RemoveEventValidator;
import pl.kielce.tu.worldyouthday.utils.exception.ValidationException;
import pl.kielce.tu.worldyouthday.version.VersionTable;
import pl.kielce.tu.worldyouthday.version.Versionable;

@Service
public class RemoveEventService {

    @Autowired
    private SchedulerRepository schedulerRepository;

    @Autowired
    private RemoveEventValidator removeEventValidator;

    @Versionable(table = VersionTable.SCHEDULER)
    public void removeEvent(String eventId) throws ValidationException {
        removeEventValidator.validate(eventId);
        Event event = schedulerRepository.findOne(eventId);
        schedulerRepository.delete(event);
    }
}
