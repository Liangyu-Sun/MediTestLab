package com.softwaremanage.meditestlab.service.train_module;

import com.alibaba.excel.EasyExcel;
import com.softwaremanage.meditestlab.pojo.dto.AbilityExcelDto;
import com.softwaremanage.meditestlab.pojo.exam_module.Assessment;
import com.softwaremanage.meditestlab.pojo.train_module.Ability;
import com.softwaremanage.meditestlab.repository.exam_module.AssessmentRepository;
import com.softwaremanage.meditestlab.repository.exam_module.AbilityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Service
public class AbilityService {

    @Autowired
    private AssessmentRepository assessmentRepository;

    @Autowired
    private AbilityRepository abilityRepository;

    public void readAndWriteAbilityList(Integer inspectorId) {
        List<Assessment> completedAssessments = assessmentRepository.findByInspectorIdAndCompleted(inspectorId, true);

        for (Assessment assessment : completedAssessments) {
            Ability ability = new Ability();
            ability.setInspectorId(inspectorId);
            ability.setProjectId(assessment.getProjectId());
            ability.setSId(assessment.getProjectId()); // Assuming sId is the same as projectId
            ability.setAbilityName("Project " + assessment.getProjectId()); // Assuming project name is "Project {projectId}"

            abilityRepository.save(ability);
        }
    }

    public String exportAbilityListToExcel(Integer inspectorId) {
        List<Ability> abilities = abilityRepository.findByInspectorId(inspectorId);
        List<AbilityExcelDto> abilityExcelDtos = new ArrayList<>();

        for (Ability ability : abilities) {
            AbilityExcelDto dto = new AbilityExcelDto();
            dto.setInspectorId(ability.getInspectorId());
            dto.setProjectId(ability.getProjectId());
            dto.setSId(ability.getSId());
            dto.setAbilityName(ability.getAbilityName());
            abilityExcelDtos.add(dto);
        }

        String fileName = "AbilityList_" + inspectorId + ".xlsx";
        String filePath = System.getProperty("user.dir") + File.separator + fileName;

        try {
            EasyExcel.write(filePath, AbilityExcelDto.class).sheet("Ability List").doWrite(abilityExcelDtos);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return filePath;
    }

    public String exportAllAbilitiesToExcel() {
        List<Ability> abilities = abilityRepository.findAll();
        List<AbilityExcelDto> abilityExcelDtos = new ArrayList<>();

        for (Ability ability : abilities) {
            AbilityExcelDto dto = new AbilityExcelDto();
            dto.setInspectorId(ability.getInspectorId());
            dto.setProjectId(ability.getProjectId());
            dto.setSId(ability.getSId());
            dto.setAbilityName(ability.getAbilityName());
            abilityExcelDtos.add(dto);
        }

        String fileName = "AllAbilities.xlsx";
        String filePath = System.getProperty("user.dir") + File.separator + fileName;

        try {
            EasyExcel.write(filePath, AbilityExcelDto.class).sheet("All Abilities").doWrite(abilityExcelDtos);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return filePath;
    }
}
