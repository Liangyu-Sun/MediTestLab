package com.softwaremanage.meditestlab.pojo.exam_module;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "assessment")
public class Assessment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "assessment_id")
    private Long assessmentId;

    @Column(name = "inspector_id", nullable = false)
    private Long inspectorId;

    @Column(name = "answer_sheet_url")
    private String answerSheetUrl;

    @Column(name = "operation_video_url")
    private String operationVideoUrl;

    @Column(name = "is_completed", nullable = false)
    private boolean isCompleted;

    @Column(name = "completion_time")
    private Date completionTime;

    @Column(name = "certificate_url")
    private String certificateUrl;

    @Column(name = "project_id", nullable = false)
    private Long projectId;
}

