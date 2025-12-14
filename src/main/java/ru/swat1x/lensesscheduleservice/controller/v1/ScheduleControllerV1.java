package ru.swat1x.lensesscheduleservice.controller.v1;

import org.springframework.web.bind.annotation.*;
import ru.swat1x.lensesscheduleservice.model.ScheduleModel;

import java.util.UUID;

/**
 * @author swat1x (Vadim Smyshlyaev)
 * Created at 12.12.2025
 */
@RequestMapping("/v1/schedule")
public interface ScheduleControllerV1 {

    @GetMapping("/getScheduleById")
    ScheduleModel getScheduleById(@RequestParam(name = "scheduleId") UUID scheduleId);

    @PostMapping("/createNewSchedule")
    ScheduleModel createNewSchedule(@RequestBody(required = false) ScheduleModel scheduleModel);

    @PostMapping("/updateSchedule")
    ScheduleModel updateSchedule(@RequestBody ScheduleModel scheduleModel);

    @PostMapping("/callLensesUpdate")
    ScheduleModel callLensesUpdate(@RequestParam(name = "scheduleId") UUID scheduleId);

}
