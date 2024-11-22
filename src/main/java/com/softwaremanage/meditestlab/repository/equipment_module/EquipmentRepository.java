package com.softwaremanage.meditestlab.repository.equipment_module;

import com.softwaremanage.meditestlab.pojo.equipment_module.Equipment;
import com.softwaremanage.meditestlab.pojo.pre_launch_project_module.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EquipmentRepository extends JpaRepository<Equipment, Integer> {
    void deleteAllBypIdIn(List<Integer> pIds);
}
