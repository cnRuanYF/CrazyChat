package com.crazychat.client.ui;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.border.EmptyBorder;

import com.crazychat.client.ClientService;
import com.crazychat.client.constant.ColorConst;
import com.crazychat.client.constant.ImageConst;
import com.crazychat.client.constant.StringConst;
import com.crazychat.client.ui.component.AnimatePanel;
import com.crazychat.client.ui.component.FrameControlButtons;
import com.crazychat.client.ui.listener.FrameControlButtonsListener;

/**
 * 客户端顶栏面板
 * 
 * @author RuanYaofeng
 * @date 2018-04-19 09:25
 */
@SuppressWarnings("serial")
public class TopPanel extends AnimatePanel {

    /** 外层Frame */
    private MainFrame mainFrame;

    /*
     * 标签页
     */
    public static final int TAB_DIALOG = 0;
    public static final int TAB_CONTACTS = 1;

    /** 储存当前标签页索引 */
    int tabIndex = 0;

    /*
     * 用户状态组件
     */
    private JLabel lblUserPicture;
    private JLabel lblUserNickname;
    private JLabel lblUserStatus;

    /*
     * 标签切换按钮
     */
    private JButton btnDialogTab;
    private JButton btnContactsTab;

    /** 窗口控制按钮 */
    private FrameControlButtons frameControlButtons;

    /*
     * 尺寸定义
     */
    private Dimension cornerBoxSize = new Dimension(240, 56);
    private Dimension userHeadSize = new Dimension(40, 40);

    /** 储存鼠标按下位置 (实现窗口拖动) */
    Point dragStartPoint;

