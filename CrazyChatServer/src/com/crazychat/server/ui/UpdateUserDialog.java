package com.crazychat.server.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.crazychat.dao.UserDAO;
import com.crazychat.dao.impl.UserDAOImpl;
import com.crazychat.entity.User;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * 服务端修改个人信息模态窗口
 * 
 * @author VisonSun
 * @date 2018-04-23 22:29
 */
@SuppressWarnings("serial")
public class UpdateUserDialog extends JDialog {

    private final JPanel contentPanel = new JPanel();
    public JTextField txtName;
    public JTextField txtGender;
    public JTextField txtNickname;
    public JTextField txtPhone;
    public JTextField txtMail;
    private User user;

    /**
     * @param user the user to set
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        try {
            UpdateUserDialog dialog = new UpdateUserDialog();
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Create the dialog.
     */
    public UpdateUserDialog() {
        setTitle(" 修改账号信息");
        setBounds(100, 100, 356, 372);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(null);
        setLocationRelativeTo(null);

        JLabel labName = new JLabel("账号");
        labName.setBounds(73, 35, 59, 27);
        contentPanel.add(labName);
        {
            txtName = new JTextField();
            txtName.setEditable(false);
            txtName.setEnabled(false);
            txtName.setBounds(133, 33, 116, 31);
            contentPanel.add(txtName);
            txtName.setColumns(10);
        }
        {
            JLabel labGender = new JLabel("性别");
            labGender.setBounds(73, 81, 59, 27);
            contentPanel.add(labGender);
        }
        {
            txtGender = new JTextField();
            txtGender.setColumns(10);
            txtGender.setBounds(133, 79, 116, 31);
            contentPanel.add(txtGender);
        }
        {
            JLabel labNickname = new JLabel("昵称");
            labNickname.setBounds(73, 127, 59, 27);
            contentPanel.add(labNickname);
        }
        {
            txtNickname = new JTextField();
            txtNickname.setColumns(10);
            txtNickname.setBounds(133, 125, 116, 31);
            contentPanel.add(txtNickname);
        }
        {
            JLabel labPhone = new JLabel("电话");
            labPhone.setBounds(73, 173, 59, 27);
            contentPanel.add(labPhone);
        }
        {
            txtPhone = new JTextField();
            txtPhone.setColumns(10);
            txtPhone.setBounds(133, 171, 116, 31);
            contentPanel.add(txtPhone);
        }
        {
            JLabel labMail = new JLabel("邮箱");
            labMail.setBounds(73, 219, 59, 27);
            contentPanel.add(labMail);
        }
        {
            txtMail = new JTextField();
            txtMail.setColumns(10);
            txtMail.setBounds(133, 217, 116, 31);
            contentPanel.add(txtMail);
        }
        {
            JPanel buttonPane = new JPanel();
            buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
            getContentPane().add(buttonPane, BorderLayout.SOUTH);
            {
                JButton btnUpdate = new JButton("修改");
                btnUpdate.addActionListener(new ActionListener() {

                    public void actionPerformed(ActionEvent arg0) {
                        if (!txtGender.getText().equals("女") && !txtGender.getText().equals("男")) {
                            JOptionPane.showMessageDialog(null, "性别只能为男或女");
                        } else {
                            UserDAO dao = new UserDAOImpl();
                            if (txtGender.getText().equals("男")) {
                                user.setGender(1);
                            } else if (txtGender.getText().equals("女")) {
                                user.setGender(0);
                            } else {
                                JOptionPane.showMessageDialog(null, "性别只能为男或女");
                            }
                            user.setNickname(txtNickname.getText());
                            user.setPhone(txtPhone.getText());
                            user.setEmail(txtMail.getText());
                            try {
                                dao.update(user);
                            } catch (Exception e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                            UpdateUserDialog.this.dispose();
                        }
                    }
                });
                btnUpdate.setActionCommand("OK");
                buttonPane.add(btnUpdate);
                getRootPane().setDefaultButton(btnUpdate);
            }
            {
                JButton btnCancel = new JButton("取消");
                btnCancel.addActionListener(new ActionListener() {

                    public void actionPerformed(ActionEvent e) {
                        UpdateUserDialog.this.dispose();
                    }
                });
                btnCancel.setActionCommand("Cancel");
                buttonPane.add(btnCancel);
            }
        }
    }
}
