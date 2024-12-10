package com.softwaremanage.meditestlab.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import com.alibaba.excel.write.builder.ExcelWriterSheetBuilder;
import com.softwaremanage.meditestlab.pojo.ResponseMessage;
import com.softwaremanage.meditestlab.pojo.dto.EquipmentSolutionDto;
import com.softwaremanage.meditestlab.pojo.dto.ProcurementDto;
import com.softwaremanage.meditestlab.pojo.equipment_module.Equipment;
import com.softwaremanage.meditestlab.pojo.equipment_module.EquipmentSolution;
import com.softwaremanage.meditestlab.pojo.equipment_module.Procurement;
import com.softwaremanage.meditestlab.service.equipment_module.EquipmentService;
import com.softwaremanage.meditestlab.service.equipment_module.EquipmentSolutionService;
import com.softwaremanage.meditestlab.service.equipment_module.ProcurementService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;


@RestController
@RequestMapping("/api/equipment")
public class EquipmentController {
    @Autowired
    private EquipmentService equipmentService;

    @Autowired
    private EquipmentSolutionService equipmentSolutionService;

    @Autowired
    private ProcurementService procurementService;

    // 查询某个项目的所有设备
    @GetMapping("/allequipment/{pId}")
    public ResponseMessage<List<Equipment>> findEquipment(@PathVariable Integer pId) {
        List<Equipment> equipmentList = equipmentService.getEquipment(pId);
        return ResponseMessage.success(equipmentList);

}
    //添加设备
    @PostMapping("/addequipment")
    public ResponseMessage addEquipment(@RequestBody Equipment equipment) {
        equipmentService.addEquipment(equipment);
        return ResponseMessage.success("添加成功");
    }
    //修改设备
    @PutMapping("/editequipment")
    public ResponseMessage editEquipment(@RequestBody Equipment equipment) {
        equipmentService.editEquipment(equipment);
        return ResponseMessage.success("修改成功");
    }

    //为某个设备添加其对应的解决方案
    @PostMapping("/addsolution/{eId}")
    public ResponseMessage addSolution(@PathVariable Integer eId,
                                                          @RequestPart(value = "esName", required = false) String esName,
                                                          @RequestPart(value = "manufacturer", required = false) String manufacturer,
                                                          @RequestPart(value = "photo", required = false) MultipartFile photo,
                                                          @RequestPart(value = "details", required = false) String details) {
        if (esName == null || esName.isEmpty()) {
            return ResponseMessage.error("解决方案名称不能为空");
        }
        List<EquipmentSolution> existingSolutions = equipmentSolutionService.findSolutionsByeId(eId);
        if (existingSolutions.size() >= 4) {
            return ResponseMessage.error("该设备的解决方案已达到4个，无法继续添加");
        }


        // 处理文件上传
        String photoUrl = (photo != null) ? equipmentSolutionService.savePhoto(photo) : null;

        // 创建 EquipmentSolutionDto 对象并设置字段
        EquipmentSolutionDto solutionDto = new EquipmentSolutionDto();
        solutionDto.setEId(eId);
        solutionDto.setEsName(esName);
        solutionDto.setManufacturer(manufacturer != null ? manufacturer : "");
        solutionDto.setDetails(details != null ? details : "");
        solutionDto.setPhoto(photoUrl != null ? photoUrl : "");

        // 保存解决方案
        EquipmentSolution solution = equipmentSolutionService.addSolution(solutionDto);
        return ResponseMessage.success(solution);

//        return ResponseMessage.success("solution");
    }


    //查询某个设备对应的解决方案
    @GetMapping("/findsolution/{eId}")
    public ResponseMessage findSolutions(@PathVariable Integer eId) {
        List<EquipmentSolution> solutions = equipmentSolutionService.findSolutionsByeId(eId);
        return ResponseMessage.success(solutions);
    }

    //删除某个解决方案
    @DeleteMapping("/deletesolution/{esId}")
    public ResponseMessage deleteSolution(@PathVariable Integer esId) {
        //删除原来的照片
        equipmentSolutionService.deletePhoto(esId);
        equipmentSolutionService.deleteSolution(esId);
        return ResponseMessage.success("删除成功");
    }

