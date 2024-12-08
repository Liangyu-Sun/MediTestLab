package com.softwaremanage.meditestlab.service.train_module;

import com.softwaremanage.meditestlab.pojo.exam_module.Assessment;
import com.softwaremanage.meditestlab.repository.exam_module.AssessmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AssessmentService {

    @Autowired
    private AssessmentRepository assessmentRepository;

    public Assessment updateAssessmentCompletion(Integer assessmentId, boolean Completed) {
        Optional<Assessment> assessmentOpt = assessmentRepository.findById(assessmentId);
        if (assessmentOpt.isPresent()) {
            Assessment assessment = assessmentOpt.get();
            assessment.setCompleted(Completed);
            return assessmentRepository.save(assessment);
        } else {
            throw new RuntimeException("Assessment not found with id: " + assessmentId);
        }
    }

    public Assessment getAssessmentByUserIdAndProjectId(Integer uId, Integer pId) {
        return assessmentRepository.findByInspectorIdAndProjectId(uId, pId);
    }
    public Assessment saveAssessment(Assessment assessment) {

        return assessmentRepository.save(assessment);

    }
}
