package com.crazychat.server.ui;

import javax.swing.JPanel;
import javax.swing.JTextPane;

import com.crazychat.server.ServerService;
import com.crazychat.server.constant.StringConst;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 服务器状态面板
 * 
 * @author RuanYaofeng, VisonSun
 * @date 2018-04-18 10:34
 */
@SuppressWarnings("serial")
public class ServerLogsPanel extends JPanel {

    public JTextPane txtPane = new JTextPane();
    private JButton btnExportLog = new JButton(StringConst.EXPORT_LOGS);
    private SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private SimpleDateFormat dateFormaterForTxt = new SimpleDateFormat("yyyy-MM-dd,HH`mm`ss");

    /**
     * Create the panel.
     */
    public ServerLogsPanel() {
        // 设置layout
        setLayout(new BorderLayout(0, 0));

        ServerService.setServerLogsPanel(this);

        add(txtPane, BorderLayout.CENTER);
        add(btnExportLog, BorderLayout.NORTH);
        txtPane.setEditable(false);

        clickListener();

    }

    /**
     * 在聊天框中追加消息
     * 
     * @param msg 消息内容
     */
    public void appendMsg(String msg) {
        txtPane.setText(txtPane.getText() + dateFormater.format(new Date()) + "\t" + msg + "\n");
    }

    public void clickListener() {
        btnExportLog.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                if (txtPane.getText() == null) {
                    JOptionPane.showMessageDialog(null, "日志为空, 导出失败.");
                } else {
                    FileWriter fw = null;
                    try {
                        fw = new FileWriter("./res/" + dateFormaterForTxt.format(new Date()) + ".txt");
                        fw.write(txtPane.getText());
                        JOptionPane.showMessageDialog(null,
                                "导出日志成功, 路径: res/" + dateFormaterForTxt.format(new Date()) + ".txt");
                        fw.close();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
