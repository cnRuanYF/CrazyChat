package com.crazychat.client.socket;

import java.io.IOException;
import java.net.Socket;
import java.util.Properties;

import com.crazychat.client.ClientService;
import com.crazychat.client.constant.StringConst;

/**
 * 用于和服务器建立连接的线程
 * 
 * @author RuanYaofeng
 * @date 2018-04-17 16:56
 */
public class ServerConnector implements Runnable {

    /** 服务器主机 */
    private String host = null;
    /** 服务器端口 */
    private int port;

    /**
     * 构造一个建立连接的线程对象
     */
    public ServerConnector() {}

    /**
     * 构造一个建立连接的线程对象<br>
     * (指定服务器主机端口)
     * 
     * @param host 服务器主机
     * @param port 服务器端口
     */
    public ServerConnector(String host, int port) {
        this.host = host;
        this.port = port;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Runnable#run()
     */
    @Override
    public void run() {
        try {
            if (host == null) {
                // 若没有通过构造方法设置服务器主机端口，则从配置文件获取服务器参数
                Properties properties = new Properties();
                properties.load(getClass().getClassLoader().getResourceAsStream("server.properties"));
                host = properties.getProperty("server.host");
                port = Integer.parseInt(properties.getProperty("server.port"));
            }

            // 获取到服务器的Socket对象
            ClientService.socket = new Socket(host, port);

            // 启动接收消息线程
            ClientService.responseReceiver = new ResponseReceiver();
            new Thread(ClientService.responseReceiver).start(); // XXX 是否可用线程池？

            // 连接完毕执行继续登录操作
            ClientService.loginBiz.connected();

        } catch (NullPointerException e) {
            ClientService.loginFrame.onError(StringConst.ERROR_LOAD_SERVER_PROPERTIES);
        } catch (IOException e) {
            ClientService.loginFrame.onError(StringConst.CONNECTING_SERVER_FAILED);
        }

    }

}
