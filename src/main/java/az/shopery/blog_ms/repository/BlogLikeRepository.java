package az.shopery.blog_ms.repository;

import az.shopery.blog_ms.model.entity.BlogEntity;
import az.shopery.blog_ms.model.entity.BlogLikeEntity;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogLikeRepository extends JpaRepository<BlogLikeEntity, UUID> {
    void deleteByUserEmailAndBlog(String userEmail, BlogEntity blog);
    boolean existsByUserEmailAndBlog(String userEmail, BlogEntity blog);
    Integer countByBlog(BlogEntity blog);
    Page<BlogLikeEntity> findAllByUserEmailOrderByLikedAtDesc(String userEmail, Pageable pageable);
}
