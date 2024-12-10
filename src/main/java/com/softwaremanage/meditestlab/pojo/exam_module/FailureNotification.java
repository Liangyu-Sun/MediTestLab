package com.softwaremanage.meditestlab.pojo.exam_module;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "failure_notification")
public class FailureNotification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id")
    private Long notificationId;

    @Column(name = "inspector_id", nullable = false)
    private Long inspectorId;

    @Column(name = "project_id", nullable = false)
    private Long projectId;

    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(name = "assessment_id")
    private Long assessmentId;
}
