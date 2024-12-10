package com.softwaremanage.meditestlab.service.train_module;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DownloadService {

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Autowired
    private ResourceService resourceService;

    public Resource downloadFile(String fileName) throws Exception {
        Path filePath = Paths.get(uploadDir).resolve(fileName).normalize();
        if (Files.exists(filePath)) {
            InputStream inputStream = Files.newInputStream(filePath);
            return new InputStreamResource(inputStream);
        } else {
            throw new Exception("File not found " + fileName);
        }
    }


}
