package com.softwaremanage.meditestlab.controller;

import com.softwaremanage.meditestlab.pojo.exam_module.Assessment;
import com.softwaremanage.meditestlab.service.train_module.*;
import com.softwaremanage.meditestlab.pojo.exam_module.*;
import org.hibernate.sql.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
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
    private UploadService uploadService;
    @Autowired
    private DownloadService downloadService;
    @Autowired
    private FailureService failureService;
    @Autowired
    private AssessmentService assessmentService;
    @Autowired
    private AbilityService abilityService;
    @Autowired
    private PdfService pdfService;


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

    //增加失败通知
    @PostMapping("/failure")
    public FailureNotification addFailureNotification(@RequestBody FailureNotification failure) {
        return failureService.addFailureNotification(failure);
    }


    //修改考核
    @PutMapping("/assessment/{assessmentId}/completion")
    public Assessment updateAssessmentCompletion(
            @PathVariable Integer assessmentId,
            @RequestParam boolean isCompleted) {
        return assessmentService.updateAssessmentCompletion(assessmentId, isCompleted);
    }
    //添加考核
    @PostMapping("/assessment")
    public ResponseEntity<Assessment> createAssessment(@RequestBody Assessment assessment) {
        Assessment savedAssessment = assessmentService.saveAssessment(assessment);
        return ResponseEntity.ok(savedAssessment);
    }

    //生成pdf文件
    @GetMapping("/generate-pdf/{trainingId}")
    public ResponseEntity<byte[]> generatePdf(@PathVariable Integer trainingId) {
        try {
            byte[] pdfBytes = pdfService.generatePdf(trainingId);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=training_certificate.pdf")
                    .contentType(org.springframework.http.MediaType.APPLICATION_PDF)
                    .body(pdfBytes);
        } catch (IOException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }


    //读写人员能力清单
    @PostMapping("/ability/{inspectorId}")
    public ResponseEntity<String> readAndWriteAbilityList(@PathVariable Integer inspectorId) {
        try {
            abilityService.readAndWriteAbilityList(inspectorId);
            return ResponseEntity.ok("Ability list updated successfully.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to update ability list: " + e.getMessage());
        }
    }





}
