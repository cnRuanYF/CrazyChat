package com.crazychat.client.biz;

import com.crazychat.entity.User;

/**
 * 登录相关业务接口
 * 
 * @author RuanYaofeng
 * @date 2018-04-16 14:28
 */
public interface LoginBiz extends BaseBiz {

    /**
     * 登录操作
     * 
     * @param user 要登录的用户对象
     */
    void login(User user);

    /**
     * 继续登录 (成功与服务器创建连接时调用)
     */
    void connected();
    
    /**
     * 注销
     */
    void logout();

}
