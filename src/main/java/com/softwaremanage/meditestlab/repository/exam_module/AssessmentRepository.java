package com.softwaremanage.meditestlab.repository.exam_module;

import com.softwaremanage.meditestlab.pojo.exam_module.Assessment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AssessmentRepository extends JpaRepository<Assessment, Integer> {
    Assessment findByInspectorIdAndProjectId(Integer inspectorId, Integer projectId);
    List<Assessment> findByInspectorIdAndCompleted(Integer inspectorId, boolean Completed);

    @Query("SELECT a FROM Assessment a WHERE a.inspectorId = :uId AND a.projectId = :pId ORDER BY a.completionTime DESC")
    Optional<Assessment> findLatestByInspectorIdAndProjectId(@Param("uId") Integer uId, @Param("pId") Integer pId);
}
