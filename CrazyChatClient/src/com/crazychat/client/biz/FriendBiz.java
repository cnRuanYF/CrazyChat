package com.crazychat.client.biz;

/**
 * 好友相关业务接口
 * 
 * @author RuanYaofeng
 * @date 2018-04-17 14:01
 */
public interface FriendBiz extends BaseBiz {

    /**
     * 获取好友列表
     */
    void listFriends();

    /**
     * 添加好友
     * 
     * @param friendUserId 要加为好友的用户ID
     */
    void addFriend(int friendUserId);
}
