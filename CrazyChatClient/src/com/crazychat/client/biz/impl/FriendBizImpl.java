package com.crazychat.client.biz.impl;

import java.util.List;

import javax.swing.JOptionPane;

import com.crazychat.client.ClientService;
import com.crazychat.client.biz.FriendBiz;
import com.crazychat.client.constant.StringConst;
import com.crazychat.entity.DataPacket;
import com.crazychat.entity.DataPacketSignalEnum;
import com.crazychat.entity.Friend;
import com.crazychat.entity.User;

/**
 * 好友业务实现类
 * 
 * @author RuanYaofeng, QiuWenYi
 * @date 2018-04-18 21:22
 */
public class FriendBizImpl implements FriendBiz {

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
        case LIST_FRIENDS:
            if (packet.isSuccess()) {
                ClientService.friends = (List<User>) packet.getContent();
                ClientService.loginFrame.updateLoginProgress();

                // 继续获取群列表
                ClientService.groupBiz.listGroups();
            } else {
                ClientService.loginFrame.onError(packet.getMessage());
            }
            break;

        case ADD_FRIEND:
            if (packet.isSuccess()) {
                User friendUser = ((Friend) packet.getContent()).getFriendUser();
                // XXX 使用字符串常量替代
                JOptionPane.showMessageDialog(null, "你已经和 " + friendUser.getNickname() + " 成为好友。",
                        StringConst.ADD_FRIEND_SUCCESS, JOptionPane.PLAIN_MESSAGE);
                ClientService.mainFrame.newFriendAdded(friendUser);
            } else {
                JOptionPane.showMessageDialog(null, packet.getMessage(), StringConst.ADD_FRIEND_FAILED,
                        JOptionPane.ERROR_MESSAGE);
            }
            break;

        default:
            break;
        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.crazychat.client.biz.FriendBiz#listFriends()
     */
    @Override
    public void listFriends() {
        DataPacket packet = new DataPacket();
        packet.setSignal(DataPacketSignalEnum.LIST_FRIENDS);
        packet.setContent(ClientService.currentUser.getId());
        sendRequest(packet);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.crazychat.client.biz.FriendBiz#addFriend(int)
     */
    @Override
    public void addFriend(int friendUserId) {
        DataPacket packet = new DataPacket();
        packet.setSignal(DataPacketSignalEnum.ADD_FRIEND);

        Friend friend = new Friend();
        friend.setUserId(ClientService.currentUser.getId());

        User friendUser = new User();
        friendUser.setId(friendUserId);

        friend.setFriendUser(friendUser);

        packet.setContent(friend);

        sendRequest(packet);
    }

}
