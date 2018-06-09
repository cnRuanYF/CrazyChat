package com.crazychat.client.biz.impl;

import java.util.List;

import com.crazychat.client.ClientService;
import com.crazychat.client.biz.GroupBiz;
import com.crazychat.entity.DataPacket;
import com.crazychat.entity.DataPacketSignalEnum;
import com.crazychat.entity.Group;

/**
 * 群业务实现类
 * 
 * @author RuanYaofeng
 * @date 2018-04-18 21:49
 */
public class GroupBizImpl implements GroupBiz {

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
        case LIST_GROUPS:
            if (packet.isSuccess()) {
                ClientService.groups = (List<Group>) packet.getContent();
                ClientService.loginFrame.updateLoginProgress();

                // 继续获取消息记录
                ClientService.messageBiz.listAllMessageRecords();
            } else {
                ClientService.loginFrame.onError(packet.getMessage());
            }
            break;

        // TODO 其他响应的处理
        default:
            break;
        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.crazychat.client.biz.GroupBiz#listGroups()
     */
    @Override
    public void listGroups() {
        DataPacket packet = new DataPacket(DataPacketSignalEnum.LIST_GROUPS, ClientService.currentUser.getId());
        sendRequest(packet);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.crazychat.client.biz.GroupBiz#getGroupProfile(int)
     */
    @Override
    public void getGroupProfile(int groupId) {
        DataPacket packet = new DataPacket(DataPacketSignalEnum.GET_GROUP_PROFILE, groupId);
        sendRequest(packet);
    }

}
