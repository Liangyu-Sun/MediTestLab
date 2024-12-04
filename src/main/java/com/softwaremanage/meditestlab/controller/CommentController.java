package com.softwaremanage.meditestlab.controller;

import com.softwaremanage.meditestlab.pojo.comment_module.Comment;
import com.softwaremanage.meditestlab.service.comment_module.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/comments") // 控制器的路径
public class CommentController {

    @Autowired
    private CommentService commentService;

    // 获取所有留言
    @GetMapping
    public ResponseEntity<List<Comment>> getAllComments() {
        List<Comment> comments = commentService.getAllComments();
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    // 根据留言ID获取留言
    @GetMapping("/{commentId}")
    public ResponseEntity<Comment> getCommentById(@PathVariable Integer commentId) {
        Optional<Comment> comment = commentService.getCommentById(commentId);
        return comment.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // 根据留言类型获取留言
    @GetMapping("/type/{commentType}")
    public ResponseEntity<List<Comment>> getCommentsByType(@PathVariable String commentType) {
        List<Comment> comments = commentService.getCommentsByType(commentType);
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    // 根据检测人员ID获取留言
    @GetMapping("/detector/{detectorId}")
    public ResponseEntity<List<Comment>> getCommentsByDetector(@PathVariable Integer detectorId) {
        List<Comment> comments = commentService.getCommentsByDetector(detectorId);
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    // 创建新的留言
    @PostMapping
    public ResponseEntity<Comment> createComment(@RequestBody Comment comment) {
        Comment createdComment = commentService.createComment(comment);
        return new ResponseEntity<>(createdComment, HttpStatus.CREATED);
    }

    // 更新留言（根据留言ID）
    @PutMapping("/{commentId}")
    public ResponseEntity<Comment> updateComment(@PathVariable Integer commentId, @RequestBody Comment comment) {
        Optional<Comment> updatedComment = commentService.updateComment(commentId, comment);
        return updatedComment.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // 删除留言（根据留言ID）
    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Integer commentId) {
        boolean isDeleted = commentService.deleteComment(commentId);
        return isDeleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

}
