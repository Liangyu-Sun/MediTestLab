package com.softwaremanage.meditestlab.controller.query_module;

import com.softwaremanage.meditestlab.pojo.dto.QueryDto;
import com.softwaremanage.meditestlab.pojo.pre_launch_project_module.Project;
import com.softwaremanage.meditestlab.service.query_module.QueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/query")
public class QueryController {

    @Autowired
    private QueryService queryService;

    @PostMapping("/project")
    public List<Project> queryProjects(@RequestBody QueryDto queryDto) {
        return queryService.queryProjects(queryDto);
    }
}
