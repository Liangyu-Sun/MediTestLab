package com.softwaremanage.meditestlab.service.query_module;

import com.softwaremanage.meditestlab.pojo.dto.QueryDto;
import com.softwaremanage.meditestlab.pojo.pre_launch_project_module.Project;
import com.softwaremanage.meditestlab.repository.query_module.QueryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QueryService {

    @Autowired
    private QueryRepository queryRepository;

    public List<Project> queryProjects(QueryDto queryDto) {
        return queryRepository.findByQueryDto(queryDto);
    }
}
