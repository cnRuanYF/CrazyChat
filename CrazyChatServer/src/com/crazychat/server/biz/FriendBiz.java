package com.crazychat.server.biz;

import java.net.Socket;

import com.crazychat.entity.Friend;

/**
 * 好友相关业务接口
 * 
 * @author RuanYaofeng, VisonSun
 * @date 2018-04-19 15:06
 */
public interface FriendBiz extends BaseBiz {

    /**
     * 获取好友列表
     * 
     * @param socket 客户端Socket对象
     * @param userId 要获取列表的用户ID
     */
    void listFriends(Socket socket, Integer userId);

    /**
     * 添加好友
     * 
     * @param socket 客户端Socket对象
     * @param friend 要进行添加请求的关系对象
     */
    void addFriend(Socket socket, Friend friend);

    /**
     * 删除好友
     * 
     * @param socket 客户端Socket对象
     * @param friend 要进行删除请求的关系对象
     */
    void deleteFriend(Socket socket, Friend friend);

    /**
     * 备注好友
     * 
     * @param socket 客户端Socket对象
     * @param friend 要进行备注请求的关系对象
     */
    void remarkFriend(Socket socket, Friend friend);
}
