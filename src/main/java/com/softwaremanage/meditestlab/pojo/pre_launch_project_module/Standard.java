package com.softwaremanage.meditestlab.pojo.pre_launch_project_module;

import jakarta.persistence.*;

@Table
@Entity
public class Standard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer sId;
    @Column
    private String sName;
    @Column
    private String major_category;
    @Column
    private String type;
    @Column
    private String sNum;

    public Standard() {}

    public int getsId() {
        return sId;
    }

    public void setsId(int sId) {
        this.sId = sId;
    }

    public String getsNum() {
        return sNum;
    }

    public void setsNum(String sNum) {
        this.sNum = sNum;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMajor_category() {
        return major_category;
    }

    public void setMajor_category(String major_category) {
        this.major_category = major_category;
    }

    public String getsName() {
        return sName;
    }

    public void setsName(String sName) {
        this.sName = sName;
    }
}
