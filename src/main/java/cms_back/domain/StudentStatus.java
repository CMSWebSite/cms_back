package cms_back.domain;

/**
 * Student 상태. Phase C에서 이름 정리:
 *  - 이전: ACTIVE / GRADUATED / LEAVE
 *  - 변경: ACTIVE / ALUMNI   / INACTIVE
 *
 * 기존 데이터(GRADUATED, LEAVE)는 {@code StudentStatusMigrationRunner}가
 * 부팅 시 UPDATE 문으로 일괄 변환한다.
 */
public enum StudentStatus {
    ACTIVE,    // 재학
    ALUMNI,    // 졸업 (구 GRADUATED)
    INACTIVE   // 비활성/휴학 (구 LEAVE)
}
