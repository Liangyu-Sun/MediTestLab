package com.softwaremanage.meditestlab.service.train_module;

import com.softwaremanage.meditestlab.pojo.train_module.Resource;
import com.softwaremanage.meditestlab.service.train_module.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class UploadService {

    @Autowired
    private ResourceService resourceService;

    @Value("${file.upload-dir}")
    private String uploadDir;

    public String uploadMaterial(Integer pId, MultipartFile file, String resourceType) throws IOException {
        if (file.isEmpty()) {
            throw new IOException("File is empty");
        }

        // 生成唯一的文件名
        String originalFilename = file.getOriginalFilename();
        String uniqueFileName = UUID.randomUUID().toString() + "_" + originalFilename;

        // 构建文件路径
        Path filePath = Paths.get(uploadDir, uniqueFileName);
        // 确保目录存在
        Files.createDirectories(filePath.getParent()); // Ensure the directory exists
        //将文件内容写入本地文件系统
        Files.copy(file.getInputStream(), filePath);

        // 保存文件信息到数据库
        Resource resource = new Resource();
        resource.setVUrl(uniqueFileName); // 存储相对路径
        resource.setProjectId(pId);
        resource.setResourceType(resourceType);
        resourceService.save(resource);

        return uniqueFileName;
    }
}