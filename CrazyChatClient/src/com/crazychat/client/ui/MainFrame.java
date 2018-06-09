package com.crazychat.client.ui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicSplitPaneUI;

import com.crazychat.client.ClientService;
import com.crazychat.client.constant.ColorConst;
import com.crazychat.client.constant.ImageConst;
import com.crazychat.client.constant.StringConst;
import com.crazychat.client.ui.component.ResizebleFrame;
import com.crazychat.client.ui.component.ShadowBorder;
import com.crazychat.client.ui.listener.ContactListItemSelectListener;
import com.crazychat.entity.User;

/**
 * 客户端主界面Frame
 * 
 * @author RuanYaofeng
 * @date 2018-04-19 09:22
 */
@SuppressWarnings("serial")
public class MainFrame extends ResizebleFrame {

    /*
     * CardLayout标签页名称定义
     */
    private static final String CARDNAME_DIALOG = "D";
    private static final String CARDNAME_CONTACT = "C";
    private static final String CARDNAME_CONTACT_FRIEND = "CF";
    private static final String CARDNAME_CONTACT_GROUP = "CG";
    private static final String CARDNAME_PROFILE_FRIEND = "PF";
    private static final String CARDNAME_PROFILE_GROUP = "PG";

    /*
     * 好友/群Tab按钮边框定义
     */
    Border normalContactTabBorder = BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 2, 0, ColorConst.BACKGROUND),
            BorderFactory.createEmptyBorder(8, 8, 8, 8));
    Border selectedContactTabBorder = BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 2, 0, ColorConst.THEME_PRIMARY),
            BorderFactory.createEmptyBorder(8, 8, 8, 8));

    /*
     * 左右面板分割线定义
     */
    Border dividerBorder = BorderFactory.createLineBorder(ColorConst.LIST_BG, 10);

    /** 阴影边框定义 (此窗口需要调用ShadowBorder的方法，需要实例化一个新对象) */
    ShadowBorder shadowBorder = ShadowBorder.newInstance(false);

    /** Frame内容面板 */
    private JPanel contentPane;

    /** 顶部面板 */
    private TopPanel topPanel;

    /** 消息列表面板 */
    public ContactListPanel clpRecent;
    /** 好友列表面板 */
    public ContactListPanel clpFriends;
    /** 群列表面板 */
    public ContactListPanel clpGroups;

    /** 聊天面板 */
    public ChatPanel chatPanel = new ChatPanel();
    /** 好友资料面板 */
    private ProfilePanel friendProfilePanel = new ProfilePanel();
    /** 群资料面板 */
    private ProfilePanel groupProfilePanel = new ProfilePanel();

    /** 好友列表Tab按钮 */
    private JButton btnContactFriendTab;
    /** 群列表Tab按钮 */
    private JButton btnContactGroupTab;
    /** 加好友按钮 */
    private JButton btnAddFriend;

    /*
     * 主区域
     */
    private JPanel mainPanel;
    private CardLayout mainCard;

    /*
     * 聊天区域
     */
    private JPanel chatAreaPanel;
    private CardLayout chatAreaCard;

    /*
     * 联系人列表区域
     */
    private JPanel contactListPanel;
    private CardLayout contactListCard;

    /*
     * 联系人资料区域
     */
    private JPanel contactProfilePanel;
    private CardLayout contactProfileCard;

    /**
     * Create the frame.
     */
    public MainFrame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false); // 防止从任务栏操作最大化最小化

        setSize(800, 600);
        setLocationRelativeTo(null);
        setBackground(new Color(0, 0, 0, 0));

        setIconImage(ImageConst.IC_TAB_DIALOG_FOCUS.getImage());
        setTitle(StringConst.CRAZYCHAT);

        initUI();
        initEvent();
    }

    /**
     * 初始化UI布局
     */
    private void initUI() {
        contentPane = (JPanel) getContentPane();
        contentPane.setLayout(new BorderLayout(0, 0));
        contentPane.setBorder(BorderFactory.createCompoundBorder(shadowBorder,
                BorderFactory.createLineBorder(ColorConst.THEME_PRIMARY, 2)));

        ////////////////////////////////////////////////////////////////
        //// 大局
        ////////////////////////////////////////////////////////////////
        /*
         * 顶栏
         */
        topPanel = new TopPanel(this);
        contentPane.add(topPanel, BorderLayout.NORTH);

        mainCard = new CardLayout();
        mainPanel = new JPanel(mainCard);

        ////////////////////////////////////////////////////////////////
        //// 聊天板块
        ////////////////////////////////////////////////////////////////
        JSplitPane messagingPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        messagingPanel.setOpaque(false);
        messagingPanel.setBorder(null);
        messagingPanel.setDividerSize(2);
        messagingPanel.setResizeWeight(0.25);
        ((BasicSplitPaneUI) messagingPanel.getUI()).getDivider().setBorder(dividerBorder);

        // 列表区域
        clpRecent = new ContactListPanel();
        clpRecent.setMinimumSize(new Dimension(192, 0));

        clpRecent.setListData(ClientService.dialogs);

        // 对话框区域
        chatAreaCard = new CardLayout();
        chatAreaPanel = new JPanel(chatAreaCard);
        chatAreaPanel.setMinimumSize(new Dimension(384, 0));

        chatAreaPanel.add(new PlaceHolderPanel());

        // 布局
        messagingPanel.add(clpRecent);
        messagingPanel.add(chatAreaPanel);

        ////////////////////////////////////////////////////////////////
        //// 联系人板块
        ////////////////////////////////////////////////////////////////
        JSplitPane contactPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        contactPanel.setOpaque(false);
        contactPanel.setBorder(null);
        contactPanel.setDividerSize(2);
        contactPanel.setResizeWeight(0.25);
        ((BasicSplitPaneUI) contactPanel.getUI()).getDivider().setBorder(dividerBorder);

        /*
         * 列表区
         */
        JPanel contactListContainerPanel = new JPanel(new BorderLayout());
        contactListContainerPanel.setMinimumSize(new Dimension(240, 0));

        // 列表Tab
        Box contactListTabBox = Box.createHorizontalBox();
        contactListTabBox.setBackground(ColorConst.BACKGROUND);
        contactListTabBox.setOpaque(true);
        contactListTabBox.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, ColorConst.DIVIDER_GRAY));
        contactListTabBox.setCursor(Cursor.getDefaultCursor());

        btnContactFriendTab = new JButton(StringConst.FRIEND);
        btnContactFriendTab.setFocusPainted(false);
        btnContactFriendTab.setBackground(null);
        btnContactFriendTab.setBorder(selectedContactTabBorder);
        btnContactFriendTab.setPreferredSize(new Dimension(128, 32));

        btnContactGroupTab = new JButton(StringConst.GROUP);
        btnContactGroupTab.setFocusPainted(false);
        btnContactGroupTab.setBackground(null);
        btnContactGroupTab.setBorder(normalContactTabBorder);
        btnContactGroupTab.setPreferredSize(new Dimension(128, 32));
        btnContactGroupTab.setForeground(ColorConst.TEXT_SECONDARY);

        contactListTabBox.add(Box.createHorizontalGlue());
        contactListTabBox.add(btnContactFriendTab);
        contactListTabBox.add(Box.createHorizontalGlue());
        contactListTabBox.add(btnContactGroupTab);
        contactListTabBox.add(Box.createHorizontalGlue());

        // 列表Card
        contactListCard = new CardLayout();
        contactListPanel = new JPanel(contactListCard);

        clpFriends = new ContactListPanel();
        clpGroups = new ContactListPanel();

        clpFriends.setListData(ClientService.friends);
        clpGroups.setListData(ClientService.groups);

        contactListPanel.add(CARDNAME_CONTACT_FRIEND, clpFriends);
        contactListPanel.add(CARDNAME_CONTACT_GROUP, clpGroups);

        btnAddFriend = new JButton(StringConst.ADD_FRIEND_OR_GROUP);
        btnAddFriend.setBackground(ColorConst.THEME_PRIMARY);
        btnAddFriend.setForeground(ColorConst.TEXT_TITLE_PRIMARY);
        btnAddFriend
                .setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(ColorConst.BACKGROUND, 4),
                        BorderFactory.createEmptyBorder(8, 16, 8, 16)));
        btnAddFriend.setFocusPainted(false);
        btnAddFriend.setCursor(Cursor.getDefaultCursor());

        // 布局
        contactListContainerPanel.add(contactListTabBox, BorderLayout.NORTH);
        contactListContainerPanel.add(contactListPanel);
        contactListContainerPanel.add(btnAddFriend, BorderLayout.SOUTH);

        /*
         * 资料区
         */
        contactProfileCard = new CardLayout();
        contactProfilePanel = new JPanel(contactProfileCard);

        contactProfilePanel.add(CARDNAME_PROFILE_FRIEND, friendProfilePanel);
        contactProfilePanel.add(CARDNAME_PROFILE_GROUP, groupProfilePanel);

        /*
         * 布局
         */
        contactPanel.add(contactListContainerPanel);
        contactPanel.add(contactProfilePanel);

        ////////////////////////////////////////////////////////////////
        //// 总布局
        ////////////////////////////////////////////////////////////////
        mainPanel.add(CARDNAME_DIALOG, messagingPanel);
        mainPanel.add(CARDNAME_CONTACT, contactPanel);
        contentPane.add(mainPanel);
    }

    /**
     * 初始化事件
     */
    private void initEvent() {
        /*
         * 列表项事件
         */
        ContactListItemSelectListener contactListItemSelectListener = new ContactListItemSelectListener() {

            @Override
            public void listItemSelected(Component eventSource, int itemIndex) {
                if (eventSource == clpRecent) { // 选中消息记录列表项的情形
                    ClientService.chatTarget = ClientService.dialogs.get(itemIndex);
                    chatPanel.setChatTarget(ClientService.chatTarget);
                    chatPanel.updateMessageRecord();

                    chatAreaPanel.removeAll();
                    chatAreaPanel.add(chatPanel);
                    chatAreaCard.first(chatAreaPanel);

                } else if (eventSource == clpFriends) { // 选中好友列表项的情形
                    friendProfilePanel.setData(ClientService.friends.get(itemIndex));

                } else if (eventSource == clpGroups) { // 选中群列表项的情形
                    groupProfilePanel.setData(ClientService.groups.get(itemIndex));
                }
            }

            @Override
            public void listItemDoubleClicked(Component eventSource, int itemIndex) {

                /*
                 * 双击好友列表项或群列表项，跳转到聊天界面
                 */
                if (eventSource == clpFriends || eventSource == clpGroups) {

                    // 取出聊天对象
                    Object target;
                    if (eventSource == clpFriends) {
                        // 双击的是好友列表项
                        target = ClientService.friends.get(itemIndex);
                    } else {
                        // 双击的是群列表项
                        target = ClientService.groups.get(itemIndex);
                    }

                    // 若已存在会话列表中，则先移除
                    if (ClientService.dialogs.contains(target)) {
                        ClientService.dialogs.remove(target);
                    }

                    // 插入会话列表最前面
                    ClientService.dialogs.add(0, target);

                    clpRecent.setListData(ClientService.dialogs); // 刷新列表
                    ClientService.chatTarget = target;
                    chatPanel.setChatTarget(target);
                    chatPanel.updateMessageRecord();
                    switchTab(TopPanel.TAB_DIALOG);

                    chatAreaPanel.removeAll();
                    chatAreaPanel.add(chatPanel);
                    chatAreaCard.first(chatAreaPanel);
                }

            }
        };
        clpRecent.setContactListItemSelectListener(contactListItemSelectListener);
        clpFriends.setContactListItemSelectListener(contactListItemSelectListener);
        clpGroups.setContactListItemSelectListener(contactListItemSelectListener);

        /*
         * 好友/群Tab按钮
         */
        ActionListener contactListTabActionListener = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == btnContactFriendTab) {
                    contactListCard.show(contactListPanel, CARDNAME_CONTACT_FRIEND);
                    contactProfileCard.show(contactProfilePanel, CARDNAME_PROFILE_FRIEND);

                    btnContactFriendTab.setBorder(selectedContactTabBorder);
                    btnContactGroupTab.setBorder(normalContactTabBorder);

                    btnContactFriendTab.setForeground(ColorConst.TEXT_PRIMARY);
                    btnContactGroupTab.setForeground(ColorConst.TEXT_SECONDARY);
                } else if (e.getSource() == btnContactGroupTab) {
                    contactListCard.show(contactListPanel, CARDNAME_CONTACT_GROUP);
                    contactProfileCard.show(contactProfilePanel, CARDNAME_PROFILE_GROUP);

                    btnContactFriendTab.setBorder(normalContactTabBorder);
                    btnContactGroupTab.setBorder(selectedContactTabBorder);

                    btnContactFriendTab.setForeground(ColorConst.TEXT_SECONDARY);
                    btnContactGroupTab.setForeground(ColorConst.TEXT_PRIMARY);
                }
            }
        };
        btnContactFriendTab.addActionListener(contactListTabActionListener);
        btnContactGroupTab.addActionListener(contactListTabActionListener);

        /*
         * 加好友按钮
         */
         btnAddFriend.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                ClientService.showAddFriendFrame();
            }
        });

        /*
         * 窗口大小改变重绘边框
         */
        addComponentListener(new ComponentAdapter() {

            @Override
            public void componentResized(ComponentEvent e) {
                shadowBorder.enablePaint();
            }
        });
    }

    /**
     * 切换标签页的操作
     * 
     * @param tab 标签索引
     */
    public void switchTab(int tab) {
        switch (tab) {
        case TopPanel.TAB_DIALOG:
            mainCard.show(mainPanel, CARDNAME_DIALOG);
            break;
        case TopPanel.TAB_CONTACTS:
            mainCard.show(mainPanel, CARDNAME_CONTACT);
            break;
        }
        topPanel.setTabIndex(tab);
        topPanel.updateTabStatus();
    }

    /**
     * 刷新用户信息
     * 
     * @param tab 标签索引
     */
    public void updateUserStatus() {
        topPanel.updateUserStatus();
    }

    /**
     * 新好友被添加
     * 
     * @param friendUser 好友用户对象
     */
    public void newFriendAdded(User friendUser) {
        // 加入集合
        ClientService.friends.add(friendUser); // XXX List<User>改为List<Friend>
        ClientService.dialogs.add(0, friendUser); // 插到会话列表顶部

        // 刷新列表
        clpFriends.setListData(ClientService.friends);
        clpRecent.setListData(ClientService.dialogs);

        // 开始聊天
        ClientService.chatTarget = friendUser;
        chatPanel.setChatTarget(friendUser);
        chatPanel.updateMessageRecord();
        switchTab(TopPanel.TAB_DIALOG);

        // 刷新视图
        chatAreaPanel.removeAll();
        chatAreaPanel.add(chatPanel);
        chatAreaCard.first(chatAreaPanel);
    }

}
