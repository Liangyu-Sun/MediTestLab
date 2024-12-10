package com.softwaremanage.meditestlab.controller;

import com.softwaremanage.meditestlab.pojo.exam_module.Assessment;
import com.softwaremanage.meditestlab.pojo.exam_module.FailureNotification;
import com.softwaremanage.meditestlab.pojo.train_module.LearningRecord;
import com.softwaremanage.meditestlab.service.train_module.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;

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
    @Autowired
    private ResourceService resourceService;

    //上传文件
    @PostMapping(value = "/{projectId}/materials", consumes = "multipart/form-data")
    public ResponseEntity<String> uploadMaterial(
            @PathVariable Integer projectId,
            @RequestParam("file") MultipartFile file,
            @RequestParam("resourceType") String resourceType,
            @RequestParam("inspectorId") Integer inspectorId) {

        try {
            System.out.print(projectId);
            String uniqueFileName = uploadService.uploadMaterial(projectId, file, resourceType);

            if ("答题卡".equals(resourceType) || "操作视频".equals(resourceType)) {
                Assessment assessment;
                try {
                    assessment = assessmentService.getAssessmentByInspectorIdAndProjectId(inspectorId, projectId);
                } catch (RuntimeException e) {
                    // 如果未找到记录，则创建新的 Assessment 记录
                    assessment = new Assessment();
                    assessment.setInspectorId(inspectorId);
                    assessment.setProjectId(projectId);
                }
                if ("答题卡".equals(resourceType)) {
                    assessment.setAnswerSheetUrl(uniqueFileName);
                } else if ("操作视频".equals(resourceType)) {
                    assessment.setOperationVideoUrl(uniqueFileName);
                }
                assessment.setCompletionTime(new Date());
                assessmentService.saveAssessment(assessment);
            }

            return ResponseEntity.ok("Material " + file.getOriginalFilename() + " uploaded successfully!");
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


    //根据检测人员Id和项目Id来查询考核记录
    @GetMapping("/assessment")
    public Assessment getAssessment(@RequestParam Integer uId, @RequestParam Integer pId) {
        return assessmentService.getAssessmentByInspectorIdAndProjectId(uId, pId);
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
        return assessmentService.getAssessmentByInspectorIdAndProjectId(uId, pId);
    }

    // 读取失败通知
    @GetMapping("/failure-notifications")
    public ResponseEntity<List<FailureNotification>> getFailureNotifications(@RequestParam Integer inspectorId) {
        try {
            List<FailureNotification> notifications = failureService.getFailureNotificationsByInspectorId(inspectorId);
            return ResponseEntity.ok(notifications);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    //根据项目Id和资源类型来查询所有文件
    @GetMapping("/files/{pId}/{resourceType}")
    public ResponseEntity<List<com.softwaremanage.meditestlab.pojo.train_module.Resource>> getFilesByProjectIdAndResourceType(
            @PathVariable Integer pId,
            @PathVariable String resourceType) {
        try {
            List<com.softwaremanage.meditestlab.pojo.train_module.Resource> files = resourceService.findByProjectIdAndResourceType(pId, resourceType);
            return ResponseEntity.ok(files);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

}
