package az.shopery.blog_ms.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@FeignClient(name = "aws-ms", url = "${feign.client.config.aws-ms.url}")
public interface AwsClient {

    @PutMapping(value = "/api/v1/aws/update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<String> updateFile(@RequestParam String oldKey, @RequestPart("file") MultipartFile newFile);

    @DeleteMapping("/api/v1/aws")
    ResponseEntity<Void> deleteFile(@RequestParam(required = false) String fileKey);

    @GetMapping("/api/v1/aws/presigned-url")
    ResponseEntity<String> getPresignedUrl(@RequestParam(required = false) String fileKey);
}
