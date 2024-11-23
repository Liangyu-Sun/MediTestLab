package com.softwaremanage.meditestlab.repository.equipment_module;

import com.softwaremanage.meditestlab.pojo.equipment_module.EquipmentSolution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EquipmentSolutionRepository extends JpaRepository<EquipmentSolution, Integer> {

    List<EquipmentSolution> findAllByeId(Integer eId);
}
