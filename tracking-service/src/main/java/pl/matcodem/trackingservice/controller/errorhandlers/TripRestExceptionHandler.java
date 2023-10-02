package pl.matcodem.trackingservice.controller.errorhandlers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pl.matcodem.trackingservice.controller.TripRestController;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.NoSuchElementException;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice(basePackageClasses = TripRestController.class)
public class TripRestExceptionHandler {

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<Object> handleSqlIntegrityException(SQLIntegrityConstraintViolationException ex) {
        return createResponseEntity(new ErrorResponse(
                HttpStatus.BAD_REQUEST,
                "Unable to find trip: " + ex.getMessage()));
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<Object> handleNoSuchElementException(HttpServletRequest req) {
        return createResponseEntity(new ErrorResponse(
                HttpStatus.NOT_FOUND,
                "The row for trip is not existent: " + req.getRequestURI()));
    }

    private ResponseEntity<Object> createResponseEntity(ErrorResponse errorResponse) {
        return new ResponseEntity<>(errorResponse, errorResponse.getStatus());
    }
}
