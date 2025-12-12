package ru.swat1x.lensesscheduleservice.service;

import lombok.NonNull;
import ru.swat1x.lensesscheduleservice.model.ScheduleModel;
import ru.swat1x.lensesscheduleservice.model.UpdateNotificationModel;

/**
 * @author swat1x (Vadim Smyshlyaev)
 * Created at 11.12.2025
 */
public interface NotificationService {

    /**
     * Отправить расписание для уведомления пользователя
     *
     * @return отправленная на уведомление модель
     * */
    UpdateNotificationModel publishToNotifications(@NonNull ScheduleModel scheduleModel);

}
