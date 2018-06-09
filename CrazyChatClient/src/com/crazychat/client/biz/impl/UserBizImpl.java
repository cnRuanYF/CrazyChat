package com.crazychat.client.biz.impl;

import java.util.List;

import javax.swing.JOptionPane;

import com.crazychat.client.ClientService;
import com.crazychat.client.biz.UserBiz;
import com.crazychat.entity.DataPacket;
import com.crazychat.entity.DataPacketSignalEnum;
import com.crazychat.entity.User;

/**
 * 用户业务实现类
 * 
 * @author QiuWenYi, RuanYaofeng
 * @date 2018年4月23日 下午4:39:05
 */
public class UserBizImpl implements UserBiz {

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.crazychat.client.biz.BaseBiz#handleResponse(com.crazychat.entity.
     * DataPacket)
     */
    @SuppressWarnings("unchecked")
    @Override
    public void handleResponse(DataPacket packet) {
        switch (packet.getSignal()) {
        case FIND_USER:
            if (packet.isSuccess()) {
                ClientService.addFriendFrame.onUserFinded((List<User>) packet.getContent());
            } else {
                ClientService.addFriendFrame.onError(packet.getMessage());
            }
            break;

        case GET_USER_PROFILE:
            // TODO 获取用户信息业务
            break;

        case UPDATE_USER_PROFILE:
            JOptionPane.showMessageDialog(null, packet.getMessage());
        default:
            break;
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.crazychat.client.biz.UserBiz#findUser(java.lang.String)
     */
    @Override
    public void findUser(String keyword) {
        DataPacket packet = new DataPacket(DataPacketSignalEnum.FIND_USER, keyword);
        sendRequest(packet);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.crazychat.client.biz.UserBiz#getUserProfile(int)
     */
    @Override
    public void getUserProfile(int userId) {
        DataPacket packet = new DataPacket(DataPacketSignalEnum.GET_USER_PROFILE, userId);
        sendRequest(packet);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.crazychat.client.biz.UserBiz#updateUserProfile(com.crazychat.entity.
     * User)
     */
    @Override
    public void updateUserProfile(User user) {
        DataPacket packet = new DataPacket(DataPacketSignalEnum.UPDATE_USER_PROFILE, user);
        sendRequest(packet);
    }

}
