package com.softwaremanage.meditestlab.pojo.train_module;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "resource")
public class Resource {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "resource_id")
    private Integer resourceId;

    @Column(name = "r_url", nullable = false)
    private String vUrl;

    @Column(name = "project_id", nullable = false)
    private Integer projectId;

    @Column(name = "resource_type", nullable = false)
    private String resourceType;
}
