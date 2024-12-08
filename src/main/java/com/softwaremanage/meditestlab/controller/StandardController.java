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

    //查询所有标准
    @GetMapping("/allstandards")
    public ResponseMessage getStandards() {
        return ResponseMessage.success(standardService.getStandards());
    }


    @DeleteMapping("/delete/{sId}")
    public ResponseMessage<String> deleteStandard(@PathVariable Integer sId) {
        standardService.deleteStandard(sId);
        return ResponseMessage.success("删除成功");
    }

    //查询某个标准对应的项目
    @GetMapping("/projects/{sId}")
    public ResponseMessage getProjects(@PathVariable Integer sId) {
        return ResponseMessage.success(standardService.getProjects(sId));
    }




}
