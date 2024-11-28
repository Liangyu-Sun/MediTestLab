package com.softwaremanage.meditestlab.pojo.regulation_module;

import jakarta.persistence.*;

@Entity
@Table(name = "test_personnel")
public class TestPersonnel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;  // TestPersonnel的ID

    @Column(nullable = false)
    private Integer testId;  // 关联到 ComparisonTest 的 ID

    @Column(nullable = false)
    private Integer personnelId;  // 检测人员ID

    // Getter 和 Setter 方法
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTestId() {
        return testId;
    }

    public void setTestId(Integer testId) {
        this.testId = testId;
    }

    public Integer getPersonnelId() {
        return personnelId;
    }

    public void setPersonnelId(Integer personnelId) {
        this.personnelId = personnelId;
    }
}