    //修改某个解决方案
    @PutMapping("/editsolution/{esId}")
    public ResponseMessage updateSolution(@PathVariable Integer esId,
                                          @RequestPart(value = "esName", required = false) String esName,
                                          @RequestPart(value = "manufacturer", required = false) String manufacturer,
                                          @RequestPart(value = "photo", required = false) MultipartFile photo,
                                          @RequestPart(value = "details", required = false) String details) {
        if (esName == null || esName.isEmpty()) {
            return ResponseMessage.error("解决方案名称不能为空");
        }
        //删除原来的照片
        equipmentSolutionService.deletePhoto(esId);
        // 处理文件上传
        String photoUrl = (photo != null) ? equipmentSolutionService.savePhoto(photo) : null;

        // 创建 EquipmentSolutionDto 对象并设置字段
        EquipmentSolutionDto solutionDto = new EquipmentSolutionDto();
        solutionDto.setEsId(esId);
        solutionDto.setEsName(esName);
        solutionDto.setManufacturer(manufacturer != null ? manufacturer : "");
        solutionDto.setDetails(details != null ? details : "");
        solutionDto.setPhoto(photoUrl != null ? photoUrl : "");
        //查询esId对应的解决方案的eId并赋值给solutionDto
        EquipmentSolution solution = equipmentSolutionService.findSolutionByesId(esId);
        solutionDto.setEId(solution.getEId());


        EquipmentSolution solution1 = equipmentSolutionService.editSolution(solutionDto);
        return ResponseMessage.success(solution1);

    }

    //检测人员选择某个解决方案,并将其保存到采购表中
    @PostMapping("/choosesolution/{esId}/{uId}")
    public ResponseMessage chooseSolution(@PathVariable Integer esId, @PathVariable Integer uId) {

        Procurement procurement = new Procurement();
        procurement.setEsId(esId);
        procurement.setJcId(uId);
        procurement.setStatus("计划采购");
        procurementService.addProcurement(procurement);
        return ResponseMessage.success("选择成功");
    }

    //检测人员查看自己Id对应的采购表，返回数据包括：检测人员Id对应的uRealname，该标准的type和sName，该项目的pName和pNum，该设备的eName，解决方案的esName,status
    @GetMapping("/findprocurement/{uId}")
    public ResponseMessage findProcurement(@PathVariable Integer uId) {

        List<ProcurementDto> procurementDtos = procurementService.findProcurementByuId(uId);
        if(procurementDtos == null)
            return ResponseMessage.error("暂无计划采购记录");
        return ResponseMessage.success(procurementDtos);
    }

    //支持人员查看所有采购表
    @GetMapping("/allprocurement")
    public ResponseMessage findAllProcurement() {
        List<ProcurementDto> procurementDtos = procurementService.findAllProcurement();
        if(procurementDtos == null)
            return ResponseMessage.error("暂无计划采购记录");
        return ResponseMessage.success(procurementDtos);
    }

    //支持人员修改采购表，uRealname，该标准的type和sName，该项目的pName和pNum，该设备的eName都不可修改
    // 支持人员可修改的东西只有：采购的status
    // 检测人员可修改的东西只有：解决方案的esName，但是检测人员想修改的话，需要先删除原来的解决方案，再重新选择（不调用该接口）
    @PutMapping("/editprocurement")
    public ResponseMessage editProcurement(@RequestBody @Valid List<ProcurementDto> procurementDtos) {
        for (ProcurementDto procurementDto : procurementDtos) {
            procurementService.edit(procurementDto);
        }
        return ResponseMessage.success("修改成功");
    }

    //检测人员删除某个采购表
    @DeleteMapping("/deleteprocurement/{prId}")
    public ResponseMessage deleteProcurement(@PathVariable Integer prId) {
        procurementService.deleteProcurement(prId);
        return ResponseMessage.success("删除成功");
    }

    //支持人员导出所有采购表为excel文件
    @GetMapping("/exportprocurement")
    public void exportProcurement(HttpServletResponse response) throws IOException {
        List<ProcurementDto> procurementDtos = procurementService.findAllProcurement();
        if (procurementDtos == null || procurementDtos.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            return;
        }

        // 设置响应头
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        String fileName = URLEncoder.encode("Equipment_Planned_Purchase_List", "UTF-8");

        response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
//        response.setHeader("Content-Disposition",
//                "attachment; filename=\"" + fileName + ".xlsx\"; filename*=UTF-8''" + fileName + ".xlsx");
        response.setHeader("Content-Disposition", "attachment; filename=" + fileName + ".xlsx");

        // 使用EasyExcel写入数据
        ExcelWriterBuilder writerBuilder = EasyExcel.write(response.getOutputStream(), ProcurementDto.class);
        ExcelWriterSheetBuilder sheetBuilder = writerBuilder.sheet("设备计划采购清单");
        sheetBuilder.doWrite(procurementDtos);
    }

    //检测人员留言，留言类型为增加设备
    @PostMapping("/message_equipment/{uId}/{pId}")
    public ResponseMessage message_equipment(@PathVariable Integer uId, @PathVariable Integer pId, @RequestBody String message) {
        equipmentService.addMessage(uId, pId, message, "增加设备");
        return ResponseMessage.success("留言成功");
    }

    //检测人员留言，留言类型为增加解决方案
    @PostMapping("/message_equipmentsolution/{uId}/{pId}")
    public ResponseMessage message_equipmentsolution(@PathVariable Integer uId, @PathVariable Integer pId, @RequestBody String message) {
        equipmentService.addMessage(uId, pId, message, "增加解决方案");
        return ResponseMessage.success("留言成功");
    }




}
