package com.softwaremanage.meditestlab.pojo.train_module;
import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
@Table(name = "learning_record")
public class LearningRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "video_id")
    private Long videoId;

    @Column(name = "inspector_id", nullable = false)
    private Long inspectorId;

    @Column(name = "watch_percentage", nullable = false)
    private double watchPercentage;

    @Column(name = "project_id", nullable = false)
    private Long projectId;
}
