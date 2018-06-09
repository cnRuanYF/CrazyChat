package com.crazychat.client.socket;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

import com.crazychat.client.ClientService;
import com.crazychat.client.constant.StringConst;
import com.crazychat.entity.DataPacket;

/**
 * 用于发送数据包的线程
 * 
 * @author RuanYaofeng
 * @date 2018-04-17 09:20
 */
public class RequestSender implements Runnable {

    /** 要发送的数据包对象 */
    private DataPacket packet;

    /**
     * 构造一个数据包发送线程
     * 
     * @param packet 要发送的数据包对象
     */
    public RequestSender(DataPacket packet) {
        this.packet = packet;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Runnable#run()
     */
    @Override
   synchronized public void run() {
        if (ClientService.socket != null) {
            try {
                OutputStream os = ClientService.socket.getOutputStream();
                BufferedOutputStream bos = new BufferedOutputStream(os);
                ObjectOutputStream oos = new ObjectOutputStream(bos);
                oos.writeObject(packet);
                oos.flush();
            } catch (IOException e) {
                System.out.println(StringConst.ERROR_SOCKET_COMM + StringConst.ERROR_INFOMATION + e.getMessage());
            }
        }
    }

}
