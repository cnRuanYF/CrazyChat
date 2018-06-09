package com.crazychat.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 群成员实体类
 * 
 * @author VisonSun
 * @date 2018-04-17 08:35
 */
@SuppressWarnings("serial")
public class GroupMember implements Serializable {

    /** 群ID */
    private int groupId;
    /** 群成员的用户对象 */
    private User memberUser;
    /** 群名片 */
    private String memberNickname;
    /** 加群时间 */
    private Date joinTime;

    /**
     * 构造一个群成员类实体
     */
    public GroupMember() {}

    /**
     * 构造一个群成员实体
     * 
     * @param groupId 群ID
     * @param memberUser 成员对象
     * @param memberNicname 成员昵称
     * @param joinTime 成员加入时间
     */
    public GroupMember(int groupId, User memberUser, String memberNickname, Date joinTime) {
        super();
        this.groupId = groupId;
        this.memberUser = memberUser;
        this.memberNickname = memberNickname;
        this.joinTime = joinTime;
    }

    /**
     * @return the groupId
     */
    public int getGroupId() {
        return groupId;
    }

    /**
     * @param groupId the groupId to set
     */
    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    /**
     * @return the memberUser
     */
    public User getMemberUser() {
        return memberUser;
    }

    /**
     * @param memberUser the memberUser to set
     */
    public void setMemberUser(User memberUser) {
        this.memberUser = memberUser;
    }

    /**
     * @return the memberNickname
     */
    public String getMemberNickname() {
        return memberNickname;
    }

    /**
     * @param memberNickname the memberNickname to set
     */
    public void setMemberNickname(String memberNickname) {
        this.memberNickname = memberNickname;
    }

    /**
     * @return the joinTime
     */
    public Date getJoinTime() {
        return joinTime;
    }

    /**
     * @param joinTime the joinTime to set
     */
    public void setJoinTime(Date joinTime) {
        this.joinTime = joinTime;
    }

    @Override
    public String toString() {
        return "GroupMember [groupId=" + groupId + ", memberUser=" + memberUser + ", memberNicname=" + memberNickname
                + ", joinTime=" + joinTime + "]";
    }

}
