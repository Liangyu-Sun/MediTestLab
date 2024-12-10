package com.softwaremanage.meditestlab.service.equipment_module;

import com.softwaremanage.meditestlab.pojo.equipment_module.Equipment;
import com.softwaremanage.meditestlab.pojo.comment_module.Comment;
import com.softwaremanage.meditestlab.repository.comment_module.CommentRepository;
import com.softwaremanage.meditestlab.repository.equipment_module.EquipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EquipmentService {
    @Autowired
    private EquipmentRepository equipmentRepository;

    @Autowired
    private CommentRepository commentRepository;

    public List<Equipment> getEquipment(Integer pId) {
        return equipmentRepository.findAllBypId(pId);
    }

    public void addEquipment(Equipment equipment) {
        equipmentRepository.save(equipment);
    }

    public void editEquipment(Equipment equipment) {
        //先找到原来该eId对应的设备，把pId赋值给新的设备
        Equipment oldEquipment = equipmentRepository.findById(equipment.geteId()).get();
        equipment.setpId(oldEquipment.getpId());
        equipmentRepository.save(equipment);
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
