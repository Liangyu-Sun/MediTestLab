package com.softwaremanage.meditestlab.repository.exam_module;

import com.softwaremanage.meditestlab.pojo.exam_module.Assessment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssessmentRepository extends JpaRepository<Assessment, Integer> {
    Assessment findByInspectorIdAndProjectId(Integer inspectorId, Integer projectId);
    List<Assessment> findByInspectorIdAndCompleted(Integer inspectorId, boolean Completed);


}
