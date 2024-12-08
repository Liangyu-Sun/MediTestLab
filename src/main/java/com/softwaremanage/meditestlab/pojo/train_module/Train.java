package com.softwaremanage.meditestlab.pojo.train_module;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "train")
public class Train {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "training_id")
    private Integer trainingId;

    @Column(name = "inspector_id", nullable = false)
    private Integer inspectorId;

    @Column(name = "is_completed", nullable = false)
    private boolean isCompleted;

    @Column(name = "completion_time")
    private Date completionTime;

    @Column(name = "certificate_url")
    private String certificateUrl;

    @Column(name = "project_id", nullable = false)
    private Integer projectId;
}
