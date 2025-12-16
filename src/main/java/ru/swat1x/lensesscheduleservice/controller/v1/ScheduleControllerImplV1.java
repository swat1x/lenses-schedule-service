package ru.swat1x.lensesscheduleservice.controller.v1;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    ScheduleService scheduleService;
    NotificationService notificationService;

    @PostMapping("/publishSchedule")
    public ScheduleModel publishSchedule(@RequestParam(name = "scheduleId") UUID scheduleId) {
        var schedule = scheduleService.getScheduleById(scheduleId);
        this.notificationService.publishToNotifications(schedule);
        return schedule;
    }

    @Override
    public ScheduleModel getScheduleById(UUID scheduleId) {
        return scheduleService.getScheduleById(scheduleId);
    }

    @Override
    public ScheduleModel createNewSchedule(ScheduleModel scheduleModel) {
        var schedule =  scheduleService.createNewSchedule(scheduleModel);
        this.notificationService.publishToNotifications(schedule);
        return schedule;
    }

    @Override
    public ScheduleModel updateSchedule(ScheduleModel scheduleModel) {
        return scheduleService.updateSchedule(scheduleModel.getScheduleId(), scheduleModel);
    }

    @Override
    public ScheduleModel callLensesUpdate(UUID scheduleId) {
        return scheduleService.callLensesUpdate(scheduleId);
    }

}
