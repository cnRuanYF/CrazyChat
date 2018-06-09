package com.crazychat.client.ui;

import java.awt.Cursor;
import java.awt.Font;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.crazychat.client.constant.ColorConst;
import com.crazychat.client.constant.ImageConst;

/**
 * 默认显示在聊天区域的占位面板
 * 
 * @author RuanYaofeng
 * @date 2018-04-20 01:17
 */
@SuppressWarnings("serial")
public class PlaceHolderPanel extends JPanel {

    /**
     * Create the panel.
     */
    public PlaceHolderPanel() {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setBackground(ColorConst.BACKGROUND_GRAY);
        setCursor(Cursor.getDefaultCursor());
        
        JLabel lblCC = new JLabel(ImageConst.IC_LOGO_LARGE);
        lblCC.setFont(new Font(null, Font.ITALIC, 64));
        lblCC.setForeground(ColorConst.TEXT_SECONDARY);

        add(Box.createGlue());
        add(lblCC);
        add(Box.createGlue());
    }

}
