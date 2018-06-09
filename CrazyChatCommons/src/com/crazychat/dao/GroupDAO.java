package com.crazychat.dao;

import java.util.List;

import com.crazychat.entity.Group;
import com.crazychat.entity.User;

/**
 * 群组DAO接口
 * 
 * @author RuanYaofeng
 * @date 2018年4月16日 上午10:38:00
 */
public interface GroupDAO extends BaseDAO<Group, Integer> {

    /**
     * 根据群名查找群(模糊查找)
     * 
     * @param groupName 群名
     * @return 查找到的群对象，未找到则返回null
     * @throws Exception 处理过程中发生错误均抛出异常到业务层处理
     */
    Group findByName(String groupName) throws Exception;

    /**
     * 根据关键字查询群
     * 
     * @param keyword 关键字
     * @return 查找到的群集合
     * @throws Exception
     */
    List<Group> findBykeyword(String keyword) throws Exception;
    
    /**
     * 根据群ID获取群成员
     * 
     * @param groupId 群ID
     * @return 查找到的群成员用户对象集合，未找到则返回null
     * @throws Exception 处理过程中发生错误均抛出异常到业务层处理
     */
    List<User> listMembers(int groupId) throws Exception;
}
