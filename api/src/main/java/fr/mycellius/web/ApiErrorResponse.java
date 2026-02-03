package fr.mycellius.web;
import java.time.Instant;
import java.util.List;
public record ApiErrorResponse(
        String error,
        String message,
        int status,
        String path,
        String timestamp,
        List<FieldError> details
) {
    public record FieldError(String field, String message) {}
    public static ApiErrorResponse of(String error, String message, int status, String path) {
        return new ApiErrorResponse(
                error,
                message,
                status,
                path,
                Instant.now().toString(),
                List.of()
        );
    }
    public static ApiErrorResponse validation(String message, int status, String path, List<FieldError> details) {
        return new ApiErrorResponse(
                "VALIDATION_ERROR",
                message,
                status,
                path,
                Instant.now().toString(),
                details == null ? List.of() : details
        );
    }
}