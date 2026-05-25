package az.shopery.blog_ms.controller;

import az.shopery.blog_ms.model.dto.request.BlogRequestDto;
import az.shopery.blog_ms.model.dto.response.BlogResponseDto;
import az.shopery.blog_ms.model.dto.shared.SuccessResponse;
import az.shopery.blog_ms.service.BlogLikeService;
import az.shopery.blog_ms.service.BlogService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users/me/blogs")
public class BlogController {

    private final BlogService blogService;
    private final BlogLikeService blogLikeService;

    @GetMapping
    public ResponseEntity<SuccessResponse<Page<BlogResponseDto>>> getMyBlogs(@RequestParam String email, Pageable pageable) {
        return ResponseEntity.ok(blogService.getMyBlogs(email, pageable));
    }

    @GetMapping("/{blogId}")
    public ResponseEntity<SuccessResponse<BlogResponseDto>> getMyBlog(@RequestParam String email, @PathVariable String blogId) {
        return ResponseEntity.ok(blogService.getMyBlog(email, blogId));
    }

    @PostMapping
    public ResponseEntity<SuccessResponse<BlogResponseDto>> addMyBlog(@RequestParam String email, @Valid @RequestBody BlogRequestDto blogRequestDto) {
        return ResponseEntity.ok(blogService.addMyBlog(email, blogRequestDto));
    }

    @PostMapping(value = "/{blogId}/image", consumes = {"multipart/form-data"})
    public ResponseEntity<SuccessResponse<String>> uploadBlogImage(@RequestParam String email, @PathVariable String blogId, @RequestParam("image") MultipartFile imageFile){
        return ResponseEntity.ok(blogService.updateBlogImage(email, blogId, imageFile));
    }

    @DeleteMapping("/{blogId}/image")
    public ResponseEntity<SuccessResponse<String>> deleteBlogImage(@RequestParam String email, @PathVariable String blogId) {
        return ResponseEntity.ok(blogService.deleteBlogImage(email, blogId));
    }

    @DeleteMapping("/{blogId}")
    public ResponseEntity<SuccessResponse<Void>> deleteMyBlog(@RequestParam String email, @PathVariable String blogId) {
        return ResponseEntity.ok(blogService.deleteMyBlog(email, blogId));
    }

    @PutMapping("/{blogId}")
    public ResponseEntity<SuccessResponse<BlogResponseDto>> updateMyBlog(@RequestParam String email, @RequestBody @Valid BlogRequestDto blogRequestDto, @PathVariable String blogId) {
        return ResponseEntity.ok(blogService.updateMyBlog(email, blogRequestDto, blogId));
    }

    @GetMapping("/like")
    public ResponseEntity<SuccessResponse<Page<BlogResponseDto>>> getLikedBlogs(@RequestParam String email, Pageable pageable) {
        return ResponseEntity.ok(blogLikeService.getLikedBlogs(email, pageable));
    }

    @PostMapping("/{blogId}/like")
    public ResponseEntity<SuccessResponse<Void>> likeBlog(@RequestParam String email, @PathVariable String blogId) {
        return ResponseEntity.ok(blogLikeService.toggleBlogLike(email, blogId));
    }

    @GetMapping("/save")
    public ResponseEntity<SuccessResponse<Page<BlogResponseDto>>> getSavedBlogs(@RequestParam String email, Pageable pageable){
        return ResponseEntity.ok(blogService.getSavedBlogs(email, pageable));
    }

    @PostMapping("/{blogId}/save")
    public ResponseEntity<SuccessResponse<Void>> saveBlog(@RequestParam String email, @PathVariable String blogId){
        return ResponseEntity.ok(blogService.toggleBlogSave(email, blogId));
    }

    @GetMapping("/archive")
    public ResponseEntity<SuccessResponse<Page<BlogResponseDto>>> getArchivedBlogs(@RequestParam String email, Pageable pageable) {
        return ResponseEntity.ok(blogService.getArchivedBlogs(email, pageable));
    }

    @PostMapping("/{blogId}/archive")
    public ResponseEntity<SuccessResponse<Void>> archiveBlog(@RequestParam String email, @PathVariable String blogId) {
        return ResponseEntity.ok(blogService.toggleBlogArchive(email, blogId));
    }
}
