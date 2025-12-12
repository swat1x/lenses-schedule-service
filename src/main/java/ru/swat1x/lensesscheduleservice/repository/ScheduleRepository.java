package ru.swat1x.lensesscheduleservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.swat1x.lensesscheduleservice.entity.ScheduleEntity;

import java.util.UUID;

/**
 * @author swat1x (Vadim Smyshlyaev)
 * Created at 12.12.2025
 */
@Repository
public interface ScheduleRepository extends JpaRepository<ScheduleEntity, UUID> {
}
