package com.crazychat.client.biz;

import com.crazychat.entity.Message;

/**
 * 收发消息相关业务接口
 * 
 * @author RuanYaofeng
 * @date 2018-04-17 00:11
 */
public interface MessageBiz extends BaseBiz {

    /**
     * 发送消息
     * 
     * @param chatTarget 聊天对象(User/Group)对象
     * @param message 消息内容文本
     */
    void sendMessage(Object chatTarget, String messageContent);

    /**
     * 接收消息 (接收到消息时调用)
     * 
     * @param message 消息对象
     */
    void receiveMessage(Message message);
    
    /**
     * 获取消息记录
     * @param messages 消息对象 (指定MessageType, receiverId)
     */
    void listMessageRecords(Message message);

    /**
     * 获取所有消息记录
     */
    void listAllMessageRecords();

}
