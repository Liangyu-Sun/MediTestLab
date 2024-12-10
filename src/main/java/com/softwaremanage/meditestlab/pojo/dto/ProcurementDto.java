package com.softwaremanage.meditestlab.pojo.dto;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ProcurementDto {
    //包括：检测人员Id对应的uRealname，该标准的type和sName，该项目的pName和pNum，该设备的eName，解决方案的esName,status
    @ExcelIgnore
    @JsonProperty("prId")
    private Integer prId;
    @ExcelProperty("计划采购人")
    @JsonProperty("uRealname")
    private String uRealname;
    @ExcelProperty("类别(产品/项目/参数)")
    @JsonProperty("type")
    private String type;
    @ExcelProperty("产品/项目/参数:名称")
    @JsonProperty("pName")
    private String pName;
    @ExcelProperty("标准名称")
    @JsonProperty("sName")
    private String sName;
    @ExcelProperty("标准编号（含年号）及条款号")
    @JsonProperty("pNum")
    private String pNum;
    @ExcelProperty("计划采购设备")
    @JsonProperty("eName")
    private String eName;
    @ExcelProperty("已选解决方案")
    @JsonProperty("esName")
    private String esName;
    @ExcelProperty("状态（计划采购、采购完成）")
    @JsonProperty("status")
    private String status;
}
