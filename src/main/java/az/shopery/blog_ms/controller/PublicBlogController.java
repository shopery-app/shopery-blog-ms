package az.shopery.blog_ms.controller;

import az.shopery.blog_ms.model.dto.response.BlogResponseDto;
import az.shopery.blog_ms.model.dto.shared.SuccessResponse;
import az.shopery.blog_ms.service.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/blogs")
public class PublicBlogController {

    private final BlogService blogService;

    @GetMapping
    public ResponseEntity<SuccessResponse<Page<BlogResponseDto>>> getAllBlogs(Pageable pageable) {
        return ResponseEntity.ok(blogService.getAllBlogs(pageable));
    }

    @GetMapping("/search")
    public ResponseEntity<SuccessResponse<Page<BlogResponseDto>>> search(@RequestParam String query, Pageable pageable) {
        return ResponseEntity.ok(blogService.search(query, pageable));
    }
}
