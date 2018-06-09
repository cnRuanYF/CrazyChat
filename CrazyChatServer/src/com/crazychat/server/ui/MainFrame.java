package com.crazychat.server.ui;

import javax.swing.JFrame;

import com.crazychat.server.constant.StringConst;
import javax.swing.JTabbedPane;
import java.awt.BorderLayout;

/**
 * 服务端主窗体类
 * 
 * @author RuanYaofeng, VisonSun
 * @date 2018-04-18 10:01
 */
@SuppressWarnings("serial")
public class MainFrame extends JFrame {

    /**
     * 构造主窗体
     */
    public MainFrame() {
        setTitle(StringConst.CRAZYCHAT_SERVER);
        setSize(640, 480);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        getContentPane().add(tabbedPane, BorderLayout.CENTER);

        tabbedPane.add(new ServerStatusPanel());
        tabbedPane.add(new OnlineUsersPanel());
        tabbedPane.add(new AllUsersPanel());
        tabbedPane.add(new ServerLogsPanel());

        tabbedPane.setTitleAt(0, StringConst.SERVER_STATUS);
        tabbedPane.setTitleAt(1, StringConst.ONLINE_USERS);
        tabbedPane.setTitleAt(2, StringConst.ALL_USERS);
        tabbedPane.setTitleAt(3, StringConst.SERVER_LOGS);

    }

}
