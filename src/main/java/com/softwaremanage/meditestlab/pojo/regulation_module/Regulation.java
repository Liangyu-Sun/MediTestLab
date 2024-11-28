package com.softwaremanage.meditestlab.pojo.regulation_module;

import jakarta.persistence.*;

@Entity
@Table(name = "regulations")
public class Regulation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // 规程 ID

    @Column(nullable = false)
    private String filePath; // 文件存储路径

    @Column(nullable = false)
    private Integer projectId; // 关联的项目 ID

    public Regulation() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }
}
