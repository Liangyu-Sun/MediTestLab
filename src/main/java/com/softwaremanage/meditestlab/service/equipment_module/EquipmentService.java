package com.softwaremanage.meditestlab.service.equipment_module;

import com.softwaremanage.meditestlab.pojo.equipment_module.Equipment;
import com.softwaremanage.meditestlab.repository.equipment_module.EquipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EquipmentService {
    @Autowired
    private EquipmentRepository equipmentRepository;

    public List<Equipment> getEquipment(Integer pId) {
        return equipmentRepository.findAllBypId(pId);
    }
}
