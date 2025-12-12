package ru.swat1x.lensesscheduleservice.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Service;
import ru.swat1x.lensesscheduleservice.entity.ScheduleEntity;
import ru.swat1x.lensesscheduleservice.exception.response.ScheduleNotFoundException;
import ru.swat1x.lensesscheduleservice.mapper.ScheduleServiceMapper;
import ru.swat1x.lensesscheduleservice.model.ScheduleModel;
import ru.swat1x.lensesscheduleservice.repository.ScheduleRepository;

import java.time.LocalTime;
import java.util.UUID;

/**
 * @author swat1x (Vadim Smyshlyaev)
 * Created at 12.12.2025
 */
@Service

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {

    ScheduleRepository repository;
    ScheduleServiceMapper mapper;
    NotificationService notificationService;

    private ScheduleEntity findScheduleById(@NotNull UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new ScheduleNotFoundException(id));
    }

    private ScheduleEntity createNewEntity(@Nullable ScheduleModel scheduleModel) {
        var scheduleEntity = new ScheduleEntity();
        scheduleEntity.setInterval(5);
        scheduleEntity.setTimeZone("Europe/Moscow");
        scheduleEntity.setNotificationTime(LocalTime.of(15, 0));
        scheduleEntity.setLastUpdateInstant(System.currentTimeMillis());
        scheduleEntity.setLastNotificationInstant(System.currentTimeMillis());
        scheduleEntity.setIsNotified(false);

        if (scheduleModel != null) {
            scheduleEntity = mapper.mapEntity(scheduleEntity, scheduleModel);
        }

        return scheduleEntity;
    }

    @Override
    public ScheduleModel getScheduleById(UUID scheduleId) {
        return mapper.toModel(findScheduleById(scheduleId));
    }

    @Override
    public @NotNull ScheduleModel createNewSchedule(@Nullable ScheduleModel scheduleModel) {
        var scheduleEntity = createNewEntity(scheduleModel);
        scheduleEntity = repository.save(scheduleEntity);
        var newModel = mapper.toModel(scheduleEntity);
        notificationService.publishToNotifications(newModel);
        return newModel;
    }

    @Override
    public @NotNull ScheduleModel updateSchedule(@NotNull UUID targetScheduleId, @NotNull ScheduleModel newScheduleInfo) {
        var scheduleEntity = findScheduleById(targetScheduleId);
        scheduleEntity = mapper.mapEntity(scheduleEntity, newScheduleInfo);
        scheduleEntity = repository.save(scheduleEntity);
        return mapper.toModel(scheduleEntity);
    }
}
