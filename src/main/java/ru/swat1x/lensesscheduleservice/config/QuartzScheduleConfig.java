package ru.swat1x.lensesscheduleservice.config;

import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.swat1x.lensesscheduleservice.schedule.NotificationJob;

/**
 * @author swat1x (Vadim Smyshlyaev)
 * Created at 14.12.2025
 */
@Configuration
public class QuartzScheduleConfig {

    @Bean("notify-schedule-job")
    public JobDetail notifyScheduleJob() {
        return JobBuilder.newJob(NotificationJob.class)
                .withIdentity("notify-schedule-job")
                .storeDurably()
                .build();
    }

    @Bean("notify-startup-trigger")
    public Trigger notifyStartupTrigger() {
        return TriggerBuilder.newTrigger()
                .forJob(notifyScheduleJob())
                .withIdentity("notify-startup-job")
                .startNow()
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withRepeatCount(0))       // выполнить один раз
                .build();
    }

    @Bean("notify-schedule-trigger")
    public Trigger notifyScheduleTrigger() {
        return TriggerBuilder.newTrigger()
                .forJob(notifyScheduleJob())
                .withIdentity("notify-schedule-job")
                .withSchedule(CronScheduleBuilder.cronSchedule("0 */10 * * * ?"))
                .build();
    }

}