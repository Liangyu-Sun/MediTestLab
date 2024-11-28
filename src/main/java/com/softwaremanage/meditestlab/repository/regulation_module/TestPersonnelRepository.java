package com.softwaremanage.meditestlab.repository.regulation_module;

import com.softwaremanage.meditestlab.pojo.regulation_module.TestPersonnel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TestPersonnelRepository extends JpaRepository<TestPersonnel, Integer> {

    List<TestPersonnel> findByPersonnelId(Integer personnelId);
    List<TestPersonnel> findByTestIdAndPersonnelId(Integer testId, Integer personnelId);  // 根据testId和personnelId查询

}
