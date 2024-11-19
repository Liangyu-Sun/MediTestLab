package com.softwaremanage.meditestlab.pojo.pre_launch_project_module;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProjectExcelModel {
    @ExcelProperty(index=2)
    private String pName;

    @ExcelProperty(index=4)
    private String pNum;

    @ExcelProperty(index=6)
    private String equipmentNames;


}
