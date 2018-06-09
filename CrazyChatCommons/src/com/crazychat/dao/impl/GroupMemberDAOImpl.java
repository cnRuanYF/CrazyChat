package com.crazychat.dao.impl;

import java.util.List;

import com.crazychat.dao.GroupMemberDAO;
import com.crazychat.entity.Group;
import com.crazychat.entity.User;
import com.crazychat.util.DBUtil;

/**
 * 群成员DAO实现类
 * 
 * @author VisonSun
 * @date 2018-04-17 16:33
 */
public class GroupMemberDAOImpl implements GroupMemberDAO {

    /*
     * (non-Javadoc)
     * 
     * @see com.crazychat.dao.GroupMemberDAO#add(com.crazychat.entity.Group,
     * com.crazychat.entity.User)
     */
    @Override
    public boolean add(Group group, User user) throws Exception {
        String sql = "insert into tb_groupmember value(?,?,?,now())";
        int flag = DBUtil.execUpdate(sql, group.getId(), user.getId(), "群员员");
        return flag > 0;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.crazychat.dao.GroupMemberDAO#delete(com.crazychat.entity.Group,
     * com.crazychat.entity.User)
     */
    @Override
    public boolean delete(Group group, User user) throws Exception {
        String sql = "delete from tb_groupmember where groupId=? and userId=?";
        int flag = DBUtil.execUpdate(sql, group.getId(), user.getId());
        return flag > 0;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.crazychat.dao.GroupMemberDAO#update(com.crazychat.entity.Group,
     * com.crazychat.entity.User, java.lang.String)
     */
    @Override
    public boolean update(Group group, User user, String memberNickname) throws Exception {
        String sql = "update tb_groupmember set memberNickname=? where groupId=? and userId=?";
        int flag = DBUtil.execUpdate(sql, memberNickname, group.getId(), user.getId());
        return flag > 0;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.crazychat.dao.GroupMemberDAO#findAll(com.crazychat.entity.Group)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<User> listMembers(Group group) throws Exception {
        // 查找出所有群成员的对象集合
        String sql = "select id,username,password,gender,nickname,introduction,birthday,phone,email,privacyPolicy,needVerify,registerTime,registerIP,pictureId from tb_groupmember,tb_user where groupId=? and id=userId";
        List<User> list = (List<User>) DBUtil.execQuery(sql, User.class, group.getId());
        return list;
    }

    /* (non-Javadoc)
     * @see com.crazychat.dao.GroupMemberDAO#listGroups(com.crazychat.entity.User)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Group> listGroups(User user) throws Exception {
        // 查找出用户加入的群的对象集合
        String sql = "select id,groupName,groupDesc,needVerify,createTime from tb_group,tb_groupmember where id=groupId and userId=?";
        List<Group> list = (List<Group>) DBUtil.execQuery(sql, Group.class, user.getId());
        return list;
    }

}
