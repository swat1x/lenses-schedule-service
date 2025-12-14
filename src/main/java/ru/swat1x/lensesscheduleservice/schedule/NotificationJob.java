package ru.swat1x.lensesscheduleservice.schedule;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.stereotype.Component;
import ru.swat1x.lensesscheduleservice.model.ScheduleModel;
import ru.swat1x.lensesscheduleservice.service.NotificationService;
import ru.swat1x.lensesscheduleservice.service.ScheduleService;

import java.time.Duration;

@Component
@DisallowConcurrentExecution

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class NotificationJob implements Job {

    ScheduleService scheduleService;
    NotificationService notificationService;

    @Override
    public void execute(JobExecutionContext context) {
        var suitableSchedules = this.scheduleService.findSuitableSchedules(Duration.ofMinutes(5));

        for (ScheduleModel suitableSchedule : suitableSchedules) {
            this.notificationService.publishToNotifications(suitableSchedule);
        }
    }

}