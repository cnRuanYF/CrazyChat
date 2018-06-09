package com.crazychat.client.ui;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.crazychat.client.ClientService;
import com.crazychat.client.constant.ColorConst;
import com.crazychat.client.constant.ImageConst;
import com.crazychat.client.constant.StringConst;
import com.crazychat.client.ui.component.AnimatePanel;
import com.crazychat.client.ui.component.FrameControlButtons;
import com.crazychat.client.ui.component.ShadowBorder;
import com.crazychat.entity.User;
import com.crazychat.util.EncryptionUtils;

import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Desktop;

import javax.swing.SwingConstants;

/**
 * 用户登录Frame
 * 
 * @author RuanYaofeng
 * @date 2018-04-21 14:20
 */
@SuppressWarnings("serial")
public class LoginFrame extends JFrame {

    /** 储存鼠标按下位置 (实现窗口拖动) */
    Point dragStartPoint;

    private JPanel contentPane;

    private AnimatePanel topPanel;
    private JPanel mainPanel;

    private Box loginFromBox;
    private Box loginProgressBox;
    private Box loginMessageBox;

    private JTextField txtUsername;
    private JPasswordField pwdPassword;

    private JLabel lblTitle;
    private JLabel lblLogo;
    private JLabel lblRegister;
    private JLabel lblLoginMessage;

    private JLabel lblProgressConnect;
    private JLabel lblProgressLogin;
    private JLabel lblProgressGetFriends;
    private JLabel lblProgressGetGroups;
    private JLabel lblProgressGetMessages;

    private JButton btnLogin;
    private JButton btnBack;

