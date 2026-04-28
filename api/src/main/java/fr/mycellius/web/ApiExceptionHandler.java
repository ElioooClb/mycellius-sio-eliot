package fr.mycellius.web;
import fr.mycellius.domain.exception.PageNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.List;
@RestControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(PageNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handlePageNotFound(PageNotFoundException ex,
                                                               HttpServletRequest request) {
        ApiErrorResponse body = ApiErrorResponse.of(
                "PAGE_NOT_FOUND",
                ex.getMessage(),
                HttpStatus.NOT_FOUND.value(),
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiErrorResponse> handleIllegalArgument(IllegalArgumentException ex,
                                                                  HttpServletRequest request) {
        ApiErrorResponse body = ApiErrorResponse.of(
                "INVALID_INPUT",
                ex.getMessage(),
                HttpStatus.BAD_REQUEST.value(),
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleValidation(MethodArgumentNotValidException ex,
                                                             HttpServletRequest request) {
        List<ApiErrorResponse.FieldError> details = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(f -> new ApiErrorResponse.FieldError(f.getField(), f.getDefaultMessage()))
                .toList();
        ApiErrorResponse body = ApiErrorResponse.validation(
                "Le JSON envoyé est invalide",
                HttpStatus.BAD_REQUEST.value(),
                request.getRequestURI(),
                details
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }
}