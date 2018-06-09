package com.crazychat.dao;

import java.util.List;

import com.crazychat.entity.Friend;
import com.crazychat.entity.User;

/**
 * 好友DAO接口
 * 
 * @author VisonSun
 * @date 2018-04-17 14:50
 */
public interface FriendDAO {

    /**
     * 添加好友
     * 
     * @param userId 自身用户ID
     * @param friendId 好友用户ID
     * @return 添加是否成功
     * @throws Exception 处理过程中发生错误均抛出异常到业务层处理
     */
    boolean add(int userId, int friendId) throws Exception;

    /**
     * 删除好友
     * 
     * @param userId 自身用户ID
     * @param friendId 好友用户ID
     * @return 操作是否成功
     * @throws Exception 处理过程中发生错误均抛出异常到业务层处理
     */
    boolean delete(int userId, int friendId) throws Exception;

    /**
     * 更新好友数据(改备注)
     * 
     * @param userId 自身用户ID
     * @param friendId 好友用户ID
     * @param remark 好友用户的备注
     * @return 操作是否成功
     * @throws Exception 处理过程中发生错误均抛出异常到业务层处理
     */
    boolean update(int userId, int friendId, String remark) throws Exception;

    /**
     * 获取好友列表
     * 
     * @param userId 自身用户ID
     * @return 好友的集合
     * @throws Exception 处理过程中发生错误均抛出异常到业务层处理
     */
    List<User> findAll(int userId) throws Exception;
    
    /**
     * 获取单条好友对象
     * 
     * @param userId 用户ID
     * @param friendId 好友ID
     * @return 好友关系对象
     * @throws Exception  处理过程中发生错误均抛出异常到业务层处理
     */
    Friend findFriend(int userId, int friendId) throws Exception;

}
