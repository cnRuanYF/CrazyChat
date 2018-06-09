package com.crazychat.client.ui.component;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Image;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import com.crazychat.client.ClientService;
import com.crazychat.client.constant.ColorConst;
import com.crazychat.client.constant.ImageConst;
import com.crazychat.client.constant.StringConst;
import com.crazychat.entity.Message;

import sun.font.FontDesignMetrics;

/**
 * 消息气泡制造者类
 * 
 * @author RuanYaofeng
 * @date 2018-04-21 22:59
 */
public class MessageBubble {

    /*
     * 用户文字断行
     */
    static Font font = new Font(StringConst.DEFAULT_FONT_NAME, Font.PLAIN, 14);
    static FontMetrics fontMetrics = FontDesignMetrics.getMetrics(font);

    /**
     * 禁止实例化
     */
    private MessageBubble() {}

    /**
     * 创建一个提示消息框
     * 
     * @param text 提示信息文本
     * @return 消息框Box对象
     */
    public static Box createHint(String text) {
        // 整条消息的容器
        Box box = Box.createHorizontalBox();
        box.setBorder(new EmptyBorder(8, 8, 8, 8));

        // 提示标签
        JLabel lbl = new JLabel(text);
        lbl.setBackground(ColorConst.DIVIDER_GRAY);
        lbl.setForeground(Color.WHITE);
        lbl.setBorder(new LineBorder(ColorConst.DIVIDER_GRAY, 4, true));
        lbl.setOpaque(true);

        // 布局
        box.add(Box.createHorizontalGlue());
        box.add(lbl);
        box.add(Box.createHorizontalGlue());

        return box;
    }

    /**
     * 创建一个聊天气泡
     * 
     * @param containerWidth 容器宽度
     * @param pictureId 用户头像ID
     * @param message 消息对象
     * @return 聊天气泡Box对象
     */
    public static Box createMessage(int containerWidth, int pictureId, Message message) {

        String messageContent = WrapLineByWidth(containerWidth - 200, message.getMessageContent());

        // 整条消息的容器
        Box box = Box.createHorizontalBox();
        box.setBorder(new EmptyBorder(8, 8, 8, 8));

        // 头像
        JLabel picture = new JLabel();
        picture.setAlignmentY(Component.TOP_ALIGNMENT);
        picture.setIcon(new ImageIcon(
                ImageConst.IC_USER_PICTURES[pictureId].getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH)));

        // 消息箭头
        JLabel triangle = new JLabel();
        triangle.setAlignmentY(Component.TOP_ALIGNMENT);

        // 消息内容
        JTextPane bubble = new JTextPane();
        bubble.setEditable(false);
        bubble.setBorder(new EmptyBorder(8, 8, 8, 8));
        bubble.setAlignmentY(Component.TOP_ALIGNMENT);
        bubble.setText(messageContent);
        bubble.setMaximumSize(new Dimension(containerWidth, (messageContent.length() + 1) * fontMetrics.getHeight()));

        // 根据类型布局
        if (message.getSenderId() == ClientService.currentUser.getId()) {
            // 发出的消息在右侧
            triangle.setIcon(ImageConst.IC_MESSAGE_BUBBLE_TRIANGLE_RIGHT);
            bubble.setBackground(ColorConst.THEME_PRIMARY);
            bubble.setForeground(ColorConst.TEXT_TITLE_PRIMARY);

            box.add(Box.createHorizontalStrut(96));
            box.add(Box.createHorizontalGlue());
            box.add(bubble);
            box.add(triangle);
            box.add(picture);
        } else {
            // 接收的消息在左侧
            triangle.setIcon(ImageConst.IC_MESSAGE_BUBBLE_TRIANGLE_LEFT);
            bubble.setBackground(ColorConst.BACKGROUND_GRAY);

            box.add(picture);
            box.add(triangle);
            box.add(bubble);
            box.add(Box.createHorizontalGlue());
            box.add(Box.createHorizontalStrut(96));
        }

        return box;
    }

    /**
     * 根据指定的宽度让字符串自动换行
     * 
     * @param width 宽度
     * @param string 未经处理的字符串
     * @return
     */
    public static String WrapLineByWidth(int width, String string) {

        StringBuilder sb = new StringBuilder();
        int lineWidth = 0;

        for (int i = 0; i < string.length(); i++) {
            sb.append(string.charAt(i));
            lineWidth += fontMetrics.charWidth(string.charAt(i));
            if (string.charAt(i) == '\n') {
                lineWidth = 0;
            } else if (lineWidth > width) {
                lineWidth = 0;
                sb.append('\n');
            }
        }

        return sb.toString();
    }
}
