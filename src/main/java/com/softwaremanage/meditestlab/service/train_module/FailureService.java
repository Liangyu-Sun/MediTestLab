package com.softwaremanage.meditestlab.service.train_module;

import com.softwaremanage.meditestlab.pojo.exam_module.FailureNotification;
import com.softwaremanage.meditestlab.repository.exam_module.FailureNotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FailureService {

    @Autowired
    private FailureNotificationRepository failureNotificationRepository;

    public FailureNotification addFailureNotification(FailureNotification failure) {
        return failureNotificationRepository.save(failure);
    }

    public List<FailureNotification> getAllFailureNotifications() {
        return failureNotificationRepository.findAll();
    }

    public List<FailureNotification> getFailureNotificationsByInspectorId(Integer inspectorId) {
        return failureNotificationRepository.findByInspectorId(inspectorId);
    }
}