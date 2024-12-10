package com.softwaremanage.meditestlab.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import com.alibaba.excel.write.builder.ExcelWriterSheetBuilder;
import com.softwaremanage.meditestlab.pojo.ResponseMessage;
import com.softwaremanage.meditestlab.pojo.dto.ApplicationDto;
import com.softwaremanage.meditestlab.pojo.dto.ProcurementDto;
import com.softwaremanage.meditestlab.pojo.dto.SampleDto;
import com.softwaremanage.meditestlab.pojo.dto.SampleListDto;
import com.softwaremanage.meditestlab.pojo.sample_module.Application;
import com.softwaremanage.meditestlab.pojo.sample_module.Sample;
import com.softwaremanage.meditestlab.service.sample_module.ApplicationService;
import com.softwaremanage.meditestlab.service.sample_module.SampleService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

@RestController
@RequestMapping("/api/sample")
public class SampleController {
    @Autowired
    private SampleService sampleService;

    @Autowired
    private ApplicationService applicationService;


    @PostMapping("/addSample")
    public ResponseMessage addSample(@RequestBody SampleDto sampleDto) {
        System.out.println(sampleDto);
        sampleService.addSample(sampleDto);
        return ResponseMessage.success("添加成功");
    }
    //查找某个标准对应的所有样品
    @GetMapping("/allSample/{sId}")
    public ResponseMessage allSample(@PathVariable Integer sId) {
        //如果不是空的，返回success，否则返回error
        System.out.println(sampleService.allSample(sId));
        if(sampleService.allSample(sId).isEmpty())
            return ResponseMessage.error("暂无样品记录");
        return ResponseMessage.success(sampleService.allSample(sId));
    }

    //删除某个样品
    @DeleteMapping("/deleteSample/{saId}")
    public ResponseMessage deleteSample(@PathVariable Integer saId) {
        sampleService.deleteSample(saId);
        return ResponseMessage.success("删除成功");
    }
    //某个检测人员申请某个样品，申请之前查询是否已有该uId和saId对应的申请，若有且状态为“申请需求”，则提示“该样品正在申请中”
    @PostMapping("/applySample")
    public ResponseMessage applySample(@RequestBody ApplicationDto applicationDto) {
        //某个检测人员申请某个样品，申请之前查询是否已有该uId和saId对应的申请，若有且状态为“申请需求”，则提示“该样品正在申请中”,若没有或者状态不为“申请完成”，则添加申请
        System.out.println(applicationDto);
        List<Application> applicationList = applicationService.applySample(applicationDto);
        if (applicationList.size() == 0) {
            applicationService.addApplication(applicationDto);
            return ResponseMessage.success("申请成功");
        } else {
            return ResponseMessage.error("该样品正在申请中");
        }
    }
    //查找所有样品申请
    @GetMapping("/allApplication")
    public ResponseMessage allApplication() {
        List<SampleListDto> sampleListDtos = applicationService.allApplication();
        if(sampleListDtos == null)
            return ResponseMessage.error("暂无样品申请记录");
        return ResponseMessage.success(sampleListDtos);
    }

    //查找某个检测人员对应的所有样品申请
    @GetMapping("/allApplication/{uId}")
    public ResponseMessage allApplicationByuId(@PathVariable Integer uId) {
        List<SampleListDto> sampleListDtos = applicationService.allApplicationByuId(uId);
        if(sampleListDtos == null)
            return ResponseMessage.error("暂无样品申请记录");
        return ResponseMessage.success(sampleListDtos);
    }

    //支持人员修改某条申请对应的实际数量
    @PutMapping("/updateApplication")
    public ResponseMessage updateApplication(@RequestBody ApplicationDto applicationDto) {
        applicationService.updateApplication(applicationDto);
        return ResponseMessage.success("修改成功");
    }
    //检测人员修改某条申请对应的申请需求
    @PutMapping("/updateApplicationDemand")
    public ResponseMessage updateApplicationDemand(@RequestBody ApplicationDto applicationDto) {
        applicationService.updateApplicationDemand(applicationDto);
        return ResponseMessage.success("修改成功");
    }
    //检测人员删除某条申请
    @DeleteMapping("/deleteApplication/{aId}")
    public ResponseMessage deleteApplication(@PathVariable Integer aId) {
        applicationService.deleteApplication(aId);
        return ResponseMessage.success("删除成功");
    }
    //支持人员导出样品申请清单为excel文件
    @GetMapping("/exportsampleList")
    public void exportsampleList(HttpServletResponse response) throws IOException {
        List<SampleListDto> sampleListDtos = applicationService.allApplication();
        if (sampleListDtos == null || sampleListDtos.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            return;
        }

        // 设置响应头
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        String fileName = URLEncoder.encode("Sample_Request_List", "UTF-8");

        response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
//        response.setHeader("Content-Disposition",
//                "attachment; filename=\"" + fileName + ".xlsx\"; filename*=UTF-8''" + fileName + ".xlsx");
        response.setHeader("Content-Disposition", "attachment; filename=" + fileName + ".xlsx");

        // 使用EasyExcel写入数据
        ExcelWriterBuilder writerBuilder = EasyExcel.write(response.getOutputStream(), SampleListDto.class);
        ExcelWriterSheetBuilder sheetBuilder = writerBuilder.sheet("样品需求清单");
        sheetBuilder.doWrite(sampleListDtos);
    }

    //检测人员留言，留言类型为增加样品信息
    @PostMapping("/message_sample/{uId}/{pId}")
    public ResponseMessage message_sample(@PathVariable Integer uId, @PathVariable Integer pId, @RequestBody String message) {
        sampleService.addMessage(uId, pId, message, "增加样品信息");
        return ResponseMessage.success("留言成功");
    }

}
