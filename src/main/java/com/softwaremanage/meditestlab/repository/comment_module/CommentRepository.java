package com.softwaremanage.meditestlab.repository.comment_module;

import com.softwaremanage.meditestlab.pojo.comment_module.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {

    // 查询所有留言
    List<Comment> findAll();

    Optional<Comment> findById(Integer commentId);

    List<Comment> findByDetectorId(Integer detectorId);

    List<Comment> findByProjectId(Integer projectId);

    List<Comment> findByCommentType(String commentType);

    List<Comment> findByDescriptionContaining(String description);
}

