package com.softwaremanage.meditestlab.controller;

import com.softwaremanage.meditestlab.pojo.ResponseMessage;
import com.softwaremanage.meditestlab.pojo.account_management_module.User;
import com.softwaremanage.meditestlab.pojo.dto.UserDto;
import com.softwaremanage.meditestlab.repository.account_management_module.UserRepository;
import com.softwaremanage.meditestlab.service.account_management_module.UserService;
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
    public ResponseMessage loginUser(@RequestBody UserDto userDto) {
        try {
            User user = userService.validateLogin(userDto);
            return ResponseMessage.success(user.getuId());
        } catch (IllegalArgumentException e) {
            return ResponseMessage.error(e.getMessage());
        }
    }

    //用户查询
    @GetMapping("/{uId}")//
    public ResponseMessage<User> findUser(@PathVariable Integer uId) {
        User userNew = userService.getUser(uId);
        return ResponseMessage.success(userNew);
    }

    //用户修改个人信息
    @PutMapping("/edit")
    public ResponseMessage<User> editInformation(@Valid @RequestBody UserDto userDto) {

        User user = userService.edit(userDto);
        return ResponseMessage.success(user);
    }

}
