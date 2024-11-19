package com.softwaremanage.meditestlab.service.pre_launch_project_module;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.read.listener.PageReadListener;
import com.softwaremanage.meditestlab.pojo.equipment_module.Equipment;
import com.softwaremanage.meditestlab.pojo.pre_launch_project_module.Project;
import com.softwaremanage.meditestlab.pojo.pre_launch_project_module.ProjectExcelModel;
import com.softwaremanage.meditestlab.pojo.pre_launch_project_module.Standard;
import com.softwaremanage.meditestlab.pojo.pre_launch_project_module.StandardExcelModel;
import com.softwaremanage.meditestlab.repository.equipment_module.EquipmentRepository;
import com.softwaremanage.meditestlab.repository.pre_launch_project_module.ProjectRepository;
import com.softwaremanage.meditestlab.repository.pre_launch_project_module.StandardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class StandardService {
    @Autowired
    private StandardRepository standardRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private EquipmentRepository equipmentRepository;

    public String importExcel(MultipartFile file){
        try {
            List<StandardExcelModel> standardData = EasyExcel.read(file.getInputStream())
                    .head(StandardExcelModel.class)
                    .sheet(0)
                    .doReadSync();
            List<ProjectExcelModel> projectData = EasyExcel.read(file.getInputStream())
                    .head(ProjectExcelModel.class)
                    .sheet(1)
                    .doReadSync();

            if (standardData.size() >1 && projectData.size()>1) {
                StandardExcelModel standardRow = standardData.get(0);

                Standard standard = new Standard();
                standard.setMajor_category(standardRow.getMajorCategory());
                standard.setType(standardRow.getType());
                standard.setsName(standardRow.getSName());
                standard.setsNum(standardRow.getSNum());

                standardRepository.save(standard);

                List<Project> projects = new ArrayList<>();
                for (int i =1; i < projectData.size(); i++) {
                    ProjectExcelModel projectRow = projectData.get(i);
                    Project project = new Project();
                    project.setpName(projectRow.getPName());
                    project.setpNum(projectRow.getPNum());
                    project.setsId(standard.getsId());
                    projects.add(project);
                }

                projectRepository.saveAll(projects);

                List<Equipment> equipments = new ArrayList<>();
                for (int i =1; i < projectData.size(); i++) {
                    ProjectExcelModel projectRow = projectData.get(i);
                    if(projectRow.getEquipmentNames()==null){
                        String equipmentName = "空";
                        Equipment equipment = new Equipment();
                        equipment.seteName(equipmentName.trim());
                        equipment.setpId(projects.get(i-1).getpId());
                        equipments.add(equipment);
                    }
                    else{
                    String[] equipmentNames = projectRow.getEquipmentNames().split("\n"); // G3及以下
                    for (String equipmentName : equipmentNames) {
                        Equipment equipment = new Equipment();
                        equipment.seteName(equipmentName.trim());
                        equipment.setpId(projects.get(i-1).getpId());
                        equipments.add(equipment);
                    }
                }
                }

                equipmentRepository.saveAll(equipments);

            return "导入成功";
            } else {
                return "导入失败，表格不符合规范";
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "导入失败: " + e.getMessage();
        }
    }

}
