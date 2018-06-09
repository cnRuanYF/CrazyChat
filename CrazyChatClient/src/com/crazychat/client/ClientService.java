package com.crazychat.client;

import java.awt.EventQueue;
import java.awt.Font;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.swing.UIManager;

import com.crazychat.client.biz.FriendBiz;
import com.crazychat.client.biz.GroupBiz;
import com.crazychat.client.biz.LoginBiz;
import com.crazychat.client.biz.MessageBiz;
import com.crazychat.client.biz.UserBiz;
import com.crazychat.client.biz.impl.FriendBizImpl;
import com.crazychat.client.biz.impl.GroupBizImpl;
import com.crazychat.client.biz.impl.LoginBizImpl;
import com.crazychat.client.biz.impl.MessageBizImpl;
import com.crazychat.client.biz.impl.UserBizImpl;
import com.crazychat.client.constant.StringConst;
import com.crazychat.client.socket.ResponseReceiver;
import com.crazychat.client.ui.MainFrame;
import com.crazychat.client.ui.AddFriendFrame;
import com.crazychat.client.ui.EditProfileFrame;
import com.crazychat.client.ui.LoginFrame;
import com.crazychat.entity.Group;
import com.crazychat.entity.Message;
import com.crazychat.entity.User;

/**
 * 客户端服务类<br>
 * 负责持有全局状态对象 (当前用户、会话列表等)
 * 
 * @author RuanYaofeng
 * @date 2018年4月16日 下午2:12:05
 */
public class ClientService {

    /*
     * 网络层对象
     */
    /** 到服务器的连接 */
    public static Socket socket = null;
    /** 接收响应的线程 */
    public static ResponseReceiver responseReceiver;

    /*
     * 状态对象
     */
    /** 当前登录用户 */
    public static User currentUser;

    /** 聊天对象 */
    public static Object chatTarget;

    /** 会话列表 */
    public static List<Object> dialogs = null;
    /** 好友列表 */
    public static List<User> friends = null;
    /** 群列表 */
    public static List<Group> groups = null;
    /** 消息记录 */
    public static List<Message> messages = null;

    /*
     * UI层对象
     */
    /** 登录界面 */
    public static LoginFrame loginFrame;
    /** 主界面 */
    public static MainFrame mainFrame;
    /** 资料修改界面 */
    public static EditProfileFrame editProfileFrame;
    /** 搜索用户/添加好友界面 */
    public static AddFriendFrame addFriendFrame;

    /*
     * 业务层对象
     */
    public static LoginBiz loginBiz = new LoginBizImpl();
    public static UserBiz userBiz = new UserBizImpl();
    public static FriendBiz friendBiz = new FriendBizImpl();
    public static GroupBiz groupBiz = new GroupBizImpl();
    public static MessageBiz messageBiz = new MessageBizImpl();

    /*
     * 其他
     */
    public static String registerURL = null;

    /**
     * 初始化全局对象
     */
    static {
        // 全局字体
        UIManager.put("Label.font", new Font(StringConst.DEFAULT_FONT_NAME, Font.PLAIN, 14));
        UIManager.put("Button.font", new Font(StringConst.DEFAULT_FONT_NAME, Font.PLAIN, 14));
        UIManager.put("CheckBox.font", new Font(StringConst.DEFAULT_FONT_NAME, Font.PLAIN, 14));
        UIManager.put("TextField.font", new Font(StringConst.DEFAULT_FONT_NAME, Font.PLAIN, 14));
        UIManager.put("TextArea.font", new Font(StringConst.DEFAULT_FONT_NAME, Font.PLAIN, 14));
        UIManager.put("TextPane.font", new Font(StringConst.DEFAULT_FONT_NAME, Font.PLAIN, 14));
        UIManager.put("Spinner.font", new Font(StringConst.DEFAULT_FONT_NAME, Font.PLAIN, 14));
        UIManager.put("RadioButton.font", new Font(StringConst.DEFAULT_FONT_NAME, Font.PLAIN, 14));

        // 读取服务器配置
        try {
            Properties properties = new Properties();
            properties.load(ClientService.class.getClassLoader().getResourceAsStream("server.properties"));
            registerURL = properties.getProperty("websrv.register");
        } catch (IOException e) {
            System.err.println(StringConst.ERROR_LOAD_SERVER_PROPERTIES);
        }
    }

