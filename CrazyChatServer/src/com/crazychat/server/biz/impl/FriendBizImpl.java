package com.crazychat.server.biz.impl;

import java.net.Socket;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.crazychat.dao.FriendDAO;
import com.crazychat.dao.impl.FriendDAOImpl;
import com.crazychat.entity.DataPacket;
import com.crazychat.entity.DataPacketSignalEnum;
import com.crazychat.entity.Friend;
import com.crazychat.entity.User;
import com.crazychat.server.ServerService;
import com.crazychat.server.biz.FriendBiz;

/**
 * 好友相关业务实现类
 * 
 * @author VisonSun
 * @date 2018-04-19 09:10
 */
public class FriendBizImpl implements FriendBiz {

    /*
     * (non-Javadoc)
     * 
     * @see com.crazychat.server.biz.BaseBiz#handleRequest(java.net.Socket,
     * com.crazychat.entity.DataPacket)
     */
    @Override
    public void handleRequest(Socket socket, DataPacket packet) {

        switch (packet.getSignal()) {
        case LIST_FRIENDS:
            Integer userId = (Integer) packet.getContent();
            listFriends(socket, userId);
            break;

        case ADD_FRIEND:
            Friend friend = (Friend) packet.getContent();
            addFriend(socket, friend);
            break;

        case DELETE_FRIEND:
            Friend deleteFriend = (Friend) packet.getContent();
            deleteFriend(socket, deleteFriend);
            break;

        case REMARK_FRIEND:
            Friend remarkFriend = (Friend) packet.getContent();
            remarkFriend(socket, remarkFriend);
            break;

        default:
            break;
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.crazychat.server.biz.FriendBiz#listFriends()
     */
    @Override
    public void listFriends(Socket socket, Integer userId) {

        DataPacket packet = new DataPacket();
        packet.setSignal(DataPacketSignalEnum.LIST_FRIENDS);

        // 数据库操作
        List<User> listFriends = null;
        FriendDAO dao = new FriendDAOImpl();

        try {
            listFriends = dao.findAll(userId);
        } catch (Exception e) {
            // TODO 异常！DAO异常提示优化
            packet.setSuccess(false);
            packet.setMessage(e.getMessage());
            sendResponse(socket, packet);
        }

        if (listFriends == null) {
            // 若好友列表为空,则操作失败并返回信息
            packet.setSuccess(false);
            packet.setMessage("还没有好友呢");// XXX 常量
        } else {
            // 若好友列表不为空,则存入数据包并发送响应
            packet.setSuccess(true);
            packet.setContent(listFriends);
        }
        sendResponse(socket, packet);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.crazychat.server.biz.FriendBiz#addFriend(java.net.Socket)
     */
    @Override
    public void addFriend(Socket socket, Friend friend) {

        DataPacket packet = new DataPacket();
        packet.setSignal(DataPacketSignalEnum.ADD_FRIEND);

        int userId = friend.getUserId();
        int friendId = friend.getFriendUser().getId();
        // 添加是否成功
        boolean flag = false;
        // 用户视角的好友关系对象
        Friend userToFriend = null;
        // 好友视角的好友关系对象
        Friend friendToUser = null;

        // 数据库操作
        FriendDAO dao = new FriendDAOImpl();
        try {
            // 不添加自己为好友
            if (friend.getUserId() != friend.getFriendUser().getId()) {
                flag = dao.add(userId, friendId);
            }
        } catch (Exception e) {
            // TODO 异常！DAO异常提示优化
            packet.setSuccess(false);
            packet.setMessage(e.getMessage());
            sendResponse(socket, packet);
        }

        // 如果成功
        // 先判断添加的好友是否在线, 在线即向其发送对方视角的Friend对象
        // 把添加的好友关系对象添加进数据包并返回响应
        if (flag) {
            try {
                // 将用户和好友视角的好友关系存入
                userToFriend = dao.findFriend(userId, friendId);
                friendToUser = dao.findFriend(friendId, userId);

                // 获取在线用户集合的键,即key(userId)的集合
                Set<Integer> loginId = ServerService.onlineUserSockets.keySet();

                // 遍历在线用户ID的集合
                Iterator<Integer> iteratorId = loginId.iterator();
                while (iteratorId.hasNext()) {
                    // 若好友在线, 存入好友视角的数据包, 获取socket, 并向其发送数据包
                    if (iteratorId.next() == friendId) {
                        DataPacket packetToFriend = new DataPacket(DataPacketSignalEnum.ADD_FRIEND,friendToUser);
                        packetToFriend.setSuccess(true);
                        sendResponse(ServerService.onlineUserSockets.get(friendId), packetToFriend);
                        break;
                    }
                }

                // 设置成功信号, 存入用户视角的数据包
                packet.setSuccess(true);
                packet.setContent(userToFriend);
                sendResponse(socket, packet);
            } catch (Exception e) {
                // TODO 异常！DAO异常提示优化
                packet.setSuccess(false);
                packet.setMessage(e.getMessage());
                sendResponse(socket, packet);
            }
        } else {
            packet.setSuccess(false);
            packet.setMessage("添加好友未成功");// XXX 常量
            sendResponse(socket, packet);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.crazychat.server.biz.FriendBiz#deleteFriend(java.net.Socket)
     */
    @Override
    public void deleteFriend(Socket socket, Friend friend) {

        DataPacket packet = new DataPacket();
        packet.setSignal(DataPacketSignalEnum.DELETE_FRIEND);

        int userId = friend.getUserId();
        int friendId = friend.getFriendUser().getId();

        // 删除是否成功
        boolean flag = false;

        // 数据库操作
        FriendDAO dao = new FriendDAOImpl();

        try {
            flag = dao.delete(userId, friendId);
        } catch (Exception e) {
            // TODO 异常！DAO异常提示优化
            packet.setSuccess(false);
            packet.setMessage(e.getMessage());
            sendResponse(socket, packet);
        }

        // 若操作成功
        // 先判断被删除好友是否在线, 把用户ID存入数据包并发给该好友的socket
        // 然后发送响应信息
        if (flag) {
            // 获取在线用户集合的键,即key(userId)的集合
            Set<Integer> loginId = ServerService.onlineUserSockets.keySet();

            // 遍历在线用户ID的集合
            Iterator<Integer> iteratorId = loginId.iterator();
            while (iteratorId.hasNext()) {
                // 若好友在线, 存入好友视角的数据包, 获取socket, 并向其发送数据包
                if (iteratorId.next() == friendId) {
                    packet.setContent(userId);
                    sendResponse(ServerService.onlineUserSockets.get(friendId), packet);
                }
            }

            packet.setSuccess(true);
            packet.setMessage("删除好友成功");// XXX 常量
        } else {
            packet.setSuccess(false);
            packet.setMessage("删除好友未成功");// XXX 常量
        }
        sendResponse(socket, packet);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.crazychat.server.biz.FriendBiz#remarkFriend(java.net.Socket)
     */
    @Override
    public void remarkFriend(Socket socket, Friend friend) {

        DataPacket packet = new DataPacket();
        packet.setSignal(DataPacketSignalEnum.REMARK_FRIEND);

        int userId = friend.getUserId();
        int friendId = friend.getFriendUser().getId();
        String remark = friend.getFriendRemark();
        // 改备注是否成功
        boolean flag = false;

        // 数据库操作
        FriendDAO dao = new FriendDAOImpl();
        try {
            flag = dao.update(userId, friendId, remark);
        } catch (Exception e) {
            // TODO 异常！DAO异常提示优化
            packet.setSuccess(false);
            packet.setMessage(e.getMessage());
            sendResponse(socket, packet);
        }

        // 如果成功,把好友关系对象存入数据包并返回响应
        if (flag) {
            packet.setSuccess(true);
            packet.setMessage("备注修改成功");// XXX 常量
        } else {
            packet.setSuccess(false);
            packet.setMessage("备注修改未成功");// XXX 常量
        }
        sendResponse(socket, packet);
    }
}
