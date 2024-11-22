package com.softwaremanage.meditestlab.service.account_management_module;

import com.softwaremanage.meditestlab.pojo.account_management_module.User;
import com.softwaremanage.meditestlab.pojo.dto.UserDto;
import com.softwaremanage.meditestlab.repository.account_management_module.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
    public User validateLogin(UserDto userDto) {
        User user = userRepository.findByuName(userDto.getuName()).orElse(null);
        if (user == null) {
            throw new IllegalArgumentException("用户名不存在");
        } else if (!user.getuPassword().equals(userDto.getuPassword())) {
            throw new IllegalArgumentException("密码错误");
        } else if (!user.getuIdentity().equals(userDto.getuIdentity())) {
            throw new IllegalArgumentException("用户身份错误");
        }
        return user; // 登录成功，返回用户对象
    }


    public User getUser(Integer uId) {
        return userRepository.findById(uId).orElse(null);
    }

    public User edit(UserDto userDto) {
        // 查找用户名是否已存在，但排除当前用户的 uId
        Optional<User> existingUser = userRepository.findByuName(userDto.getuName());
        if (existingUser.isPresent() && !existingUser.get().getuId().equals(userDto.getuId())) {
            throw new IllegalArgumentException("用户名已存在");
        }

        // 复制属性并保存用户信息
        User user = new User();
        BeanUtils.copyProperties(userDto, user);
        return userRepository.save(user);
    }

}
