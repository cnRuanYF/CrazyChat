package com.crazychat.client.ui;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicSplitPaneUI;

import com.crazychat.client.ClientService;
import com.crazychat.client.constant.ColorConst;
import com.crazychat.client.constant.StringConst;
import com.crazychat.client.ui.component.MessageBubble;
import com.crazychat.client.ui.component.SimpleScrollBarUI;
import com.crazychat.entity.Group;
import com.crazychat.entity.Message;
import com.crazychat.entity.User;

/**
 * 聊天面板
 * 
 * @author RuanYaofeng
 * @date 2018-04-20 16:55
 */
@SuppressWarnings("serial")
public class ChatPanel extends JPanel {

    /** 当前对话目标 */
    private Object chatTarget;
    /** 当前对话目标类型 */
    private int chatMessageType;

    /*
     * UI控件
     */
    private JLabel lblTitle;
    private Box messageRecordBox;
    private JTextArea txtMessageEditor;
    private JButton btnSend;
    private JScrollPane messageRecordScrollPane;

    /**
     * 构造聊天窗口
     */
    public ChatPanel() {
        initUI();
        initEvent();
    }

    /**
     * @param chatTarget the chatTarget to set
     */
    public void setChatTarget(Object chatTarget) {
        this.chatTarget = chatTarget;

        // 判断聊天对象的类型
        if (chatTarget != null && chatTarget instanceof User) {
            chatMessageType = Message.TYPE_FRIEND;
            lblTitle.setText("与 " + ((User) chatTarget).getNickname() + " 聊天中");
        } else if (chatTarget != null && chatTarget instanceof Group) {
            chatMessageType = Message.TYPE_GROUP;
            lblTitle.setText("在 " + ((Group) chatTarget).getGroupName() + " 群聊中");
        }

        txtMessageEditor.requestFocus();
    }

    /**
     * @return the chatMessageType
     */
    public int getChatMessageType() {
        return chatMessageType;
    }

