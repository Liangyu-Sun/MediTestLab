package com.softwaremanage.meditestlab.repository.query_module;

import com.softwaremanage.meditestlab.pojo.pre_launch_project_module.Project;
import com.softwaremanage.meditestlab.pojo.dto.QueryDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QueryRepository extends JpaRepository<Project, Long> {

    // 使用 QueryDto 传入的条件进行查询
    @Query("SELECT p FROM Project p WHERE " +
            "(:#{#queryDto.projectName} IS NULL OR p.pName LIKE %:#{#queryDto.projectName}%) AND " +
            "(:#{#queryDto.projectId} IS NULL OR p.pId = :#{#queryDto.projectId}) AND " +
            "(:#{#queryDto.userId} IS NULL OR p.sId = :#{#queryDto.userId})")
    List<Project> findByQueryDto(QueryDto queryDto);
}
