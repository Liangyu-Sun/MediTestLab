package com.softwaremanage.meditestlab.service.equipment_module;

import com.softwaremanage.meditestlab.pojo.dto.EquipmentSolutionDto;
import com.softwaremanage.meditestlab.pojo.equipment_module.EquipmentSolution;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.softwaremanage.meditestlab.repository.equipment_module.EquipmentSolutionRepository;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class EquipmentSolutionService {
    @Autowired
    private EquipmentSolutionRepository equipmentSolutionRepository;


    public EquipmentSolution add(@Valid EquipmentSolutionDto equipmentSolutionDto) {
        EquipmentSolution equipmentSolution = new EquipmentSolution();
        equipmentSolution.setEsName(equipmentSolutionDto.getEsName());
        equipmentSolution.setManufacturer(equipmentSolutionDto.getManufacturer());
        equipmentSolution.setPhoto(equipmentSolutionDto.getPhoto());
        equipmentSolution.setDetails(equipmentSolutionDto.getDetails());
        return equipmentSolutionRepository.save(equipmentSolution);
    }

    public String savePhoto(MultipartFile photo) {
        // 获取项目根目录
        String projectRoot = System.getProperty("user.dir");
        // 定义保存照片的目录
        String directory = projectRoot + File.separator + "img";
        // 如果目录不存在，则创建目录
        File dir = new File(directory);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        // 生成唯一的文件名
        String fileName = System.currentTimeMillis() + "_" + photo.getOriginalFilename();
        // 定义文件路径
        Path filePath = Paths.get(directory, fileName);

        try {
            // 将文件保存到指定目录
            Files.write(filePath, photo.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
            // 根据需要处理异常
            return null;
        }
//        System.out.println(filePath.toString());
        // 返回文件路径
        return filePath.toString();
    }

    public EquipmentSolution addSolution(EquipmentSolutionDto solutionDto) {
        EquipmentSolution equipmentSolution = new EquipmentSolution();
        equipmentSolution.setEId(solutionDto.getEId());
        equipmentSolution.setEsName(solutionDto.getEsName());
        equipmentSolution.setManufacturer(solutionDto.getManufacturer());
        equipmentSolution.setDetails(solutionDto.getDetails());
        equipmentSolution.setPhoto(solutionDto.getPhoto());
        return equipmentSolutionRepository.save(equipmentSolution);
    }

    public List<EquipmentSolution> findSolutionsByeId(Integer eId) {
        return equipmentSolutionRepository.findAllByeId(eId);
    }

    public void deleteSolution(Integer esId) {
        equipmentSolutionRepository.deleteById(esId);

    }

    public EquipmentSolution editSolution(EquipmentSolutionDto solutionDto) {
        EquipmentSolution equipmentSolution = new EquipmentSolution();
        equipmentSolution.setEsId(solutionDto.getEsId());
        equipmentSolution.setEId(solutionDto.getEId());
        equipmentSolution.setEsName(solutionDto.getEsName());
        equipmentSolution.setManufacturer(solutionDto.getManufacturer());
        equipmentSolution.setDetails(solutionDto.getDetails());
        equipmentSolution.setPhoto(solutionDto.getPhoto());
        return equipmentSolutionRepository.save(equipmentSolution);
    }

    public void deletePhoto(Integer esId) {
        EquipmentSolution equipmentSolution = equipmentSolutionRepository.findById(esId).get();
        String photo = equipmentSolution.getPhoto();
        File file = new File(photo);
        if (file.exists()) {
            file.delete();
        }
    }

    public EquipmentSolution findSolutionByesId(Integer esId) {
        return equipmentSolutionRepository.findById(esId).get();
    }
}
