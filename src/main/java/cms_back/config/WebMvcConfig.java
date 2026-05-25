package cms_back.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Paths;

/**
 * 업로드된 파일을 정적 자원으로 노출한다.
 * 기본: 로컬 디렉터리 ./uploads 를 /uploads/** 로 접근 가능하게 한다.
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private final String uploadDir;
    private final String publicBaseUrl;

    public WebMvcConfig(
            @Value("${app.upload.dir:./uploads}") String uploadDir,
            @Value("${app.upload.public-base-url:/uploads}") String publicBaseUrl
    ) {
        this.uploadDir = uploadDir;
        this.publicBaseUrl = publicBaseUrl;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String absolute = Paths.get(uploadDir).toAbsolutePath().normalize().toUri().toString();
        String pattern = publicBaseUrl.endsWith("/") ? publicBaseUrl + "**" : publicBaseUrl + "/**";
        registry.addResourceHandler(pattern).addResourceLocations(absolute);
    }
}
