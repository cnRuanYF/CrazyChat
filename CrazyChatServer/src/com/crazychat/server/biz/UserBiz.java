package com.crazychat.server.biz;

import java.net.Socket;

import com.crazychat.entity.User;

/**
 * 用户管理相关业务接口
 * 
 * @author QiuWenYi, RuanYaofeng
 * @date 2018年4月19日 下午2:33:35
 */
public interface UserBiz extends BaseBiz {

    /**
     * 根据关键字查找用户
     * 
     * @param socket 客户端Socket对象
     * @param keyword 关键字
     */
    void findUser(Socket socket, String keyword);

    /**
     * 获取用户资料
     * 
     * @param socket 客户端Socket对象
     * @param userId 用户ID
     */
    void getUserProfile(Socket socket, int userId);

    /**
     * 修改个人资料
     * 
     * @param socket 客户端Socket对象
     * @param user 新的User对象
     */
    void updateUserProfile(Socket socket, User user);
}
