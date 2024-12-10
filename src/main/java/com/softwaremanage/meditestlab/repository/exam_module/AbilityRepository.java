package com.softwaremanage.meditestlab.repository.exam_module;

import com.softwaremanage.meditestlab.pojo.train_module.Ability;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AbilityRepository extends JpaRepository<Ability, Long> {
    List<Ability> findByInspectorId(Integer inspectorId);

}
