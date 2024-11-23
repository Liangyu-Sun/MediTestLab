package com.softwaremanage.meditestlab.pojo.equipment_module;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Table
@Entity
public class EquipmentSolution {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer esId;//解决方案Id
    @Column
    private Integer eId;//设备Id
    @Column
    private String esName;//解决方案名称
    @Column
    //生产厂家
    private String manufacturer;
    @Column
    //照片
    private String photo;
    @Column(columnDefinition = "TEXT")
    //详情
    private String details;

}
