package az.shopery.blog_ms.repository;

import az.shopery.blog_ms.model.entity.BlogEntity;
import az.shopery.blog_ms.model.entity.SavedBlogEntity;
import az.shopery.blog_ms.model.entity.UserEntity;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SavedBlogRepository extends JpaRepository<SavedBlogEntity, UUID> {
    List<SavedBlogEntity> findAllByBlog(BlogEntity blog);
    Boolean existsByBlogAndUser(BlogEntity blog, UserEntity user);
    void deleteByBlogAndUser(BlogEntity blog, UserEntity user);
    Page<SavedBlogEntity> findAllByUserIdAndIsArchived(UUID userId, Boolean isArchived, Pageable pageable);
}
