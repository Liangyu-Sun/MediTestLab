package com.softwaremanage.meditestlab.controller;

import com.alibaba.excel.EasyExcel;
import com.softwaremanage.meditestlab.pojo.dto.ComparisonTestDto;
import com.softwaremanage.meditestlab.pojo.dto.RegulationDto;
import com.softwaremanage.meditestlab.pojo.regulation_module.ComparisonTest;
import com.softwaremanage.meditestlab.pojo.regulation_module.Regulation;
import com.softwaremanage.meditestlab.pojo.regulation_module.TestPersonnel;
import com.softwaremanage.meditestlab.repository.regulation_module.ComparisonTestRepository;
import com.softwaremanage.meditestlab.service.regulation_module.RegulationService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;
import java.util.stream.Collectors;

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

    // 添加比对测试方案（支持文件上传）
    @PostMapping("/comparison-test")
    public String addComparisonTest(
            @RequestPart("file") MultipartFile file,
            @RequestParam("projectId") Integer projectId,
            @RequestParam("status") String status,
            @RequestParam(value = "plannedTime", required = false) String plannedTime,
            @RequestParam(value = "completionTime", required = false) String completionTime) {
        try {
            // 保存上传的文件
            String filePath = regulationService.saveComparisonTestFile(file);

            // 创建 ComparisonTest 对象
            ComparisonTest comparisonTest = new ComparisonTest();
            comparisonTest.setTestPlanStorageAddress(filePath); // 修改这里
            comparisonTest.setProjectId(projectId);
            comparisonTest.setStatus(status);
            comparisonTest.setPlannedTime(plannedTime);
            comparisonTest.setCompletionTime(completionTime);

            // 调用 Service 保存比对测试信息
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

    // 检查比对测试是否存在
    @GetMapping("/exists/{testId}")
    public boolean isComparisonTestExist(@PathVariable("testId") Integer testId) {
        return regulationService.isComparisonTestExist(testId);
    }
    
    //查询自己计划和完成的比对测试
    @GetMapping("/my-comparison-tests/{personnelId}")
    public List<ComparisonTest> getMyComparisonTests(@PathVariable("personnelId") Integer personnelId,
                                                     @RequestParam(value = "status", defaultValue = "") String status) {
        return regulationService.getMyComparisonTests(personnelId, status);
    }

    @GetMapping("/export-comparison-tests")
    public void exportComparisonTests(HttpServletResponse response) throws IOException {
        List<ComparisonTestDto> comparisonTestDtos = regulationService.getComparisonTestDtoList();
        if (comparisonTestDtos.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            return;
        }

        // 设置响应头
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        String fileName = URLEncoder.encode("Comparison_Tests", "UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=" + fileName + ".xlsx");

        // 写入数据到 Excel
        EasyExcel.write(response.getOutputStream(), ComparisonTestDto.class)
                .sheet("比对测试清单")
                .doWrite(comparisonTestDtos);
    }

    public void exportRegulationList(HttpServletResponse response) throws IOException {
        List<RegulationDto> regulationDtoList = regulationService.getRegulationDtoList();

        // 设置响应头
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        String fileName = URLEncoder.encode("Regulation_List", "UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=" + fileName + ".xlsx");

        // 使用 EasyExcel 写入数据
        EasyExcel.write(response.getOutputStream(), RegulationDto.class)
                .sheet("规程清单")
                .doWrite(regulationDtoList);
    }
}
