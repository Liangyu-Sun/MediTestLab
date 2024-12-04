package com.softwaremanage.meditestlab.service.comment_module;

import com.softwaremanage.meditestlab.pojo.comment_module.Comment;
import com.softwaremanage.meditestlab.repository.comment_module.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    // 获取所有留言
    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }

    // 根据ID获取留言
    public Optional<Comment> getCommentById(Integer commentId) {
        return commentRepository.findById(commentId);
    }

    // 根据留言类型获取留言
    public List<Comment> getCommentsByType(String commentType) {
        return commentRepository.findByCommentType(commentType);
    }

    // 根据检测人员ID获取留言
    public List<Comment> getCommentsByDetector(Integer detectorId) {
        return commentRepository.findByDetectorId(detectorId);
    }

    // 创建新的留言
    public Comment createComment(Comment comment) {
        return commentRepository.save(comment);
    }

    // 更新留言
    public Optional<Comment> updateComment(Integer commentId, Comment comment) {
        if (commentRepository.existsById(commentId)) {
            comment.setCommentId(commentId);  // 更新留言ID
            return Optional.of(commentRepository.save(comment));
        } else {
            return Optional.empty();  // 如果留言不存在，返回空
        }
    }

    // 删除留言
    public boolean deleteComment(Integer commentId) {
        if (commentRepository.existsById(commentId)) {
            commentRepository.deleteById(commentId);
            return true;
        } else {
            return false;
        }
    }

}

