package com.crazychat.client.ui;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;

import com.crazychat.client.constant.ColorConst;
import com.crazychat.client.constant.ImageConst;
import com.crazychat.client.constant.StringConst;
import com.crazychat.client.ui.component.MessageBubble;
import com.crazychat.client.ui.component.SimpleScrollBarUI;
import com.crazychat.entity.Group;
import com.crazychat.entity.User;

/**
 * 用户/群资料面板
 * 
 * @author RuanYaofeng
 * @date 2018-04-22 16:21
 */
@SuppressWarnings("serial")
public class ProfilePanel extends JScrollPane {

    /** 资料项目尺寸 */
    Dimension profileItemSize = new Dimension(320, 64);

    /** 资料标签尺寸 */
    Dimension profileLabelSize = new Dimension(96, 34);

    /** 日期格式化 */
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年 M月 d日");

    /** 头像图片 */
    private JLabel lblPicture;

    /** 标题 */
    private JLabel lblTitle;

    /** 内容面板 */
    private JPanel contentPane;

    /**
     * 构造资料窗口
     */
    public ProfilePanel() {
        setCursor(Cursor.getDefaultCursor());
        initUI();
        intiEvent();
    }

    /**
     * 初始化UI控件
     */
    private void initUI() {
        contentPane = new JPanel();
        BoxLayout layout = new BoxLayout(contentPane, BoxLayout.Y_AXIS);
        contentPane.setLayout(layout);
        contentPane.setBackground(ColorConst.BACKGROUND);

        // 滚动面板
        setBorder(null);
        setHorizontalScrollBar(null);
        getVerticalScrollBar().setUI(new SimpleScrollBarUI(ColorConst.BACKGROUND));
        getVerticalScrollBar().setPreferredSize(new Dimension(10, 0));
        getVerticalScrollBar().setUnitIncrement(5);
        setViewportView(contentPane);

        // 头像
        lblPicture = new JLabel();
        lblPicture.setAlignmentX(Component.CENTER_ALIGNMENT);

        // 标题 (昵称/群名)
        lblTitle = new JLabel();
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblTitle.setFont(new Font(StringConst.DEFAULT_FONT_NAME, Font.PLAIN, 32));

        // 初始状态使用占位符
        contentPane.add(new PlaceHolderPanel());
    }

    /**
     * 初始化事件
     */
    private void intiEvent() {}

    /**
     * 设置要查看的用户
     */
    public void setData(User user) {

        contentPane.removeAll();

        lblPicture.setIcon(ImageConst.IC_USER_PICTURES[user.getPictureId()]);
        lblTitle.setText(user.getNickname());

        contentPane.add(Box.createVerticalGlue());
        contentPane.add(Box.createVerticalStrut(32));
        contentPane.add(lblPicture);
        contentPane.add(Box.createVerticalStrut(20));
        contentPane.add(lblTitle);
        contentPane.add(Box.createVerticalStrut(20));

        if (user.getIntroduction() != null) {
            // 强制换行解决简介过长问题
            String intro = MessageBubble.WrapLineByWidth(profileItemSize.width - profileLabelSize.width - 32,
                    user.getIntroduction());
            contentPane.add(createProfileItem("简介", intro));
        }

        contentPane.add(createProfileItem("用户名", user.getUsername()));

        String gender = user.getGender() == 1 ? "♂ 男" : (user.getGender() == 0 ? "♀ 女" : "🌈 保密");
        contentPane.add(createProfileItem("性别", gender));

        contentPane.add(createProfileItem("生日", user.getBirthday()));
        contentPane.add(createProfileItem("手机号", user.getPhone()));
        contentPane.add(createProfileItem("邮箱", user.getEmail()));
        contentPane.add(createProfileItem("注册时间", user.getRegisterTime()));

        contentPane.add(Box.createVerticalStrut(32));
        contentPane.add(Box.createVerticalGlue());

        // 刷新布局
        revalidate();
        repaint();
    }

    /**
     * 设置要查看的群
     */
    public void setData(Group group) {
        contentPane.removeAll();

        lblPicture.setIcon(ImageConst.IC_GROUP_PICTURES[group.getPictureId()]);
        lblTitle.setText(group.getGroupName());

        contentPane.add(Box.createVerticalGlue());
        contentPane.add(Box.createVerticalStrut(32));
        contentPane.add(lblPicture);
        contentPane.add(Box.createVerticalStrut(20));
        contentPane.add(lblTitle);
        contentPane.add(Box.createVerticalStrut(20));

        // 强制换行解决简介过长问题
        String intro = MessageBubble.WrapLineByWidth(profileItemSize.width - profileLabelSize.width - 16,
                group.getGroupDesc());
        contentPane.add(createProfileItem("简介", intro));

        contentPane.add(createProfileItem("创建时间", group.getCreateTime()));

        contentPane.add(Box.createVerticalStrut(32));
        contentPane.add(Box.createVerticalGlue());

        // 刷新布局
        revalidate();
        repaint();
    }

    /**
     * 创建一个资料项目Box
     * 
     * @param key 项目名称
     * @param value 项目值
     * @return 资料项目Box对象
     */
    private Box createProfileItem(String key, String value) {
        Box box = Box.createHorizontalBox();
        box.setMaximumSize(profileItemSize);

        JLabel lblKey = new JLabel(key);
        lblKey.setHorizontalAlignment(SwingConstants.RIGHT);
        lblKey.setForeground(ColorConst.TEXT_SECONDARY);
        lblKey.setAlignmentY(TOP_ALIGNMENT);
        lblKey.setMinimumSize(profileLabelSize);
        lblKey.setMaximumSize(profileLabelSize);
        lblKey.setPreferredSize(profileLabelSize);

        JTextPane txtValue = new JTextPane();
        txtValue.setBackground(null);
        txtValue.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        txtValue.setAlignmentY(TOP_ALIGNMENT);
        txtValue.setText(value);

        box.add(lblKey);
        box.add(txtValue);

        return box;
    }

    /**
     * 创建一个资料项目Box
     * 
     * @param key 项目名称
     * @param date 项目日期值
     * @return 资料项目Box对象
     */
    private Box createProfileItem(String key, Date date) {
        if (date == null) {
            return createProfileItem(key, "未知");
        } else {
            return createProfileItem(key, simpleDateFormat.format(date));
        }
    }

}
