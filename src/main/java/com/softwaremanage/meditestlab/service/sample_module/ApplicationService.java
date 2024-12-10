package com.softwaremanage.meditestlab.service.sample_module;

import com.softwaremanage.meditestlab.pojo.dto.ApplicationDto;
import com.softwaremanage.meditestlab.pojo.dto.ProcurementDto;
import com.softwaremanage.meditestlab.pojo.dto.SampleListDto;
import com.softwaremanage.meditestlab.pojo.equipment_module.Procurement;
import com.softwaremanage.meditestlab.pojo.sample_module.Application;
import com.softwaremanage.meditestlab.repository.account_management_module.UserRepository;
import com.softwaremanage.meditestlab.repository.pre_launch_project_module.StandardRepository;
import com.softwaremanage.meditestlab.repository.sample_module.ApplicationRepository;
import com.softwaremanage.meditestlab.repository.sample_module.SampleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ApplicationService {
    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StandardRepository standardRepository;

    @Autowired
    private SampleRepository sampleRepository;

    public List<Application> applySample(ApplicationDto applicationDto) {
        //申请之前查询是否已有该uId和saId对应的申请，若有且状态为“申请需求”，则提示“该样品正在申请中”,若没有或者状态不为“申请完成”，则添加申请
//
        return applicationRepository.findAll().stream()
                .filter(application -> application.getJcId().equals(applicationDto.getJcId()) &&
                        application.getSaId().equals(applicationDto.getSaId()) &&
                        "申请需求".equals(application.getAState()))
                .collect(Collectors.toList());
    }

    public void addApplication(ApplicationDto applicationDto) {
        Application application = new Application();
        application.setJcId(applicationDto.getJcId());
        application.setSaId(applicationDto.getSaId());
        application.setADemand(applicationDto.getADemand());
        application.setAState("申请需求");
        application.setANum("");
        //系统当前时间
        application.setATime(LocalDateTime.now());
        application.setAGiveTime(null);
        applicationRepository.save(application);
    }

    public List<SampleListDto> allApplication() {

        if(applicationRepository.findAll().isEmpty())
            return null;
        List<Application> applicationList = applicationRepository.findAll();
        List<SampleListDto> sampleListDtoList = new ArrayList<>();
        for (Application application : applicationList) {
            SampleListDto sampleListDto = new SampleListDto();
            sampleListDto.setAId(application.getAId());
            sampleListDto.setURealname(userRepository.findById(application.getJcId()).get().getuRealname());
            sampleListDto.setSName(standardRepository.findById(sampleRepository.findById(application.getSaId()).get().getSId()).get().getsName());
            sampleListDto.setSNum(standardRepository.findById(sampleRepository.findById(application.getSaId()).get().getSId()).get().getsNum());
            sampleListDto.setSaName(sampleRepository.findById(application.getSaId()).get().getSaName());
            sampleListDto.setSaModel(sampleRepository.findById(application.getSaId()).get().getSaModel());
            sampleListDto.setSaFactory(sampleRepository.findById(application.getSaId()).get().getSaFactory());
            sampleListDto.setSaBatch(sampleRepository.findById(application.getSaId()).get().getSaBatch());
            sampleListDto.setADemand(application.getADemand());
            sampleListDto.setANum(application.getANum());
            sampleListDto.setATime(application.getATime());
            sampleListDto.setAGiveTime(application.getAGiveTime());

            sampleListDtoList.add(sampleListDto);
        }
        return sampleListDtoList;
    }

    public void updateApplication(ApplicationDto applicationDto) {
        Application application = applicationRepository.findById(applicationDto.getAId()).get();
        application.setAState("发放完成");
        application.setANum(applicationDto.getANum());
        application.setAGiveTime(LocalDateTime.now());
        applicationRepository.save(application);

    }

    public List<SampleListDto> allApplicationByuId(Integer uId) {

        if(applicationRepository.findByJcId(uId).isEmpty())
            return null;
        List<Application> applicationList = applicationRepository.findByJcId(uId);
        List<SampleListDto> sampleListDtoList = new ArrayList<>();
        for (Application application : applicationList) {
            SampleListDto sampleListDto = new SampleListDto();
            sampleListDto.setAId(application.getAId());
            sampleListDto.setURealname(userRepository.findById(application.getJcId()).get().getuRealname());
            sampleListDto.setSName(standardRepository.findById(sampleRepository.findById(application.getSaId()).get().getSId()).get().getsName());
            sampleListDto.setSNum(standardRepository.findById(sampleRepository.findById(application.getSaId()).get().getSId()).get().getsNum());
            sampleListDto.setSaName(sampleRepository.findById(application.getSaId()).get().getSaName());
            sampleListDto.setSaModel(sampleRepository.findById(application.getSaId()).get().getSaModel());
            sampleListDto.setSaFactory(sampleRepository.findById(application.getSaId()).get().getSaFactory());
            sampleListDto.setSaBatch(sampleRepository.findById(application.getSaId()).get().getSaBatch());
            sampleListDto.setADemand(application.getADemand());
            sampleListDto.setANum(application.getANum());
            sampleListDto.setATime(application.getATime());
            sampleListDto.setAGiveTime(application.getAGiveTime());

            sampleListDtoList.add(sampleListDto);
        }
        return sampleListDtoList;
    }

    public void updateApplicationDemand(ApplicationDto applicationDto) {
        Application application = applicationRepository.findById(applicationDto.getAId()).get();
        application.setADemand(applicationDto.getADemand());
        application.setATime(LocalDateTime.now());
        applicationRepository.save(application);
    }

    public void deleteApplication(Integer aId) {
        applicationRepository.deleteById(aId);

    }
}
