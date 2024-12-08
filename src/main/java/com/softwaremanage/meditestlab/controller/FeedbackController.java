package com.softwaremanage.meditestlab.controller;

import com.softwaremanage.meditestlab.pojo.train_module.LearningRecord;
import com.softwaremanage.meditestlab.service.train_module.LearnRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/feedback")
public class FeedbackController {

    @Autowired
    private LearnRecordService learnRecordService;
    //修改学习记录
    @PutMapping("/updateLearningRecord/{videoId}")
    public LearningRecord updateLearningRecord(
            @PathVariable Integer videoId,
            @RequestBody LearningRecord updatedRecord) {
        return learnRecordService.updateLearningRecord(videoId, updatedRecord);
    }
}
