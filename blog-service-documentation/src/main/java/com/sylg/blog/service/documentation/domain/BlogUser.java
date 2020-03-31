package com.sylg.blog.service.documentation.domain;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

import lombok.Data;

@Data
@Table(name = "blog_user")
public class BlogUser {
    /**
     * 用户id
     */
    @Id
    @Column(name = "user_code")
    @GeneratedValue(generator = "JDBC")
    private Long userCode;

    /**
     * 登录账号
     */
    @Column(name = "login_code")
    private String loginCode;

    /**
     * 用户昵称
     */
    @Column(name = "user_name")
    private String userName;

    /**
     * 登录密码
     */
    @Column(name = "`PASSWORD`")
    private String password;

    /**
     * 电子邮箱
     */
    @Column(name = "email")
    @Email(message = "邮箱格式错误")
    private String email;

    /**
     * 手机号码
     */
    @Column(name = "mobile")
    @Pattern(regexp ="^[1][3,4,5,6,7,8,9][0-9]{9}$", message = "手机号格式有误")
    @Max(value = 11,message = "手机号只能为{max}位")
    @Min(value = 11,message = "手机号只能为{min}位")
    private String mobile;

    /**
     * 办公电话
     */
    @Column(name = "phone")
    private String phone;

    /**
     * 用户性别
     */
    @Column(name = "sex")
    private String sex;

    /**
     * 头像路径
     */
    @Column(name = "avatar")
    private String avatar;

    /**
     * 个性签名
     */
    @Column(name = "SIGN")
    private String sign;

    /**
     * 绑定的微信号
     */
    @Column(name = "wx_openid")
    private String wxOpenid;

    /**
     * 绑定的手机串号
     */
    @Column(name = "mobile_imei")
    private String mobileImei;

    /**
     * 管理员类型（0非管理员 1系统管理员  2二级管理员）
     */
    @Column(name = "mgr_type")
    private String mgrType;

    /**
     * 密码安全级别（0初始 1很弱 2弱 3安全 4很安全）
     */
    @Column(name = "pwd_security_level")
    private Short pwdSecurityLevel;

    /**
     * 密码最后更新时间
     */
    @Column(name = "pwd_update_date")
    private Date pwdUpdateDate;

    /**
     * 密码修改记录
     */
    @Column(name = "pwd_update_record")
    private String pwdUpdateRecord;

    /**
     * 密保问题
     */
    @Column(name = "pwd_question")
    private String pwdQuestion;

    /**
     * 密保问题答案
     */
    @Column(name = "pwd_question_answer")
    private String pwdQuestionAnswer;

    /**
     * 密保问题2
     */
    @Column(name = "pwd_question_2")
    private String pwdQuestion2;

    /**
     * 密保问题答案2
     */
    @Column(name = "pwd_question_answer_2")
    private String pwdQuestionAnswer2;

    /**
     * 密保问题3
     */
    @Column(name = "pwd_question_3")
    private String pwdQuestion3;

    /**
     * 密保问题答案3
     */
    @Column(name = "pwd_question_answer_3")
    private String pwdQuestionAnswer3;

    /**
     * 密码问题修改时间
     */
    @Column(name = "pwd_quest_update_date")
    private Date pwdQuestUpdateDate;

    /**
     * 最后登陆IP
     */
    @Column(name = "last_login_ip")
    private String lastLoginIp;

    /**
     * 最后登陆时间
     */
    @Column(name = "last_login_date")
    private Date lastLoginDate;

    /**
     * 扩展 String 1
     */
    @Column(name = "extend_s1")
    private String extendS1;

    /**
     * 扩展 String 2
     */
    @Column(name = "extend_s2")
    private String extendS2;

    /**
     * 扩展 String 3
     */
    @Column(name = "extend_s3")
    private String extendS3;

    /**
     * 扩展 String 4
     */
    @Column(name = "extend_s4")
    private String extendS4;

    /**
     * 扩展 String 5
     */
    @Column(name = "extend_s5")
    private String extendS5;

    /**
     * 扩展 String 6
     */
    @Column(name = "extend_s6")
    private String extendS6;

    /**
     * 扩展 String 7
     */
    @Column(name = "extend_s7")
    private String extendS7;

    /**
     * 扩展 String 8
     */
    @Column(name = "extend_s8")
    private String extendS8;

    /**
     * 扩展 Integer 1
     */
    @Column(name = "extend_i1")
    private BigDecimal extendI1;

    /**
     * 扩展 Integer 2
     */
    @Column(name = "extend_i2")
    private BigDecimal extendI2;

    /**
     * 扩展 Integer 3
     */
    @Column(name = "extend_i3")
    private BigDecimal extendI3;

    /**
     * 扩展 Integer 4
     */
    @Column(name = "extend_i4")
    private BigDecimal extendI4;

    /**
     * 扩展 Float 1
     */
    @Column(name = "extend_f1")
    private BigDecimal extendF1;

    /**
     * 扩展 Float 2
     */
    @Column(name = "extend_f2")
    private BigDecimal extendF2;

    /**
     * 扩展 Float 3
     */
    @Column(name = "extend_f3")
    private BigDecimal extendF3;

    /**
     * 扩展 Float 4
     */
    @Column(name = "extend_f4")
    private BigDecimal extendF4;

    /**
     * 扩展 Date 1
     */
    @Column(name = "extend_d1")
    private Date extendD1;

    /**
     * 扩展 Date 2
     */
    @Column(name = "extend_d2")
    private Date extendD2;

    /**
     * 扩展 Date 3
     */
    @Column(name = "extend_d3")
    private Date extendD3;

    /**
     * 扩展 Date 4
     */
    @Column(name = "extend_d4")
    private Date extendD4;
}