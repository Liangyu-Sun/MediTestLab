package com.softwaremanage.meditestlab.pojo.train_module;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "resource")
public class Resource {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rid")
    private Long resourceId;

    @Column(name = "rurl", nullable = false)
    private String vUrl;

    @Column(name = "pid", nullable = false)
    private Long pId;

    @Column(name = "resource_type", nullable = false)
    private String resourceType;
}
