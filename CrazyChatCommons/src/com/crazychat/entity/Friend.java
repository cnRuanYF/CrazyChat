package com.crazychat.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 好友关系实体类
 * 
 * @author RuanYaofeng
 * @date 2018年4月16日 下午12:26:29
 */
@SuppressWarnings("serial")
public class Friend implements Serializable {

    /** 用户ID */
    private int userId;
    /** 好友用户对象 */
    private User friendUser;
    /** 好友备注 */
    private String friendRemark;
    /** 加好友时间 */
    private Date associateTime;

    /**
     * 构造一个好友关系实体
     */
    public Friend() {}

    /**
     * 构造一个好友关系实体
     * 
     * @param userId 用户ID
     * @param friendUser 好友用户对象
     * @param friendRemark 好友备注
     */
    public Friend(int userId, User friendUser, String friendRemark) {
        super();
        this.userId = userId;
        this.friendUser = friendUser;
        this.friendRemark = friendRemark;
    }

    /**
     * @return the userId
     */
    public int getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * @return the friendUser
     */
    public User getFriendUser() {
        return friendUser;
    }

    /**
     * @param friendUser the friendUser to set
     */
    public void setFriendUser(User friendUser) {
        this.friendUser = friendUser;
    }

    /**
     * @return the friendRemark
     */
    public String getFriendRemark() {
        return friendRemark;
    }

    /**
     * @param friendRemark the friendRemark to set
     */
    public void setFriendRemark(String friendRemark) {
        this.friendRemark = friendRemark;
    }

    /**
     * @return the associateTime
     */
    public Date getAssociateTime() {
        return associateTime;
    }

    /**
     * @param associateTime the associateTime to set
     */
    public void setAssociateTime(Date associateTime) {
        this.associateTime = associateTime;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Friend [userId=" + userId + ", friendUser=" + friendUser + ", friendRemark=" + friendRemark
                + ", associateTime=" + associateTime + "]";
    }

}
