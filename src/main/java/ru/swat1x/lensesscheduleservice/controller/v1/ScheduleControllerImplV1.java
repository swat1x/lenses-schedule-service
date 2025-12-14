package ru.swat1x.lensesscheduleservice.controller.v1;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.RestController;
import ru.swat1x.lensesscheduleservice.model.ScheduleModel;
import ru.swat1x.lensesscheduleservice.service.NotificationService;
import ru.swat1x.lensesscheduleservice.service.ScheduleService;

import java.util.UUID;

/**
 * @author swat1x (Vadim Smyshlyaev)
 * Created at 12.12.2025
 */
@RestController

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ScheduleControllerImplV1 implements ScheduleControllerV1 {

    ScheduleService service;
    NotificationService notificationService;

    @Override
    public ScheduleModel getScheduleById(UUID scheduleId) {
        return service.getScheduleById(scheduleId);
    }

    @Override
    public ScheduleModel createNewSchedule(ScheduleModel scheduleModel) {
        var schedule =  service.createNewSchedule(scheduleModel);
        this.notificationService.publishToNotifications(schedule);
        return schedule;
    }

    @Override
    public ScheduleModel updateSchedule(ScheduleModel scheduleModel) {
        return service.updateSchedule(scheduleModel.getScheduleId(), scheduleModel);
    }

    @Override
    public ScheduleModel callLensesUpdate(UUID scheduleId) {
        return null;
    }

}
