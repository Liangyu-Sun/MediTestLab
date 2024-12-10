package com.softwaremanage.meditestlab.service.sample_module;

import com.softwaremanage.meditestlab.pojo.comment_module.Comment;
import com.softwaremanage.meditestlab.pojo.dto.SampleDto;
import com.softwaremanage.meditestlab.pojo.sample_module.Sample;
import com.softwaremanage.meditestlab.repository.comment_module.CommentRepository;
import com.softwaremanage.meditestlab.repository.sample_module.SampleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SampleService {
    @Autowired
    private SampleRepository sampleRepository;

    @Autowired
    private CommentRepository commentRepository;

    public void addSample(SampleDto sampleDto) {
        System.out.println(sampleDto);
        Sample sample = new Sample();
        sample.setSaName(sampleDto.getSaName());
        sample.setSaModel(sampleDto.getSaModel());
        sample.setSaFactory(sampleDto.getSaFactory());
        sample.setSaBatch(sampleDto.getSaBatch());
        sample.setSId(sampleDto.getSId());
        sampleRepository.save(sample);
    }

    //查找某个标准对应的所有样品
    public List<Sample> allSample(Integer sId) {
        return sampleRepository.findAllBysId(sId);

    }

    public void deleteSample(Integer saId) {
        sampleRepository.deleteById(saId);
    }


    public void addMessage(Integer uId, Integer pId, String message, String commentType) {
        // 保存留言
        Comment comment = new Comment();
        comment.setDetectorId(uId);
        comment.setProjectId(pId);
        comment.setDescription(message);
        comment.setApplyTime(LocalDateTime.now());
        comment.setCommentType(commentType);
        commentRepository.save(comment);

    }
}
