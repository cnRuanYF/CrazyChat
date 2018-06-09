package com.crazychat.client.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JSpinner.DateEditor;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SpinnerDateModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.MatteBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.crazychat.client.ClientService;
import com.crazychat.client.constant.ColorConst;
import com.crazychat.client.constant.ImageConst;
import com.crazychat.client.constant.StringConst;
import com.crazychat.client.ui.component.AnimatePanel;
import com.crazychat.client.ui.component.FrameControlButtons;
import com.crazychat.client.ui.component.ShadowBorder;
import com.crazychat.client.ui.listener.FrameControlButtonsListener;
import com.crazychat.entity.User;

/**
 * 修改资料Frame
 * 
 * @author RuanYaofeng
 * @date 2018-04-23 14:22
 */
@SuppressWarnings("serial")
public class EditProfileFrame extends JFrame {

    /** 表单区边框 */
    private static final Border FORM_AREA_BORDER = BorderFactory.createEmptyBorder(8, 8, 8, 8);

    /** 下划线边框 */
    private static final MatteBorder UNDERLINE_BORDER = BorderFactory.createMatteBorder(0, 0, 1, 0,
            ColorConst.DIVIDER_GRAY);

    /** 储存鼠标按下位置 (实现窗口拖动) */
    Point dragStartPoint;

    /** 窗口控制按钮组 */
    private FrameControlButtons frameControlButtons;

    private JPanel contentPane;

    /*
     * 顶栏
     */
    private AnimatePanel topPanel;
    private JLabel lblTitle;

    private JPanel mainPanel;

    private Box profileEditFromBox;

    /** 头像预览 */
    private JLabel lblPicturePreview;

    /*
     * 表单字段
     */
    private JLabel lblNickname;

    /*
     * 表单控件
     */
    private JSpinner spnPicture;
    private JTextField txtNickname;
    private JTextPane txtIntro;
    private ButtonGroup genderButtonGroup = new ButtonGroup();
    private JRadioButton rdbtnGenderMale;
    private JRadioButton rdbtnGenderFemale;
    private JSpinner spnBirthday;
    private JTextField txtPhone;
    private JTextField txtEmail;

    /** 保存按钮 */
    private JButton btnSave;

    /*
     * 属性
     */
    private int pictureId;
    private String nickname;
    private String intro;
    private int gender;
    private Date birthday;
    private String phone;
    private String email;

