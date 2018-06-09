package com.crazychat.client.ui.component;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;

import com.crazychat.client.constant.ImageConst;
import com.crazychat.client.ui.listener.FrameControlButtonsListener;

/**
 * 自定义窗口右上角按钮组
 * 
 * @author RuanYaofeng
 * @date 2018-04-21 12:02
 */
@SuppressWarnings("serial")
public class FrameControlButtons extends Box {

    /*
     * 窗口控制按钮
     */
    JButton btnMinimize;
    JButton btnMaximize;
    JButton btnRestore;
    JButton btnClose;

    /** 控制的Frame */
    private JFrame frame;

    /** 关闭按钮点击监听 */
    private FrameControlButtonsListener frameControlButtonsListener = null;

    /**
     * 构造
     */
    public FrameControlButtons(JFrame frame) {
        super(BoxLayout.X_AXIS);

        this.frame = frame;

        initUI(); // 初始化UI控件
        initEvent();// 初始化窗口事件
    }

    /**
     * @param frameControlButtonsListener the frameControlButtonsListener to set
     */
    public void setFrameControlButtonsListener(FrameControlButtonsListener frameControlButtonsListener) {
        this.frameControlButtonsListener = frameControlButtonsListener;
    }

    /**
     * 初始化UI控件
     */
    void initUI() {
        btnMinimize = new JButton();
        btnMaximize = new JButton();
        btnRestore = new JButton();
        btnClose = new JButton();
        btnMinimize.setIcon(ImageConst.IC_WINDOW_MIN_NORMAL);
        btnMinimize.setRolloverIcon(ImageConst.IC_WINDOW_MIN_HOVER);
        btnMinimize.setPressedIcon(ImageConst.IC_WINDOW_MIN_DOWN);
        btnMaximize.setIcon(ImageConst.IC_WINDOW_MAX_NORMAL);
        btnMaximize.setRolloverIcon(ImageConst.IC_WINDOW_MAX_HOVER);
        btnMaximize.setPressedIcon(ImageConst.IC_WINDOW_MAX_DOWN);
        btnRestore.setIcon(ImageConst.IC_WINDOW_RESTORE_NORMAL);
        btnRestore.setRolloverIcon(ImageConst.IC_WINDOW_RESTORE_HOVER);
        btnRestore.setPressedIcon(ImageConst.IC_WINDOW_RESTORE_DOWN);
        btnClose.setIcon(ImageConst.IC_WINDOW_CLOSE_NORMAL);
        btnClose.setRolloverIcon(ImageConst.IC_WINDOW_CLOSE_HOVER);
        btnClose.setPressedIcon(ImageConst.IC_WINDOW_CLOSE_DOWN);

        // 最大化和还原按钮默认不显示
        btnRestore.setVisible(false);
        btnRestore.setVisible(false);

        for (JButton btn : new JButton[] { btnMinimize, btnMaximize, btnRestore, btnClose }) {
            btn.setAlignmentY(Component.TOP_ALIGNMENT);
            btn.setBorder(null);
            btn.setFocusPainted(false);
            btn.setContentAreaFilled(false);
            add(btn);
        }
    }

    /**
     * 初始化事件监听
     */
    private void initEvent() {
        ActionListener menuBtnActionListener = new ActionListener() {

            /** 当前窗口是否最大化 */
            boolean isWindowMaximized = false;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == btnMinimize) {
                    // 点击最小化按钮的处理
                    frame.setExtendedState(JFrame.ICONIFIED);
                } else if (e.getSource() == btnMaximize || e.getSource() == btnRestore) {
                    // 点击最大化/还原按钮的处理
                    frame.setExtendedState(isWindowMaximized ? JFrame.NORMAL : JFrame.MAXIMIZED_BOTH);
                    btnMaximize.setVisible(isWindowMaximized);
                    btnRestore.setVisible(!isWindowMaximized);
                    isWindowMaximized = !isWindowMaximized;
                } else if (e.getSource() == btnClose) {
                    // 点击关闭按钮的处理
                    if (frameControlButtonsListener == null) {
                        System.exit(0); // TODO 退出的提示
                    } else {
                        frameControlButtonsListener.onCloseButtonClick();
                    }
                }
            }
        };
        btnMinimize.addActionListener(menuBtnActionListener);
        btnMaximize.addActionListener(menuBtnActionListener);
        btnRestore.addActionListener(menuBtnActionListener);
        btnClose.addActionListener(menuBtnActionListener);
    }

    /**
     * 设置是否显示最小化按钮
     * 
     * @param visible 是否显示
     */
    public void setMinimizeButtonVisible(boolean visible) {
        btnMinimize.setVisible(visible);
    }

    /**
     * 设置是否显示最大化按钮
     * 
     * @param visible 是否显示
     */
    public void setMaximizeButtonVisible(boolean visible) {
        btnMaximize.setVisible(visible);
    }

}