package com.crazychat.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 群实体类
 * 
 * @author RuanYaofeng
 * @date 2018年4月16日 上午9:57:50
 */
@SuppressWarnings("serial")
public class Group implements Serializable {

    /** 群ID */
    private int id;
    /** 群名 */
    private String groupName;
    /** 群简介 */
    private String groupDesc;
    /** 群主ID */
    private int creatorId;

    /** 加群是否需要验证 */
    private boolean needVerify;

    /** 创建时间 */
    private Date createTime;

    /** 群头像 */
    private int pictureId;

    /**
     * 构造一个群对象
     */
    public Group() {
        this.createTime = new Date();
    }

    /**
     * 构造一个群对象
     * 
     * @param id 群ID
     */
    public Group(int id) {
        super();
        this.id = id;
    }

    /**
     * 构造一个群对象
     * 
     * @param groupName 群名
     * @param groupDesc 群简介
     * @param creatorId 创建者ID
     */
    public Group(String groupName, String groupDesc, int creatorId) {
        this();
        this.groupName = groupName;
        this.groupDesc = groupDesc;
        this.creatorId = creatorId;
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
     * @return the groupName
     */
    public String getGroupName() {
        return groupName;
    }

    /**
     * @param groupName the groupName to set
     */
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    /**
     * @return the groupDesc
     */
    public String getGroupDesc() {
        return groupDesc;
    }

    /**
     * @param groupDesc the groupDesc to set
     */
    public void setGroupDesc(String groupDesc) {
        this.groupDesc = groupDesc;
    }

    /**
     * @return the creatorId
     */
    public int getCreatorId() {
        return creatorId;
    }

    /**
     * @param creatorId the creatorId to set
     */
    public void setCreatorId(int creatorId) {
        this.creatorId = creatorId;
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
     * @return the createTime
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime the createTime to set
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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
        return "Group [id=" + id + ", groupName=" + groupName + ", groupDesc=" + groupDesc + ", creatorId=" + creatorId
                + ", needVerify=" + needVerify + ", createTime=" + createTime + ", pictureId=" + pictureId + "]";
    }
}
