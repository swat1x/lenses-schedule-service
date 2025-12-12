package ru.swat1x.lensesscheduleservice.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.UUID;

/**
 * @author swat1x (Vadim Smyshlyaev)
 * Created at 11.12.2025
 */
@Getter
@Setter
@Accessors(chain = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateNotificationModel {

    UUID notificationId;

    ZonedDateTime notificationDateTime;

    ScheduleModel schedule;

}
