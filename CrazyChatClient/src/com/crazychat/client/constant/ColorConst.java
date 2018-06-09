package com.crazychat.client.constant;

import java.awt.Color;

/**
 * 颜色常量类
 * 
 * @author RuanYaofeng
 * @date 2018-04-16 22:03
 */
public class ColorConst {

    /** 透明 */
    public static final Color TRANSPARENT = new Color(0, 0, 0, 0);

    /** 主题色-主要 */
    public static final Color THEME_PRIMARY = new Color(64, 192, 192);
    /** 主题色-主要-暗 */
    public static final Color THEME_PRIMARY_DARK = new Color(32, 160, 160);
    /** 主题色-重点 */
    public static final Color THEME_ACCENT = new Color(192, 32, 128);

    /** 文字色-主要 */
    public static final Color TEXT_PRIMARY = new Color(0, 0, 0);
    /** 文字色-次要 */
    public static final Color TEXT_SECONDARY = new Color(128, 128, 128);
    /** 标题文字色-主要 */
    public static final Color TEXT_TITLE_PRIMARY = new Color(255, 255, 255);
    /** 标题文字色-次要 */
    public static final Color TEXT_TITLE_SECONDARY = new Color(255, 255, 255, 128);

    /** 背景色 */
    public static final Color BACKGROUND = Color.WHITE;
    /** 背景色-暗 */
    public static final Color BACKGROUND_GRAY = new Color(240, 240, 240);

    /** 分割线颜色 */
    public static final Color DIVIDER_GRAY = new Color(192, 192, 192);

    /** 列表背景色 */
    public static final Color LIST_BG = new Color(244, 244, 244);
    /** 列表项背景色 */
    public static final Color LISTITEM_BG = LIST_BG;
    /** 列表项背景色-获得焦点 */
    public static final Color LISTITEM_BG_HOVER = new Color(230, 230, 230);
    /** 列表项背景色-选中 */
    public static final Color LISTITEM_BG_SELECTED = new Color(220, 220, 220);

}
