package com.crazychat.dao.impl;

import java.net.InetAddress;
import java.util.List;

import com.crazychat.dao.UserDAO;
import com.crazychat.entity.User;
import com.crazychat.util.DBUtil;

/**
 * 用户DAO实现类
 * 
 * @author VisonSun
 * @date 2018-04-17 00:12
 */
public class UserDAOImpl implements UserDAO {

    /** 数据表中的所有字段 */
    private static final String ALL_COLUMNS = "id,username,password,gender,nickname,introduction,birthday,phone,email,privacyPolicy,needVerify,registerTime,registerIP,pictureId";

    /*
     * (non-Javadoc)
     * 
     * @see com.crazychat.dao.BaseDAO#add(java.lang.Object)
     */
    @SuppressWarnings("static-access")
    @Override
    public boolean add(User obj) throws Exception {

        String sql = "insert into tb_user values(null,?,?,?,?,?,?,?,?,?,?,now(),?,?)";

        // 参数 privacyPolicy 默认 PRIVACY_FRIEND
        // 参数 needVerify 默认 true
        // 参数 registerTime 默认 now()
        // 参数 registerIP 默认 本机IP - InetAddress.getLocalHost().getHostAddress()
        int flag = DBUtil.execUpdate(sql, obj.getUsername(), obj.getPassword(), obj.getGender(), obj.getNickname(),
                obj.getIntroduction(), obj.getBirthday(), obj.getPhone(), obj.getEmail(), obj.PRIVACY_FRIEND, true,
                InetAddress.getLocalHost().getHostAddress().toString(), obj.getPictureId());

        return flag > 0;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.crazychat.dao.BaseDAO#delete(java.lang.Object)
     */
    @Override
    public boolean delete(User obj) throws Exception {

        // 根据传入的用户对象ID删除
        String sql = "delete from tb_user where id=?";
        int flag = DBUtil.execUpdate(sql, obj.getId());

        return flag > 0;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.crazychat.dao.BaseDAO#update(java.lang.Object)
     */
    @Override
    public boolean update(User obj) throws Exception {

        // 注册日期和IP不能改,用户ID不能改
        String sql = "update tb_user set username=?,password=?,gender=?,nickname=?,introduction=?,birthday=?,phone=?,email=?,privacyPolicy=?,needVerify=?,pictureId=? where id=?";
        int flag = DBUtil.execUpdate(sql, obj.getUsername(), obj.getPassword(), obj.getGender(), obj.getNickname(),
                obj.getIntroduction(), obj.getBirthday(), obj.getPhone(), obj.getEmail(), obj.getPrivacyPolicy(),
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
    public List<User> findAll() throws Exception {

        String sql = "select " + ALL_COLUMNS + " from tb_user";
        List<User> list = (List<User>) DBUtil.execQuery(sql, User.class);

        return list;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.crazychat.dao.BaseDAO#findById(java.lang.Object)
     */
    @SuppressWarnings("unchecked")
    @Override
    public User findById(Integer id) throws Exception {
        String sql = "select " + ALL_COLUMNS + " from tb_user where id=?";
        List<User> list = (List<User>) DBUtil.execQuery(sql, User.class, id);

        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.crazychat.dao.UserDAO#findByUsername(java.lang.String)
     */
    @SuppressWarnings("unchecked")
    @Override
    public User findByUsername(String username) throws Exception {
        String sql = "select " + ALL_COLUMNS + " from tb_user where username=?";
        List<User> list = (List<User>) DBUtil.execQuery(sql, User.class, username);

        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.crazychat.dao.UserDAO#findByKeyword(java.lang.String)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<User> findByKeyword(String keyword) throws Exception {
        String sql = "select " + ALL_COLUMNS + " from tb_user where id=? or username=? or nickname LIKE ?";
        List<User> list = (List<User>) DBUtil.execQuery(sql, User.class, keyword, keyword, "%" + keyword + "%");

        return list;
    }

}