package az.shopery.blog_ms.util.aws;

import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {
    String store(MultipartFile multipartFile);
    void delete(String fileKey);
}