    /**
     * 初始化UI控件
     */
    private void initUI() {
        setLayout(new BorderLayout());
        setBackground(ColorConst.BACKGROUND);

        /*
         * 顶部 - 标题
         */
        Box titleBox = Box.createHorizontalBox();
        titleBox.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2),
                        BorderFactory.createMatteBorder(0, 0, 1, 0, ColorConst.DIVIDER_GRAY)),
                BorderFactory.createEmptyBorder(8, 8, 8, 8)));

        lblTitle = new JLabel();
        lblTitle.setFont(new Font(StringConst.DEFAULT_FONT_NAME, Font.PLAIN, 18));

        titleBox.add(lblTitle);
        add(titleBox, BorderLayout.NORTH);

        /*
         * 中部 - JSplitPane
         */
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        splitPane.setOpaque(false);
        splitPane.setBorder(null);
        splitPane.setDividerSize(5);
        splitPane.setResizeWeight(0.8);

        // 使用设置边框的方式达到更改SplitPane分割线颜色的目的 (向内描边)
        Border dividerBorder = BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(ColorConst.BACKGROUND, 2),
                BorderFactory.createLineBorder(ColorConst.DIVIDER_GRAY, 10));
        ((BasicSplitPaneUI) splitPane.getUI()).getDivider().setBorder(dividerBorder);

        messageRecordScrollPane = new JScrollPane();
        messageRecordScrollPane.setMinimumSize(new Dimension(0, 64)); // 设置最小高度控制SplitPane的拖动范围
        messageRecordScrollPane.setBorder(null);
        messageRecordScrollPane.setHorizontalScrollBar(null);
        messageRecordScrollPane.getVerticalScrollBar().setUI(new SimpleScrollBarUI(ColorConst.BACKGROUND));
        messageRecordScrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(10, 0));
        messageRecordScrollPane.getVerticalScrollBar().setUnitIncrement(10);
        messageRecordScrollPane.repaint();

        JPanel messageRecordPanel = new JPanel(new BorderLayout());
        messageRecordPanel.setBackground(ColorConst.BACKGROUND);

        messageRecordBox = Box.createVerticalBox();
        messageRecordBox.setBorder(new EmptyBorder(8, 8, 8, 8));

        // 编辑器部分
        JScrollPane messageEditorScrollPane = new JScrollPane();
        messageEditorScrollPane.setMinimumSize(new Dimension(0, 64)); // 设置最小高度控制SplitPane的拖动范围
        messageEditorScrollPane.setBorder(null);
        messageEditorScrollPane.setHorizontalScrollBar(null);
        messageEditorScrollPane.getVerticalScrollBar().setUI(new SimpleScrollBarUI(ColorConst.BACKGROUND));
        messageEditorScrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(10, 0));
        messageEditorScrollPane.getVerticalScrollBar().setUnitIncrement(5);
        messageEditorScrollPane.repaint();

        txtMessageEditor = new JTextArea();
        txtMessageEditor.setLineWrap(true);
        txtMessageEditor.setWrapStyleWord(true);
        txtMessageEditor.setBorder(new EmptyBorder(16, 16, 16, 16));

        // 布局嵌套
        messageRecordPanel.add(messageRecordBox, BorderLayout.NORTH);
        messageRecordScrollPane.setViewportView(messageRecordPanel);
        messageEditorScrollPane.setViewportView(txtMessageEditor);
        splitPane.add(messageRecordScrollPane);
        splitPane.add(messageEditorScrollPane);
        add(splitPane);

        /*
         * 底部-发送按钮
         */
        Box bottomBox = Box.createHorizontalBox();

        btnSend = new JButton(StringConst.SEND_WITH_HOTKEY);
        btnSend.setBackground(ColorConst.THEME_PRIMARY);
        btnSend.setForeground(ColorConst.TEXT_TITLE_PRIMARY);
        btnSend.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
        btnSend.setFocusPainted(false);

        // 布局嵌套
        bottomBox.add(Box.createHorizontalGlue());
        bottomBox.add(btnSend);
        bottomBox.add(Box.createRigidArea(new Dimension(10, 56)));
        add(bottomBox, BorderLayout.SOUTH);

        /*
         * 冲突解决
         */
        this.setCursor(Cursor.getDefaultCursor());
        messageRecordScrollPane.getVerticalScrollBar().setCursor(Cursor.getDefaultCursor());
        messageEditorScrollPane.getVerticalScrollBar().setCursor(Cursor.getDefaultCursor());
    }

    /**
     * 初始化事件
     */
    private void initEvent() {
        /*
         * 发送按钮事件
         */
        btnSend.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });

        /*
         * 编辑框 Ctrl+Enter
         */
        txtMessageEditor.addKeyListener(new KeyAdapter() {

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getModifiers() == KeyEvent.CTRL_MASK && e.getKeyChar() == KeyEvent.VK_ENTER) {
                    sendMessage();
                }
            }
        });

        /*
         * 改变窗口大小时重新加载消息以适配器气泡
         */
        addComponentListener(new ComponentAdapter() {

            @Override
            public void componentResized(ComponentEvent e) {
                updateMessageRecord();
            }
        });
    }

    /**
     * 发送消息的处理
     */
    public void sendMessage() {
        // 从文本框获取消息内容
        String messageContent = txtMessageEditor.getText().trim();
        if (messageContent.equals("")) {
            appendHint(StringConst.MESSAGE_SHOULD_NOT_EMPTY);
            return;
        }

        txtMessageEditor.setText("");
        ClientService.messageBiz.sendMessage(chatTarget, messageContent);
    }

    /**
     * 刷新聊天记录
     */
    public void updateMessageRecord() {
        // 清空先前信息
        messageRecordBox.removeAll();

        // 添加新好友提示
        appendHint(chatMessageType == Message.TYPE_FRIEND ? StringConst.START_CHAT_WITH_FRIEND_HINT
                : StringConst.START_CHAT_WITH_GROUP_HINT);

        // 遍历所有聊天消息
        for (Message msg : ClientService.messages) {

            // 判断消息类型是否与正在聊天的对象匹配
            if (msg.getMessageType() != chatMessageType) {
                continue;
            }

            // 判断聊天对象的类型 (顺便读取头像)
            int targetId = -1;
            int pictureId = -1;
            if (chatTarget instanceof User) {
                targetId = ((User) chatTarget).getId();
                pictureId = ((User) chatTarget).getPictureId();
            } else if (chatTarget instanceof Group) {
                targetId = ((Group) chatTarget).getId();
                pictureId = ((Group) chatTarget).getPictureId();
            }

            // 判断消息类型
            if (msg.getSenderId() == ClientService.currentUser.getId() && msg.getReceiverId() == targetId) {
                // 发出的消息
                appendMessageRecord(ClientService.currentUser.getPictureId(), msg);
            } else if (msg.getSenderId() != ClientService.currentUser.getId() && msg.getReceiverId() == targetId) {
                // 收到的群聊消息
                appendMessageRecord(pictureId, msg);
            } else if (msg.getReceiverId() == ClientService.currentUser.getId() && msg.getSenderId() == targetId) {
                // 收到的私聊消息
                appendMessageRecord(pictureId, msg);
            }
        }

        // 刷新布局
        revalidate();
        repaint();
    }

    /**
     * 在消息记录布局中添加消息
     * 
     * @param pictureId 发送者头像ID
     * @param message 消息对象
     */
    public void appendMessageRecord(int pictureId, Message message) {
        messageRecordBox.add(MessageBubble.createMessage(getWidth(), pictureId, message));
    }

    /**
     * 在消息记录布局中添加提示信息
     * 
     * @param text 提示文本
     */
    public void appendHint(String text) {
        messageRecordBox.add(MessageBubble.createHint(text));

        // 刷新布局
        revalidate();
        repaint();

        scrollToBottom();
    }

    /**
     * 滚动消息记录至最底部
     */
    public void scrollToBottom() {
        messageRecordScrollPane.getVerticalScrollBar()
                .setValue(messageRecordScrollPane.getVerticalScrollBar().getMaximum());
    }
}
