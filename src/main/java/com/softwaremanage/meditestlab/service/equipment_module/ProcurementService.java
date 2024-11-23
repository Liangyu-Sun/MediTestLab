package com.softwaremanage.meditestlab.service.equipment_module;

import com.softwaremanage.meditestlab.pojo.dto.ProcurementDto;
import com.softwaremanage.meditestlab.pojo.equipment_module.Procurement;
import com.softwaremanage.meditestlab.repository.account_management_module.UserRepository;
import com.softwaremanage.meditestlab.repository.equipment_module.EquipmentRepository;
import com.softwaremanage.meditestlab.repository.equipment_module.EquipmentSolutionRepository;
import com.softwaremanage.meditestlab.repository.pre_launch_project_module.ProjectRepository;
import com.softwaremanage.meditestlab.repository.pre_launch_project_module.StandardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.softwaremanage.meditestlab.repository.equipment_module.ProcurementRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProcurementService {
    @Autowired
    private ProcurementRepository procurementRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EquipmentSolutionRepository equipmentSolutionRepository;

    @Autowired
    private EquipmentRepository equipmentRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private StandardRepository standardRepository;

    public void addProcurement(Procurement procurement) {
        procurementRepository.save(procurement);

    }

    public List<ProcurementDto> findProcurementByuId(Integer uId) {
        if(procurementRepository.findProcurementByjcId(uId).isEmpty())
            return null;
        List<Procurement> procurementList = procurementRepository.findProcurementByjcId(uId);
        List<ProcurementDto> procurementDtoList = new ArrayList<>();;
        for (Procurement procurement : procurementList) {
            ProcurementDto procurementDto = new ProcurementDto();
            //包括：检测人员Id对应的uRealname，该标准的type和sName，该项目的pName和pNum，该设备的eName，解决方案的esName,status
            procurementDto.setPrId(procurement.getPrId());
            procurementDto.setURealname(userRepository.findById(uId).get().getuRealname());
            procurementDto.setPName(projectRepository.findById(equipmentRepository.findById(equipmentSolutionRepository.findById(procurement.getEsId()).get().getEId()).get().getpId()).get().getpName());
            procurementDto.setPNum(projectRepository.findById(equipmentRepository.findById(equipmentSolutionRepository.findById(procurement.getEsId()).get().getEId()).get().getpId()).get().getpNum());
            procurementDto.setType(standardRepository.findById(projectRepository.findById(equipmentRepository.findById(equipmentSolutionRepository.findById(procurement.getEsId()).get().getEId()).get().getpId()).get().getsId()).get().getType());
            procurementDto.setSName(standardRepository.findById(projectRepository.findById(equipmentRepository.findById(equipmentSolutionRepository.findById(procurement.getEsId()).get().getEId()).get().getpId()).get().getsId()).get().getsName());
            procurementDto.setEName(equipmentRepository.findById(equipmentSolutionRepository.findById(procurement.getEsId()).get().getEId()).get().geteName());
            procurementDto.setEsName(equipmentSolutionRepository.findById(procurement.getEsId()).get().getEsName());
            procurementDto.setStatus(procurement.getStatus());
            procurementDtoList.add(procurementDto);
        }
        return procurementDtoList;
    }

    public List<ProcurementDto> findAllProcurement() {
        if(procurementRepository.findAll().isEmpty())
            return null;
        List<Procurement> procurementList = procurementRepository.findAll();
        List<ProcurementDto> procurementDtoList = new ArrayList<>();;
        for (Procurement procurement : procurementList) {
            ProcurementDto procurementDto = new ProcurementDto();
            //包括：检测人员Id对应的uRealname，该标准的type和sName，该项目的pName和pNum，该设备的eName，解决方案的esName,status
            procurementDto.setPrId(procurement.getPrId());
            procurementDto.setURealname(userRepository.findById(procurement.getJcId()).get().getuRealname());
            procurementDto.setPName(projectRepository.findById(equipmentRepository.findById(equipmentSolutionRepository.findById(procurement.getEsId()).get().getEId()).get().getpId()).get().getpName());
            procurementDto.setPNum(projectRepository.findById(equipmentRepository.findById(equipmentSolutionRepository.findById(procurement.getEsId()).get().getEId()).get().getpId()).get().getpNum());
            procurementDto.setType(standardRepository.findById(projectRepository.findById(equipmentRepository.findById(equipmentSolutionRepository.findById(procurement.getEsId()).get().getEId()).get().getpId()).get().getsId()).get().getType());
            procurementDto.setSName(standardRepository.findById(projectRepository.findById(equipmentRepository.findById(equipmentSolutionRepository.findById(procurement.getEsId()).get().getEId()).get().getpId()).get().getsId()).get().getsName());
            procurementDto.setEName(equipmentRepository.findById(equipmentSolutionRepository.findById(procurement.getEsId()).get().getEId()).get().geteName());
            procurementDto.setEsName(equipmentSolutionRepository.findById(procurement.getEsId()).get().getEsName());
            procurementDto.setStatus(procurement.getStatus());
            procurementDtoList.add(procurementDto);
    }
        return procurementDtoList;
    }

    public void edit(ProcurementDto procurementDto) {
        Procurement procurement = procurementRepository.findById(procurementDto.getPrId()).get();
        procurement.setStatus(procurementDto.getStatus());

        procurementRepository.save(procurement);
    }

    public void deleteProcurement(Integer prId) {
        procurementRepository.deleteById(prId);

    }


}
