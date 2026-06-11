package cms_back.service.admin;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * 현재 인증된 관리자의 식별 정보(이메일)를 createdBy/updatedBy 등에 기록하기 위한 헬퍼.
 * JwtAuthenticationFilter가 SecurityContext의 principal 이메일(username)을 채워 두는 것을 전제로 한다.
 */
@Component
public class AdminContext {

    /** 현재 요청을 수행 중인 관리자의 이메일. 없으면 "system". */
    public String currentActor() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || auth.getName() == null) {
            return "system";
        }
        return auth.getName();
    }
}
