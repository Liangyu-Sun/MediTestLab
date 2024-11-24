package com.softwaremanage.meditestlab.pojo.dto;

import java.util.Collection;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {
    private String username;
    private Collection<String> roles;
    private String token;

}


