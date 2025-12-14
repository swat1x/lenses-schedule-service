package ru.swat1x.lensesscheduleservice.service;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.swat1x.lensesscheduleservice.model.ScheduleModel;
import ru.swat1x.lensesscheduleservice.model.UpdateNotificationModel;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.UUID;

/**
 * @author swat1x (Vadim Smyshlyaev)
 * Created at 12.12.2025
 */
@Service

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    KafkaTemplate<UUID, UpdateNotificationModel> notificationsTemplate;
    ScheduleService scheduleService;

    @Override
    public UpdateNotificationModel publishToNotifications(@NonNull ScheduleModel scheduleModel) {
        var timeZone = ZoneId.of(scheduleModel.getTimeZone());
        var zonedDateTime = ZonedDateTime.now(timeZone)
                .toLocalDate()
                .atTime(scheduleModel.getNotificationTime())
                .atZone(timeZone);

        var notificationId = UUID.randomUUID();
        var updateNotificationModel = new UpdateNotificationModel()
                .setNotificationId(notificationId)
                .setSchedule(scheduleModel)
                .setNotificationDateTime(zonedDateTime);

        // Маркируем как уведомленный
        this.scheduleService.markScheduleAsNotified(scheduleModel);

        // Публикация в кафку
        this.notificationsTemplate.sendDefault(notificationId, updateNotificationModel);

        return updateNotificationModel;
    }

}
