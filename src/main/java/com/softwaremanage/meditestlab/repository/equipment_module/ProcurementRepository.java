package com.softwaremanage.meditestlab.repository.equipment_module;

import com.softwaremanage.meditestlab.pojo.equipment_module.Procurement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProcurementRepository extends JpaRepository<Procurement, Integer> {
    List<Procurement> findProcurementByjcId(Integer uId);
}
