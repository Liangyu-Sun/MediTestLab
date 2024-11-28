package com.softwaremanage.meditestlab.repository.regulation_module;

import com.softwaremanage.meditestlab.pojo.regulation_module.ComparisonTest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ComparisonTestRepository extends JpaRepository<ComparisonTest, Integer> {

    List<ComparisonTest> findByProjectId(Integer projectId);
    List<ComparisonTest> findByTestId(Integer testIds); // 根据 testId 列表查询
    List<ComparisonTest> findByTestIdAndStatus(Integer testIds, String status); // 根据 testId 列表和状态查询
}
