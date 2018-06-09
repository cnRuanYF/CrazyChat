package com.crazychat.client.socket;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;

import javax.swing.JOptionPane;

import com.crazychat.client.ClientService;
import com.crazychat.client.constant.ImageConst;
import com.crazychat.client.constant.StringConst;
import com.crazychat.entity.DataPacket;

/**
 * 用于接收数据包的线程
 * 
 * @author RuanYaofeng
 * @date 2018-04-16 16:20
 */
public class ResponseReceiver implements Runnable {

    /** 线程是否运行 */
    private boolean running;

    /*
     * 对象输入流
     */
    private InputStream is = null;
    private BufferedInputStream bis = null;
    private ObjectInputStream ois = null;

    @Override
    public void run() {
        running = true;

        while (running && ClientService.socket != null) {
            try {
                is = ClientService.socket.getInputStream();
                bis = new BufferedInputStream(is);
                ois = new ObjectInputStream(bis);
                Object obj = ois.readObject();

                // FIXME 模拟网络延时
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {}

                // 判断读出的对象是否为数据包
                if (obj instanceof DataPacket) {
                    DataPacket packet = (DataPacket) obj;

                    // 根据数据包信号
                    switch (packet.getSignal()) {

                    /*
                     * 登录相关业务
                     */
                    case USER_REGISTER:
                    case USER_LOGIN:
                    case USER_LOGOUT:
                    case USER_CHANGE_STATUS:
                        ClientService.loginBiz.handleResponse(packet);
                        break;

                    /*
                     * 用户相关业务
                     */
                    case FIND_USER:
                    case GET_USER_PROFILE:
                    case UPDATE_USER_PROFILE:
                        ClientService.userBiz.handleResponse(packet);
                        break;

                    /*
                     * 好友相关业务
                     */
                    case LIST_FRIENDS:
                    case ADD_FRIEND:
                    case DELETE_FRIEND:
                    case REMARK_FRIEND:
                        ClientService.friendBiz.handleResponse(packet);
                        break;

                    /*
                     * 群相关业务
                     */
                    case LIST_GROUPS:
                    case GET_GROUP_PROFILE:
                    case UPDATE_GROUP_PROFILE:
                    case CREATE_GROUP:
                    case FIND_GROUP:
                    case JOIN_GROUP:
                    case EXIT_GROUP:
                    case KICK_OUT_GROUP:
                        ClientService.groupBiz.handleResponse(packet);
                        break;

                    /*
                     * 消息收发相关业务
                     */
                    case SEND_MESSAGE:
                    case LIST_MESSAGE_RECORDS:
                    case LIST_ALL_MESSAGE_RECORDS:
                        ClientService.messageBiz.handleResponse(packet);
                        break;
                    /*
                     * 其他
                     */
                    default:
                        break;
                    }
                }
            } catch (NullPointerException e) {
                JOptionPane.showMessageDialog(null,
                        StringConst.ERROR_CONNECTION_CLOSED + StringConst.ERROR_INFOMATION + "[NPE]" + e.getMessage(),
                        StringConst.CRAZYCHAT, JOptionPane.ERROR_MESSAGE, ImageConst.IC_TAB_DIALOG_FOCUS);
                ClientService.handleLogout();
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null,
                        StringConst.ERROR_CONNECTION_CLOSED + StringConst.ERROR_INFOMATION + "[IOE]" + e.getMessage(),
                        StringConst.CRAZYCHAT, JOptionPane.ERROR_MESSAGE, ImageConst.IC_TAB_DIALOG_FOCUS);
                ClientService.handleLogout();
            } catch (ClassNotFoundException e) {
                JOptionPane.showMessageDialog(null,
                        StringConst.ERROR_CONNECTION_CLOSED + StringConst.ERROR_INFOMATION + e.getMessage(),
                        StringConst.CRAZYCHAT, JOptionPane.ERROR_MESSAGE, ImageConst.IC_TAB_DIALOG_FOCUS);
                ClientService.handleLogout();
            }
        }
    }

    /**
     * 停止接收 (结束线程)
     */
    public void stop() {
        this.running = false;
    }

}
