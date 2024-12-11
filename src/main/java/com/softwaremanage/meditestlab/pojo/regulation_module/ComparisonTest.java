package com.softwaremanage.meditestlab.pojo.regulation_module;

import jakarta.persistence.*;

@Entity
@Table(name = "comparison_tests")
public class ComparisonTest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer testId;  // 比对测试ID

    @Column(nullable = false)
    private Integer projectId;  // 关联项目ID

    @Column(nullable = false)
    private String status;  // 比对测试的状态

    private String testPlanStorageAddress;  // 测试方案存储地址（文件路径）

    private String plannedTime;  // 计划时间

    private String completionTime;  // 完成时间

    @Column(nullable = true)
    private String applicant;  // 比对测试申请人

    @Column(nullable = true)
    private String category;  // 类别 (产品/项目/参数)

    // Getters and Setters

    public Integer getTestId() {
        return testId;
    }

    public void setTestId(Integer testId) {
        this.testId = testId;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
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
}
