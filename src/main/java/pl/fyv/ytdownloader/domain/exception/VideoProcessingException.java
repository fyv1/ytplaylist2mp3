package pl.fyv.ytdownloader.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Video processing exception")
public class VideoProcessingException extends RuntimeException {
}
