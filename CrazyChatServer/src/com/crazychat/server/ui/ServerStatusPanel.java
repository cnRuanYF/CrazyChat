package com.crazychat.server.ui;

import javax.swing.JPanel;

import com.crazychat.server.ServerService;
import com.crazychat.server.biz.impl.LoginBizImpl;
import com.crazychat.server.constant.StringConst;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.SwingConstants;

/**
 * 服务器状态面板
 * 
 * @author RuanYaofeng, VisonSun
 * @date 2018-04-18 10:09
 */
@SuppressWarnings("serial")
public class ServerStatusPanel extends JPanel {

    JLabel lblServerStatus;
    JButton btnStartServer, btnStopServer;

    /**
     * Create the panel.
     */
    public ServerStatusPanel() {
        initUI();
        initEvent();
    }

    /**
     * 初始化UI组件
     */
    private void initUI() {
        // setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        lblServerStatus = new JLabel();
        lblServerStatus.setHorizontalAlignment(SwingConstants.CENTER);
        lblServerStatus.setIcon(new ImageIcon("./res/ServerStarting.gif"));

        btnStartServer = new JButton(StringConst.STARTUP_SERVER);
        btnStopServer = new JButton(StringConst.SHUTDOWN_SERVER);

        setLayout(new BorderLayout(0, 0));

        add(btnStartServer, BorderLayout.NORTH);
        add(lblServerStatus, BorderLayout.CENTER);
        lblServerStatus.setVisible(false);

        setBackground(new Color(102, 206, 255, 255));
    }

    /**
     * 初始化事件监听
     */
    private void initEvent() {
        btnStartServer.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                ServerService.startServer();
                lblServerStatus.setVisible(true);
                remove(btnStartServer);
                add(btnStopServer, BorderLayout.NORTH);

            }
        });

        btnStopServer.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                new LoginBizImpl().closeServer();
                ServerService.stopServer();
                lblServerStatus.setVisible(false);
                remove(btnStopServer);
                add(btnStartServer, BorderLayout.NORTH);
            }
        });
    }
}
