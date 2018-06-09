package com.crazychat.client.ui.listener;

import java.awt.Component;

/**
 * 好友列表项选择监听器
 * 
 * @author RuanYaofeng
 * @date 2018-04-20 19:40
 */
public interface ContactListItemSelectListener {

    /**
     * 列表项被选中
     * 
     * @param eventSource 事件源 (列表对象)
     * @param itemIndex 选中项的索引
     */
    void listItemSelected(Component eventSource, int itemIndex);

    /**
     * 列表项被双击
     * 
     * @param eventSource 事件源 (列表对象)
     * @param itemIndex 列表项的索引
     */
    void listItemDoubleClicked(Component eventSource, int itemIndex);
}
