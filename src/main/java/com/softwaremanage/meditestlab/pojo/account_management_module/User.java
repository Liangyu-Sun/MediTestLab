package com.softwaremanage.meditestlab.pojo.account_management_module;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Data
@Entity
@Table(name = "my_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer uId;
    @Column
    private String uName;
    @Column
    private String uPassword;


    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "my_role", joinColumns = @JoinColumn(name = "uId"))
    @Column
    private Set<String> uIdentity;


    @Column
    private String uRealname;
    @Column
    private String uPhone;
    @Column
    private String uInstitution;




}
