package com.crazychat.server.biz.impl;

import java.net.Socket;
import java.util.List;

import com.crazychat.dao.UserDAO;
import com.crazychat.dao.impl.UserDAOImpl;
import com.crazychat.entity.DataPacket;
import com.crazychat.entity.DataPacketSignalEnum;
import com.crazychat.entity.User;
import com.crazychat.server.biz.UserBiz;
import com.crazychat.server.constant.StringConst;

/**
 * 用户业务实现类
 * 
 * @author QiuWenYi, RuanYaofeng
 * @date 2018年4月19日 下午1:59:53
 */
public class UserBizImpl implements UserBiz {

    /*
     * (non-Javadoc)
     * 
     * @see com.crazychat.server.biz.BaseBiz#handleRequest(java.net.Socket,
     * com.crazychat.entity.DataPacket)
     */
    @Override
    public void handleRequest(Socket socket, DataPacket packet) {
        
        switch (packet.getSignal()) {
        case FIND_USER:
            findUser(socket, (String) packet.getContent());
            break;

        case GET_USER_PROFILE:
            User loginUser = (User) packet.getContent();
            getUserProfile(socket, loginUser.getId());
            break;

        case UPDATE_USER_PROFILE:
            User user = (User) packet.getContent();
            updateUserProfile(socket, user);
            break;

        default:
            break;
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.crazychat.server.biz.UserBiz#findUser(java.net.Socket, int)
     */
    @Override
    public void findUser(Socket socket, String keyword) {
        DataPacket packet = new DataPacket();
        packet.setSignal(DataPacketSignalEnum.FIND_USER);

        // 数据库操作
        List<User> userList = null;
        UserDAO dao = new UserDAOImpl();

        try {
            userList = dao.findByKeyword(keyword);
        } catch (Exception e) {
            // TODO 异常！DAO异常提示优化
            packet.setSuccess(false);
            packet.setMessage(e.getMessage());
            sendResponse(socket, packet);
            return;
        }

        if (userList != null) {
            packet.setSuccess(true);
            packet.setContent(userList);
        } else {
            packet.setSuccess(false);
            packet.setMessage(StringConst.NO_USER_HAS_BEEN_FOUND);
        }
        sendResponse(socket, packet);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.crazychat.server.biz.UserBiz#getUserProfile(int)
     */
    @Override
    public void getUserProfile(Socket socket, int userId) {
        DataPacket packet = new DataPacket();
        packet.setSignal(DataPacketSignalEnum.GET_USER_PROFILE);

        // 数据库操作
        User userProfile = null;
        UserDAO dao = new UserDAOImpl();

        try {
            userProfile = dao.findById(userId);
        } catch (Exception e) {
            // TODO 异常！DAO异常提示优化
            packet.setSuccess(false);
            packet.setMessage(e.getMessage());
            sendResponse(socket, packet);
            return;
        }

        if (userProfile != null) {
            packet.setSuccess(true);
            packet.setMessage(StringConst.GET_USER_PROFILE_SUCCESS);
            packet.setContent(userProfile);
        } else {
            packet.setSuccess(false);
            packet.setMessage(StringConst.GET_USER_PROFILE_FAILED);
        }
        sendResponse(socket, packet);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.crazychat.server.biz.UserBiz#updateUserProfile(com.crazychat.entity.
     * User)
     */
    @Override
    public void updateUserProfile(Socket socket, User user) {
        DataPacket packet = new DataPacket();
        packet.setSignal(DataPacketSignalEnum.UPDATE_USER_PROFILE);

        // 数据库操作
        boolean success = false;
        UserDAO dao = new UserDAOImpl();
        try {
            success = dao.update(user);
        } catch (Exception e) {
            // TODO 异常！DAO异常提示优化
            packet.setSuccess(false);
            packet.setMessage(e.getMessage());
            sendResponse(socket, packet);
            return;
        }

        if (success) {
            packet.setSuccess(true);
            packet.setMessage(StringConst.UPDATE_USER_PROFILE_SUCCESS);
        } else {
            packet.setSuccess(false);
            packet.setMessage(StringConst.UPDATE_USER_PROFILE_FAILED);
        }
        sendResponse(socket, packet);

    }

}
