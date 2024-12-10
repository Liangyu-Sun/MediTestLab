package com.softwaremanage.meditestlab.pojo.comment_module;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // 自增主键
    private Integer commentId;        // 留言Id

    @Column(nullable = false)
    private Integer detectorId;       // 检测人员Id

    @Column(nullable = false)
    private Integer projectId;        // 项目Id

    @Column(nullable = false)
    private LocalDateTime applyTime;  // 申请时间

    @Column(nullable = false)
    private String commentType;       // 留言类型

    @Column(nullable = false, length = 500)
    private String description;       // 具体描述


    public Comment() {
    }

    public Comment(Integer commentId, Integer detectorId, Integer projectId, LocalDateTime applyTime, String commentType, String description) {
        this.commentId = commentId;
        this.detectorId = detectorId;
        this.projectId = projectId;
        this.applyTime = applyTime;
        this.commentType = commentType;
        this.description = description;
    }


    public Integer getCommentId() {
        return commentId;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }

    public Integer getDetectorId() {
        return detectorId;
    }

    public void setDetectorId(Integer detectorId) {
        this.detectorId = detectorId;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public LocalDateTime getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(LocalDateTime applyTime) {
        this.applyTime = applyTime;
    }

    public String getCommentType() {
        return commentType;
    }

    public void setCommentType(String commentType) {
        this.commentType = commentType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "commentId=" + commentId +
                ", detectorId=" + detectorId +
                ", projectId=" + projectId +
                ", applyTime=" + applyTime +
                ", commentType='" + commentType + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
