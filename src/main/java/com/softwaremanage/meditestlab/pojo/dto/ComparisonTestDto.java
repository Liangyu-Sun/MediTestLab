package com.softwaremanage.meditestlab.pojo.dto;

import com.alibaba.excel.annotation.ExcelProperty;

public class ComparisonTestDto {

    @ExcelProperty("比对测试申请人")
    private String applicant;

    @ExcelProperty("类别(产品/项目/参数)")
    private String category;

    @ExcelProperty("测试状态")
    private String status;

    @ExcelProperty("测试计划存储地址")
    private String testPlanStorageAddress;

    @ExcelProperty("计划时间")
    private String plannedTime;

    @ExcelProperty("完成时间")
    private String completionTime;

    // Getters and Setters
    public String getApplicant() {
        return applicant;
    }

    public void setApplicant(String applicant) {
        this.applicant = applicant;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTestPlanStorageAddress() {
        return testPlanStorageAddress;
    }

    public void setTestPlanStorageAddress(String testPlanStorageAddress) {
        this.testPlanStorageAddress = testPlanStorageAddress;
    }

    public String getPlannedTime() {
        return plannedTime;
    }

    public void setPlannedTime(String plannedTime) {
        this.plannedTime = plannedTime;
    }

    public String getCompletionTime() {
        return completionTime;
    }

    public void setCompletionTime(String completionTime) {
        this.completionTime = completionTime;
    }
}
