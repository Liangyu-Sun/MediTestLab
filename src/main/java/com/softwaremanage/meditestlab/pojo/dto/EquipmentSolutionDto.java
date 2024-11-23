package com.softwaremanage.meditestlab.pojo.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class EquipmentSolutionDto {
    private Integer esId;//解决方案Id
    private Integer eId;//设备Id
    @NotBlank(message = "解决方案名称不能为空")
    private String esName;//解决方案名称
    //生产厂家
    private String manufacturer;
    //照片
    private String photo;
    //详情
    private String details;

}
