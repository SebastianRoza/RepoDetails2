package sebastianroza.com.example.RepoDetails.exceptionHandler;

import feign.FeignException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
@ControllerAdvice
public class GeneralExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<String> repoExceptionErrorResponse(FeignException e) {
        return switch (e.status()) {
            case 301 -> ResponseEntity.status(301).body("Repo moved permanently");
            case 403 -> ResponseEntity.status(403).body("Forbidden");
            case 404 -> ResponseEntity.status(404).body("Repo not found");
            default -> ResponseEntity.status(e.status()).body("Unknown error occurred");
        };
    }
}
