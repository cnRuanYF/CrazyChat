package com.crazychat.dao.impl;

import java.util.List;

import com.crazychat.dao.MessageDAO;
import com.crazychat.entity.Message;
import com.crazychat.util.DBUtil;

/**
 * 聊天消息DAO实现类
 * 
 * @author VisonSun
 * @date 2018-04-17 00:12
 */
public class MessageDAOImpl implements MessageDAO {

    /** 数据表中的所有字段 */
    private static final String ALL_COLUMNS = "id,messageType,senderId,receiverId,messageContent,sendTime";

    /*
     * (non-Javadoc)
     * 
     * @see com.crazychat.dao.BaseDAO#add(java.lang.Object)
     */
    @Override
    public boolean add(Message obj) throws Exception {
        String sql = "insert into tb_message values(null,?,?,?,?,now())";

        // 参数 sendTime 默认 now()
        int flag = DBUtil.execUpdate(sql, obj.getMessageType(), obj.getSenderId(), obj.getReceiverId(),
                obj.getMessageContent());

        return flag > 0;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.crazychat.dao.BaseDAO#delete(java.lang.Object)
     */
    @Override
    public boolean delete(Message obj) throws Exception {
        // 根据传入的信息对象的ID删除
        String sql = "delete from tb_message where id=?";
        int flag = DBUtil.execUpdate(sql, obj.getId());
        return flag > 0;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.crazychat.dao.BaseDAO#update(java.lang.Object)
     */
    @Override
    public boolean update(Message obj) throws Exception {
        // 聊天信息禁止修改
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.crazychat.dao.BaseDAO#findAll()
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Message> findAll() throws Exception {
        String sql = "select " + ALL_COLUMNS + " from tb_message";
        List<Message> list = (List<Message>) DBUtil.execQuery(sql, Message.class);

        return list;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.crazychat.dao.BaseDAO#findById(java.lang.Object)
     */
    @SuppressWarnings("unchecked")
    @Override
    public Message findById(Long id) throws Exception {
        String sql = "select " + ALL_COLUMNS + " from tb_message where id=?";
        List<Message> list = (List<Message>) DBUtil.execQuery(sql, Message.class, id);

        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.crazychat.dao.MessageDAO#findByType(int)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Message> findByType(int messageType) throws Exception {
        String sql = "select " + ALL_COLUMNS + " from tb_message where messageType=?";
        List<Message> list = (List<Message>) DBUtil.execQuery(sql, Message.class, messageType);

        return list;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.crazychat.dao.MessageDAO#findBySender(int)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Message> findBySender(int senderId) throws Exception {
        String sql = "select " + ALL_COLUMNS + " from tb_message where senderId=?";
        List<Message> list = (List<Message>) DBUtil.execQuery(sql, Message.class, senderId);
        return list;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.crazychat.dao.MessageDAO#findByReceiver(int, int)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Message> findByReceiver(int messageType, int receiverId) throws Exception {
        String sql = "select " + ALL_COLUMNS + " from tb_message where messageType=? and receiverId=?";
        List<Message> list = (List<Message>) DBUtil.execQuery(sql, Message.class, messageType, receiverId);
        return list;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.crazychat.dao.MessageDAO#findAllMessage(int)
     */
    @Override
    public List<Message> findAllMessage(int userId) throws Exception {
        List<Message> list = findGroupMessageWithoutSender(userId);
        List<Message> listFriendMessage = findFriendMessage(userId);
        List<Message> listSenderMessage = findBySender(userId);

        // 把所有私聊消息去重加入list
        list.removeAll(listFriendMessage);
        list.addAll(listFriendMessage);
        
        // 把所有自己发送的消息去重加入list
        list.removeAll(listSenderMessage);
        list.addAll(listSenderMessage);
        
        return list;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.crazychat.dao.MessageDAO#findBySenderAndReceiver(int, int, int)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Message> findBySenderAndReceiver(int senderId, int messageType, int receiverId) throws Exception {
        String sql = "select " + ALL_COLUMNS + " from tb_message where senderId=? and messageType=? and receiverId=?";
        List<Message> list = (List<Message>) DBUtil.execQuery(sql, Message.class, senderId, messageType, receiverId);
        return list;
    }

    /**
     * 查找用户所有私聊消息
     * 
     * @param userId 接收者ID
     * @return 查找到的消息对象集合，未找到则返回null
     * @throws Exception 处理过程中发生错误均抛出异常到业务层处理
     */
    @SuppressWarnings("unchecked")
    private List<Message> findFriendMessage(int userId) throws Exception {
        String sql = "select " + ALL_COLUMNS + " from tb_message where messageType=? and receiverId=?";
        List<Message> list = (List<Message>) DBUtil.execQuery(sql, Message.class, Message.TYPE_FRIEND, userId);
        return list;
    }

    /**
     * 查询用户所有群消息(除了自己发送的群消息)
     * 
     * @param userId 接收者ID
     * @return 查找到的消息对象集合，未找到则返回null
     * @throws Exception 处理过程中发生错误均抛出异常到业务层处理
     */
    @SuppressWarnings("unchecked")
    private List<Message> findGroupMessageWithoutSender(int userId) throws Exception {
        String sql = "select " + ALL_COLUMNS
                + " from tb_message,tb_groupmember where receiverId=groupId and messageType=? and userId=? and senderId<>?";
        List<Message> list = (List<Message>) DBUtil.execQuery(sql, Message.class, Message.TYPE_GROUP, userId, userId);
        return list;
    }
}
