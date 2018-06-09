package com.crazychat.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户聊天消息实体类
 * 
 * @author RuanYaofeng
 * @date 2018年4月15日 下午6:22:15
 */
@SuppressWarnings("serial")
public class Message implements Serializable {

    /* 消息类型常量 */
    public static final int TYPE_FRIEND = 0; // 私聊消息
    public static final int TYPE_GROUP = 1; // 群聊消息

    /** 消息ID */
    private long id;
    /** 消息类型 */
    private int messageType;
    /** 发送者ID */
    private int senderId;
    /** 接收者ID(群ID) */
    private int receiverId;
    /** 消息内容 */
    private String messageContent;
    /** 发送时间 */
    private Date sendTime;

    /**
     * 构造一条聊天消息
     */
    public Message() {
        this.sendTime = new Date();
    }
    
    /**
     * 构造一条聊天信息
     * @param id 聊天信息ID
     */
    public Message(long id) {
        super();
        this.id = id;
    }

    /**
     * 构造一条聊天消息
     * 
     * @param messageType 消息类型 (TYPE_FRIEND / TYPE_GROUP)
     * @param senderId 发送者ID
     * @param receiverId 接收者ID (群ID)
     * @param messageContent 消息内容
     */
    public Message(int messageType, int senderId, int receiverId, String messageContent) {
        this();
        this.messageType = messageType;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.messageContent = messageContent;
    }

    /**
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * @return the messageType
     */
    public int getMessageType() {
        return messageType;
    }

    /**
     * @param messageType the messageType to set
     */
    public void setMessageType(int messageType) {
        this.messageType = messageType;
    }

    /**
     * @return the senderId
     */
    public int getSenderId() {
        return senderId;
    }

    /**
     * @param senderId the senderId to set
     */
    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }

    /**
     * @return the receiverId
     */
    public int getReceiverId() {
        return receiverId;
    }

    /**
     * @param receiverId the receiverId to set
     */
    public void setReceiverId(int receiverId) {
        this.receiverId = receiverId;
    }

    /**
     * @return the messageContent
     */
    public String getMessageContent() {
        return messageContent;
    }

    /**
     * @param messageContent the messageContent to set
     */
    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    /**
     * @return the sendTime
     */
    public Date getSendTime() {
        return sendTime;
    }

    /**
     * @param sendTime the sendTime to set
     */
    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Message [id=" + id + ", messageType=" + messageType + ", senderId=" + senderId + ", receiverId="
                + receiverId + ", messageContent=" + messageContent + ", sendTime=" + sendTime + "]";
    }

}
