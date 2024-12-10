package com.softwaremanage.meditestlab.repository.pre_launch_project_module;

import com.softwaremanage.meditestlab.pojo.pre_launch_project_module.Standard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StandardRepository extends JpaRepository<Standard, Integer> {

    boolean existsBysNum(String sNum);
    Standard findBySId(Integer sId);
}
