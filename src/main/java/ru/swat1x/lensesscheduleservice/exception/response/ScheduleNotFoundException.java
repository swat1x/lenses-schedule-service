package ru.swat1x.lensesscheduleservice.exception.response;

import org.springframework.http.HttpStatus;

import java.util.UUID;

/**
 * @author swat1x (Vadim Smyshlyaev)
 * Created at 29.11.2025
 */
public class ScheduleNotFoundException extends ServiceException {

    public ScheduleNotFoundException(UUID scheduleId) {
        super("Cannot find schedule with id: " + scheduleId, HttpStatus.NOT_FOUND);
    }

}