    /**
     * Create the frame.
     */
    public EditProfileFrame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false); // 防止从任务栏操作最大化最小化
        setUndecorated(true);

        setSize(480 + ShadowBorder.DEFAULT_SIZE * 2, 640 + ShadowBorder.DEFAULT_SIZE * 2);
        setLocationRelativeTo(null);
        setBackground(ColorConst.TRANSPARENT);

        setIconImage(ImageConst.IC_TAB_DIALOG_FOCUS.getImage());
        setTitle(StringConst.EDIT_USER_PROFILE);

        initUI();
        initData();
        initEvent();
    }

    /**
     * 初始化UI组件
     */
    private void initUI() {
        contentPane = (JPanel) getContentPane();
        contentPane.setLayout(new BorderLayout());
        contentPane.setBorder(BorderFactory.createCompoundBorder(ShadowBorder.newInstance(),
                BorderFactory.createLineBorder(ColorConst.THEME_PRIMARY, 2)));

        /*
         * 标题区域
         */
        topPanel = new AnimatePanel();
        topPanel.setLayout(new BorderLayout());
        topPanel.setBackground(ColorConst.THEME_PRIMARY);

        Box titleBox = Box.createHorizontalBox();

        lblTitle = new JLabel(StringConst.EDIT_USER_PROFILE);
        lblTitle.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
        lblTitle.setForeground(ColorConst.TEXT_TITLE_PRIMARY);
        lblTitle.setIcon(ImageConst.IC_LOGO_SMALL);
        lblTitle.setAlignmentY(TOP_ALIGNMENT);

        frameControlButtons = new FrameControlButtons(this);
        frameControlButtons.setMaximizeButtonVisible(false);
        frameControlButtons.setAlignmentY(TOP_ALIGNMENT);

        titleBox.add(lblTitle);
        titleBox.add(Box.createHorizontalGlue());
        titleBox.add(frameControlButtons);

        topPanel.add(titleBox, BorderLayout.NORTH);

        contentPane.add(topPanel, BorderLayout.NORTH);

        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(ColorConst.BACKGROUND);

        contentPane.add(mainPanel);

        /*
         * 表单
         */
        profileEditFromBox = Box.createVerticalBox();

        // 尺寸定义
        Dimension formItemSize = new Dimension(320, 64);
        Dimension formLabelSize = new Dimension(64, 0);

        // 头像预览
        lblPicturePreview = new JLabel(ImageConst.IC_USER_PICTURES[ClientService.currentUser.getPictureId()]);
        lblPicturePreview.setAlignmentX(Component.CENTER_ALIGNMENT);

        // 头像ID
        Box pictureBox = Box.createHorizontalBox();
        pictureBox.setMaximumSize(formItemSize);
        pictureBox.setBorder(UNDERLINE_BORDER);

        JLabel lblPicture = new JLabel(StringConst.PROFILE_PICTURE);
        lblPicture.setHorizontalAlignment(SwingConstants.RIGHT);
        lblPicture.setForeground(ColorConst.TEXT_SECONDARY);
        lblPicture.setPreferredSize(formLabelSize);

        spnPicture = new JSpinner();
        spnPicture.setBorder(FORM_AREA_BORDER);
        spnPicture.setBackground(null);
        spnPicture.setModel(new SpinnerNumberModel(ClientService.currentUser.getPictureId(), 0,
                ImageConst.USER_PICTURES_COUNT - 1, 1));

        pictureBox.add(lblPicture);
        pictureBox.add(spnPicture);

        // 昵称
        Box nicknameBox = Box.createHorizontalBox();
        nicknameBox.setMaximumSize(formItemSize);
        nicknameBox.setBorder(UNDERLINE_BORDER);

        lblNickname = new JLabel(StringConst.NICKNAME);
        lblNickname.setHorizontalAlignment(SwingConstants.RIGHT);
        lblNickname.setForeground(ColorConst.TEXT_SECONDARY);
        lblNickname.setPreferredSize(formLabelSize);

        txtNickname = new JTextField();
        txtNickname.setBackground(null);
        txtNickname.setBorder(FORM_AREA_BORDER);

        nicknameBox.add(lblNickname);
        nicknameBox.add(txtNickname);

        // 简介
        Box introBox = Box.createHorizontalBox();
        introBox.setMaximumSize(formItemSize);
        introBox.setBorder(UNDERLINE_BORDER);

        JLabel lblIntro = new JLabel(StringConst.INTRODUCTION);
        lblIntro.setHorizontalAlignment(SwingConstants.RIGHT);
        lblIntro.setForeground(ColorConst.TEXT_SECONDARY);
        lblIntro.setPreferredSize(formLabelSize);

        txtIntro = new JTextPane();
        txtIntro.setBackground(null);
        txtIntro.setBorder(FORM_AREA_BORDER);

        introBox.add(lblIntro);
        introBox.add(txtIntro);

        // 性别
        Box genderBox = Box.createHorizontalBox();
        genderBox.setMaximumSize(formItemSize);
        genderBox.setBorder(UNDERLINE_BORDER);

        JLabel lblGender = new JLabel(StringConst.GENDER);
        lblGender.setHorizontalAlignment(SwingConstants.RIGHT);
        lblGender.setForeground(ColorConst.TEXT_SECONDARY);
        lblGender.setPreferredSize(formLabelSize);

        rdbtnGenderMale = new JRadioButton(StringConst.GENDER_MALE);
        rdbtnGenderMale.setAlignmentX(Component.RIGHT_ALIGNMENT);
        rdbtnGenderMale.setBorder(FORM_AREA_BORDER);
        rdbtnGenderMale.setBackground(null);
        rdbtnGenderMale.setMinimumSize(new Dimension(96, 32));

        rdbtnGenderFemale = new JRadioButton(StringConst.GENDER_FEMALE);
        rdbtnGenderFemale.setAlignmentX(Component.RIGHT_ALIGNMENT);
        rdbtnGenderFemale.setBorder(FORM_AREA_BORDER);
        rdbtnGenderFemale.setBackground(null);
        rdbtnGenderFemale.setMinimumSize(new Dimension(96, 32));

        genderButtonGroup.add(rdbtnGenderMale);
        genderButtonGroup.add(rdbtnGenderFemale);

        genderBox.add(Box.createHorizontalStrut(36));
        genderBox.add(lblGender);
        genderBox.add(Box.createHorizontalStrut(8));
        genderBox.add(rdbtnGenderMale);
        genderBox.add(Box.createHorizontalStrut(8));
        genderBox.add(rdbtnGenderFemale);

        // 生日
        Box birthdayBox = Box.createHorizontalBox();
        birthdayBox.setMaximumSize(formItemSize);
        birthdayBox.setBorder(UNDERLINE_BORDER);

        JLabel lblBirthday = new JLabel(StringConst.BIRTHDAY);
        lblBirthday.setHorizontalAlignment(SwingConstants.RIGHT);
        lblBirthday.setForeground(ColorConst.TEXT_SECONDARY);
        lblBirthday.setPreferredSize(formLabelSize);

        spnBirthday = new JSpinner(new SpinnerDateModel());
        spnBirthday.setBorder(FORM_AREA_BORDER);
        spnBirthday.setBackground(null);

        DateEditor dateEditor = new DateEditor(spnBirthday, StringConst.PROFILE_DATA_FORMAT);
        spnBirthday.setEditor(dateEditor);

        birthdayBox.add(lblBirthday);
        birthdayBox.add(spnBirthday);

        // 手机号
        Box phoneBox = Box.createHorizontalBox();
        phoneBox.setMaximumSize(formItemSize);
        phoneBox.setBorder(UNDERLINE_BORDER);

        JLabel lblPhone = new JLabel(StringConst.PHONE_NUMBER);
        lblPhone.setHorizontalAlignment(SwingConstants.RIGHT);
        lblPhone.setForeground(ColorConst.TEXT_SECONDARY);
        lblPhone.setPreferredSize(formLabelSize);

        txtPhone = new JTextField();
        txtPhone.setBackground(null);
        txtPhone.setBorder(FORM_AREA_BORDER);

        phoneBox.add(lblPhone);
        phoneBox.add(txtPhone);

        // 邮箱
        Box emailBox = Box.createHorizontalBox();
        emailBox.setMaximumSize(formItemSize);
        emailBox.setBorder(UNDERLINE_BORDER);

        JLabel lblEmail = new JLabel(StringConst.EMAIL);
        lblEmail.setHorizontalAlignment(SwingConstants.RIGHT);
        lblEmail.setForeground(ColorConst.TEXT_SECONDARY);
        lblEmail.setPreferredSize(formLabelSize);

        txtEmail = new JTextField();
        txtEmail.setBackground(null);
        txtEmail.setBorder(FORM_AREA_BORDER);

        emailBox.add(lblEmail);
        emailBox.add(txtEmail);

        // 保存按钮
        btnSave = new JButton(StringConst.SAVE);
        btnSave.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnSave.setBackground(ColorConst.THEME_PRIMARY);
        btnSave.setForeground(ColorConst.TEXT_TITLE_PRIMARY);
        btnSave.setBorder(BorderFactory.createEmptyBorder(8, 64, 8, 64));
        btnSave.setFocusPainted(false);

        // 嵌套布局
        profileEditFromBox.add(Box.createVerticalStrut(20));
        profileEditFromBox.add(lblPicturePreview);
        profileEditFromBox.add(Box.createVerticalGlue());
        profileEditFromBox.add(pictureBox);
        profileEditFromBox.add(Box.createVerticalGlue());
        profileEditFromBox.add(nicknameBox);
        profileEditFromBox.add(Box.createVerticalGlue());
        profileEditFromBox.add(introBox);
        profileEditFromBox.add(Box.createVerticalGlue());
        profileEditFromBox.add(genderBox);
        profileEditFromBox.add(Box.createVerticalGlue());
        profileEditFromBox.add(birthdayBox);
        profileEditFromBox.add(Box.createVerticalGlue());
        profileEditFromBox.add(phoneBox);
        profileEditFromBox.add(Box.createVerticalGlue());
        profileEditFromBox.add(emailBox);
        profileEditFromBox.add(Box.createVerticalGlue());
        profileEditFromBox.add(btnSave);
        profileEditFromBox.add(Box.createVerticalStrut(20));

        mainPanel.add(profileEditFromBox);

    }

    /**
     * 初始化数据
     */
    private void initData() {
        User u = ClientService.currentUser;

        spnPicture.setValue(u.getPictureId());
        txtNickname.setText(u.getNickname());
        txtIntro.setText(u.getIntroduction());

        (u.getGender() == 1 ? rdbtnGenderMale : rdbtnGenderFemale).setSelected(true);

        if (u.getBirthday() != null) {
            spnBirthday.setValue(u.getBirthday());
        }
        txtPhone.setText(u.getPhone());
        txtEmail.setText(u.getEmail());
    }

    /**
     * 初始化事件
     */
    private void initEvent() {
        /*
         * 保存按钮
         */
        btnSave.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                saveProfile();
                dispose();
                ClientService.editProfileFrame = null;
            }
        });

        /*
         * 实现窗口拖曳
         */
        MouseListener windowDragListener = new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                dragStartPoint = e.getPoint(); // 获取到的坐标是相对于容器而非屏幕
            }
        };
        topPanel.addMouseListener(windowDragListener);

        MouseMotionListener windowDragMotionListener = new MouseMotionAdapter() {

            @Override
            public void mouseDragged(MouseEvent e) {
                // mouseDragged触发时的窗口位置
                Point frameLocation = getLocation();
                setLocation(frameLocation.x - dragStartPoint.x + e.getX(),
                        frameLocation.y - dragStartPoint.y + e.getY());
            }
        };
        topPanel.addMouseMotionListener(windowDragMotionListener);

        /*
         * 关闭时不退出
         */
        frameControlButtons.setFrameControlButtonsListener(new FrameControlButtonsListener() {

            @Override
            public void onCloseButtonClick() {
                dispose();
                ClientService.editProfileFrame = null;
            }
        });

        /*
         * 头像预览
         */
        spnPicture.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent e) {
                JSpinner spn = (JSpinner) e.getSource();
                lblPicturePreview.setIcon(ImageConst.IC_USER_PICTURES[(int) spn.getValue()]);
            }
        });

        /*
         * 手机号格式限制
         */
        txtPhone.addKeyListener(new KeyAdapter() {

            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar() < '0' || e.getKeyChar() > '9' || txtPhone.getText().length() >= 11) {
                    e.consume(); // 屏蔽非法输入
                }
            }
        });

        /*
         * 实时表单验证
         */
        DocumentListener validateDocumentListener = new DocumentListener() {

            @Override
            public void removeUpdate(DocumentEvent e) {
                validateForm();
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                validateForm();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                validateForm();
            }
        };
        txtNickname.getDocument().addDocumentListener(validateDocumentListener);
    }

    /**
     * 表单验证
     */
    private void validateForm() {
        boolean isValidate = true;

        // XXX 暂时只验证昵称不能为空
        nickname = txtNickname.getText().trim();
        if (nickname.equals("")) {
            lblNickname.setForeground(Color.RED);
            isValidate = false;
        } else {
            lblNickname.setForeground(ColorConst.TEXT_SECONDARY);
        }

        btnSave.setEnabled(isValidate);
    }

    /**
     * 提交修改
     */
    private void saveProfile() {
        pictureId = (int) spnPicture.getValue();
        nickname = txtNickname.getText().trim();
        intro = txtIntro.getText().trim();
        gender = rdbtnGenderMale.isSelected() ? 1 : 0;
        birthday = (Date) spnBirthday.getValue();
        phone = txtPhone.getText();
        email = txtEmail.getText().trim();

        User u = new User();

        // 填回原信息
        u.setId(ClientService.currentUser.getId());
        u.setUsername(ClientService.currentUser.getUsername());
        u.setPassword(ClientService.currentUser.getPassword());

        // 设置新信息
        u.setPictureId(pictureId);
        u.setNickname(nickname);
        u.setIntroduction(intro);
        u.setGender(gender);
        u.setBirthday(birthday);
        u.setPhone(phone);
        u.setEmail(email);

        ClientService.userBiz.updateUserProfile(u);

        // XXX 暂时直接修改当前用户信息
        ClientService.currentUser = u;
        ClientService.mainFrame.updateUserStatus();
    }
}
