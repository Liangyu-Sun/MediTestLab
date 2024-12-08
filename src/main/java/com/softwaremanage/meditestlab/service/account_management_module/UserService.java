package com.softwaremanage.meditestlab.service.account_management_module;

import com.softwaremanage.meditestlab.pojo.account_management_module.User;
import com.softwaremanage.meditestlab.pojo.dto.LoginResponse;
import com.softwaremanage.meditestlab.pojo.dto.UserDto;
import com.softwaremanage.meditestlab.repository.account_management_module.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.oauth2.jwt.JwtClaimsSet;
//import org.springframework.security.oauth2.jwt.JwtEncoder;
//import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
//import org.springframework.stereotype.Service;

import java.util.Collection;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;


import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

//    private final AuthenticationManager authenticationManager;
//    private final JwtEncoder jwtEncoder;
//
//    public UserService(AuthenticationManager authenticationManager, JwtEncoder jwtEncoder) {
//        this.authenticationManager = authenticationManager;
//        this.jwtEncoder = jwtEncoder;
//    }

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
        } else if (!user.getUPassword().equals(userDto.getuPassword())) {
            throw new IllegalArgumentException("密码错误");
        } else if (!user.getUIdentity().equals(userDto.getuIdentity())) {
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
        if (existingUser.isPresent() && !existingUser.get().getUId().equals(userDto.getuId())) {
            throw new IllegalArgumentException("用户名已存在");
        }

        // 复制属性并保存用户信息
        User user = new User();
        BeanUtils.copyProperties(userDto, user);
        return userRepository.save(user);
    }

//    public LoginResponse authenticate(String username, String password) {
//        var authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(username, password));
//        String token = generateToken(authentication);
//        Collection<String> roles = authentication.getAuthorities().stream()
//                .map(GrantedAuthority::getAuthority)
//                .collect(Collectors.toList());
//        return new LoginResponse(username, roles, token);
//    }
//
//    private String generateToken(Authentication authentication) {
//        Instant now = Instant.now();
//
//        String scope = authentication.getAuthorities().stream()
//                .map(GrantedAuthority::getAuthority)
//                .collect(Collectors.joining(" "));
//
//        JwtClaimsSet claims = JwtClaimsSet.builder()
//                .issuer("self")
//                .issuedAt(now)
//                .expiresAt(now.plus(1, ChronoUnit.HOURS))
//                .subject(authentication.getName())
//                .claim("authorities", scope)
//                .build();
//
//        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
//    }

}
