package com.softwaremanage.meditestlab.controller;


import com.softwaremanage.meditestlab.pojo.ResponseMessage;
import com.softwaremanage.meditestlab.service.pre_launch_project_module.StandardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/standard")
public class StandardController {

    @Autowired
    private StandardService standardService;

    @PostMapping("/importExcel")
    public ResponseMessage<String> importExcel(@RequestParam("file") MultipartFile file) {
        String message = standardService.importExcel(file);
        return ResponseMessage.success(message);

    }

    @DeleteMapping("/{sId}")
    public ResponseMessage<String> deleteStandard(@PathVariable Integer sId) {
        standardService.deleteStandard(sId);
        return ResponseMessage.success("删除成功");
    }

}
