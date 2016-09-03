package pl.kielce.tu.worldyouthday.phone.services;

import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.kielce.tu.worldyouthday.phone.Phone;
import pl.kielce.tu.worldyouthday.phone.PhoneRepository;
import pl.kielce.tu.worldyouthday.utils.exception.ValidationException;
import pl.kielce.tu.worldyouthday.version.VersionTable;
import pl.kielce.tu.worldyouthday.version.Versionable;

@Service
public class RemovePhoneService {

    @Autowired
    protected PhoneRepository phoneRepository;

    @Transactional
    @Versionable(table = VersionTable.PHONES)
    public void removePhone(String id) throws ValidationException {
        if (Strings.isNullOrEmpty(id)) {
            throw new ValidationException("Empty Id");
        }
        Phone phone = phoneRepository.findOne(id);
        if (phone != null) {
            phoneRepository.delete(phone);
        } else {
            throw new ValidationException("Phone not found");
        }
    }


}
