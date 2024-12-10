package com.softwaremanage.meditestlab.pojo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class SampleDto {
    @JsonProperty("saId")
    private Integer saId;
    //样品名称
    @JsonProperty("saName")
    private String saName;
    //规格型号
    @JsonProperty("saModel")
    private String saModel;
    //生产厂家
    @JsonProperty("saFactory")
    private String saFactory;
    //批号
    @JsonProperty("saBatch")
    private String saBatch;
    //标准Id
    @JsonProperty("sId")
    private Integer sId;
}
