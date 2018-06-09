package com.crazychat.server.socket;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

import com.crazychat.entity.DataPacket;
import com.crazychat.server.ServerService;

/**
 * 用于发送响应数据包的线程
 * 
 * @author RuanYaofeng
 * @date 2018-04-18 14:21
 */
public class ResponseSender implements Runnable {

    /** 要发送到的Socket对象 */
    private Socket socket;
    /** 要发送的数据包对象 */
    private DataPacket packet;

    /**
     * 构造一个数据包发送线程
     * 
     * @param socket 要发送到的Socket对象
     * @param packet 要发送的数据包对象
     */
    public ResponseSender(Socket socket, DataPacket packet) {
        this.socket = socket;
        this.packet = packet;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Runnable#run()
     */
    @Override
    public void run() {
        if (socket != null) {
            try {
                OutputStream os = socket.getOutputStream();
                BufferedOutputStream bos = new BufferedOutputStream(os);
                ObjectOutputStream oos = new ObjectOutputStream(bos);
                oos.writeObject(packet);
                oos.flush();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                ServerService.serverLogsPanel.appendMsg("响应数据包线程ResponseSender出错");
                e.printStackTrace();
            }
        }
    }

}
