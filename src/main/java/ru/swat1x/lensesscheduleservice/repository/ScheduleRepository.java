package ru.swat1x.lensesscheduleservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.swat1x.lensesscheduleservice.entity.ScheduleEntity;

import java.util.List;
import java.util.UUID;

/**
 * @author swat1x (Vadim Smyshlyaev)
 * Created at 12.12.2025
 */
@Repository
public interface ScheduleRepository extends JpaRepository<ScheduleEntity, UUID> {

    @Query("select se from ScheduleEntity se where se.isNotified is true and se.plannedNotificationTimestamp <= ?1")
    List<ScheduleEntity> findSuitableSchedules(long toNotifyUntil);

}
