package com.crazychat.server.biz;

import java.net.Socket;

import com.crazychat.entity.User;

/**
 * 登录相关业务接口
 * 
 * @author RuanYaofeng, VisonSun
 * @date 2018-04-19 00:58
 */
public interface LoginBiz extends BaseBiz {

    /**
     * 登录操作
     * 
     * @param socket 客户端Socket对象
     * @param user 要登录的用户对象
     */
    void login(Socket socket, User loginUser);

    /**
     * 客户端注销操作
     * 
     * @param socket 客户端Socket对象
     */
    void logoutFromClient(Socket socket);

    /**
     * 服务端踢下线操作
     * 
     * @param user
     */
    void logoutFromServer(User user);

}
