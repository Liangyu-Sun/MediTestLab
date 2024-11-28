package com.softwaremanage.meditestlab.controller;

import com.softwaremanage.meditestlab.pojo.regulation_module.ComparisonTest;
import com.softwaremanage.meditestlab.pojo.regulation_module.Regulation;
import com.softwaremanage.meditestlab.pojo.regulation_module.TestPersonnel;
import com.softwaremanage.meditestlab.service.regulation_module.RegulationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/regulations")
public class RegulationController {

    @Autowired
    private RegulationService regulationService;

    // 上传规程PDF
    @PostMapping("/upload")
    public String uploadRegulationPDF(@RequestParam("file") MultipartFile file, @RequestParam("projectId") Integer projectId) {
        try {
            regulationService.uploadRegulationPDF(file, projectId);
            return "上传成功";
        } catch (Exception e) {
            return "上传失败：" + e.getMessage();
        }
    }

    // 查询规程PDF
    @GetMapping("/{id}")
    public Regulation getRegulationById(@PathVariable("id") Integer id) {
        try {
            return regulationService.getRegulationById(id);
        } catch (Exception e) {
            // 如果发生异常，打印日志并返回合适的错误信息
            e.printStackTrace();
            return null; // 可以改成返回一个更友好的错误信息或自定义异常
        }
    }


    // 替换规程PDF
    @PutMapping("/replace/{id}")
    public String replaceRegulation(@PathVariable("id") Integer id, @RequestParam("file") MultipartFile file) {
        try {
            regulationService.replaceRegulationPDF(id, file);
            return "规程PDF替换成功！";
        } catch (Exception e) {
            return "规程PDF替换失败：" + e.getMessage();
        }
    }

    // 添加比对测试方案
    @PostMapping("/comparison-test")
    public String addComparisonTest(@RequestBody ComparisonTest comparisonTest) {
        try {
            regulationService.addComparisonTest(comparisonTest);
            return "比对测试方案添加成功！";
        } catch (Exception e) {
            return "比对测试方案添加失败：" + e.getMessage();
        }
    }

    // 获取比对测试清单
    @GetMapping("/comparison-test-list/{projectId}")
    public List<ComparisonTest> getComparisonTestList(@PathVariable("projectId") Integer projectId) {
        return regulationService.getComparisonTestList(projectId);
    }

    //修改比对测试状态和完成时间
    @PutMapping("/comparison-test/{testId}")
    public String updateComparisonTest(@PathVariable("testId") Integer testId, @RequestBody ComparisonTest updatedTest) {
        try {
            regulationService.updateComparisonTestStatus(testId, updatedTest);
            return "比对测试更新成功！";
        } catch (Exception e) {
            return "比对测试更新失败：" + e.getMessage();
        }
    }

    //申请比对测试
    @PostMapping("/apply-comparison-test/{testId}")
    public String applyForComparisonTest(@PathVariable("testId") Integer testId, @RequestBody TestPersonnel personnel) {
        try {
            regulationService.applyForComparisonTest(testId, personnel);
            return "比对测试申请成功！";
        } catch (Exception e) {
            return "比对测试申请失败：" + e.getMessage();
        }
    }

    //查询是否有再次申请权限
    @GetMapping("/can-apply/{testId}")
    public boolean canApplyForComparisonTest(@PathVariable("testId") Integer testId, @RequestParam Integer personnelId) {
        return regulationService.canApplyForComparisonTest(testId, personnelId);
    }

    //查询自己计划和完成的比对测试
    @GetMapping("/my-comparison-tests/{personnelId}")
    public List<ComparisonTest> getMyComparisonTests(@PathVariable("personnelId") Integer personnelId,
                                                     @RequestParam(value = "status", defaultValue = "") String status) {
        return regulationService.getMyComparisonTests(personnelId, status);
    }

}
