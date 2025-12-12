package ru.swat1x.lensesscheduleservice.exception.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

/**
 * @author swat1x (Vadim Smyshlyaev)
 * Created at 29.11.2025
 */
@Getter
@Setter
@Accessors(chain = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ExceptionResponseModel {

    int code;

    String status;

    String message;

}
