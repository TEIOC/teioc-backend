package dev.astranfalio.teioc.service;

import dev.astranfalio.teioc.entity.InternEntity;
import dev.astranfalio.teioc.repository.InternRepository;
import jakarta.validation.Validator;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

@Service
public class InternDataService extends AbstractDataService<InternEntity, Integer, InternRepository> {

    public InternDataService(InternRepository repository, Validator validator) {
        super(repository, validator);
    }

    @Override
    public InternEntity findById(Integer integer) {
        InternEntity internEntity = super.findById(integer);
        refreshInternStatus(internEntity);
        return internEntity;
    }

    public InternEntity findByEmail(String internEmail) {
        InternEntity internEntity = repository.findByEmail(internEmail);
        if (internEntity == null) {
            throw new ResourceNotFoundException("Intern not found with email input. Email:" + internEmail);
        }
        return internEntity;
    }

    public void reviseLastConnection(String internEmail) {
        InternEntity internEntity = findByEmail(internEmail);
        LocalDate localDate = getLocalDate();
        java.sql.Date currentDate = java.sql.Date.valueOf(localDate);
        if (!isInternStatusExpired(internEntity)) {
            internEntity.setLastConnection(currentDate);
        }
    }

    public void refreshInternStatus(InternEntity internEntity) {
        if (isInternStatusExpired(internEntity)) {
            internEntity.setStatus(false);
        }
    }

    public boolean isInternStatusExpired(InternEntity internEntity) {
        if (internEntity.isStatus()) {
            LocalDate localDate = getLocalDate();
            if (internEntity.getLastConnection() != null) {
                LocalDate lastConnectionDate = internEntity.getLastConnection().toLocalDate();
                long monthsElapsed = ChronoUnit.MONTHS.between(lastConnectionDate, localDate);
                return monthsElapsed > 3;
            } else {
                throw new ResourceNotFoundException(
                        "Last connection field of  userId:" + internEntity.getId() + " is null.");
            }
        } else {
            return true;
        }
    }

    private static LocalDate getLocalDate() {
        ZoneId zoneId = ZoneId.systemDefault();
        return LocalDate.now(zoneId);
    }
}
