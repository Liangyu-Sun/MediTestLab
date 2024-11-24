package com.softwaremanage.meditestlab.utill;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.softwaremanage.meditestlab.pojo.account_management_module.User;
import com.softwaremanage.meditestlab.pojo.dto.LoginResponse;
import com.softwaremanage.meditestlab.repository.account_management_module.UserRepository;
import com.softwaremanage.meditestlab.service.account_management_module.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
@Component
public class TokenUtill {
    private static final long EXPIRE_TIME = 24 * 60 * 60 * 1000;

    @Autowired
    UserRepository userRepository;

    public String getToken(User user) {
        Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
        return JWT.create()
                .withAudience(String.valueOf(user.getUId()))
                .withExpiresAt(date)
                .sign(Algorithm.HMAC256(user.getUPassword()));
    }

    public boolean verifyToken(String token) {
        try {
            Integer userId=Integer.parseInt(JWT.decode(token).getAudience().get(0));
            User user= userRepository.findByuId(userId).get();
            JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(user.getUPassword())).build();
            jwtVerifier.verify(token);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public User getUser(String token){
        Integer userId=Integer.parseInt(JWT.decode(token).getAudience().get(0));
        return userRepository.findById(userId).get();
    }
}
