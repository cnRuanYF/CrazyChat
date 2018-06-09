package com.crazychat.server.biz.impl;

import java.net.Socket;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.crazychat.dao.GroupMemberDAO;
import com.crazychat.dao.MessageDAO;
import com.crazychat.dao.impl.GroupMemberDAOImpl;
import com.crazychat.dao.impl.MessageDAOImpl;
import com.crazychat.entity.DataPacket;
import com.crazychat.entity.DataPacketSignalEnum;
import com.crazychat.entity.Group;
import com.crazychat.entity.Message;
import com.crazychat.entity.User;
import com.crazychat.server.ServerService;
import com.crazychat.server.biz.MessageBiz;

/**
 * 收发消息相关业务实现类
 * 
 * @author VisonSun
 * @date 2018-04-19 15:48
 */
public class MessageBizImpl implements MessageBiz {

    /*
     * (non-Javadoc)
     * 
     * @see com.crazychat.server.biz.BaseBiz#handleRequest(java.net.Socket,
     * com.crazychat.entity.DataPacket)
     */
    @Override
    public void handleRequest(Socket socket, DataPacket packet) {

        // 判断是好友消息还是群组消息
        switch (packet.getSignal()) {
        case SEND_MESSAGE:
            sendMessage(socket, (Message) packet.getContent());
            break;
        case LIST_MESSAGE_RECORDS:
            listMessageRecords(socket, (Message) packet.getContent());
            break;
        case LIST_ALL_MESSAGE_RECORDS:
            listAllMessageRecords(socket, (Integer) packet.getContent());
            break;
        default:
            break;
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.crazychat.server.biz.MessageBiz#sendMessage(com.crazychat.entity.
     * Message)
     */
    @Override
    public void sendMessage(Socket socket, Message message) {
        DataPacket packet = new DataPacket();
        packet.setSignal(DataPacketSignalEnum.SEND_MESSAGE);

        // 数据库操作
        boolean flag = false;
        MessageDAO dao = new MessageDAOImpl();

        try {
            flag = dao.add(message);
        } catch (Exception e) {
            // TODO 异常！DAO异常提示优化
            packet.setSuccess(false);
            packet.setMessage(e.getMessage());
            sendResponse(socket, packet);
            return;
        }

        // 操作是否成功
        if (flag) {
            // 判断类型并向在线对象发送信息数据包
            sendToOnline(message);
        } else {
            packet.setSuccess(false);
            packet.setMessage("消息发送未成功");// XXX 常量
            sendResponse(socket, packet);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.crazychat.server.biz.MessageBiz#messageRecords(com.crazychat.entity.
     * Message)
     */
    @Override
    public void listMessageRecords(Socket socket, Message message) {
        DataPacket packet = new DataPacket();
        packet.setSignal(DataPacketSignalEnum.LIST_MESSAGE_RECORDS);

        // 消息列表
        List<Message> listMessageRecord = null;

        // 数据库操作, 获取消息列表
        MessageDAO dao = new MessageDAOImpl();
        try {
            listMessageRecord = dao.findByReceiver(message.getMessageType(), message.getReceiverId());
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // 若成功, 则把列表存入数据包并响应
        if (listMessageRecord == null) {
            packet.setSuccess(false);
            packet.setMessage("获取消息失败或者还没有消息");// XXX 常量
        } else {
            packet.setSuccess(true);
            packet.setContent(listMessageRecord);
        }
        sendResponse(socket, packet);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.crazychat.server.biz.MessageBiz#messageRecords(java.net.Socket,
     * int)
     */
    @Override
    public void listAllMessageRecords(Socket socket, int userId) {
        DataPacket packet = new DataPacket();
        packet.setSignal(DataPacketSignalEnum.LIST_ALL_MESSAGE_RECORDS);

        // 消息列表
        List<Message> listMessageRecord = null;

        // 数据库操作, 获取消息列表
        MessageDAO dao = new MessageDAOImpl();
        try {
            listMessageRecord = dao.findAllMessage(userId);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // 若成功, 则把列表存入数据包并响应
        if (listMessageRecord == null) {
            packet.setSuccess(false);
            packet.setMessage("获取消息失败或者还没有消息");// XXX 常量
        } else {
            packet.setSuccess(true);
            packet.setContent(listMessageRecord);
        }
        sendResponse(socket, packet);
    }

    /**
     * 向在线的对象发送信息<br>
     * 分私聊和群聊
     * 
     * @param message 信息对象
     * @param socket 客户端对象
     */
    private void sendToOnline(Message message) {
        DataPacket packet = new DataPacket();
        packet.setSignal(DataPacketSignalEnum.SEND_MESSAGE);

        // 若是私聊消息, 判断是否在线
        if (message.getMessageType() == Message.TYPE_FRIEND) {
            sendMessageToUser(message, packet);
        }

        // 若是群聊消息, 发送给所有在线群员, 不包括自己
        else if (message.getMessageType() == Message.TYPE_GROUP) {
            sendMessageToGroup(message, packet);
        }
    }

    /**
     * 发送私聊消息
     * 
     * @param message 消息对象
     * @param packet 数据包
     */
    private void sendMessageToUser(Message message, DataPacket packet) {
        // 把消息信息存入数据包
        packet.setContent(message);

        // 获取并遍历在线用户ID的集合
        Set<Integer> loginId = ServerService.onlineUserSockets.keySet();
        Iterator<Integer> iteratorId = loginId.iterator();
        while (iteratorId.hasNext()) {
            // 若好友在线, 发送带有message的数据包给该好友的客户端socket
            if (iteratorId.next() == message.getReceiverId()) {
                packet.setSuccess(true);
                sendResponse(ServerService.onlineUserSockets.get(message.getReceiverId()), packet);
                break;
            }
        }
    }

    /**
     * 发送群消息
     * 
     * @param message 消息内容
     * @param packet 数据包
     */
    private void sendMessageToGroup(Message message, DataPacket packet) {

        GroupMemberDAO groupDao = new GroupMemberDAOImpl();
        try {
            // 数据库操作：获取群成员列表
            List<User> memberList = groupDao.listMembers(new Group(message.getReceiverId()));
            // 把消息信息存入数据包
            packet.setContent(message);

            // 获取并遍历在线用户ID的集合
            Set<Integer> loginId = ServerService.onlineUserSockets.keySet();
            Iterator<Integer> iteratorId = loginId.iterator();
            // 循环在线用户
            while (iteratorId.hasNext()) {
                // 循环群员
                for (User members : memberList) {
                    // 满足条件即在线群员
                    if (iteratorId.next() == members.getId()) {
                        // 除了发送者以外, 向其发送message数据包
                        if (message.getSenderId() != members.getId()) {
                            packet.setSuccess(true);
                            sendResponse(ServerService.onlineUserSockets.get(members.getId()), packet);
                        }
                    }
                }
            }
        } catch (Exception e) {
            // TODO 异常！DAO异常提示优化
            packet.setSuccess(false);
            packet.setMessage(e.getMessage());
            return;
        }
    }
}
