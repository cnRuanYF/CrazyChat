package com.crazychat.server.biz;

import com.crazychat.server.socket.ResponseSender;

import java.net.Socket;

import com.crazychat.entity.DataPacket;

/**
 * 基础业务接口
 * 
 * @author RuanYaofeng
 * @date 2018-04-18 23:43
 */
public interface BaseBiz {

    /**
     * 发送响应到客户端 (默认实现)
     * 
     * @param socket 要发送到的Socket对象
     * @param packet 要发送的数据包对象
     */
    default void sendResponse(Socket socket, DataPacket packet) {
        // XXX 使用线程池代替
        new Thread(new ResponseSender(socket, packet)).start();
    }

    /**
     * 处理客户端请求（由接收消息线程调用）
     * 
     * @param socket 客户端Socket对象
     * @param packet 客户端请求的数据包
     */
    void handleRequest(Socket socket, DataPacket packet);
}
