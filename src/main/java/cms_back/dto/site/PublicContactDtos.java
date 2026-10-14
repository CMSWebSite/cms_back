package cms_back.dto.site;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public final class PublicContactDtos {

    private PublicContactDtos() {}

    /** 비로그인 사용자가 Contact us 에서 제출하는 지원 정보. */
    public record CreateRequest(
            @NotBlank(message = "이름은 필수입니다.") @Size(max = 100) String name,
            @NotBlank(message = "이메일 또는 전화번호는 필수입니다.") @Size(max = 200) String contact,
            @NotBlank(message = "전공은 필수입니다.") @Size(max = 200) String major,
            String introduce
    ) {}

    /** 제출 성공 응답 (관리자 열람용 상세는 노출하지 않는다). */
    public record CreateResponse(Long id) {}
}
