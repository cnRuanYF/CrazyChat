package com.crazychat.client.biz.impl;

import javax.swing.JOptionPane;

import com.crazychat.client.ClientService;
import com.crazychat.client.biz.LoginBiz;
import com.crazychat.client.constant.ImageConst;
import com.crazychat.client.constant.StringConst;
import com.crazychat.client.socket.ServerConnector;
import com.crazychat.entity.DataPacket;
import com.crazychat.entity.DataPacketSignalEnum;
import com.crazychat.entity.User;

/**
 * 用户登录业务实现类
 * 
 * @author RuanYaofeng
 * @date 2018-04-17 19:34
 */
public class LoginBizImpl implements LoginBiz {

    /** 要登录的用户 */
    private User loginUser;

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.crazychat.client.biz.BaseBiz#handleResponse(com.crazychat.entity.
     * DataPacket)
     */
    @Override
    public void handleResponse(DataPacket packet) {
        switch (packet.getSignal()) {
        case USER_LOGIN:
            if (packet.isSuccess()) {
                ClientService.currentUser = (User) packet.getContent();
                ClientService.loginFrame.updateLoginProgress();

                // 继续获取好友列表
                ClientService.friendBiz.listFriends();
            } else {
                ClientService.loginFrame.onError(packet.getMessage());
            }
            break;

        case USER_LOGOUT:
            JOptionPane.showMessageDialog(null, StringConst.ERROR_CONNECTION_CLOSED, StringConst.CRAZYCHAT,
                    JOptionPane.ERROR_MESSAGE, ImageConst.IC_TAB_DIALOG_FOCUS);
            ClientService.handleLogout();

        default:
            break;
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.crazychat.client.biz.LoginBiz#logout()
     */
    @Override
    public void logout() {
        DataPacket packet = new DataPacket(DataPacketSignalEnum.USER_LOGOUT, loginUser);
        sendRequest(packet);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.crazychat.client.biz.LoginBiz#login(com.crazychat.entity.User)
     */
    @Override
    public void login(User user) {
        loginUser = user;
        new Thread(new ServerConnector()).start(); // XXX 使用线程池代替

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.crazychat.client.biz.LoginBiz#connected()
     */
    @Override
    public void connected() {
        ClientService.loginFrame.updateLoginProgress();
        DataPacket packet = new DataPacket(DataPacketSignalEnum.USER_LOGIN, loginUser);
        sendRequest(packet);
    }

}
