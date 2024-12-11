package com.softwaremanage.meditestlab.repository.query_module;

import com.softwaremanage.meditestlab.pojo.pre_launch_project_module.Project;
import com.softwaremanage.meditestlab.pojo.dto.QueryDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QueryRepository extends JpaRepository<Project, Long> {

    // 查询标准编号和条款号，不为空时直接查询
    @Query("SELECT p FROM Project p WHERE " +
            "(:#{#queryDto.standardNumber} IS NOT NULL AND p.pNum = :#{#queryDto.standardNumber}) OR " +
            "(:#{#queryDto.projectName} IS NULL AND :#{#queryDto.category} IS NULL AND :#{#queryDto.type} IS NULL) ")
    List<Project> findByStandardNumberOrOtherConditions(QueryDto queryDto);

    // 通过大类、类别和标准名称查找符合条件的标准ID
    @Query("SELECT s.standardId FROM Standard s WHERE " +
            "(:#{#queryDto.category} IS NULL OR s.majorCategory LIKE %:#{#queryDto.category}%) AND " +
            "(:#{#queryDto.type} IS NULL OR s.type LIKE %:#{#queryDto.type}%) AND " +
            "(:#{#queryDto.standardName} IS NULL OR s.standardName LIKE %:#{#queryDto.standardName}%)")
    List<Integer> findStandardIds(QueryDto queryDto);

    // 通过项目名称查找符合条件的项目
    @Query("SELECT p FROM Project p WHERE " +
            "(:#{#queryDto.projectName} IS NULL OR p.pName LIKE %:#{#queryDto.projectName}%)")
    List<Project> findProjectsByProjectName(QueryDto queryDto);

}
