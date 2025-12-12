package ru.swat1x.lensesscheduleservice.mapper;

import org.mapstruct.*;
import ru.swat1x.lensesscheduleservice.entity.ScheduleEntity;
import ru.swat1x.lensesscheduleservice.model.ScheduleModel;

/**
 * @author swat1x (Vadim Smyshlyaev)
 * Created at 12.12.2025
 */
@Mapper(
        componentModel = "spring",
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValueIterableMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT
)
public interface ScheduleServiceMapper {

    ScheduleModel toModel(ScheduleEntity entity);

    ScheduleModel mapModel(@MappingTarget ScheduleModel target, ScheduleModel model);

    @Mapping(target = "scheduleId", ignore = true)
    ScheduleEntity mapEntity(ScheduleModel model);

    @Mapping(target = "scheduleId", ignore = true)
    ScheduleEntity mapEntity(@MappingTarget ScheduleEntity entity, ScheduleModel model);


}
