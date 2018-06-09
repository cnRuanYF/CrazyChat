package com.crazychat.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户实体类
 * 
 * @author RuanYaofeng
 * @date 2018年4月15日 下午3:07:31
 */
@SuppressWarnings("serial")
public class User implements Serializable {

    /* 隐私策略常量 */
    public static final int PRIVACY_PUBLIC = 0; // 公开
    public static final int PRIVACY_FRIEND = 1; // 好友可查
    public static final int PRIVACY_PRIVATE = 2; // 资料保密

    /** 用户ID */
    private int id;
    /** 用户名 */
    private String username;
    /** 密码 */
    private String password;

    /** 性别 */
    private int gender;
    /** 昵称 */
    private String nickname;
    /** 个人简介 */
    private String introduction;
    /** 生日 */
    private Date birthday;
    /** 手机号 */
    private String phone;
    /** 邮箱 */
    private String email;

    /** 隐私策略 */
    private int privacyPolicy;
    /** 加好友是否需要验证 */
    private boolean needVerify;

    /** 注册日期 */
    private Date registerTime;
    /** 注册IP */
    private String registerIP;

    /** 用户头像 */
    private int pictureId;

    /**
     * 构造一个用户对象
     */
    public User() {}

    public User(int id, String password) {
        this.id = id;
        this.password = password;
    }

    /**
     * 构造一个用户对象
     * 
     * @param username 用户名
     * @param password 密码
     */
    public User(String username, String password) {
        super();
        this.username = username;
        this.password = password;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the gender
     */
    public int getGender() {
        return gender;
    }

    /**
     * @param gender the gender to set
     */
    public void setGender(int gender) {
        this.gender = gender;
    }

    /**
     * @return the nickname
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * @param nickname the nickname to set
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * @return the introduction
     */
    public String getIntroduction() {
        return introduction;
    }

    /**
     * @param introduction the introduction to set
     */
    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    /**
     * @return the birthday
     */
    public Date getBirthday() {
        return birthday;
    }

    /**
     * @param birthday the birthday to set
     */
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    /**
     * @return the phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * @param phone the phone to set
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the privacyPolicy
     */
    public int getPrivacyPolicy() {
        return privacyPolicy;
    }

    /**
     * @param privacyPolicy the privacyPolicy to set
     */
    public void setPrivacyPolicy(int privacyPolicy) {
        this.privacyPolicy = privacyPolicy;
    }

    /**
     * @return the needVerify
     */
    public boolean isNeedVerify() {
        return needVerify;
    }

    /**
     * @param needVerify the needVerify to set
     */
    public void setNeedVerify(boolean needVerify) {
        this.needVerify = needVerify;
    }

    /**
     * @return the registerTime
     */
    public Date getRegisterTime() {
        return registerTime;
    }

    /**
     * @param registerTime the registerTime to set
     */
    public void setRegisterTime(Date registerTime) {
        this.registerTime = registerTime;
    }

    /**
     * @return the registerIP
     */
    public String getRegisterIP() {
        return registerIP;
    }

    /**
     * @param registerIP the registerIP to set
     */
    public void setRegisterIP(String registerIP) {
        this.registerIP = registerIP;
    }

    /**
     * @return the pictureId
     */
    public int getPictureId() {
        return pictureId;
    }

    /**
     * @param pictureId the pictureId to set
     */
    public void setPictureId(int pictureId) {
        this.pictureId = pictureId;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "User [id=" + id + ", username=" + username + ", password=" + password + ", gender=" + gender
                + ", nickname=" + nickname + ", introduction=" + introduction + ", birthday=" + birthday + ", phone="
                + phone + ", email=" + email + ", privacyPolicy=" + privacyPolicy + ", needVerify=" + needVerify
                + ", registerTime=" + registerTime + ", registerIP=" + registerIP + ", pictureId=" + pictureId + "]";
    }
}
