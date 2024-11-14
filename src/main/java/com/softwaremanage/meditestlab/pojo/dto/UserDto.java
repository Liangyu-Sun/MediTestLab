package com.softwaremanage.meditestlab.pojo.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

public class UserDto {

    private Integer uId;
    @NotBlank(message = "用户名不能为空")
    private String uName;
    @NotBlank(message = "密码不能为空")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,}$", message = "密码必须至少6位，并且同时包含数字和字母")
    private String uPassword;
    @NotBlank(message = "身份信息不能为空")
    @Pattern(regexp = "支持人员|检测人员", message = "身份信息必须是'支持人员'或'检测人员'")
    private String uIdentity;
    @NotBlank(message = "真实姓名不能为空")
    private String uRealname;
    @NotBlank(message = "手机号不能为空")
    @Length(min = 11, max = 11, message = "手机号长度必须为11位")
    private String uPhone;
    @NotBlank(message = "机构名不能为空")
    private String uInstitution;

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

    @Override
    public String toString() {
        return "UserDto{" +
                "uId=" + uId +
                ", uName='" + uName + '\'' +
                ", uPassword='" + uPassword + '\'' +
                ", uIdentity='" + uIdentity + '\'' +
                ", uRealname='" + uRealname + '\'' +
                ", uPhone='" + uPhone + '\'' +
                ", uInstitution='" + uInstitution + '\'' +
                '}';
    }
}
