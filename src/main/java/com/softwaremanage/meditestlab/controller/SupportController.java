package com.softwaremanage.meditestlab.controller;

import com.softwaremanage.meditestlab.pojo.train_module.Resource;
import com.softwaremanage.meditestlab.service.train_module.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@RestController
@RequestMapping("/api/support")
public class SupportController {

    @Autowired
    private ResourceService resourceService;

    @Value("${file.upload-dir}")
    private String uploadDir;

    @PostMapping(value = "/{pId}/materials", consumes = "multipart/form-data")
    public ResponseEntity<String> uploadMaterial(
            @PathVariable Long pId,
            @RequestParam("file") MultipartFile file) {

        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("File is empty");
        }

        try {
            // 生成唯一的文件名
            String originalFilename = file.getOriginalFilename();
            String uniqueFileName = UUID.randomUUID().toString() + "_" + originalFilename;

            // 构建文件路径
            Path filePath = Paths.get(uploadDir, uniqueFileName);
            Files.createDirectories(filePath.getParent()); // 确保目录存在
            Files.copy(file.getInputStream(), filePath);

            // 将文件内容写入本地文件系统
            Files.copy(file.getInputStream(), filePath);

            // 保存文件信息到数据库
            Resource resource = new Resource();
            resource.setVUrl(uniqueFileName); // 存储相对路径
            resource.setPId(pId);
            resource.setResourceType("material");
            resourceService.save(resource);

            return ResponseEntity.ok("Material " + originalFilename + " uploaded successfully!");
        } catch (IOException e) {
            return ResponseEntity.badRequest().body("Failed to upload the file: " + e.getMessage());
        }
    }
}
