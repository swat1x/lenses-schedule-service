package ru.swat1x.lensesscheduleservice.model;

import jakarta.persistence.Column;
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
 * Created at 11.12.2025
 */
@Getter
@Setter
@ToString
@Accessors(chain = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ScheduleModel {

    UUID scheduleId;

    Integer interval;

    String timeZone;

    LocalTime notificationTime;

    Long plannedNotificationTimestamp;

    Long lastNotificationTimestamp;

    Boolean isNotified;

}
