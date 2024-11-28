package com.softwaremanage.meditestlab.service.regulation_module;

import com.softwaremanage.meditestlab.pojo.regulation_module.Regulation;
import com.softwaremanage.meditestlab.pojo.regulation_module.ComparisonTest;
import com.softwaremanage.meditestlab.pojo.regulation_module.TestPersonnel;
import com.softwaremanage.meditestlab.repository.regulation_module.RegulationRepository;
import com.softwaremanage.meditestlab.repository.regulation_module.ComparisonTestRepository;
import com.softwaremanage.meditestlab.repository.regulation_module.TestPersonnelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RegulationService {

    @Autowired
    private RegulationRepository regulationRepository;

    @Autowired
    private ComparisonTestRepository comparisonTestRepository;

    @Autowired
    private TestPersonnelRepository testPersonnelRepository;

    // 上传规程PDF
    public void uploadRegulationPDF(MultipartFile file, Integer projectId) throws IOException {
        // 直接使用传入的 projectId 来进行文件上传和存储
        long count = regulationRepository.countByProjectId(projectId);  // 查询该项目下已有的规程数量

        if (count >= 4) {
            throw new RuntimeException("项目已上传4个规程，不能再上传。");
        }

        // 上传文件并存储
        File targetFile = new File("/regulations/" + file.getOriginalFilename());
        file.transferTo(targetFile);

        Regulation regulation = new Regulation();
        regulation.setProjectId(projectId);  // 关联项目ID
        regulation.setFilePath(targetFile.getAbsolutePath());
        regulationRepository.save(regulation);
    }

    // 替换规程PDF
    public void replaceRegulationPDF(Integer id, MultipartFile file) throws IOException {
        Regulation regulation = regulationRepository.findById(id).orElseThrow(() -> new RuntimeException("规程未找到"));
        File targetFile = new File("/regulations/" + file.getOriginalFilename());
        file.transferTo(targetFile);

        regulation.setFilePath(targetFile.getAbsolutePath());
        regulationRepository.save(regulation);
    }

    // 获取规程
    public Regulation getRegulationById(Integer id) {
        return regulationRepository.findById(id).orElseThrow(() -> new RuntimeException("规程未找到"));
    }

    // 添加比对测试方案
    public void addComparisonTest(ComparisonTest comparisonTest) {
        comparisonTestRepository.save(comparisonTest);
    }

    // 获取比对测试清单
    public List<ComparisonTest> getComparisonTestList(Integer projectId) {
        return comparisonTestRepository.findByProjectId(projectId);
    }

    //修改比对测试状态和完成时间
    public void updateComparisonTestStatus(Integer testId, ComparisonTest updatedTest) {
        ComparisonTest existingTest = comparisonTestRepository.findById(testId)
                .orElseThrow(() -> new RuntimeException("比对测试未找到"));

        existingTest.setStatus(updatedTest.getStatus());  // 更新状态
        existingTest.setCompletionTime(updatedTest.getCompletionTime());  // 更新完成时间

        comparisonTestRepository.save(existingTest);  // 保存修改
    }

    //申请比对测试
    public void applyForComparisonTest(Integer testId, TestPersonnel personnel) {
        // 检查检测人员是否有权限申请
        ComparisonTest test = comparisonTestRepository.findById(testId)
                .orElseThrow(() -> new RuntimeException("比对测试未找到"));
        // 如果可以，添加人员到测试
        TestPersonnel newPersonnel = new TestPersonnel();
        newPersonnel.setTestId(testId);
        newPersonnel.setPersonnelId(personnel.getPersonnelId());
        testPersonnelRepository.save(newPersonnel);
    }

    //查询是否有再次申请权限
    public boolean canApplyForComparisonTest(Integer testId, Integer personnelId) {
        // 查询检测人员是否已申请过该比对测试
        List<TestPersonnel> personnelList = testPersonnelRepository.findByTestIdAndPersonnelId(testId, personnelId);
        return personnelList.isEmpty();
    }

    // 查询检测人员计划和完成的比对测试
    public List<ComparisonTest> getMyComparisonTests(Integer personnelId, String status) {

        // 查找所有的 TestPersonnel 记录
        List<TestPersonnel> testPersonnelList = testPersonnelRepository.findByPersonnelId(personnelId);

        // 如果没有找到 TestPersonnel，返回空列表
        if (testPersonnelList.isEmpty()) {
            return new ArrayList<>();
        }

        // 提取所有的 testId
        List<Integer> testIds = testPersonnelList.stream()
                .map(TestPersonnel::getTestId)
                .toList();

        // 创建一个空的列表来存储查询结果
        List<ComparisonTest> comparisonTests = new ArrayList<>();

        // 根据 status 查询比对测试记录
        if (status == null || status.isEmpty()) {
            for (Integer testId : testIds) {
                comparisonTests.addAll(comparisonTestRepository.findByTestId(testId));
            }
        } else {
            for (Integer testId : testIds) {
                comparisonTests.addAll(comparisonTestRepository.findByTestIdAndStatus(testId, status));
            }
        }

        return comparisonTests;
    }



}
