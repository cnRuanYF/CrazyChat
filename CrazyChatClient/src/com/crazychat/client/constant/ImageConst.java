package com.crazychat.client.constant;

import java.awt.Image;

import javax.swing.ImageIcon;

/**
 * 图片/图标常量类
 * 
 * @author RuanYaofeng
 * @date 2018-04-19 14:35
 */
public class ImageConst {

    /*
     * Logo
     */
    public static final ImageIcon IC_LOGO_SMALL = new ImageIcon("res/img/logo_small.png");
    public static final ImageIcon IC_LOGO_MIDDLE = new ImageIcon("res/img/logo_middle.png");
    public static final ImageIcon IC_LOGO_LARGE = new ImageIcon("res/img/logo_large.png");

    /*
     * 登录相关
     */
    public static final ImageIcon IC_CHECKBOX_NORMAL = new ImageIcon("res/img/checkbox_normal.png");
    public static final ImageIcon IC_CHECKBOX_CHECKED = new ImageIcon("res/img/checkbox_checked.png");

    /*
     * 窗口按钮相关
     */
    public static final ImageIcon IC_WINDOW_MIN_NORMAL = new ImageIcon("res/img/sysbtn_min_normal.png");
    public static final ImageIcon IC_WINDOW_MIN_HOVER = new ImageIcon("res/img/sysbtn_min_hover.png");
    public static final ImageIcon IC_WINDOW_MIN_DOWN = new ImageIcon("res/img/sysbtn_min_down.png");

    public static final ImageIcon IC_WINDOW_MAX_NORMAL = new ImageIcon("res/img/sysbtn_max_normal.png");
    public static final ImageIcon IC_WINDOW_MAX_HOVER = new ImageIcon("res/img/sysbtn_max_hover.png");
    public static final ImageIcon IC_WINDOW_MAX_DOWN = new ImageIcon("res/img/sysbtn_max_down.png");

    public static final ImageIcon IC_WINDOW_RESTORE_NORMAL = new ImageIcon("res/img/sysbtn_restore_normal.png");
    public static final ImageIcon IC_WINDOW_RESTORE_HOVER = new ImageIcon("res/img/sysbtn_restore_hover.png");
    public static final ImageIcon IC_WINDOW_RESTORE_DOWN = new ImageIcon("res/img/sysbtn_restore_down.png");

    public static final ImageIcon IC_WINDOW_CLOSE_NORMAL = new ImageIcon("res/img/sysbtn_close_normal.png");
    public static final ImageIcon IC_WINDOW_CLOSE_HOVER = new ImageIcon("res/img/sysbtn_close_hover.png");
    public static final ImageIcon IC_WINDOW_CLOSE_DOWN = new ImageIcon("res/img/sysbtn_close_down.png");

    /*
     * 顶栏Tab图标
     */
    public static final ImageIcon IC_TAB_DIALOG_NORMAL = new ImageIcon("res/img/icon_dialog_normal.png");
    public static final ImageIcon IC_TAB_DIALOG_FOCUS = new ImageIcon("res/img/icon_dialog_focus.png");

    public static final ImageIcon IC_TAB_CONTACTS_NORMAL = new ImageIcon("res/img/icon_contacts_normal.png");
    public static final ImageIcon IC_TAB_CONTACTS_FOCUS = new ImageIcon("res/img/icon_contacts_focus.png");

    /*
     * 用户状态
     */
    public static final ImageIcon IC_USER_STATUS_ONLINE = new ImageIcon("res/img/status_online.png");

    /*
     * 头像
     */
    public static final int USER_PICTURES_COUNT = 189;
    public static final int GROUP_PICTURES_COUNT = 16;
    public static final ImageIcon[] IC_USER_PICTURES = new ImageIcon[USER_PICTURES_COUNT];
    public static final ImageIcon[] IC_GROUP_PICTURES = new ImageIcon[GROUP_PICTURES_COUNT];

    static {
        for (int i = 0; i < USER_PICTURES_COUNT; i++) {
            IC_USER_PICTURES[i] = new ImageIcon("res/img/avatar_user/" + i + ".png");
        }
        for (int i = 0; i < GROUP_PICTURES_COUNT; i++) {
            IC_GROUP_PICTURES[i] = new ImageIcon(new ImageIcon("res/img/avatar_group/" + i + ".png").getImage()
                    .getScaledInstance(256, 256, Image.SCALE_SMOOTH));
        }
    }

    /*
     * 聊天界面
     */
    public static final ImageIcon IC_MESSAGE_BUBBLE_TRIANGLE_LEFT = new ImageIcon("res/img/msgbubble_tri_left.png");
    public static final ImageIcon IC_MESSAGE_BUBBLE_TRIANGLE_RIGHT = new ImageIcon("res/img/msgbubble_tri_right.png");

}
