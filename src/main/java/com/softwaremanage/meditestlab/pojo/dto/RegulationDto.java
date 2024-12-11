package com.softwaremanage.meditestlab.pojo.dto;

import com.alibaba.excel.annotation.ExcelProperty;

public class RegulationDto {

    @ExcelProperty("规程ID")
    private Integer regulationId;

    @ExcelProperty("项目ID")
    private Integer projectId;

    @ExcelProperty("规程存储地址")
    private String filePath;

    // Getters and Setters
    public Integer getRegulationId() {
        return regulationId;
    }

    public void setRegulationId(Integer regulationId) {
        this.regulationId = regulationId;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
