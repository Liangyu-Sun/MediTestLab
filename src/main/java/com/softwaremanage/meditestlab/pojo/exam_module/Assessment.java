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
    private Integer assessmentId;

    @Column(name = "inspector_id", nullable = false)
    private Integer inspectorId;

    @Column(name = "answer_sheet_url")
    private String answerSheetUrl;

    @Column(name = "operation_video_url")
    private String operationVideoUrl;

    @Column(name = "completed", nullable = false)
    public boolean completed;

    @Column(name = "completion_time")
    private Date completionTime;

    @Column(name = "certificate_url")
    private String certificateUrl;

    @Column(name = "project_id", nullable = false)
    private Integer projectId;
}

