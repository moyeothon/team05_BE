package projects.moyethon.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class DiaryExceptionHandler {

    @ExceptionHandler(CustomDiaryException.class)
    protected ResponseEntity<ErrorResponseEntity> handleCustomDiaryException(CustomDiaryException e) {
        return ErrorResponseEntity.errorResponseEntity(e.getErrorCode());
    }
}
