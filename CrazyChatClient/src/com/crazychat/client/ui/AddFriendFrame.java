package com.crazychat.client.ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.crazychat.client.ClientService;
import com.crazychat.client.constant.ColorConst;
import com.crazychat.client.constant.ImageConst;
import com.crazychat.client.constant.StringConst;
import com.crazychat.client.ui.component.AnimatePanel;
import com.crazychat.client.ui.component.FrameControlButtons;
import com.crazychat.client.ui.component.ShadowBorder;
import com.crazychat.client.ui.listener.FrameControlButtonsListener;
import com.crazychat.entity.User;

/**
 * 搜索，添加好友
 * 
 * @author QiuWenYi
 * @date 2018-04-16 22:20
 */
@SuppressWarnings("serial")
public class AddFriendFrame extends JFrame {

    /** 储存鼠标按下位置 (实现窗口拖动) */
    Point dragStartPoint;

    private JPanel contentPane;

    private FrameControlButtons frameControlButtons;
    private AnimatePanel topPanel;
    private JPanel mainPanel;

    private JLabel lblTitle;

    /*
     * 搜索表单
     */
    private Box searchFromBox;
    private JTextField txtKeyword;
    private JButton btnFindUser;

    /*
     * 搜索进度
     */
    private Box searchProgressBox;
    private JLabel lblSearching;

    /*
     * 错误信息
     */
    private Box errorMessageBox;
    private JLabel lblErrorMessage;
    private JButton btnBack;

    /*
     * 搜索结果
     */
    private Box searchResultBox;
    private ProfilePanel profilePanel;
    private JButton btnAdd;

    /** 找到的用户 */
    private User resultUser;