    /**
     * 初始化客户端 (显示登录界面)
     */
    public static void initClient() {
        // 初始化集合
        dialogs = new ArrayList<>();

        // 显示登录界面
        EventQueue.invokeLater(new Runnable() {

            public void run() {
                try {
                    loginFrame = new LoginFrame();
                    loginFrame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace(); // XXX 捕捉异常 (显示窗口的时候)
                }
            }
        });
    }

    /**
     * (登录成功) 显示主界面
     */
    public static void showMainFrame() {
        /*
         * 初始化最近会话列表
         */
        for (Message message : messages) { // 遍历所有消息
            // 要获取的聊天对象
            Object target = null;

            // 判断消息类型
            if (message.getMessageType() == Message.TYPE_GROUP) { // 群消息
                // 遍历所有群
                for (Group g : groups) {
                    // 群消息无论是不是自己发的,接收者都是群ID
                    if (g.getId() == message.getReceiverId()) {
                        target = g;
                        continue;
                    }
                }
            } else { // 私聊消息
                // 遍历所有好友
                for (User u : friends) {
                    // 好友消息判断：发送者或接收者为对方
                    if (u.getId() == message.getReceiverId() || u.getId() == message.getSenderId()) {
                        target = u;
                        continue;
                    }
                }
            }

            // 若目标不为空则加入会话列表
            if (target != null) {
                // 若已存在会话列表中，则先移除
                if (ClientService.dialogs.contains(target)) {
                    ClientService.dialogs.remove(target);
                }

                // 插入会话列表最前面
                ClientService.dialogs.add(0, target);
            }
        }

        /*
         * 显示主界面
         */
        EventQueue.invokeLater(new Runnable() {

            public void run() {
                try {
                    if (loginFrame != null) {
                        loginFrame.dispose();
                    }

                    mainFrame = new MainFrame();
                    mainFrame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace(); // XXX 捕捉异常 (显示窗口的时候)
                }
            }
        });
    }

    /**
     * 显示用户资料修改界面
     */
    public static void showEditProfileFrame() {
        EventQueue.invokeLater(new Runnable() {

            public void run() {
                try {
                    if (editProfileFrame == null) {
                        editProfileFrame = new EditProfileFrame();
                        editProfileFrame.setVisible(true);
                    } else {
                        editProfileFrame.requestFocus();
                    }
                } catch (Exception e) {
                    e.printStackTrace(); // XXX 捕捉异常 (显示窗口的时候)
                }
            }
        });
    }

    /**
     * 显示添加好友界面
     */
    public static void showAddFriendFrame() {
        EventQueue.invokeLater(new Runnable() {

            public void run() {
                try {
                    if (addFriendFrame == null) {
                        addFriendFrame = new AddFriendFrame();
                        addFriendFrame.setVisible(true);
                    } else {
                        addFriendFrame.requestFocus();
                    }
                } catch (Exception e) {
                    e.printStackTrace(); // XXX 捕捉异常 (显示窗口的时候)
                }
            }
        });
    }

    /**
     * 处理退出登录
     */
    public static void handleLogout() {
        // 释放UI对象
        mainFrame.dispose();
        mainFrame = null;
        loginFrame.dispose();
        loginFrame = null;

        // 释放状态对象
        currentUser = null;
        socket = null;

        // 释放集合
        dialogs = null;
        friends = null;
        groups = null;
        messages = null;

        // 倒垃圾
        System.gc();

        // 重新初始化
        initClient();
    }

}
