package com.softwaremanage.meditestlab.pojo.dto;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class ProcurementDto {
    //包括：检测人员Id对应的uRealname，该标准的type和sName，该项目的pName和pNum，该设备的eName，解决方案的esName,status
    @ExcelIgnore
    private Integer prId;
    @ExcelProperty("计划采购人")
    private String uRealname;
    @ExcelProperty("类别(产品/项目/参数)")
    private String type;
    @ExcelProperty("产品/项目/参数:名称")
    private String pName;
    @ExcelProperty("标准名称")
    private String sName;
    @ExcelProperty("标准编号（含年号）及条款号")
    private String pNum;
    @ExcelProperty("计划采购设备")
    private String eName;
    @ExcelProperty("已选解决方案")
    private String esName;
    @ExcelProperty("状态（计划采购、采购完成）")
    private String status;
}
