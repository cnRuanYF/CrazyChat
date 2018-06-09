package com.crazychat.client.biz;

import com.crazychat.entity.User;

/**
 * 用户管理相关业务接口
 * 
 * @author RuanYaofeng
 * @date 2018-04-17 13:58
 */
public interface UserBiz extends BaseBiz {

    /**
     * 根据关键字查找用户
     * 
     * @param keyword 关键字
     */
    void findUser(String keyword);

    /**
     * 获取用户资料
     * 
     * @param userId 用户ID
     */
    void getUserProfile(int userId);

    /**
     * 修改个人资料
     * 
     * @param user 新的User对象
     */
    void updateUserProfile(User user);
}
