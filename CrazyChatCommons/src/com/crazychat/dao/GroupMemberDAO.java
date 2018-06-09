package com.crazychat.dao;

import java.util.List;

import com.crazychat.entity.Group;
import com.crazychat.entity.User;

/**
 * 群员DAO接口
 * 
 * @author VisonSun
 * @date 2018-04-17 14:50
 */
public interface GroupMemberDAO {

    /**
     * 添加群成员
     * 
     * @param group 群对象
     * @param user 成员对象
     * @return 添加是否成功
     * @throws Exception 处理过程中发生错误均抛出异常到业务层处理
     */
    boolean add(Group group, User user) throws Exception;

    /**
     * 删除群成员
     * 
     * @param group 群对象
     * @param user 成员对象
     * @return 删除是否成功
     * @throws Exception 处理过程中发生错误均抛出异常到业务层处理
     */
    boolean delete(Group group, User user) throws Exception;

    /**
     * 更新成员数据(修改群名片)
     * 
     * @param user 自身用户对象
     * @param memberNickname 好友用户的备注
     * @return 操作是否成功
     * @throws Exception 处理过程中发生错误均抛出异常到业务层处理
     */
    boolean update(Group group, User user, String memberNickname) throws Exception;

    /**
     * 获取群成员列表
     * 
     * @param group 要获取列表的群对象
     * @return 群成员对象的集合
     * @throws Exception 处理过程中发生错误均抛出异常到业务层处理
     */
    List<User> listMembers(Group group) throws Exception;
    
    /**
     * 获取该账号加入的群列表
     * 
     * @param user 登录的用户对象
     * @return 群组对象的集合
     * @throws Exception 处理过程中发生错误均抛出异常到业务层处理
     */
    List<Group> listGroups(User user) throws Exception;

}
