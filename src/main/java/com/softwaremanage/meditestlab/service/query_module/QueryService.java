package com.softwaremanage.meditestlab.service.query_module;

import com.softwaremanage.meditestlab.pojo.dto.QueryDto;
import com.softwaremanage.meditestlab.pojo.pre_launch_project_module.Project;
import com.softwaremanage.meditestlab.repository.query_module.QueryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class QueryService {

    @Autowired
    private QueryRepository queryRepository;

    // 查询项目的方法
    public List<Project> queryProjects(QueryDto queryDto) {
        if (queryDto.getStandardNumber() != null) {
            // 如果标准编号或条款号不为空，直接查询
            return queryRepository.findByStandardNumberOrOtherConditions(queryDto);
        }

        // 否则，先查找符合条件的标准ID
        List<Integer> standardIds = queryRepository.findStandardIds(queryDto);

        // 如果没有符合的标准ID，返回空
        if (standardIds.isEmpty()) {
            return List.of();
        }

        // 根据标准ID查询符合条件的项目
        List<Project> projects = queryRepository.findProjectsByProjectName(queryDto);

        // 过滤出符合sId条件的项目
        return projects.stream()
                .filter(p -> standardIds.contains(p.getsId()))
                .collect(Collectors.toList());
    }
}
