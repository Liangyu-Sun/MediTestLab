package com.softwaremanage.meditestlab.pojo.equipment_module;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Table
@Entity
public class Procurement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer prId;
    @Column
    //检测人员Id
    private Integer jcId;
    @Column
    private Integer esId;
    @Column
    //状态
    private String status;
}