    /**
     * Create the frame.
     */
    public LoginFrame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false); // 防止从任务栏操作最大化最小化
        setUndecorated(true);

        setSize(480 + ShadowBorder.DEFAULT_SIZE * 2, 320 + ShadowBorder.DEFAULT_SIZE * 2);
        setLocationRelativeTo(null);
        setBackground(ColorConst.TRANSPARENT);

        setIconImage(ImageConst.IC_TAB_DIALOG_FOCUS.getImage());
        setTitle(StringConst.CRAZYCHAT_LOGIN);

        initUI();
        initEvent();
    }

    /**
     * 初始化UI组件
     */
    private void initUI() {
        contentPane = (JPanel) getContentPane();
        contentPane.setLayout(new BorderLayout());
        contentPane.setBorder(BorderFactory.createCompoundBorder(ShadowBorder.newInstance(),
                BorderFactory.createLineBorder(ColorConst.THEME_PRIMARY, 2)));

        /*
         * 标题区域
         */
        topPanel = new AnimatePanel();
        topPanel.setLayout(new BorderLayout());
        topPanel.setBackground(ColorConst.THEME_PRIMARY);

        Box titleBox = Box.createHorizontalBox();

        lblTitle = new JLabel(StringConst.USER_LOGIN);
        lblTitle.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        lblTitle.setForeground(ColorConst.TEXT_TITLE_PRIMARY);
        lblTitle.setAlignmentY(TOP_ALIGNMENT);

        FrameControlButtons frameControlButtons = new FrameControlButtons(this);
        frameControlButtons.setMaximizeButtonVisible(false);
        frameControlButtons.setAlignmentY(TOP_ALIGNMENT);

        titleBox.add(lblTitle);
        titleBox.add(Box.createHorizontalGlue());
        titleBox.add(frameControlButtons);

        lblLogo = new JLabel(ImageConst.IC_LOGO_MIDDLE);
        lblLogo.setBorder(BorderFactory.createEmptyBorder(0, 0, 16, 0));

        topPanel.add(titleBox, BorderLayout.NORTH);
        topPanel.add(lblLogo);

        contentPane.add(topPanel, BorderLayout.NORTH);

        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(ColorConst.BACKGROUND);

        contentPane.add(mainPanel);

        /*
         * 登录表单
         */
        loginFromBox = Box.createVerticalBox();

        // 尺寸定义
        Dimension formItemSize = new Dimension(320, 64);
        Dimension formLabelSize = new Dimension(64, 0);

        // 用户名
        Box usernameBox = Box.createHorizontalBox();
        usernameBox.setMaximumSize(formItemSize);
        usernameBox.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, ColorConst.DIVIDER_GRAY));

        JLabel lblUsername = new JLabel(StringConst.USERNAME);
        lblUsername.setHorizontalAlignment(SwingConstants.RIGHT);
        lblUsername.setForeground(ColorConst.TEXT_SECONDARY);
        lblUsername.setPreferredSize(formLabelSize);

        txtUsername = new JTextField();
        txtUsername.setBackground(null);
        txtUsername.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        usernameBox.add(lblUsername);
        usernameBox.add(txtUsername);

        // 密码
        Box passwordBox = Box.createHorizontalBox();
        passwordBox.setMaximumSize(formItemSize);
        passwordBox.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, ColorConst.DIVIDER_GRAY));

        JLabel lblPassword = new JLabel(StringConst.PASSWORD);
        lblPassword.setHorizontalAlignment(SwingConstants.RIGHT);
        lblPassword.setForeground(ColorConst.TEXT_SECONDARY);
        lblPassword.setPreferredSize(formLabelSize);

        pwdPassword = new JPasswordField();
        pwdPassword.setBackground(null);
        pwdPassword.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        passwordBox.add(lblPassword);
        passwordBox.add(pwdPassword);

        // 注册
        lblRegister = new JLabel(StringConst.REGISTER);
        lblRegister.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblRegister.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lblRegister.setForeground(ColorConst.THEME_PRIMARY);

        // 登录按钮
        btnLogin = new JButton(StringConst.LOGIN);
        btnLogin.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnLogin.setBackground(ColorConst.THEME_PRIMARY);
        btnLogin.setForeground(ColorConst.TEXT_TITLE_PRIMARY);
        btnLogin.setBorder(BorderFactory.createEmptyBorder(8, 64, 8, 64));
        btnLogin.setFocusPainted(false);

        // 嵌套布局
        loginFromBox.add(Box.createVerticalGlue());
        loginFromBox.add(usernameBox);
        loginFromBox.add(Box.createVerticalGlue());
        loginFromBox.add(passwordBox);
        loginFromBox.add(Box.createVerticalGlue());
        loginFromBox.add(lblRegister);
        loginFromBox.add(Box.createVerticalGlue());
        loginFromBox.add(btnLogin);
        loginFromBox.add(Box.createVerticalStrut(20));

        /*
         * 登录进度指示
         */
        loginProgressBox = Box.createVerticalBox();

        lblProgressConnect = new JLabel();
        lblProgressLogin = new JLabel();
        lblProgressGetFriends = new JLabel();
        lblProgressGetGroups = new JLabel();
        lblProgressGetMessages = new JLabel();

        loginProgressBox.add(Box.createVerticalGlue());

        for (JLabel lbl : new JLabel[] { lblProgressConnect, lblProgressLogin, lblProgressGetFriends,
                lblProgressGetGroups, lblProgressGetMessages }) {
            lbl.setForeground(ColorConst.TEXT_SECONDARY);
            lbl.setBorder(BorderFactory.createEmptyBorder(6, 96, 6, 0));
            loginProgressBox.add(lbl);
        }

        loginProgressBox.add(Box.createVerticalGlue());

        /*
         * 登录失败提示
         */
        loginMessageBox = Box.createVerticalBox();

        lblLoginMessage = new JLabel();
        lblLoginMessage.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblLoginMessage.setForeground(ColorConst.THEME_ACCENT);

        btnBack = new JButton(StringConst.BACK);
        btnBack.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnBack.setBackground(ColorConst.THEME_ACCENT);
        btnBack.setForeground(ColorConst.TEXT_TITLE_PRIMARY);
        btnBack.setBorder(BorderFactory.createEmptyBorder(8, 64, 8, 64));
        btnBack.setFocusPainted(false);

        // 嵌套布局
        loginMessageBox.add(Box.createVerticalGlue());
        loginMessageBox.add(lblLoginMessage);
        loginMessageBox.add(Box.createVerticalGlue());
        loginMessageBox.add(btnBack);
        loginMessageBox.add(Box.createVerticalStrut(20));

        /*
         * 默认布局登陆表单
         */
        mainPanel.add(loginFromBox);

        // 如果没有成功获取注册地址则不显示注册按钮
        lblRegister.setVisible(ClientService.registerURL != null);
    }

    /**
     * 初始化事件
     */
    private void initEvent() {
        /*
         * 用户注册
         */
        lblRegister.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    Desktop.getDesktop().browse(new URI(ClientService.registerURL));
                } catch (IOException exception) {
                    System.err.println(exception.getMessage());
                } catch (URISyntaxException exception) {
                    System.err.println(exception.getMessage());
                }
            }
        });

        /*
         * 用户名文本域
         */
        txtUsername.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                pwdPassword.requestFocus(); // 使密码框获得焦点
            }
        });

        /*
         * 登录按钮
         */
        ActionListener loginActionListener = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String username = txtUsername.getText().trim();
                String password = new String(pwdPassword.getPassword()).trim();

                // 非空检查
                if (username.equals("") || password.equals("")) {
                    onError(StringConst.ERROR_USERNAME_OR_PASSWORD_IS_NULL);
                    return;
                }

                // 处理登录业务
                User user = new User(username, EncryptionUtils.getSHA1(password));
                ClientService.loginBiz.login(user);

                // 显示登陆状态UI
                lblTitle.setText(StringConst.LOGINING);
                topPanel.startAnimation(AnimatePanel.SLANT_GRADIENT_ANIM);

                lblProgressConnect.setText(StringConst.CONNECTING_SERVER);
                lblProgressLogin.setText(StringConst.LOGINING);
                lblProgressGetFriends.setText(StringConst.GETTING_FRIEND_LIST);
                lblProgressGetGroups.setText(StringConst.GETTING_GROUP_LIST);
                lblProgressGetMessages.setText(StringConst.GETTING_MESSAGE_RECORD);

                lblProgressConnect.setIcon(ImageConst.IC_CHECKBOX_NORMAL);
                lblProgressLogin.setIcon(ImageConst.IC_CHECKBOX_NORMAL);
                lblProgressGetFriends.setIcon(ImageConst.IC_CHECKBOX_NORMAL);
                lblProgressGetGroups.setIcon(ImageConst.IC_CHECKBOX_NORMAL);
                lblProgressGetMessages.setIcon(ImageConst.IC_CHECKBOX_NORMAL);

                mainPanel.removeAll();
                mainPanel.add(loginProgressBox);

                revalidate();
                repaint();
            }
        };
        btnLogin.addActionListener(loginActionListener);
        pwdPassword.addActionListener(loginActionListener);

        /*
         * 返回按钮
         */
        btnBack.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                contentPane.setBorder(BorderFactory.createCompoundBorder(ShadowBorder.newInstance(),
                        BorderFactory.createLineBorder(ColorConst.THEME_PRIMARY, 2)));

                topPanel.setBackground(ColorConst.THEME_PRIMARY);

                lblTitle.setText(StringConst.USER_LOGIN);
                mainPanel.removeAll();
                mainPanel.add(loginFromBox);

                revalidate();
                repaint();

                txtUsername.requestFocus();
            }
        });

        /*
         * 实现窗口拖曳
         */
        MouseListener windowDragListener = new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                dragStartPoint = e.getPoint(); // 获取到的坐标是相对于容器而非屏幕
            }
        };
        topPanel.addMouseListener(windowDragListener);

        MouseMotionListener windowDragMotionListener = new MouseMotionAdapter() {

            @Override
            public void mouseDragged(MouseEvent e) {
                // mouseDragged触发时的窗口位置
                Point frameLocation = getLocation();
                setLocation(frameLocation.x - dragStartPoint.x + e.getX(),
                        frameLocation.y - dragStartPoint.y + e.getY());
            }
        };
        topPanel.addMouseMotionListener(windowDragMotionListener);

        /*
         * 显示窗体时使文本框获得焦点
         */
        addWindowListener(new WindowAdapter() {

            @Override
            public void windowOpened(WindowEvent e) {
                txtUsername.requestFocus();
            }
        });
    }

    /**
     * 刷新登录进度
     */
    public void updateLoginProgress() {
        // 更新复选框状态
        if (ClientService.socket != null) {
            lblProgressConnect.setIcon(ImageConst.IC_CHECKBOX_CHECKED);
            lblProgressConnect.setText(StringConst.CONNECTING_SERVER_OK);
        }
        if (ClientService.currentUser != null) {
            lblProgressLogin.setIcon(ImageConst.IC_CHECKBOX_CHECKED);
            lblProgressLogin.setText(StringConst.LOGINING_OK);
        }
        if (ClientService.friends != null) {
            lblProgressGetFriends.setIcon(ImageConst.IC_CHECKBOX_CHECKED);
            lblProgressGetFriends.setText(StringConst.GETTING_FRIEND_LIST_OK);
        }
        if (ClientService.groups != null) {
            lblProgressGetGroups.setIcon(ImageConst.IC_CHECKBOX_CHECKED);
            lblProgressGetGroups.setText(StringConst.GETTING_GROUP_LIST_OK);
        }
        if (ClientService.messages != null) {
            lblProgressGetMessages.setIcon(ImageConst.IC_CHECKBOX_CHECKED);
            lblProgressGetMessages.setText(StringConst.GETTING_MESSAGE_RECORD_OK);
        }

        // 是否进入主界面
        if (ClientService.socket != null && ClientService.currentUser != null && ClientService.friends != null
                && ClientService.groups != null && ClientService.messages != null) {
            ClientService.showMainFrame();
        }
    }

    /**
     * 登录失败的操作
     * 
     * @param errorMessage 错误提示
     */
    public void onError(String errorMessage) {
        contentPane.setBorder(BorderFactory.createCompoundBorder(ShadowBorder.newInstance(),
                BorderFactory.createLineBorder(ColorConst.THEME_ACCENT, 2)));

        topPanel.setBackground(ColorConst.THEME_ACCENT);

        lblTitle.setText(StringConst.LOGIN_FAILED);
        topPanel.stopAnimation();

        lblLoginMessage.setText(errorMessage);

        mainPanel.removeAll();
        mainPanel.add(loginMessageBox);

        btnBack.requestFocus();

        revalidate();
        repaint();

        // 关闭上一次的Socket
        if (ClientService.socket != null) {
            try {
                ClientService.socket.close();
                ClientService.socket = null;
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }
    }

}
