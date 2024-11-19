package com.softwaremanage.meditestlab.pojo.equipment_module;

import jakarta.persistence.*;

@Table
@Entity
public class Equipment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer eId;
    @Column
    private String eName;
    @Column
    private Integer pId;

    public Equipment() {}

    public Integer geteId() {
        return eId;
    }

    public void seteId(Integer eId) {
        this.eId = eId;
    }

    public Integer getpId() {
        return pId;
    }

    public void setpId(Integer pId) {
        this.pId = pId;
    }

    public String geteName() {
        return eName;
    }

    public void seteName(String eName) {
        this.eName = eName;
    }
}
