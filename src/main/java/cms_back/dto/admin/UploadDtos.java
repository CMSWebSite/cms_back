package cms_back.dto.admin;

public final class UploadDtos {

    private UploadDtos() {}

    /** 파일 업로드 성공 응답. */
    public record Response(
            String url,
            String originalName,
            long size,
            String mimeType
    ) {}
}
