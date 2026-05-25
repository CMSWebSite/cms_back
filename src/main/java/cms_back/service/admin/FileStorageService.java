package cms_back.service.admin;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import cms_back.dto.admin.UploadDtos;
import cms_back.service.exception.BusinessRuleException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Set;
import java.util.UUID;

/**
 * 업로드 파일을 로컬 디렉터리에 저장하고 공개 URL을 반환한다.
 * 운영 환경에서는 외부 스토리지(예: S3) 어댑터로 교체할 것.
 */
@Service
public class FileStorageService {

    private static final Set<String> IMAGE_MIMES =
            Set.of("image/jpeg", "image/png", "image/webp", "image/gif");
    private static final Set<String> PDF_MIMES =
            Set.of("application/pdf");

    private static final long MAX_IMAGE_BYTES = 5L * 1024 * 1024;       //  5MB
    private static final long MAX_PDF_BYTES   = 30L * 1024 * 1024;      // 30MB

    private final Path storageRoot;
    private final String publicBaseUrl;

    public FileStorageService(
            @Value("${app.upload.dir:./uploads}") String uploadDir,
            @Value("${app.upload.public-base-url:/uploads}") String publicBaseUrl
    ) throws IOException {
        this.storageRoot = Paths.get(uploadDir).toAbsolutePath().normalize();
        this.publicBaseUrl = publicBaseUrl.endsWith("/")
                ? publicBaseUrl.substring(0, publicBaseUrl.length() - 1)
                : publicBaseUrl;
        Files.createDirectories(this.storageRoot);
    }

    public UploadDtos.Response store(MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) {
            throw new BusinessRuleException("UPLOAD_EMPTY", "업로드할 파일을 선택해주세요.");
        }

        String mime = file.getContentType() == null ? "" : file.getContentType().toLowerCase();
        long size = file.getSize();

        if (IMAGE_MIMES.contains(mime)) {
            if (size > MAX_IMAGE_BYTES) {
                throw new BusinessRuleException("UPLOAD_TOO_LARGE",
                        "이미지는 5MB 이하만 업로드할 수 있습니다.");
            }
        } else if (PDF_MIMES.contains(mime)) {
            if (size > MAX_PDF_BYTES) {
                throw new BusinessRuleException("UPLOAD_TOO_LARGE",
                        "PDF는 30MB 이하만 업로드할 수 있습니다.");
            }
        } else {
            throw new BusinessRuleException("UPLOAD_TYPE_FORBIDDEN",
                    "허용되지 않은 파일 형식입니다. 이미지(jpg/png/webp/gif) 또는 PDF만 가능합니다.");
        }

        String originalName = file.getOriginalFilename() == null
                ? "file"
                : Path.of(file.getOriginalFilename()).getFileName().toString();
        String ext = extensionFromMime(mime);
        String storedName = UUID.randomUUID().toString().replace("-", "") + ext;

        Path target = storageRoot.resolve(storedName).normalize();
        if (!target.startsWith(storageRoot)) {
            throw new BusinessRuleException("UPLOAD_PATH_INVALID", "잘못된 파일 경로입니다.");
        }

        try (var in = file.getInputStream()) {
            Files.copy(in, target, StandardCopyOption.REPLACE_EXISTING);
        }

        String url = publicBaseUrl + "/" + storedName;
        return new UploadDtos.Response(url, originalName, size, mime);
    }

    private String extensionFromMime(String mime) {
        return switch (mime) {
            case "image/jpeg" -> ".jpg";
            case "image/png"  -> ".png";
            case "image/webp" -> ".webp";
            case "image/gif"  -> ".gif";
            case "application/pdf" -> ".pdf";
            default -> "";
        };
    }
}
