package com.crazychat.client.biz.impl;

import java.util.Comparator;
import java.util.List;

import com.crazychat.client.ClientService;
import com.crazychat.client.biz.MessageBiz;
import com.crazychat.entity.DataPacket;
import com.crazychat.entity.DataPacketSignalEnum;
import com.crazychat.entity.Group;
import com.crazychat.entity.Message;
import com.crazychat.entity.User;

/**
 * 消息业务实现类
 * 
 * @author RuanYaofeng
 * @date 2018-04-21 10:15
 */
public class MessageBizImpl implements MessageBiz {

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
        case SEND_MESSAGE:
            if (packet.isSuccess()) {
                receiveMessage((Message) packet.getContent());
            } else {
                System.out.println(packet.getMessage()); // TODO GUI提示
            }
            break;

        case LIST_MESSAGE_RECORDS:
            // TODO 获取消息记录
            break;

        case LIST_ALL_MESSAGE_RECORDS:
            if (packet.isSuccess()) {
                ClientService.messages = (List<Message>) packet.getContent();

                // 排序
                ClientService.messages.sort(new Comparator<Message>() {

                    @Override
                    public int compare(Message msg1, Message msg2) {
                        return (int) (msg1.getId() - msg2.getId());
                    }
                });
                ClientService.loginFrame.updateLoginProgress();
            } else {
                ClientService.loginFrame.onError(packet.getMessage());
            }
            break;

        default:
            break;
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.crazychat.client.biz.MessageBiz#sendMessage(com.crazychat.entity.
     * Message)
     */
    @Override
    public void sendMessage(Object chatTarget, String messageContent) {

        Message message = new Message();
        message.setSenderId(ClientService.currentUser.getId());
        message.setMessageContent(messageContent);

        // 判断聊天对象的类型
        if (chatTarget != null && chatTarget instanceof User) {
            // 私聊的情形
            message.setMessageType(Message.TYPE_FRIEND);
            message.setReceiverId(((User) chatTarget).getId());
        } else if (chatTarget != null && chatTarget instanceof Group) {
            // 群聊的情形
            message.setMessageType(Message.TYPE_GROUP);
            message.setReceiverId(((Group) chatTarget).getId());
        } else {
            System.out.println("聊天对象类型错误"); // TODO 后续观察是否可能出现此情况
            return;
        }

        // 放入聊天记录集合
        ClientService.messages.add(message);

        // 刷新聊天界面
        ClientService.mainFrame.chatPanel.updateMessageRecord();
        ClientService.mainFrame.chatPanel.scrollToBottom();

        // 发送数据包
        DataPacket packet = new DataPacket(DataPacketSignalEnum.SEND_MESSAGE, message);
        sendRequest(packet);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.crazychat.client.biz.MessageBiz#receiveMessage(com.crazychat.entity.
     * Message)
     */
    @Override
    public void receiveMessage(Message message) {

        // 消息发送成功的响应Message对象为空无需处理
        if (message == null) {
            return;
        }

        // 获取消息对应的聊天对象对象
        Object chatTarget = null;
        if (message.getMessageType() == Message.TYPE_FRIEND) {
            // 收到的是私聊消息，则根据发送者查找
            for (User u : ClientService.friends) {
                if (message.getSenderId() == u.getId()) {
                    chatTarget = u;
                    break;
                }
            }
        } else {
            // 收到的是群聊消息，则根据接收者查找
            for (Group g : ClientService.groups) {
                if (message.getReceiverId() == g.getId()) {
                    chatTarget = g;
                    break;
                }
            }
        }

        // 找不到消息直接跳过
        if (chatTarget == null) {
            System.out.println(message); // TODO 先随便在控制台输出
            return;
        }

        // 加入消息记录
        ClientService.messages.add(message);

        // 加入会话列表
        if (ClientService.dialogs.contains(chatTarget)) {
            // 若已存在会话列表中，则先移除
            ClientService.dialogs.remove(chatTarget);
        }
        ClientService.dialogs.add(0, chatTarget); // 插入会话列表最前面
        ClientService.mainFrame.clpRecent.setListData(ClientService.dialogs); // 刷新列表

        /*
         * 判断是否正在和消息发送者聊天
         */
        // 先判断消息类型是否与正在聊天的对象匹配
        if (message.getMessageType() == ClientService.mainFrame.chatPanel.getChatMessageType()) {

            // 获取聊天对象ID
            int targetId = -1;
            if (ClientService.chatTarget instanceof User) {
                targetId = ((User) ClientService.chatTarget).getId();
            } else if (ClientService.chatTarget instanceof Group) {
                targetId = ((Group) ClientService.chatTarget).getId();
            }
            // 消息发送者ID == 聊天对象ID ?
            if ((message.getMessageType() == Message.TYPE_FRIEND && message.getSenderId() == targetId)
                    || (message.getMessageType() == Message.TYPE_GROUP && message.getReceiverId() == targetId)) {
                ClientService.mainFrame.chatPanel.updateMessageRecord();
                ClientService.mainFrame.chatPanel.scrollToBottom();

                // 执行到这里表示正在和消息发送者聊天，无需提示处理
                return;
            }
        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.crazychat.client.biz.MessageBiz#listMessageRecords(com.crazychat.
     * entity.Message)
     */
    @Override
    public void listMessageRecords(Message message) {
        // TODO 未实现：获取消息记录
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.crazychat.client.biz.MessageBiz#listAllMessageRecords()
     */
    @Override
    public void listAllMessageRecords() {
        DataPacket packet = new DataPacket();
        packet.setSignal(DataPacketSignalEnum.LIST_ALL_MESSAGE_RECORDS);
        packet.setContent(ClientService.currentUser.getId());
        sendRequest(packet);
    }

}