    /**
     * Create the panel.
     * 
     * @param parentFrame 外层Frame容器
     */
    public TopPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        initUI(); // 初始化UI控件
        initEvent();// 初始化窗口事件
    }

    /**
     * @return the tabIndex
     */
    public int getTabIndex() {
        return tabIndex;
    }

    /**
     * @param tabIndex the tabIndex to set
     */
    public void setTabIndex(int tabIndex) {
        this.tabIndex = tabIndex;
    }

    /**
     * 初始化UI控件
     */
    void initUI() {
        /*
         * 整体布局 (分为左中右3个Box)
         */
        setBackground(ColorConst.THEME_PRIMARY);
        setLayout(new BorderLayout(0, 0));

        Box userBox = Box.createHorizontalBox();
        Box tabBox = Box.createHorizontalBox();
        Box menuBox = Box.createHorizontalBox();

        userBox.setPreferredSize(cornerBoxSize);
        menuBox.setPreferredSize(cornerBoxSize);

        add(userBox, BorderLayout.WEST);
        add(tabBox);
        add(menuBox, BorderLayout.EAST);

        // 设置鼠标为默认 (解决窗口拖曳和ResizableFrame冲突)
        setCursor(Cursor.getDefaultCursor());

        /*
         * 左侧用户信息布局
         */
        lblUserPicture = new JLabel();
        lblUserNickname = new JLabel(ClientService.currentUser.getNickname());
        lblUserStatus = new JLabel(StringConst.STATUS_ONLINE);

        lblUserPicture.setBorder(new EmptyBorder(8, 8, 8, 8));
        setUserPicture(ImageConst.IC_USER_PICTURES[ClientService.currentUser.getPictureId()]);
        lblUserPicture.setCursor(new Cursor(Cursor.HAND_CURSOR));

        lblUserNickname.setFont(new Font(StringConst.DEFAULT_FONT_NAME, Font.BOLD, 16));

        lblUserStatus.setIcon(ImageConst.IC_USER_STATUS_ONLINE);

        lblUserNickname.setForeground(ColorConst.TEXT_TITLE_PRIMARY);
        lblUserStatus.setForeground(ColorConst.TEXT_TITLE_PRIMARY);

        // 布局
        Box userTextBox = Box.createVerticalBox();
        userTextBox.add(lblUserNickname);
        userTextBox.add(lblUserStatus);

        userBox.add(lblUserPicture);
        userBox.add(userTextBox);

        /*
         * 中间标签切换按钮布局
         */
        btnDialogTab = new JButton(StringConst.MESSAGE);
        btnContactsTab = new JButton(StringConst.FRIEND);

        Font tabFont = new Font(StringConst.DEFAULT_FONT_NAME, Font.PLAIN, 16);
        btnDialogTab.setFont(tabFont);
        btnContactsTab.setFont(tabFont);

        btnDialogTab.setHorizontalTextPosition(JButton.LEFT); // 文字在图标左侧

        btnDialogTab.setRolloverIcon(ImageConst.IC_TAB_DIALOG_FOCUS);
        btnContactsTab.setRolloverIcon(ImageConst.IC_TAB_CONTACTS_FOCUS);

        updateTabStatus(); // 更新标签按钮状态

        btnDialogTab.setBorder(null);
        btnContactsTab.setBorder(null);

        btnDialogTab.setContentAreaFilled(false);
        btnContactsTab.setContentAreaFilled(false);

        btnDialogTab.setFocusPainted(false);
        btnContactsTab.setFocusPainted(false);

        // 布局
        tabBox.add(Box.createHorizontalGlue());
        tabBox.add(btnDialogTab);
        tabBox.add(Box.createHorizontalStrut(16));
        tabBox.add(btnContactsTab);
        tabBox.add(Box.createHorizontalGlue());

        /*
         * 右侧按钮布局 (窗口控制按钮)
         */
        frameControlButtons = new FrameControlButtons(mainFrame);
        menuBox.add(Box.createHorizontalGlue()); // 按钮前填充空白
        menuBox.add(frameControlButtons);
    }

    /**
     * 初始化事件监听
     */
    private void initEvent() {
        /*
         * 重写关闭按钮监听
         */
        frameControlButtons.setFrameControlButtonsListener(new FrameControlButtonsListener() {

            @Override
            public void onCloseButtonClick() {
                int result = JOptionPane.showConfirmDialog(null, StringConst.REALLY_TO_EXIT, StringConst.CRAZYCHAT,
                        JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
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
        this.addMouseListener(windowDragListener);

        MouseMotionListener windowDragMotionListener = new MouseMotionAdapter() {

            @Override
            public void mouseDragged(MouseEvent e) {
                // mouseDragged触发时的窗口位置
                Point frameLocation = mainFrame.getLocation();
                mainFrame.setLocation(frameLocation.x - dragStartPoint.x + e.getX(),
                        frameLocation.y - dragStartPoint.y + e.getY());
            }
        };
        this.addMouseMotionListener(windowDragMotionListener);

        /*
         * 标签切换的处理
         */
        MouseListener btnTabMouseListener = new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {
                ((JButton) e.getSource()).setForeground(ColorConst.TEXT_TITLE_PRIMARY);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                updateTabStatus();
            }
        };
        btnDialogTab.addMouseListener(btnTabMouseListener);
        btnContactsTab.addMouseListener(btnTabMouseListener);

        ActionListener btnTabActionListener = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // 将要切换到的标签索引
                int switchTargetIndex = tabIndex;

                if (e.getSource() == btnDialogTab) {
                    switchTargetIndex = TAB_DIALOG;
                } else if (e.getSource() == btnContactsTab) {
                    switchTargetIndex = TAB_CONTACTS;
                }

                // 只能切换到不同的标签
                if (tabIndex != switchTargetIndex) {
                    tabIndex = switchTargetIndex;
                    mainFrame.switchTab(tabIndex);
                }
            }
        };
        btnDialogTab.addActionListener(btnTabActionListener);
        btnContactsTab.addActionListener(btnTabActionListener);

        /*
         * 点击头像修改资料
         */
        lblUserPicture.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                ClientService.showEditProfileFrame();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                lblUserPicture.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4),
                        BorderFactory.createLineBorder(ColorConst.THEME_PRIMARY_DARK, 4)));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                lblUserPicture.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
            }

        });
    }

    /**
     * 根据当前标签更新标签按钮状态
     */
    public void updateTabStatus() {
        // 按钮状态重置
        btnDialogTab.setIcon(ImageConst.IC_TAB_DIALOG_NORMAL);
        btnContactsTab.setIcon(ImageConst.IC_TAB_CONTACTS_NORMAL);
        btnDialogTab.setForeground(ColorConst.TEXT_TITLE_SECONDARY);
        btnContactsTab.setForeground(ColorConst.TEXT_TITLE_SECONDARY);

        // 点亮当前标签按钮
        switch (tabIndex) {
        case 0:
            btnDialogTab.setIcon(ImageConst.IC_TAB_DIALOG_FOCUS);
            btnDialogTab.setForeground(ColorConst.TEXT_TITLE_PRIMARY);
            break;
        case 1:
            btnContactsTab.setIcon(ImageConst.IC_TAB_CONTACTS_FOCUS);
            btnContactsTab.setForeground(ColorConst.TEXT_TITLE_PRIMARY);
            break;
        default:
            break;
        }
    }

    /**
     * 刷新用户状态
     */
    public void updateUserStatus() {
        setUserPicture(ImageConst.IC_USER_PICTURES[ClientService.currentUser.getPictureId()]);
        lblUserNickname.setText(ClientService.currentUser.getNickname());
    }

    /**
     * 设置用户头像
     * 
     * @param avatarIcon
     */
    public void setUserPicture(ImageIcon avatarIcon) {
        ImageIcon icon = new ImageIcon(avatarIcon.getImage().getScaledInstance(userHeadSize.width, userHeadSize.height,
                java.awt.Image.SCALE_SMOOTH));
        lblUserPicture.setIcon(icon);
    }

}
