package com.softwaremanage.meditestlab.controller;

import com.softwaremanage.meditestlab.pojo.train_module.Train;
import com.softwaremanage.meditestlab.repository.train_module.ResourceRepository;
import com.softwaremanage.meditestlab.service.train_module.PdfService;
import com.softwaremanage.meditestlab.pojo.exam_module.Assessment;
import com.softwaremanage.meditestlab.service.train_module.*;
import com.softwaremanage.meditestlab.pojo.exam_module.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.List;

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
    @Autowired
    private ResourceService resourceService;
    @Autowired
    private TrainService trainService;


    //上传文件
    @PostMapping(value = "/{projectId}/materials", consumes = "multipart/form-data")
    public ResponseEntity<String> uploadMaterial(
            @PathVariable Integer projectId,
            @RequestParam("file") MultipartFile file,
            @RequestParam("resourceType") String resourceType) {

        try {
            String message = uploadService.uploadMaterial(projectId, file, resourceType);
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
    public ResponseEntity<Resource> generatePdf(@PathVariable Integer trainingId) {
        try {

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment","training_certificate.pdf");

            Resource inputStreamResource = pdfService.generatePdf(trainingId);
            return ResponseEntity.ok().headers(headers).body(inputStreamResource);

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

    //根据项目Id和资源类型来查询所有文件
    @GetMapping("/files")
    public ResponseEntity<List<com.softwaremanage.meditestlab.pojo.train_module.Resource>> getFilesByProjectIdAndResourceType(
            @RequestParam Integer projectId,
            @RequestParam String resourceType) {
        try {
            List<com.softwaremanage.meditestlab.pojo.train_module.Resource> files = resourceService.findByProjectIdAndResourceType(projectId, resourceType);
            return ResponseEntity.ok(files);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    // 添加培训
    @PostMapping("/train")
    public ResponseEntity<Train> createTrain(@RequestBody Train train) {
        Train savedTrain = trainService.saveTrain(train);
        return ResponseEntity.ok(savedTrain);
    }








}
