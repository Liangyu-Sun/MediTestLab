package com.softwaremanage.meditestlab.service;

import com.softwaremanage.meditestlab.pojo.User;
import com.softwaremanage.meditestlab.pojo.dto.UserDto;
import com.softwaremanage.meditestlab.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    //新增用户
    public User add(UserDto userDto) {
        // 检查用户名是否已存在
        if (userRepository.findByuName(userDto.getuName()).isPresent()) {
            throw new IllegalArgumentException("用户名已存在");
        }

        // 复制属性并保存新用户
        User user = new User();
        BeanUtils.copyProperties(userDto, user);
        return userRepository.save(user);
    }

    // 验证登录
    public String validateLogin(UserDto userDto) {
        User user = userRepository.findByuName(userDto.getuName()).orElse(null);
        if (user == null) {
            return "用户名不存在";
        } else if (!user.getuPassword().equals(userDto.getuPassword())) {
            return "密码错误";
        } else if(!user.getuIdentity().equals(userDto.getuIdentity())) {
            return "用户身份错误";
        }
        return null; // 登录成功，返回 null
    }


}
