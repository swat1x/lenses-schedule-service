package ru.swat1x.lensesscheduleservice.exception.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;

/**
 * @author swat1x (Vadim Smyshlyaev)
 * Created at 29.11.2025
 */
@Getter

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ServiceException extends RuntimeException {

    String message;
    HttpStatus status;

}
