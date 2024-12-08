package com.softwaremanage.meditestlab.service.train_module;

import com.softwaremanage.meditestlab.pojo.train_module.LearningRecord;
import com.softwaremanage.meditestlab.repository.train_module.LearningRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LearnRecordService {

    @Autowired
    private LearningRecordRepository learningRecordRepository;

    public LearningRecord updateLearningRecord(Integer videoId, LearningRecord updatedRecord) {
        Optional<LearningRecord> existingRecordOpt = learningRecordRepository.findById(videoId);
        if (existingRecordOpt.isPresent()) {
            LearningRecord existingRecord = existingRecordOpt.get();
            existingRecord.setWatchPercentage(updatedRecord.getWatchPercentage());
            existingRecord.setInspectorId(updatedRecord.getInspectorId());
            existingRecord.setProjectId(updatedRecord.getProjectId());
            return learningRecordRepository.save(existingRecord);
        } else {
            throw new RuntimeException("Learning record not found with videoId: " + videoId);
        }
    }

    public LearningRecord getLearningRecordByUserIdAndProjectId(Integer uId, Integer pId) {
        return learningRecordRepository.findByInspectorIdAndProjectId(uId, pId);
    }
}