package com.crazychat.client.ui.component;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * 动画面板类
 * 
 * @author RuanYaofeng
 * @date 2018-04-19 22:40
 */
@SuppressWarnings("serial")
public class AnimatePanel extends JPanel implements ActionListener {

    /** 是否开启动画 */
    private boolean isRunning = false;

    /** 动画类型 */
    private int animationType;

    /** 动画定时器 */
    private Timer timer = null;

    /*
     * 动画类型常量
     */
    public static final int HORIZONTAL_GRADIENT_ANIM = 0;
    public static final int SLANT_GRADIENT_ANIM = 1;

    /*
     * 渐变矩形动画相关
     */
    /** 渐变矩形的宽度 */
    private int rectWidth;
    /** 渐变矩形的高度 */
    private int rectHeight;
    /** 渐变矩形的运行速度 */
    private int rectSpeed;
    /** 渐变矩形的水平位置 */
    private int nowX = 0;
    /** 渐变矩形的垂直位置 */
    private int nowY = 0;
    /** 是否从左边往右边移动 */
    private boolean isLeft = true;
    /** 渐变颜色 */
    private static final Color COLOR_GRADIENT = Color.WHITE;

    /**
     * 构造动画面板
     */
    public AnimatePanel() {
        setOpaque(false); // 禁用默认背景绘制

        timer = new Timer(10, this);
        timer.stop();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // 根据容器大小动态调整尺寸与速度
        rectWidth = getWidth() / 2;
        rectHeight = getHeight();
        rectSpeed = getWidth() / 25;

        if (isLeft) {
            if (nowX <= getWidth())
                nowX += rectSpeed;
            else
                isLeft = false;
        } else {
            if (nowX >= 0 - rectWidth)
                nowX -= rectSpeed;
            else
                isLeft = true;
        }

        // 立即重绘
        repaint();
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        // 填充背景
        g2d.setColor(getBackground());
        g2d.fillRect(0, 0, getWidth(), getHeight());

        // 若动画运行则绘制特效
        if (isRunning) {
            switch (animationType) {
            // 横向渐变动画
            case HORIZONTAL_GRADIENT_ANIM:
                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.25f));
                if (isLeft) {
                    g2d.setPaint(new GradientPaint(nowX, 0, getBackground(), nowX + rectWidth, 0, COLOR_GRADIENT));
                } else {
                    g2d.setPaint(new GradientPaint(nowX, 0, COLOR_GRADIENT, nowX + rectWidth, 0, getBackground()));
                }
                g2d.fillRect(nowX, nowY, rectWidth, rectHeight);
                break;
            // 斜向渐变动画
            case SLANT_GRADIENT_ANIM:
                g2d.rotate(0.5);
                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.25f));
                if (isLeft) {
                    g2d.setPaint(new GradientPaint(nowX, 0, getBackground(), nowX + rectWidth, 0, COLOR_GRADIENT));
                } else {
                    g2d.setPaint(new GradientPaint(nowX, 0, COLOR_GRADIENT, nowX + rectWidth, 0, getBackground()));
                }
                g2d.fillRect(nowX, nowY - rectWidth, rectWidth, rectHeight + rectWidth);
                g2d.rotate(-0.5);

                break;
            }
        }

        // 恢复画布
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
        super.paint(g2d);
    }

    /**
     * 开始动画
     */
    public void startAnimation(int animationType) {
        this.animationType = animationType;

        nowX = 0 - rectWidth;
        nowY = 0;
        isRunning = true;
        timer.start();
    }

    /**
     * 停止动画
     */
    public void stopAnimation() {
        isRunning = false;
        timer.stop();
        repaint();
    }
}
