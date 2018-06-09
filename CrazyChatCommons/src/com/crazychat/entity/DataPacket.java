package com.crazychat.entity;

import java.io.Serializable;

/**
 * 网络通信数据包实体类
 * 
 * @author RuanYaofeng
 * @date 2018年4月15日 上午12:55:46
 */
@SuppressWarnings("serial")
public class DataPacket implements Serializable {

    /** 信号 (表示业务类型) */
    private DataPacketSignalEnum signal;

    /** 数据包内容 */
    private Object content;

    /** (用于响应) 请求是否成功 */
    private boolean success;

    /** (用于响应) 响应信息 */
    private String message;

    /**
     * 构造一个数据包对象
     */
    public DataPacket() {}

    /**
     * 构造一个数据包对象
     * 
     * @param signal 信号类型
     * @param content 数据包内容
     */
    public DataPacket(DataPacketSignalEnum signal, Object content) {
        this.signal = signal;
        this.content = content;
    }

    /**
     * @return the signal
     */
    public DataPacketSignalEnum getSignal() {
        return signal;
    }

    /**
     * @param signal the signal to set
     */
    public void setSignal(DataPacketSignalEnum signal) {
        this.signal = signal;
    }

    /**
     * @return the content
     */
    public Object getContent() {
        return content;
    }

    /**
     * @param content the content to set
     */
    public void setContent(Object content) {
        this.content = content;
    }

    /**
     * @return the success
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * @param success the success to set
     */
    public void setSuccess(boolean success) {
        this.success = success;
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "DataPacket [signal=" + signal + ", content=" + content + ", success=" + success + ", message=" + message
                + "]";
    }

}
