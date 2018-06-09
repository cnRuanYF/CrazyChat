package com.crazychat.server.biz.impl;

import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.crazychat.dao.UserDAO;
import com.crazychat.dao.impl.UserDAOImpl;
import com.crazychat.entity.DataPacket;
import com.crazychat.entity.DataPacketSignalEnum;
import com.crazychat.entity.User;
import com.crazychat.server.ServerService;
import com.crazychat.server.biz.LoginBiz;
import com.crazychat.server.constant.StringConst;

/**
 * 登录相关业务实现类
 * 
 * @author RuanYaofeng, VisonSun
 * @date 2018-04-19 00:58
 */
public class LoginBizImpl implements LoginBiz {

    /*
     * (non-Javadoc)
     * 
     * @see com.crazychat.server.biz.BaseBiz#handleRequest(java.net.Socket,
     * com.crazychat.entity.DataPacket)
     */
    @Override
    public void handleRequest(Socket socket, DataPacket packet) {
        switch (packet.getSignal()) {
        case USER_LOGIN:
            User loginUser = (User) packet.getContent();
            login(socket, loginUser);
            break;

        case USER_LOGOUT:
            logoutFromClient(socket);
            break;

        default:
            break;
        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.crazychat.server.biz.LoginBiz#login(com.crazychat.entity.User)
     */
    @Override
    public void login(Socket socket, User loginUser) {
        DataPacket packet = new DataPacket();
        packet.setSignal(DataPacketSignalEnum.USER_LOGIN);

        // 数据库操作
        User fullUser;
        UserDAO dao = new UserDAOImpl();
        try {
            fullUser = dao.findByUsername(loginUser.getUsername());
        } catch (Exception e) {
            // TODO 异常！DAO异常提示优化
            packet.setSuccess(false);
            packet.setMessage(e.getMessage());
            sendResponse(socket, packet);
            return;
        }

        if (fullUser == null) {
            packet.setSuccess(false);
            packet.setMessage(StringConst.ERROR_USER_NOT_FOUND);
        } else if (!loginUser.getPassword().equals(fullUser.getPassword())) {
            packet.setSuccess(false);
            packet.setMessage(StringConst.ERROR_WRONG_PASSWORD);
        } else if (isLogin(fullUser)) {
            packet.setSuccess(false);
            packet.setMessage(StringConst.ERROR_USER_IS_ALREADY_LOGINED);
        } else {
            packet.setSuccess(true);
            packet.setContent(fullUser);
            // 把登录的用户存入在线用户集合&在线用户socket集合
            ServerService.onlineUserSockets.put(fullUser.getId(), socket);
            ServerService.onlineUsers.add(fullUser);
        }
        sendResponse(socket, packet);

        // TODO 日志记录 - 用户登录
        ServerService.serverLogsPanel.appendMsg("用户 : [ " + loginUser.getUsername() + " ] 客户端登录成功");
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.crazychat.server.biz.LoginBiz#logoutFromClient(java.net.Socket)
     */
    @Override
    public void logoutFromClient(Socket socket) {
        // TODO 日志记录 - 客户端下线
        try {
            if (!socket.isClosed()) {
                ServerService.serverLogsPanel
                        .appendMsg("用户 : [ " + getUser(getUserid(socket)).getUsername() + " ] 客户端自行下线");

                // 从在线用户集合中删除
                for (int i = 0; i < ServerService.onlineUsers.size(); i++) {
                    if (ServerService.onlineUsers.get(i).getId() == getUserid(socket)) {
                        ServerService.onlineUsers.remove(i);
                    }
                }
                // 从在线用户socket集合中移除
                ServerService.onlineUserSockets.remove(getUserid(socket));
            }
        } catch (Exception e) {
            // 若被服务器下线过，则客户端关闭会报空指针异常
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.crazychat.server.biz.LoginBiz#logoutFromServer(com.crazychat.entity.
     * User)
     */
    @Override
    public void logoutFromServer(User user) {
        DataPacket packet = new DataPacket();
        packet.setSignal(DataPacketSignalEnum.USER_LOGOUT);

        User fullUser;
        UserDAO dao = new UserDAOImpl();
        // 数据库操作, 按ID找到用户
        try {
            fullUser = dao.findById(user.getId());
        } catch (Exception e) {
            // TODO 异常！DAO异常提示优化
            packet.setSuccess(false);
            packet.setMessage(e.getMessage());
            sendResponse(ServerService.onlineUserSockets.get(user.getId()), packet);
            return;
        }

        // 若用户为空
        if (fullUser == null) {
            packet.setSuccess(false);
            packet.setMessage(StringConst.ERROR_USER_NOT_FOUND);
        }
        // 用户不为空，响应给其，并从在线集合中删除
        else {
            packet.setSuccess(true);
            packet.setContent(fullUser);

            // 响应该用户下线了
            sendResponse(ServerService.onlineUserSockets.get(user.getId()), packet);

            // 从在线用户socket集合中移除
            ServerService.onlineUserSockets.remove(user.getId());

            // 从在线用户集合中删除
            for (int i = 0; i < ServerService.onlineUsers.size(); i++) {
                if (ServerService.onlineUsers.get(i).getId() == user.getId()) {
                    ServerService.onlineUsers.remove(i);
                }
            }
            // TODO 服务端"强行"下线
            ServerService.serverLogsPanel.appendMsg("用户 : [ " + fullUser.getUsername() + " ] 被权限DOG强制断开连接");
        }
    }

    public void closeServer() {
        DataPacket packet = new DataPacket();
        packet.setSignal(DataPacketSignalEnum.USER_LOGOUT);
        packet.setSuccess(true);

        // 获取在线用户集合的键,即key(userId)的集合
        Set<Integer> loginId = ServerService.onlineUserSockets.keySet();
        // 遍历在线用户ID的集合
        Iterator<Integer> iteratorId = loginId.iterator();
        while (iteratorId.hasNext()) {
            // 响应该用户下线了
            // 从在线用户socket集合中移除
            int userId = iteratorId.next();
            sendResponse(ServerService.onlineUserSockets.get(userId), packet);
            ServerService.onlineUserSockets.remove(userId);
        }

        // 从在线用户集合中删除
        for (int i = 0; i < ServerService.onlineUsers.size(); i++) {
            // 服务端关服下线
            ServerService.serverLogsPanel
                    .appendMsg("用户 : [ " + ServerService.onlineUsers.get(i).getUsername() + " ] 断开连接");
            ServerService.onlineUsers.remove(i);
        }
    }

    /**
     * 判断是否已登陆
     * 
     * @param fullUser 判断的账号用户对象
     * @return 是否登录
     */
    private boolean isLogin(User fullUser) {

        boolean flagList = false;
        boolean flagSocket = false;

        // 判断在线用户集合中是否存在该用户
        for (int i = 0; i < ServerService.onlineUsers.size(); i++) {
            if (ServerService.onlineUsers.get(i).getId() == fullUser.getId()) {
                flagList = true;
                break;
            }
        }

        // 判断socket集合中是否存在该用户
        Set<Integer> loginId = ServerService.onlineUserSockets.keySet();
        Iterator<Integer> iteratorId = loginId.iterator();
        while (iteratorId.hasNext()) {
            // 若该用户已在线, 返回
            if (iteratorId.next() == fullUser.getId()) {
                flagSocket = true;
                break;
            }
        }

        // 若都存在, 则返回值true && true = true
        return flagList && flagSocket;
    }

    /**
     * 根据socket查找用户ID
     * 
     * @param socket 客户端socket对象
     * @return 用户ID
     */
    private Integer getUserid(Socket socket) {
        ArrayList<Integer> keyList = new ArrayList<>();
        Integer key = null;
        // entrySet()方法就是把map中的每个键值对变成对应成Set集合中的一个对象.
        Set<Entry<Integer, Socket>> set = ServerService.onlineUserSockets.entrySet();
        Iterator<Entry<Integer, Socket>> it = set.iterator();
        while (it.hasNext()) {
            Map.Entry<Integer, Socket> entry = (Map.Entry<Integer, Socket>) it.next();
            // entry中的内容就是set集合中的每个对象(map集合中的一个键值对)3=c....
            // Map.Entry就是一种类型,专值map中的一个键值对组成的对象.
            if (entry.getValue().equals(socket)) {
                key = (Integer) entry.getKey();
                keyList.add(key);
            }
        }
        if (keyList.size() > 0) {
            return keyList.get(0);
        }
        return -1;
    }

    /**
     * 根据用户ID查找用户对象
     * 
     * @param userId 用户ID
     * @return 用户对象
     */
    private User getUser(int userId) {
        User user = null;
        try {
            user = new UserDAOImpl().findById(userId);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return user;
    }

}
