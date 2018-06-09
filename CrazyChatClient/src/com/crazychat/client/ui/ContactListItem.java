package com.crazychat.client.ui;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.border.EmptyBorder;

import com.crazychat.client.constant.ColorConst;

/**
 * 联系人列表项布局
 * 
 * @author RuanYaofeng
 * @date 2018-04-20 00:24
 */
@SuppressWarnings("serial")
public class ContactListItem extends Box {

    JLabel lblPicture;
    JLabel lblTitle;
    JLabel lblSubtitle;

    Dimension picruteSize = new Dimension(40, 40);
    Dimension layoutSize = new Dimension(240, 56);

    /** 列表项索引 */
    private int index;

    /** 是否被选中 */
    private boolean selected;

    /** 标题 */
    private String title = "Title";

    /** 副标题 */
    private String subtitle = "Subtitle";

    /**
     * 构造一个联系人列表项布局
     */
    public ContactListItem() {
        super(BoxLayout.X_AXIS);
        initUI();
        initEvent();
    }

    /**
     * 构造一个联系人列表项布局
     * 
     * @param imageIcon 图标
     * @param title 标题
     * @param subtitle 副标题
     */
    public ContactListItem(ImageIcon imageIcon, String title, String subtitle) {
        super(BoxLayout.X_AXIS);
        this.title = title;
        this.subtitle = subtitle;
        initUI();
        initEvent();
        setPicture(imageIcon);
    }

    /**
     * @return the index
     */
    public int getIndex() {
        return index;
    }

    /**
     * @param index the index to set
     */
    public void setIndex(int index) {
        this.index = index;
    }

    /**
     * @return the selected
     */
    public boolean isSelected() {
        return selected;
    }

    /**
     * @param selected the selected to set
     */
    public void setSelected(boolean selected) {
        this.selected = selected;
        updateSelectStatus();
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
        lblTitle.setText(title);
    }

    /**
     * @return the subtitle
     */
    public String getSubtitle() {
        return subtitle;
    }

    /**
     * @param subtitle the subtitle to set
     */
    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
        lblSubtitle.setText(subtitle);
    }

    /**
     * 初始化UI布局
     */
    private void initUI() {
        setPreferredSize(layoutSize);

        lblPicture = new JLabel();
        lblTitle = new JLabel(title);
        lblSubtitle = new JLabel(subtitle);

        lblPicture.setBorder(new EmptyBorder(8, 8, 8, 8));

        lblTitle.setForeground(ColorConst.TEXT_PRIMARY);
        lblSubtitle.setForeground(ColorConst.TEXT_SECONDARY);

        // 布局
        Box textBox = Box.createVerticalBox();
        textBox.add(lblTitle);
        textBox.add(lblSubtitle);

        add(lblPicture);
        add(textBox);
        add(Box.createHorizontalGlue());

        // 避免和ResizableFrame冲突，设置默认鼠标指针
        setCursor(Cursor.getDefaultCursor());
    }

    /**
     * 刷新选中状态
     */
    private void updateSelectStatus() {
        setBackground(selected ? ColorConst.LISTITEM_BG_SELECTED : ColorConst.LISTITEM_BG);
        setOpaque(selected);
    }

    /**
     * 初始化事件
     */
    private void initEvent() {
        MouseListener focusListener = new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {
                setOpaque(true);
                setBackground(ColorConst.LISTITEM_BG_HOVER);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                updateSelectStatus();
            }

        };
        addMouseListener(focusListener);
    }

    /**
     * 设置图片
     * 
     * @param imageIcon
     */
    public void setPicture(ImageIcon imageIcon) {
        ImageIcon icon = new ImageIcon(imageIcon.getImage().getScaledInstance(picruteSize.width, picruteSize.height,
                java.awt.Image.SCALE_SMOOTH));
        lblPicture.setIcon(icon);
    }

}
