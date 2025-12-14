package ru.swat1x.lensesscheduleservice.service;

import jakarta.transaction.Transactional;
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

import java.time.Duration;
import java.time.LocalTime;
import java.util.List;
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

    private ScheduleEntity findScheduleById(@NotNull UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new ScheduleNotFoundException(id));
    }

    private ScheduleEntity createNewEntity(@Nullable ScheduleModel scheduleModel) {
        var scheduleEntity = new ScheduleEntity();
        scheduleEntity.setInterval(5);
        scheduleEntity.setTimeZone("Europe/Moscow");
        scheduleEntity.setNotificationTime(LocalTime.of(15, 0));
        scheduleEntity.setLastNotificationTimestamp(0L);
        scheduleEntity.setPlannedNotificationTimestamp(System.currentTimeMillis() + Duration.ofDays(5).toMillis());
        scheduleEntity.setIsNotified(false);

        if (scheduleModel != null) {
            scheduleEntity = this.mapper.mapEntity(scheduleEntity, scheduleModel);
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
        scheduleEntity = this.repository.save(scheduleEntity);
        var newModel = this.mapper.toModel(scheduleEntity);
//        this.notificationService.publishToNotifications(newModel);
        return newModel;
    }

    @Override
    @Transactional
    public @NotNull ScheduleModel updateSchedule(@NotNull UUID targetScheduleId, @NotNull ScheduleModel newScheduleInfo) {
        var scheduleEntity = findScheduleById(targetScheduleId);
        scheduleEntity = this.mapper.mapEntity(scheduleEntity, newScheduleInfo);
        scheduleEntity = this.repository.save(scheduleEntity);
        return this.mapper.toModel(scheduleEntity);
    }

    @Override
    public List<ScheduleModel> findSuitableSchedules(Duration plusDelta) {
        var toNotifyUntil = System.currentTimeMillis() + plusDelta.toMillis();
        var suitableSchedules = this.repository.findSuitableSchedules(toNotifyUntil);
        return this.mapper.toModel(suitableSchedules);
    }

    @Override
    @Transactional
    public ScheduleModel markScheduleAsNotified(@NotNull ScheduleModel scheduleModel) {
        var scheduleEntity = findScheduleById(scheduleModel.getScheduleId());
        scheduleEntity.setIsNotified(true);
        scheduleEntity = this.repository.save(scheduleEntity);
        return this.mapper.toModel(scheduleEntity);
    }

}
