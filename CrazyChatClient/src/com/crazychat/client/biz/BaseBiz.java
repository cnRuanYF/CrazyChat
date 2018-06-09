package com.crazychat.client.biz;

import com.crazychat.client.socket.RequestSender;
import com.crazychat.entity.DataPacket;

/**
 * 基础业务接口
 * 
 * @author RuanYaofeng
 * @date 2018-04-16 14:06
 */
public interface BaseBiz {

    /**
     * 发送请求到服务器 (默认实现)
     * 
     * @param packet 数据包对象
     */
    default void sendRequest(DataPacket packet) {
        // XXX 使用线程池代替
        new Thread(new RequestSender(packet)).start();
    }

    /**
     * 处理服务器响应（由接收消息线程调用）
     * 
     * @param packet 响应的数据包
     */
    void handleResponse(DataPacket packet);
}
