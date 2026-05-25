package cms_back.service.exception;

/**
 * 비즈니스 규칙 위반에 사용한다 (HTTP 400/409 등 도메인에 따라 매핑).
 * 예: 마지막 ADMIN의 권한 강등 차단, 본인 권한 강등 차단 등.
 */
public class BusinessRuleException extends RuntimeException {
    private final String code;

    public BusinessRuleException(String code, String message) {
        super(message);
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
