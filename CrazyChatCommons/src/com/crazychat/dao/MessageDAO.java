package com.crazychat.dao;

import java.util.List;

import com.crazychat.entity.Message;

/**
 * 聊天消息DAO接口
 * 
 * @author RuanYaofeng
 * @date 2018-04-16 10:38
 */
public interface MessageDAO extends BaseDAO<Message, Long> {

    /**
     * 根据消息类型查找消息
     * 
     * @param messageType 消息类型
     * @return 查找到的消息对象集合，未找到则返回null
     * @throws Exception 处理过程中发生错误均抛出异常到业务层处理
     */
    List<Message> findByType(int messageType) throws Exception;

    /**
     * 根据发送者查找消息
     * 
     * @param senderId 发送者ID
     * @return 查找到的消息对象集合，未找到则返回null
     * @throws Exception 处理过程中发生错误均抛出异常到业务层处理
     */
    List<Message> findBySender(int senderId) throws Exception;

    /**
     * 根据接收者查找指定类型的消息
     * 
     * @param messageType 消息类型
     * @param receiverId 接收者ID
     * @return 查找到的消息对象集合，未找到则返回null
     * @throws Exception 处理过程中发生错误均抛出异常到业务层处理
     */
    List<Message> findByReceiver(int messageType, int receiverId) throws Exception;

    /**
     * 查找用户的所有信息
     * 
     * @param userId 接收者ID
     * @return 查找到的消息对象集合，未找到则返回null
     * @throws Exception 处理过程中发生错误均抛出异常到业务层处理
     */
    List<Message> findAllMessage(int userId) throws Exception;

    /**
     * 根据发送者和接收者查找消息
     * 
     * @param senderId 发送者ID
     * @param messageType 消息类型
     * @param receiverId 接收者ID
     * @return 查找到的消息对象集合，未找到则返回null
     * @throws Exception 处理过程中发生错误均抛出异常到业务层处理
     */
    List<Message> findBySenderAndReceiver(int senderId, int messageType, int receiverId) throws Exception;

}
