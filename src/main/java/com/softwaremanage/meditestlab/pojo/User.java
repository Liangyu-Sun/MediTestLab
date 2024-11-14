package com.softwaremanage.meditestlab.pojo;

import jakarta.persistence.*;

@Table
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer uId;
    @Column
    private String uName;
    @Column
    private String uPassword;
    @Column
    private String uIdentity;
    @Column
    private String uRealname;
    @Column
    private String uPhone;
    @Column
    private String uInstitution;

    @Override
    public String toString() {
        return "User{" +
                "uId=" + uId +
                ", uName='" + uName + '\'' +
                ", uPassword='" + uPassword + '\'' +
                ", uIdentity='" + uIdentity + '\'' +
                ", uRealname='" + uRealname + '\'' +
                ", uPhone='" + uPhone + '\'' +
                ", uInstitution='" + uInstitution + '\'' +
                '}';
    }

    public Integer getuId() {
        return uId;
    }

    public void setuId(Integer uId) {
        this.uId = uId;
    }

    public String getuInstitution() {
        return uInstitution;
    }

    public void setuInstitution(String uInstitution) {
        this.uInstitution = uInstitution;
    }

    public String getuPhone() {
        return uPhone;
    }

    public void setuPhone(String uPhone) {
        this.uPhone = uPhone;
    }

    public String getuRealname() {
        return uRealname;
    }

    public void setuRealname(String uRealname) {
        this.uRealname = uRealname;
    }

    public String getuIdentity() {
        return uIdentity;
    }

    public void setuIdentity(String uIdentity) {
        this.uIdentity = uIdentity;
    }

    public String getuPassword() {
        return uPassword;
    }

    public void setuPassword(String uPassword) {
        this.uPassword = uPassword;
    }

    public String getuName() {
        return uName;
    }

    public void setuName(String uName) {
        this.uName = uName;
    }
}
