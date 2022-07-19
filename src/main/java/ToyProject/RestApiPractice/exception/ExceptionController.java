package ToyProject.RestApiPractice.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@Slf4j
@RestControllerAdvice
public class ExceptionController {

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResult fieldErrorHandler(MethodArgumentNotValidException e) {

        List<FieldError> fieldErrors = e.getFieldErrors();

        ErrorResult errorResult = ErrorResult.builder()
                .code("404")
                .message("잘못된 요청입니다.")
                .build();

        for (FieldError fieldError : fieldErrors) {
            errorResult.fieldAdd(fieldError.getField(), fieldError.getDefaultMessage());
        }

        return errorResult;
    }
}
