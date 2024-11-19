package com.softwaremanage.meditestlab.pojo.pre_launch_project_module;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class StandardExcelModel {
    @ExcelProperty(index = 0)
    private String majorCategory;

    @ExcelProperty(index = 1)
    private String type;

    @ExcelProperty(index = 3)
    private String sName;

    @ExcelProperty(index = 5)
    private String sNum;


}
