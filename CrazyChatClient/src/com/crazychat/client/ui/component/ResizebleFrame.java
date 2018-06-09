package com.crazychat.client.ui.component;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;

/**
 * 可拖动边框调整大小的无边框Frame
 * 
 * Modofied by RuanYaofeng on 2018-04-23.<br>
 * Changes: 加入RESIZE_BLOCK_WIDTH属性字段, 设置最外圈不可拖动宽度范围, 用于实现带有阴影边框的透明窗体
 * 
 * @author Tassdars
 * @see <a href=
 *      "https://bbs.csdn.net/topics/350005772">JFrame在去掉标题栏的情况下怎么实现拉大收缩边框的功能？-CSDN论坛</a>
 * @date 2018-04-19 20:02
 */
@SuppressWarnings("serial")
public class ResizebleFrame extends JFrame {

    private boolean isTopLeft;// 是否处于左上角调整窗口状态
    private boolean isTop;// 是否处于上边界调整窗口状态
    private boolean isTopRight;// 是否处于右上角调整窗口状态
    private boolean isRight;// 是否处于右边界调整窗口状态
    private boolean isBottomRight;// 是否处于右下角调整窗口状态
    private boolean isBottom;// 是否处于下边界调整窗口状态
    private boolean isBottomLeft;// 是否处于左下角调整窗口状态
    private boolean isLeft;// 是否处于左边界调整窗口状态
    private final static int RESIZE_BLOCK_WIDTH = 10;// 不可拖动的外边界距离
    private final static int RESIZE_WIDTH = 16;// 判定是否为调整窗口状态的范围与边界距离
    private final static int MIN_WIDTH = 720;// 窗口最小宽度
    private final static int MIN_HEIGHT = 480;// 窗口最小高度

    public ResizebleFrame() {
        addMouseMotionListener(new ResizeAdapter(this));
        setUndecorated(true);
    }

    private class ResizeAdapter extends MouseAdapter {

        private Component c;

        public ResizeAdapter(Component c) {
            this.c = c;
        }

        @Override
        public void mouseMoved(MouseEvent event) {
            int x = event.getX();
            int y = event.getY();
            int width = c.getWidth();
            int height = c.getHeight();
            int cursorType = Cursor.DEFAULT_CURSOR;// 鼠标光标初始为默认类型，若未进入调整窗口状态，保持默认类型
            // 先将所有调整窗口状态重置
            isTopLeft = isTop = isTopRight = isRight = isBottomRight = isBottom = isBottomLeft = isLeft = false;
            if (x > RESIZE_BLOCK_WIDTH && y > RESIZE_BLOCK_WIDTH && x < width - RESIZE_BLOCK_WIDTH
                    && y < height - RESIZE_BLOCK_WIDTH) { // 屏蔽外层响应
                if (y <= RESIZE_WIDTH) {
                    if (x <= RESIZE_WIDTH) {// 左上角调整窗口状态
                        isTopLeft = true;
                        cursorType = Cursor.NW_RESIZE_CURSOR;
                    } else if (x >= width - RESIZE_WIDTH) {// 右上角调整窗口状态
                        isTopRight = true;
                        cursorType = Cursor.NE_RESIZE_CURSOR;
                    } else {// 上边界调整窗口状态
                        isTop = true;
                        cursorType = Cursor.N_RESIZE_CURSOR;
                    }
                } else if (y >= height - RESIZE_WIDTH) {
                    if (x <= RESIZE_WIDTH) {// 左下角调整窗口状态
                        isBottomLeft = true;
                        cursorType = Cursor.SW_RESIZE_CURSOR;
                    } else if (x >= width - RESIZE_WIDTH) {// 右下角调整窗口状态
                        isBottomRight = true;
                        cursorType = Cursor.SE_RESIZE_CURSOR;
                    } else {// 下边界调整窗口状态
                        isBottom = true;
                        cursorType = Cursor.S_RESIZE_CURSOR;
                    }
                } else if (x <= RESIZE_WIDTH) {// 左边界调整窗口状态
                    isLeft = true;
                    cursorType = Cursor.W_RESIZE_CURSOR;
                } else if (x >= width - RESIZE_WIDTH) {// 右边界调整窗口状态
                    isRight = true;
                    cursorType = Cursor.E_RESIZE_CURSOR;
                }
            }
            // 最后改变鼠标光标
            c.setCursor(new Cursor(cursorType));
        }

        @Override
        public void mouseDragged(MouseEvent event) {
            int x = event.getX();
            int y = event.getY();
            int width = c.getWidth();
            int height = c.getHeight();
            // 保存窗口改变后的x、y坐标和宽度、高度，用于预判是否会小于最小宽度、最小高度
            int nextX = c.getX();
            int nextY = c.getY();
            int nextWidth = width;
            int nextHeight = height;
            if (isTopLeft || isLeft || isBottomLeft) {// 所有左边调整窗口状态
                nextX += x - RESIZE_BLOCK_WIDTH;
                nextWidth -= x - RESIZE_BLOCK_WIDTH;
            }
            if (isTopLeft || isTop || isTopRight) {// 所有上边调整窗口状态
                nextY += y - RESIZE_BLOCK_WIDTH;
                nextHeight -= y - RESIZE_BLOCK_WIDTH;
            }
            if (isTopRight || isRight || isBottomRight) {// 所有右边调整窗口状态
                nextWidth = x + RESIZE_BLOCK_WIDTH;
            }
            if (isBottomLeft || isBottom || isBottomRight) {// 所有下边调整窗口状态
                nextHeight = y + RESIZE_BLOCK_WIDTH;
            }
            if (nextWidth <= MIN_WIDTH) {// 如果窗口改变后的宽度小于最小宽度，则宽度调整到最小宽度
                nextWidth = MIN_WIDTH;
                if (isTopLeft || isLeft || isBottomLeft) {// 如果是从左边缩小的窗口，x坐标也要调整
                    nextX = c.getX() + width - nextWidth;
                }
            }
            if (nextHeight <= MIN_HEIGHT) {// 如果窗口改变后的高度小于最小高度，则高度调整到最小高度
                nextHeight = MIN_HEIGHT;
                if (isTopLeft || isTop || isTopRight) {// 如果是从上边缩小的窗口，y坐标也要调整
                    nextY = c.getY() + height - nextHeight;
                }
            }
            // 最后统一改变窗口的x、y坐标和宽度、高度，可以防止刷新频繁出现的屏闪情况
            setBounds(nextX, nextY, nextWidth, nextHeight);
        }
    }

}
