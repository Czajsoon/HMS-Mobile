package hotel.management.system.be.application.exception.handler;

import hotel.management.system.be.application.exception.BadRequestException;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

/**
 * Kontroler wyłapujący wyjątki podczas działania aplikacji.
 */
@ControllerAdvice
@AllArgsConstructor
public class ExceptionControllerAdvice {

    /**
     * Handler dla BadRequestException.class.
     *
     * @param ex wyłapany wyjątek
     * @return odpowiedź HTTP
     */
    @ResponseBody
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<String> badRequestExceptionHandler(BadRequestException ex) {
        return new ResponseEntity<>(
                ex.getMessage(),
                BAD_REQUEST);
    }
}
