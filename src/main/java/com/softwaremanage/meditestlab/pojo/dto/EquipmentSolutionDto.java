package com.softwaremanage.meditestlab.pojo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class EquipmentSolutionDto {
    @JsonProperty("esId")
    private Integer esId;//解决方案Id
    @JsonProperty("eId")
    private Integer eId;//设备Id
    @NotBlank(message = "解决方案名称不能为空")
    @JsonProperty("esName")
    private String esName;//解决方案名称
    //生产厂家
    @JsonProperty("manufacturer")
    private String manufacturer;
    //照片
    @JsonProperty("photo")
    private String photo;
    //详情
    @JsonProperty("details")
    private String details;

}
