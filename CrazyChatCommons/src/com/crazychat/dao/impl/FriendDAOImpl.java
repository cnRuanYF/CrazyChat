package com.crazychat.dao.impl;

import java.util.List;

import com.crazychat.dao.FriendDAO;
import com.crazychat.entity.Friend;
import com.crazychat.entity.User;
import com.crazychat.util.DBUtil;

/**
 * 好友DAO实现类
 * 
 * @author VisonSun, RuanYaofeng
 * @date 2018-04-17 16:33
 */
public class FriendDAOImpl implements FriendDAO {

    /*
     * (non-Javadoc)
     * 
     * @see com.crazychat.dao.FriendDAO#add(com.crazychat.entity.User, int)
     */
    @Override
    public boolean add(int userId, int friendId) throws Exception {
        int flag = 0;
        int flagRe = 0;
        // 没有好友关系才能添加
        if (new FriendDAOImpl().findFriend(userId, friendId) == null
                && new FriendDAOImpl().findFriend(friendId, userId) == null) {
            String sql = "insert into tb_friend value(?,?,null,null)";
            // 双向添加好友
            flag = DBUtil.execUpdate(sql, userId, friendId);
            flagRe = DBUtil.execUpdate(sql, friendId, userId);
        }
        return flag > 0 && flagRe > 0;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.crazychat.dao.FriendDAO#delete(com.crazychat.entity.User,
     * com.crazychat.entity.User)
     */
    @Override
    public boolean delete(int userId, int friendId) throws Exception {
        String sql = "delete from tb_friend where userId=? and friendId=?";
        // 双向删除好友
        int flag = DBUtil.execUpdate(sql, userId, friendId);
        int flagRe = DBUtil.execUpdate(sql, friendId, userId);
        return flag > 0 && flagRe > 0;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.crazychat.dao.FriendDAO#update(com.crazychat.entity.User,
     * com.crazychat.entity.User)
     */
    @Override
    public boolean update(int userId, int friendId, String remark) throws Exception {
        String sql = "update tb_friend set friendRemark=? where userId=? and friendId=?";
        int flag = DBUtil.execUpdate(sql, remark, userId, friendId);
        return flag > 0;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.crazychat.dao.FriendDAO#findAll(com.crazychat.entity.User)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<User> findAll(int userId) throws Exception {
        // 查找出所有好友ID的对象集合
        String sql = "select id,username,password,gender,nickname,introduction,birthday,phone,email,privacyPolicy,needVerify,registerTime,registerIP,pictureId from tb_friend,tb_user where userId=? and friendId=id";
        List<User> list = (List<User>) DBUtil.execQuery(sql, User.class, userId);
        return list;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.crazychat.dao.FriendDAO#findFriend(int, int)
     */
    @SuppressWarnings("unchecked")
    @Override
    public Friend findFriend(int userId, int friendId) throws Exception {

        User friendUser;
        String userSql = "select id,username,password,gender,nickname,introduction,birthday,phone,email,privacyPolicy,needVerify,registerTime,registerIP,pictureId from tb_user where id=?";
        List<User> friendUserList = (List<User>) DBUtil.execQuery(userSql, User.class, friendId);

        if (friendUserList.size() <= 0) {
            return null;
        }
        friendUser = friendUserList.get(0);

        // 由于好友是friend对象,所以只查询出friendRemark和associateTime
        String friendSql = "select userId,friendRemark,associateTime from tb_friend,tb_user where userId=? and friendId=?";
        List<Friend> friendList = (List<Friend>) DBUtil.execQuery(friendSql, Friend.class, userId, friendId);
        if (friendList.size() <= 0) {
            return null;
        }

        Friend friend = friendList.get(0);
        friend.setFriendUser(friendUser);
        return friend;

    }

}
