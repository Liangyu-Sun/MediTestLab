package com.softwaremanage.meditestlab.repository.regulation_module;

import com.softwaremanage.meditestlab.pojo.regulation_module.Regulation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegulationRepository extends JpaRepository<Regulation, Integer> {
    // 根据项目ID查询规程数量
    long countByProjectId(Integer projectId);
}
