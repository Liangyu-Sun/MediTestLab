package com.softwaremanage.meditestlab.controller;

import com.softwaremanage.meditestlab.pojo.ResponseMessage;
import com.softwaremanage.meditestlab.pojo.User;
import com.softwaremanage.meditestlab.pojo.dto.UserDto;
import com.softwaremanage.meditestlab.repository.UserRepository;
import com.softwaremanage.meditestlab.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;

    // 用户注册
    @PostMapping("/register")
    public ResponseMessage<User> registerUser(@Valid @RequestBody UserDto userDto) {

            User user = userService.add(userDto);
            return ResponseMessage.success(user);
    }

    // 用户登录
    @PostMapping("/login")
    public ResponseMessage<String> loginUser(@RequestBody UserDto userDto) {
        String result = userService.validateLogin(userDto);
        if (result == null) {
            return ResponseMessage.success("登录成功");
        } else {
            return ResponseMessage.error(result); // 返回具体错误信息
        }
    }

    //用户查询
//    @GetMapping("/{userId}")//前端请求地址： URL: localhost:8080/user/具体Id  method:get
//    public ResponseMessage get(@PathVariable Integer userId) {
//        User userNew = userService.getUser(userId);
//        return ResponseMessage.success(userNew);
//    }

    //用户修改个人信息


}
