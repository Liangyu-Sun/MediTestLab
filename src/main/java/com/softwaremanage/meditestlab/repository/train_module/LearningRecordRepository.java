package com.softwaremanage.meditestlab.repository.train_module;

import com.softwaremanage.meditestlab.pojo.train_module.LearningRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LearningRecordRepository extends JpaRepository<LearningRecord, Integer> {
    LearningRecord findByInspectorIdAndProjectId(Integer inspectorId, Integer projectId);
}
