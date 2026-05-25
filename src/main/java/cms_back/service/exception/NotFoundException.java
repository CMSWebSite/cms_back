package cms_back.service.exception;

/**
 * 요청한 리소스를 찾지 못한 경우 발생한다 (HTTP 404).
 */
public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}
