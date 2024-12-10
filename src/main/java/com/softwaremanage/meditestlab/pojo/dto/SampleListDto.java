package com.softwaremanage.meditestlab.pojo.dto;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SampleListDto {
    @ExcelIgnore
    @JsonProperty("aId")
    private Integer aId;
    @ExcelProperty("需求人")
    @JsonProperty("uRealname")
    private String uRealname;
    @ExcelProperty("标准名称")
    @JsonProperty("sName")
    private String sName;
    @ExcelProperty("标准编号（含年号）及条款号")
    @JsonProperty("sNum")
    private String sNum;
    @ExcelProperty("产品名称")
    @JsonProperty("saName")
    private String saName;
    @ExcelProperty("规格型号")
    @JsonProperty("saModel")
    private String saModel;
    @ExcelProperty("生产厂家")
    @JsonProperty("saFactory")
    private String saFactory;
    @ExcelProperty("批号")
    @JsonProperty("saBatch")
    private String saBatch;
    @ExcelProperty("申请需求")
    @JsonProperty("aDemand")
    private String aDemand;
    @ExcelProperty("实际数量")
    @JsonProperty("aNum")
    private String aNum;
    @ExcelProperty("申请需求时间")
    @JsonProperty("aTime")
    private LocalDateTime aTime;
    @ExcelProperty("样品发放时间")
    @JsonProperty("aGiveTime")
    private LocalDateTime aGiveTime;
}
