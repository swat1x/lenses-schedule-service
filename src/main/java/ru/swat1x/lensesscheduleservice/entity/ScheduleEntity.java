package ru.swat1x.lensesscheduleservice.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

import java.time.LocalTime;
import java.util.UUID;

/**
 * @author swat1x (Vadim Smyshlyaev)
 * Created at 12.12.2025
 */
@Entity

@ToString
@Getter
@Setter
@Accessors(chain = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ScheduleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "schedule_id")
    UUID scheduleId;

    @Column(name = "interval")
    Integer interval;

    @Column(name = "time_zone")
    String timeZone;

    @Column(name = "notification_time")
    LocalTime notificationTime;

    @Column(name = "planned_notification_timestamp")
    Long plannedNotificationTimestamp;

    @Column(name = "last_notification_timestamp")
    Long lastNotificationTimestamp;

    @Column(name = "is_notified")
    Boolean isNotified;

}
