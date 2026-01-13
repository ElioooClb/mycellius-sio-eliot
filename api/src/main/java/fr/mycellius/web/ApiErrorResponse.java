package fr.mycellius.web;
import java.time.Instant;
public record ApiErrorResponse(
        String error,
        String message,
        int status,
        String path,
        String timestamp
) {
    public static ApiErrorResponse of(String error, String message, int status, String path) {
        return new ApiErrorResponse(
                error,
                message,
                status,
                path,
                Instant.now().toString()
        );
    }
}