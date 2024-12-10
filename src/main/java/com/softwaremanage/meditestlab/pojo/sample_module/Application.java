package com.softwaremanage.meditestlab.pojo.sample_module;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Table
@Entity
public class Application {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer aId;
    @Column
    //申请人Id
    private Integer jcId;
    @Column
    //样品Id
    private Integer saId;
    @Column
    //样品需求
    private String aDemand;
    @Column
    //状态
    private String aState;
    @Column
    //实际数量
    private String aNum;
    @Column
    //申请时间
    private LocalDateTime aTime;
    @Column
    //发放时间
    private LocalDateTime aGiveTime;
}
