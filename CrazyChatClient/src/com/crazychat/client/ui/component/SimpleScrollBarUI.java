package com.crazychat.client.ui.component;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.plaf.basic.BasicScrollBarUI;

import com.crazychat.client.constant.ColorConst;

/**
 * 自定义滚动条UI
 * 
 * @author RuanYaofeng
 * @date 2018-04-20 14:03
 */
public class SimpleScrollBarUI extends BasicScrollBarUI {

    /** 抗锯齿渲染 */
    RenderingHints antialiasRenderingHints = new RenderingHints(RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON);

    /** 滚动条边距 */
    private int horizontalMargin = 2;
    private int verticalMargin = 2;

    /**
     * 构造自定义滚动条UI
     * 
     * @param trackColor 滑道颜色
     */
    public SimpleScrollBarUI() {
        super();
    }

    /**
     * 构造自定义滚动条UI<br>
     * 滑道颜色需要执行对应ScrollPane的repaint方法才能生效
     * 
     * @param trackColor 滑道颜色
     */
    public SimpleScrollBarUI(Color trackColor) {
        super();
        this.trackColor = trackColor;
    }

    /**
     * @param horizontalMargin the horizontalMargin to set
     */
    public void setHorizontalMargin(int horizontalMargin) {
        this.horizontalMargin = horizontalMargin;
    }

    /**
     * @param verticalMargin the verticalMargin to set
     */
    public void setVerticalMargin(int verticalMargin) {
        this.verticalMargin = verticalMargin;
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.plaf.basic.BasicScrollBarUI#configureScrollBarColors()
     */
    @Override
    protected void configureScrollBarColors() {
        if (trackColor == null) {
            trackColor = ColorConst.LIST_BG;
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * javax.swing.plaf.basic.BasicScrollBarUI#paintThumb(java.awt.Graphics,
     * javax.swing.JComponent, java.awt.Rectangle)
     */
    @Override
    protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
        // 获取Graphics2D对象
        Graphics2D g2d = (Graphics2D) g;

        // 设置抗锯齿
        g2d.addRenderingHints(antialiasRenderingHints);

        // 转换坐标系原点为滚动条位置原点
        g2d.translate(thumbBounds.x, thumbBounds.y);

        // 设置颜色，半透明
        g2d.setColor(ColorConst.THEME_PRIMARY);
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.25f));

        // 填充圆角矩形 (x, y, width, height, arcWidth, arcHeight)
        g2d.fillRoundRect(horizontalMargin, verticalMargin, thumbBounds.width - horizontalMargin * 2,
                thumbBounds.height - verticalMargin * 2, thumbBounds.width, thumbBounds.width);
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.plaf.basic.BasicScrollBarUI#createDecreaseButton(int)
     */
    @Override
    protected JButton createDecreaseButton(int orientation) {
        // 返回一个无边框空按钮即可不显示按钮
        JButton emptyButton = new JButton();
        emptyButton.setBorder(null);
        return emptyButton;
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.plaf.basic.BasicScrollBarUI#createIncreaseButton(int)
     */
    @Override
    protected JButton createIncreaseButton(int orientation) {
        // 返回一个无边框空按钮即可不显示按钮
        JButton emptyButton = new JButton();
        emptyButton.setBorder(null);
        return emptyButton;
    }

}
