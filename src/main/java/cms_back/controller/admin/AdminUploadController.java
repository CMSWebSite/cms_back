package cms_back.controller.admin;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import cms_back.dto.admin.UploadDtos;
import cms_back.service.admin.FileStorageService;

import java.io.IOException;

@RestController
@RequestMapping("/api/admin/uploads")
public class AdminUploadController {

    private final FileStorageService fileStorageService;

    public AdminUploadController(FileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
    }

    @PostMapping
    public UploadDtos.Response upload(@RequestPart("file") MultipartFile file) throws IOException {
        return fileStorageService.store(file);
    }
}
