package ru.swat1x.lensesscheduleservice.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.swat1x.lensesscheduleservice.exception.response.ExceptionResponseModel;
import ru.swat1x.lensesscheduleservice.exception.response.ServiceException;

/**
 * @author swat1x (Vadim Smyshlyaev)
 * Created at 29.11.2025
 */
@Slf4j
@ControllerAdvice
public class ServiceExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({Throwable.class})
    public ResponseEntity<ExceptionResponseModel> handleStandardException(Throwable throwable) {
        log.error("Handling service exception", throwable);
        var serviceException = new ServiceException(
                throwable.getClass().getName() + ": " + throwable.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
        return buildResponse(serviceException);
    }

    @ExceptionHandler({ServiceException.class})
    public ResponseEntity<ExceptionResponseModel> handleServiceException(ServiceException serviceException) {
        return buildResponse(serviceException);
    }

    private ResponseEntity<ExceptionResponseModel> buildResponse(ServiceException serviceException) {
        var model = new ExceptionResponseModel()
                .setCode(serviceException.getStatus().value())
                .setStatus(serviceException.getStatus().name())
                .setMessage(serviceException.getMessage());
        return new ResponseEntity<>(model, serviceException.getStatus());
    }

}
