package com.crazychat.server;

import java.awt.EventQueue;
import java.awt.Font;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.UIManager;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

import com.crazychat.entity.User;
import com.crazychat.server.biz.FriendBiz;
import com.crazychat.server.biz.GroupBiz;
import com.crazychat.server.biz.LoginBiz;
import com.crazychat.server.biz.MessageBiz;
import com.crazychat.server.biz.UserBiz;
import com.crazychat.server.biz.impl.FriendBizImpl;
import com.crazychat.server.biz.impl.GroupBizImpl;
import com.crazychat.server.biz.impl.LoginBizImpl;
import com.crazychat.server.biz.impl.MessageBizImpl;
import com.crazychat.server.biz.impl.UserBizImpl;
import com.crazychat.server.socket.ClientConnector;
import com.crazychat.server.socket.RequestReceiver;
import com.crazychat.server.ui.MainFrame;
import com.crazychat.server.ui.ServerLogsPanel;

/**
 * 服务端服务类<br>
 * 负责持有全局状态对象 (已连接的用户、Socket集合等)
 * 
 * @author ChenZhiJun, RuanYaofeng
 * @date 2018-04-17 16:28
 */
public class ServerService {

    /** 服务器Socket */
    public static ServerSocket serverSocket = null;

    /** 在线用户集合 */
    public static List<User> onlineUsers = null;
    /** 每个客户端连接对象 */
    public static List<Socket> clientSockets = null;

    /** 每个用户连接对象<用户ID, 客户端Socket> */
    public static HashMap<Integer, Socket> onlineUserSockets = null;

    /** 服务器监听线程 */
    private static ClientConnector clientConnector;
    
    /** 服务器端的日志面板 */
    public static ServerLogsPanel serverLogsPanel;
    
    /**
     * @param serverLogsPanel the serverLogsPanel to set
     */
    public static void setServerLogsPanel(ServerLogsPanel serverLogsPanel) {
        ServerService.serverLogsPanel = serverLogsPanel;
    }

    /*
     * 业务层对象
     */
    public static LoginBiz loginBiz = new LoginBizImpl();
    public static UserBiz userBiz = new UserBizImpl();
    public static FriendBiz friendBiz = new FriendBizImpl();
    public static GroupBiz groupBiz = new GroupBizImpl();
    public static MessageBiz messageBiz = new MessageBizImpl();

    /**
     * 初始化全局样式
     */
    static{
        // 加载皮肤
        try {
            UIManager.setLookAndFeel(new NimbusLookAndFeel());
        } catch (Exception e) {
            // TODO: handle exception
        }

        // 全局字体
        UIManager.put("Button.font", new Font("微软雅黑", Font.PLAIN, 14));
        UIManager.put("Label.font", new Font("微软雅黑", Font.PLAIN, 14));
        UIManager.put("Checkbox.font", new Font("微软雅黑", Font.PLAIN, 14));
    }

    /**
     * 初始化服务器
     */
    public static void initServer() {
        EventQueue.invokeLater(new Runnable() {

            public void run() {
                try {

                    // 显示窗口
                    MainFrame frame = new MainFrame();
                    frame.setVisible(true);

                    // 初始化集合
                    onlineUsers = new ArrayList<User>();
                    clientSockets = new ArrayList<Socket>();
                    onlineUserSockets = new HashMap<Integer, Socket>();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 启动服务器
     */
    public static void startServer() {
        clientConnector = new ClientConnector();
        new Thread(clientConnector).start();
    }

    /**
     * 启动服务器 (指定端口)
     * 
     * @param port 端口号
     */
    public static void startServer(int port) {
        clientConnector = new ClientConnector(port);
        new Thread(clientConnector).start();
    }

    /**
     * 关闭服务器
     */
    public static void stopServer() {
        clientConnector.stop();

        if (serverSocket != null) {
            try {
                serverSocket.close();
            } catch (IOException e) {
                // TODO 异常！ 资源释放失败
                e.printStackTrace();
            }
        }
        // TODO 关闭服务器后续清理操作
    }

    /**
     * 新的客户端连接 (由ClientConnector线程调用)
     * 
     * @param socket
     */
    public static void clientConnected(Socket socket) {
        clientSockets.add(socket);
        // 开启新的线程持续接收此客户端的请求
        new Thread(new RequestReceiver(socket)).start();// XXX 是否可用线程池优化？
    }

}
