package com.crazychat.server.socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Properties;

import com.crazychat.server.ServerService;

/**
 * 持续接受客户端连接请求的线程
 * 
 * @author RuanYaofeng
 * @date 2018-04-18 18:21
 */
public class ClientConnector implements Runnable {

    /** 线程是否运行 */
    private boolean running;

    /** 监听端口 */
    private Integer port = null;

    /**
     * 构造持续接受客户端连接请求的线程对象
     */
    public ClientConnector() {}

    /**
     * 构造持续接受客户端连接请求的线程对象<br>
     * (指定监听端口)
     */
    public ClientConnector(int port) {
        this.port = port;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Runnable#run()
     */
    @Override
    public void run() {

        running = true;

        // 启动服务器Socket
        try {
            if (port == null) {
                // 若没有通过构造方法设置监听端口，则从配置文件获取参数
                Properties properties = new Properties();
                properties.load(getClass().getClassLoader().getResourceAsStream("server.properties"));
                port = Integer.parseInt(properties.getProperty("server.port"));
            }
            ServerService.serverSocket = new ServerSocket(port);
            ServerService.serverLogsPanel.appendMsg("服务器开启。");
        } catch (NullPointerException e) {
            System.out.println("读取服务器配置文件时出现错误，错误信息："+e.getMessage()); // XXX 字符串常量
            // TODO 记录日志
            ServerService.serverLogsPanel.appendMsg("读取服务器配置文件时出现错误，错误信息："+e.getMessage());
        } catch (IOException e) {
            System.out.println("启动服务器时出现错误，可能是端口被占用，错误信息："+e.getMessage()); // XXX 字符串常量
            // TODO 记录日志
            ServerService.serverLogsPanel.appendMsg("启动服务器时出现错误，可能是端口被占用，错误信息："+e.getMessage());
        }

        try {
            // 循环监听新的客户端连接
            while (running && ServerService.serverSocket != null) {
                Socket socket = ServerService.serverSocket.accept();
                ServerService.clientConnected(socket);
            }
        } catch (IOException e) {
            // TODO 记录日志
            ServerService.serverLogsPanel.appendMsg("服务器关闭。");
        }

    }

    /**
     * 停止监听 (结束线程)
     */
    public void stop() {
        this.running = false;
    }

}
