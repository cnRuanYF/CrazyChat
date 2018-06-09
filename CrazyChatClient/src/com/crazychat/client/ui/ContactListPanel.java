package com.crazychat.client.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.crazychat.client.constant.ColorConst;
import com.crazychat.client.constant.ImageConst;
import com.crazychat.client.ui.component.SimpleScrollBarUI;
import com.crazychat.client.ui.listener.ContactListItemSelectListener;
import com.crazychat.entity.Group;
import com.crazychat.entity.User;

/**
 * 联系人列表面板
 * 
 * @author RuanYaofeng
 * @date 2018-04-20 09:11
 */
@SuppressWarnings("serial")
public class ContactListPanel extends JPanel {

    /** 列表布局 */
    private Box listLayout;

    private JScrollPane scrollPane;

    /** 列表数据 */
    private List<?> listData = null;

    /** 列表项布局 */
    private List<ContactListItem> listItems = new ArrayList<>();

    /** 列表项选中监听器 */
    private ContactListItemSelectListener contactListItemSelectListener = null;

    /**
     * Create the panel.
     */
    public ContactListPanel() {
        initUI();
        initEvent();
    }

    /**
     * @return the listData
     */
    public List<?> getListData() {
        return listData;
    }

    /**
     * @param listData the listData to set
     */
    public void setListData(List<?> listData) {
        this.listData = listData;
        updateList();
    }

    /**
     * @param contactListItemSelectListener the contactListItemSelectListener to
     *            set
     */
    public void setContactListItemSelectListener(ContactListItemSelectListener contactListItemSelectListener) {
        this.contactListItemSelectListener = contactListItemSelectListener;
    }

    /**
     * 初始化UI控件
     */
    private void initUI() {
        setLayout(new BorderLayout(0, 0));
        setBackground(Color.red);

        listLayout = Box.createVerticalBox();
        listLayout.setBackground(ColorConst.LIST_BG);
        listLayout.setOpaque(true);

        scrollPane = new JScrollPane(listLayout);
        scrollPane.setBorder(null);
        scrollPane.setHorizontalScrollBar(null);
        scrollPane.getVerticalScrollBar().setUI(new SimpleScrollBarUI());
        scrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(10, 0));
        scrollPane.getVerticalScrollBar().setUnitIncrement(20);

        add(scrollPane);

        // 冲突解决
        this.setCursor(Cursor.getDefaultCursor());
        scrollPane.getVerticalScrollBar().setCursor(Cursor.getDefaultCursor());
    }

    /**
     * 初始化事件
     */
    private void initEvent() {}

    /**
     * 刷新列表
     */
    private void updateList() {
        // 清空原布局
        listLayout.removeAll();
        listItems.removeAll(listItems);

        // 添加项目
        int i = 0;
        for (Object obj : listData) {
            ImageIcon picture = null;
            String title = null;
            String subtitle = null;

            if (obj instanceof User) {
                User u = (User) obj;
                u.getPictureId();
                picture = ImageConst.IC_USER_PICTURES[u.getPictureId()];
                title = u.getNickname();
                subtitle = u.getIntroduction();
            } else if (obj instanceof Group) {
                Group g = (Group) obj;
                picture = ImageConst.IC_GROUP_PICTURES[g.getPictureId()];
                title = g.getGroupName();
                subtitle = g.getGroupDesc();
            }

            ContactListItem item = new ContactListItem();
            item.setIndex(i);
            item.setPicture(picture);
            item.setTitle(title);
            item.setSubtitle(subtitle);

            listLayout.add(item);
            listItems.add(item);
            i++;

            // 设置监听
            item.addMouseListener(contactClickListener);
        }

        // 刷新布局
        validate();
    }

    /**
     * 列表项点击监听器
     */
    MouseListener contactClickListener = new MouseAdapter() {

        @Override
        public void mousePressed(MouseEvent e) {

            // 重置所有状态
            for (ContactListItem item : listItems) {
                item.setSelected(false);
            }

            // 获取事件源
            ContactListItem item = (ContactListItem) e.getSource();
            item.setSelected(true);

            // 回调
            if (contactListItemSelectListener != null) {
                contactListItemSelectListener.listItemSelected(ContactListPanel.this, item.getIndex());

                // 双击的情形
                if (e.getClickCount() == 2) {
                    contactListItemSelectListener.listItemDoubleClicked(ContactListPanel.this, item.getIndex());
                }
            }

        }
    };

}
