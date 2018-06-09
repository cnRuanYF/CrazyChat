package com.crazychat.server.socket;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.Socket;

import com.crazychat.entity.DataPacket;
import com.crazychat.server.ServerService;

/**
 * 用于接收数据包的线程
 * 
 * @author RuanYaofeng
 * @date 2018-04-18 14:25
 */
public class RequestReceiver implements Runnable {

    /** 线程是否运行 */
    private boolean running;

    /** 到客户端的Socket对象 */
    private Socket clientSocket;

    // 对象输入流
    private InputStream is = null;
    private BufferedInputStream bis = null;
    private ObjectInputStream ois = null;

    /**
     * 构造消息接收线程
     * 
     * @param clientSocket 到客户端的Socket对象
     */
    public RequestReceiver(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {

        running = true;

        try {
            while (running && clientSocket != null) {
                is = clientSocket.getInputStream();
                bis = new BufferedInputStream(is);
                ois = new ObjectInputStream(bis);
                Object obj = ois.readObject();

                // 判断读出的对象是否为数据包
                if (obj != null && obj instanceof DataPacket) {
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
                        ServerService.loginBiz.handleRequest(clientSocket, packet);
                        break;

                    /*
                     * 用户相关业务
                     */
                    case FIND_USER:
                    case GET_USER_PROFILE:
                    case UPDATE_USER_PROFILE:
                        ServerService.userBiz.handleRequest(clientSocket, packet);
                        break;

                    /*
                     * 好友相关业务
                     */
                    case LIST_FRIENDS:
                    case ADD_FRIEND:
                    case DELETE_FRIEND:
                    case REMARK_FRIEND:
                        ServerService.friendBiz.handleRequest(clientSocket, packet);
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
                        ServerService.groupBiz.handleRequest(clientSocket, packet);
                        break;

                    /*
                     * 消息收发相关业务
                     */
                    case SEND_MESSAGE:
                    case LIST_MESSAGE_RECORDS:
                    case LIST_ALL_MESSAGE_RECORDS:
                        ServerService.messageBiz.handleRequest(clientSocket, packet);
                        break;
                    /*
                     * 其他
                     */
                    default:
                        break;
                    }
                }
            }
        } catch (IOException e) {
            if(!clientSocket.isClosed() && clientSocket !=null) {
                ServerService.loginBiz.logoutFromClient(clientSocket);
            }
        } catch (ClassNotFoundException e) {
            ServerService.serverLogsPanel.appendMsg(e.getMessage());
        }
    }

    /**
     * 停止接收 (结束线程)
     */
    public void stop() {
        this.running = false;
    }

}
