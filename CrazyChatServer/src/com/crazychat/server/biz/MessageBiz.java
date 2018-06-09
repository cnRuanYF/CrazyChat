package com.crazychat.server.biz;

import java.net.Socket;

import com.crazychat.entity.Message;

/**
 * 收发消息相关业务接口
 * 
 * @author RuanYaofeng, VisonSun
 */
public interface MessageBiz extends BaseBiz {

    /**
     * 发送消息
     * 
     * @param socket 客户端Socket对象
     * @param message 消息对象
     */
    void sendMessage(Socket socket, Message message);

    /**
     * 获取消息记录
     * 
     * @param socket 客户端Socket对象
     * @param message 消息对象
     */
    void listMessageRecords(Socket socket, Message message);

    /**
     * 拉取用户所有消息
     * 
     * @param socket 客户端socket对象
     * @param userId 用户ID
     */
    void listAllMessageRecords(Socket socket, int userId);
}
