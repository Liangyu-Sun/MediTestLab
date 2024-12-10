package com.softwaremanage.meditestlab.repository.exam_module;

import com.softwaremanage.meditestlab.pojo.exam_module.FailureNotification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FailureNotificationRepository extends JpaRepository<FailureNotification, Integer> {
    List<FailureNotification> findByInspectorId(Integer inspectorId);
}