    /**
     * Create the frame.
     */
    public AddFriendFrame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false); // 防止从任务栏操作最大化最小化
        setUndecorated(true);

        setSize(480, 320);
        setLocationRelativeTo(null);
        setBackground(ColorConst.TRANSPARENT);

        setIconImage(ImageConst.IC_TAB_DIALOG_FOCUS.getImage());
        setTitle(StringConst.ADD_FRIEND_OR_GROUP);

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

        lblTitle = new JLabel(StringConst.ADD_FRIEND_OR_GROUP);
        lblTitle.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
        lblTitle.setForeground(ColorConst.TEXT_TITLE_PRIMARY);
        lblTitle.setIcon(ImageConst.IC_LOGO_SMALL);
        lblTitle.setAlignmentY(TOP_ALIGNMENT);

        frameControlButtons = new FrameControlButtons(this);
        frameControlButtons.setMaximizeButtonVisible(false);
        frameControlButtons.setAlignmentY(TOP_ALIGNMENT);

        titleBox.add(lblTitle);
        titleBox.add(Box.createHorizontalGlue());
        titleBox.add(frameControlButtons);

        topPanel.add(titleBox, BorderLayout.NORTH);

        contentPane.add(topPanel, BorderLayout.NORTH);

        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(ColorConst.BACKGROUND);

        contentPane.add(mainPanel);

        /*
         * 表单
         */
        searchFromBox = Box.createVerticalBox();

        // 尺寸定义
        Dimension formItemSize = new Dimension(320, 64);
        Dimension formLabelSize = new Dimension(64, 0);

        // 关键字
        Box keywordBox = Box.createHorizontalBox();
        keywordBox.setMaximumSize(formItemSize);
        keywordBox.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, ColorConst.DIVIDER_GRAY));

        JLabel lblKeyword = new JLabel(StringConst.KEYWORD);
        lblKeyword.setHorizontalAlignment(SwingConstants.RIGHT);
        lblKeyword.setForeground(ColorConst.TEXT_SECONDARY);
        lblKeyword.setPreferredSize(formLabelSize);

        txtKeyword = new JTextField();
        txtKeyword.setBackground(null);
        txtKeyword.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        keywordBox.add(lblKeyword);
        keywordBox.add(txtKeyword);

        // 查询按钮
        btnFindUser = new JButton(StringConst.SEARCH);
        btnFindUser.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnFindUser.setBackground(ColorConst.THEME_PRIMARY);
        btnFindUser.setForeground(ColorConst.TEXT_TITLE_PRIMARY);
        btnFindUser.setBorder(BorderFactory.createEmptyBorder(8, 64, 8, 64));
        btnFindUser.setFocusPainted(false);

        // 嵌套布局
        searchFromBox.add(Box.createVerticalGlue());
        searchFromBox.add(keywordBox);
        searchFromBox.add(Box.createVerticalGlue());
        searchFromBox.add(btnFindUser);
        searchFromBox.add(Box.createVerticalStrut(20));

        /*
         * 查找进度指示
         */
        searchProgressBox = Box.createVerticalBox();

        lblSearching = new JLabel(StringConst.SEARCHING);
        lblSearching.setAlignmentX(CENTER_ALIGNMENT);
        lblSearching.setForeground(ColorConst.TEXT_SECONDARY);

        searchProgressBox.add(Box.createVerticalGlue());
        searchProgressBox.add(lblSearching);
        searchProgressBox.add(Box.createVerticalGlue());

        /*
         * 失败提示
         */
        errorMessageBox = Box.createVerticalBox();

        lblErrorMessage = new JLabel();
        lblErrorMessage.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblErrorMessage.setForeground(ColorConst.THEME_ACCENT);

        btnBack = new JButton(StringConst.BACK);
        btnBack.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnBack.setBackground(ColorConst.THEME_ACCENT);
        btnBack.setForeground(ColorConst.TEXT_TITLE_PRIMARY);
        btnBack.setBorder(BorderFactory.createEmptyBorder(8, 64, 8, 64));
        btnBack.setFocusPainted(false);

        // 嵌套布局
        errorMessageBox.add(Box.createVerticalGlue());
        errorMessageBox.add(lblErrorMessage);
        errorMessageBox.add(Box.createVerticalGlue());
        errorMessageBox.add(btnBack);
        errorMessageBox.add(Box.createVerticalStrut(20));

        /*
         * 搜索结果
         */
        searchResultBox = Box.createVerticalBox();

        profilePanel = new ProfilePanel();

        btnAdd = new JButton(StringConst.ADD_FRIEND);
        btnAdd.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnAdd.setBackground(ColorConst.THEME_PRIMARY);
        btnAdd.setForeground(ColorConst.TEXT_TITLE_PRIMARY);
        btnAdd.setBorder(BorderFactory.createEmptyBorder(8, 64, 8, 64));
        btnAdd.setFocusPainted(false);

        // 嵌套布局
        searchResultBox.add(Box.createVerticalGlue());
        searchResultBox.add(profilePanel);
        searchResultBox.add(Box.createVerticalGlue());
        searchResultBox.add(btnAdd);
        searchResultBox.add(Box.createVerticalStrut(20));

        /*
         * 默认布局查找表单
         */
        mainPanel.add(searchFromBox);
    }

    /**
     * 初始化事件
     */
    private void initEvent() {

        /*
         * 查找按钮
         */
        ActionListener searchActionListener = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                String keyword = txtKeyword.getText().trim();

                // 非空检查
                if (keyword.equals("")) {
                    onError(StringConst.KEYWORD_SHOULD_NOT_EMPTY);
                    return;
                }

                ClientService.userBiz.findUser(keyword);

                // 显示查找进度UI
                lblTitle.setText(StringConst.SEARCHING);
                topPanel.startAnimation(AnimatePanel.SLANT_GRADIENT_ANIM);

                mainPanel.removeAll();
                mainPanel.add(searchProgressBox);

                revalidate();
                repaint();
            }
        };
        btnFindUser.addActionListener(searchActionListener);
        txtKeyword.addActionListener(searchActionListener);

        /*
         * 返回按钮点击事件
         */
        btnBack.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                contentPane.setBorder(BorderFactory.createCompoundBorder(ShadowBorder.newInstance(),
                        BorderFactory.createLineBorder(ColorConst.THEME_PRIMARY, 2)));

                topPanel.setBackground(ColorConst.THEME_PRIMARY);

                lblTitle.setText(StringConst.ADD_FRIEND_OR_GROUP);
                mainPanel.removeAll();
                mainPanel.add(searchFromBox);

                revalidate();
                repaint();

                txtKeyword.requestFocus();
            }
        });

        /*
         * 添加好友按钮事件
         */
        btnAdd.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                ClientService.friendBiz.addFriend(resultUser.getId());
                dispose();
                ClientService.addFriendFrame = null;
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
         * 关闭时不退出
         */
        frameControlButtons.setFrameControlButtonsListener(new FrameControlButtonsListener() {

            @Override
            public void onCloseButtonClick() {
                dispose();
                ClientService.addFriendFrame = null;
            }
        });

        /*
         * 显示窗体时使文本框获得焦点
         */
        addWindowListener(new WindowAdapter() {

            @Override
            public void windowOpened(WindowEvent e) {
                txtKeyword.requestFocus();
            }
        });
    }

    /**
     * 查询失败的操作
     * 
     * @param errorMessage 错误提示
     */
    public void onError(String errorMessage) {
        contentPane.setBorder(BorderFactory.createCompoundBorder(ShadowBorder.newInstance(),
                BorderFactory.createLineBorder(ColorConst.THEME_ACCENT, 2)));

        topPanel.setBackground(ColorConst.THEME_ACCENT);

        lblTitle.setText(StringConst.SEARCH_FAILED);

        topPanel.stopAnimation();

        lblErrorMessage.setText(errorMessage);

        mainPanel.removeAll();
        mainPanel.add(errorMessageBox);

        btnBack.requestFocus();

        revalidate();
        repaint();
    }

    /**
     * 查找到好友的操作
     * 
     * @param content
     */
    public void onUserFinded(List<User> users) {
        if (users == null || users.size() == 0) {
            onError(StringConst.NO_USER_HAS_BEEN_FOUND);
            return;
        }

        resultUser = users.get(0);

        lblTitle.setText(StringConst.SEARCH_RESULT);
        topPanel.stopAnimation();
        setSize(getWidth(), 540);
        contentPane.setBorder(BorderFactory.createCompoundBorder(ShadowBorder.newInstance(),
                BorderFactory.createLineBorder(ColorConst.THEME_PRIMARY, 2)));

        profilePanel.setData(resultUser);
        
        // 加好友的限制
        btnAdd.setVisible(true);
        if(resultUser.getId()==ClientService.currentUser.getId()) {
            btnAdd.setVisible(false); // 找到的是自己，则不显示添加按钮
        } else {
            for(User u:ClientService.friends) {
                if(resultUser.getId()==u.getId()) {
                    btnAdd.setVisible(false); // 找到的是好友，也不显示添加按钮
                    break;
                }
            }
        }

        mainPanel.removeAll();
        mainPanel.add(searchResultBox);

        btnAdd.requestFocus();

        revalidate();
        repaint();
    }

}
