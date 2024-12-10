package com.softwaremanage.meditestlab.pojo.sample_module;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Table
@Entity
public class Sample {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer saId;
    @Column
    //样品名称
    private String saName;
    @Column
    //规格型号
    private String saModel;
    @Column
    //生产厂家
    private String saFactory;
    @Column
    //批号
    private String saBatch;
    @Column
    //标准Id
    private Integer sId;

}
