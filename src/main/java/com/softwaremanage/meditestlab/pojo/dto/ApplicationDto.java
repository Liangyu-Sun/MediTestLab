package com.softwaremanage.meditestlab.pojo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ApplicationDto {
    @JsonProperty("aId")
    private Integer aId;
    @JsonProperty("jcId")
    private Integer jcId;
    @JsonProperty("saId")
    private Integer saId;
    @JsonProperty("aDemand")
    private String aDemand;
    @JsonProperty("aState")
    private String aState;
    @JsonProperty("aNum")
    private String aNum;
    @JsonProperty("aTime")
    private String aTime;
    @JsonProperty("aGiveTime")
    private String aGiveTime;
}
