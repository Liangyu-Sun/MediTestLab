package com.softwaremanage.meditestlab.controller;

import com.softwaremanage.meditestlab.pojo.exam_module.Assessment;
import com.softwaremanage.meditestlab.pojo.exam_module.FailureNotification;
import com.softwaremanage.meditestlab.pojo.train_module.LearningRecord;
import com.softwaremanage.meditestlab.service.train_module.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/inspector")
public class InspectorController {

    @Autowired
    private UploadService uploadService;
    @Autowired
    private DownloadService downloadService;

    @Autowired
    private LearnRecordService learnRecordService;

    @Autowired
    private AssessmentService assessmentService;

    @Autowired
    private Pdf2Service pdf2Service;
    @Autowired
    private FailureService failureService;


    //上传文件
    @PostMapping(value = "/{pId}/materials", consumes = "multipart/form-data")
    public ResponseEntity<String> uploadMaterial(
            @PathVariable Integer pId,
            @RequestParam("file") MultipartFile file,
            @RequestParam("resourceType") String resourceType) {

        try {
            String message = uploadService.uploadMaterial(pId, file, resourceType);
            return ResponseEntity.ok(message);
        } catch (IOException e) {
            return ResponseEntity.badRequest().body("Failed to upload the file: " + e.getMessage());
        }
    }

    //下载文件
    @GetMapping("/download/{fileName}")
    public ResponseEntity<org.springframework.core.io.Resource> downloadFile(@PathVariable String fileName) {
        try {

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", fileName);

            Resource inputStreamResource = downloadService.downloadFile(fileName);
            return ResponseEntity.ok().headers(headers).body(inputStreamResource);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
    // 查看学习记录
    @GetMapping("/learningRecord")
    public LearningRecord getLearningRecord(@RequestParam Integer uId, @RequestParam Integer pId) {
        return learnRecordService.getLearningRecordByUserIdAndProjectId(uId, pId);
    }


    //生成考核证书
    @GetMapping("/generate-assessment-pdf/{assessmentId}")
    public ResponseEntity<Resource> generateAssessmentPdf(@PathVariable Integer assessmentId) {
        try {

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment","assessment_certificate.pdf");

            Resource inputStreamResource = pdf2Service.generateAssessmentPdf(assessmentId);
            return ResponseEntity.ok().headers(headers).body(inputStreamResource);

        } catch (IOException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }



    //查看考核结果（通过/未通过）
    @GetMapping("/assessmentResult")
    public Assessment getAssessmentResult(@RequestParam Integer uId, @RequestParam Integer pId) {
        return assessmentService.getAssessmentByUserIdAndProjectId(uId, pId);
    }

    //读取失败通知
    @GetMapping("/failure-notifications")
    public ResponseEntity<List<FailureNotification>> getFailureNotifications() {
        try {
            List<FailureNotification> notifications = failureService.getAllFailureNotifications();
            return ResponseEntity.ok(notifications);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

}
