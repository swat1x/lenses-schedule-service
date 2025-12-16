package ru.swat1x.lensesscheduleservice.service;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.swat1x.lensesscheduleservice.model.ScheduleModel;

import java.time.Duration;
import java.util.List;
import java.util.UUID;

/**
 * @author swat1x (Vadim Smyshlyaev)
 * Created at 11.12.2025
 */
public interface ScheduleService {

    ScheduleModel getScheduleById(UUID scheduleId);

    ScheduleModel callLensesUpdate(UUID scheduleId);

    /**
     * Создание нового расписания исходя из заданных параметров
     *
     * @param scheduleModel данные, подставляемые в новое расписание
     * @return новое расписание
     * */
    @NotNull
    ScheduleModel createNewSchedule(@Nullable ScheduleModel scheduleModel);

    /**
     * Изменение данных существующего расписания
     *
     * @param targetScheduleId ID расписание, в котором будут изменения
     * @param newScheduleInfo данные для изменения в новом расписании
     * @return изменённое расписание
     * */
    @NotNull
    ScheduleModel updateSchedule(@NotNull UUID targetScheduleId, @NotNull ScheduleModel newScheduleInfo);

    /**
     * Поиск подходящих для уведомления расписаний
     *
     * @param plusDelta добавочный временной отрезок, в котором будут искаться расписания
     * */
    List<ScheduleModel> findSuitableSchedules(Duration plusDelta);

    ScheduleModel markScheduleAsNotified(@NotNull ScheduleModel scheduleModel);

}
