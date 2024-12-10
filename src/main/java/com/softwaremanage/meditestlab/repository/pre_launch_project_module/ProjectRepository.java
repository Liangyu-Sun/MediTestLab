package com.softwaremanage.meditestlab.repository.pre_launch_project_module;

import com.softwaremanage.meditestlab.pojo.pre_launch_project_module.Project;
import com.softwaremanage.meditestlab.pojo.pre_launch_project_module.Standard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer> {
    void deleteBysId(Integer sId);

    List<Project> findAllBysId(Integer sId);
    Project findByPId(Integer pId);
}
