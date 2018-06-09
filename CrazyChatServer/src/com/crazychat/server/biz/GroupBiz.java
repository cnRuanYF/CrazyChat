package com.crazychat.server.biz;

import java.net.Socket;

import com.crazychat.entity.Group;
import com.crazychat.entity.GroupMember;
import com.crazychat.entity.User;

/**
 * 群相关业务接口
 * 
 * @author RuanYaofeng, VisonSun
 */
public interface GroupBiz extends BaseBiz {

    /**
     * 获取群列表
     * 
     * @param socket 客户端Socket对象
     * @param loginUser 登录的用户对象
     */
    void listGroups(Socket socket, User loginUser);

    /**
     * 查找群
     * 
     * @param socket 客户端Socket对象
     * @param keyword 查找的关键字
     */
    void findGroup(Socket socket, String keyword);

    /**
     * 获取群资料
     * 
     * @param socket 客户端Socket对象
     * @param groupId 群ID
     */
    void getProfile(Socket socket, Integer groupId);

    /**
     * 更新群资料
     * 
     * @param socket 客户端Socket对象
     * @param group 群对象
     */
    void updateProfile(Socket socket, Group group);

    /**
     * 创建群
     * 
     * @param socket 客户端Socket对象
     * @param group 群对象
     */
    void createGroup(Socket socket, Group group);

    /**
     * 加入群
     * 
     * @param socket 客户端Socket对象
     * @param groupMember 群成员对象
     */
    void joinGroup(Socket socket, GroupMember groupMember);

    /**
     * 退出群
     * 
     * @param socket 客户端Socket对象
     * @param groupMember 群成员对象
     */
    void exitGroup(Socket socket, GroupMember groupMember);

    /**
     * 踢出群
     * 
     * @param socket 客户端Socket对象
     * @param groupMember 群成员对象
     */
    void kickoutGroup(Socket socket, GroupMember groupMember);

}
