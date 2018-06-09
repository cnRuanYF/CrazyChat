package com.crazychat.dao.impl;

import java.util.List;

import com.crazychat.dao.GroupDAO;
import com.crazychat.entity.Group;
import com.crazychat.entity.User;
import com.crazychat.util.DBUtil;

/**
 * 群组DAO实现类
 * 
 * @author VisonSun
 * @date 2018-04-17 00:11
 */
public class GroupDAOImpl implements GroupDAO {

    /** 数据表中的所有字段 */
    private static final String ALL_COLUMNS = "id,groupName,groupDesc,creatorId,needVerify,createTime,pictureId";

    /*
     * (non-Javadoc)
     * 
     * @see com.crazychat.dao.BaseDAO#add(java.lang.Object)
     */
    @Override
    public boolean add(Group obj) throws Exception {
        String sql = "insert into tb_group values(null,?,?,?,?,now(),?)";

        // 参数 needVerify 默认 true
        // 参数 createTime 默认 now()
        int flag = DBUtil.execUpdate(sql, obj.getGroupName(), obj.getGroupDesc(), true, obj.getCreatorId(),
                obj.getPictureId());

        return flag > 0;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.crazychat.dao.BaseDAO#delete(java.lang.Object)
     */
    @Override
    public boolean delete(Group obj) throws Exception {
        // 根据传入的群对象ID删除
        String sql = "delete from tb_group where id=?";
        int flag = DBUtil.execUpdate(sql, obj.getId());

        return flag > 0;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.crazychat.dao.BaseDAO#update(java.lang.Object)
     */
    @Override
    public boolean update(Group obj) throws Exception {
        // 群ID不能改，创建时间不能改，根据ID查询要改的数据
        String sql = "update tb_group set groupName=?,groupDesc=?,creatorId=?,needVerify=?,pictureId=? where id=?";
        int flag = DBUtil.execUpdate(sql, obj.getGroupName(), obj.getGroupDesc(), obj.getCreatorId(),
                obj.isNeedVerify(), obj.getPictureId(), obj.getId());

        return flag > 0;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.crazychat.dao.BaseDAO#findAll()
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Group> findAll() throws Exception {
        String sql = "select " + ALL_COLUMNS + " from tb_group";
        List<Group> list = (List<Group>) DBUtil.execQuery(sql, Group.class);

        return list;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.crazychat.dao.BaseDAO#findById(java.lang.Object)
     */
    @SuppressWarnings("unchecked")
    @Override
    public Group findById(Integer id) throws Exception {
        // 按群ID查群
        String sql = "select " + ALL_COLUMNS + " from tb_group where id=?";
        List<Group> list = (List<Group>) DBUtil.execQuery(sql, Group.class, id);

        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    /*
     * (non-Javadoc) success
     * 
     * @see com.crazychat.dao.GroupDAO#findByName(java.lang.String)
     */
    @SuppressWarnings("unchecked")
    @Override
    public Group findByName(String groupName) throws Exception {
        // 按群名模糊查询
        String sql = "select " + ALL_COLUMNS + " from tb_group where groupName like ?";
        List<Group> list = (List<Group>) DBUtil.execQuery(sql, Group.class, "%" + groupName + "%");

        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.crazychat.dao.GroupDAO#listMembers(int)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<User> listMembers(int groupId) throws Exception {
        // 获取群所有成员的所有信息对象集合, 按群ID查
        String sql = "select id,username,password,gender,nickname,introduction,birthday,phone,email,privacyPolicy,needVerify,registerTime,registerIP,pictureId from tb_groupmember,tb_user where groupid=? and userId=id";
        List<User> list = (List<User>) DBUtil.execQuery(sql, User.class, groupId);

        return list;
    }

    /* (non-Javadoc)
     * @see com.crazychat.dao.GroupDAO#findBykeyword(java.lang.String)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Group> findBykeyword(String keyword) throws Exception {
        String sql = "select " + ALL_COLUMNS + " from tb_group where id=? or groupName like ?";
        List<Group> list = (List<Group>) DBUtil.execQuery(sql, Group.class, keyword, "%" + keyword + "%");

        return list;
    }

}