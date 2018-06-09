<h1 align="center">CrazyChat／疯聊</h1>
<p align="center"><img src="ScreenShots/Logo.png"/></p>

[![license](https://img.shields.io/github/license/mashape/apistatus.svg?style=for-the-badge)](LICENSE)

本项目是基于Java SE开发的即时通讯应用

GUI使用Swing实现，通过Socket实现客户端与服务端之间的通信

工程目录结构为Eclipse项目结构

## 项目特性／Features

##### 已实现功能

* 用户注册／登录／修改资料
* 查找／查看用户资料、添加好友、私聊
* 好友／群／会话列表
* 气泡式聊天界面
* 可拖拽改变窗口／分栏大小

##### 待实现的功能

* 查找／添加群、群聊
* 新消息提示

##### 存在的问题

* 在Windows7 Basic主题下窗口阴影显示异常，窗口大小拖拽异常
* 接收到消息时，消息记录无法滚动到最底部
* 由于Swing嵌套布局效率问题，消息数量较多时，调整窗口卡顿严重

## 目录说明／Directories

| 目录             | 说明                                        |
| ---------------- | ------------------------------------------- |
| CrazyChatClient  | 客户端工程（依赖通用模块）                  |
| CrazyChatCommons | 通用模块工程（客户端服务端Web端共用的代码） |
| CrazyChatServer  | 服务端工程（依赖通用模块）                  |
| CrazyChatWeb     | Web端工程（用于用户注册，依赖通用模块）     |
| DatabaseDesign   | 数据库设计相关文件                          |
| Documents        | 项目相关文档                                |
| ScreenShots      | 项目截图                                    |

## 项目截图／ScreenShots

### Web端

![B-1-1_Register](ScreenShots/B-1-1_Register.png)

![B-1-2_Reg-Failed](ScreenShots/B-1-2_Reg-Failed.png)

![B-1-3_Reg-Success](ScreenShots/B-1-3_Reg-Success.png)

### 客户端

![C-1-1_Login](ScreenShots/C-1-1_Login.png)

![C-1-2_Logining](ScreenShots/C-1-2_Logining.gif)

![C-1-2_Logining](ScreenShots/C-1-2_Logining.png)

![C-1-3_Login-Failed-1](ScreenShots/C-1-3_Login-Failed-1.png)

![C-1-4_Login-Failed-2](ScreenShots/C-1-4_Login-Failed-2.png)

![C-1-5_Login-Failed-3](ScreenShots/C-1-5_Login-Failed-3.png)

![C-2-1_MainFrame](ScreenShots/C-2-1_MainFrame.png)

![C-2-2_Chat-1](ScreenShots/C-2-2_Chat-1.png)

![C-2-3_Chat-2](ScreenShots/C-2-3_Chat-2.png)

![C-3-1_Friends](ScreenShots/C-3-1_Friends.png)

![C-3-2_Friend-Profile](ScreenShots/C-3-2_Friend-Profile.png)

![C-3-3_Groups](ScreenShots/C-3-3_Groups.png)

![C-3-4_Group-Profile](ScreenShots/C-3-4_Group-Profile.png)

![C-4-1_Search-User](ScreenShots/C-4-1_Search-User.png)

![C-4-2_Searching](ScreenShots/C-4-2_Searching.png)

![C-4-3_Search-Failed](ScreenShots/C-4-3_Search-Failed.png)

![C-4-4_Search-Result](ScreenShots/C-4-4_Search-Result.png)

![C-5_Edit-Profile](ScreenShots/C-5_Edit-Profile.png)

![C-6-1_Adaptive-Layout-1](ScreenShots/C-6-1_Adaptive-Layout-1.png)

![C-6-2_Adaptive-Layout-2](ScreenShots/C-6-2_Adaptive-Layout-2.png)

### 服务端

![S-1-1_Server-Stopped](ScreenShots/S-1-1_Server-Stopped.png)

![S-1-2_Server-Running](ScreenShots/S-1-2_Server-Running.png)

![S-2_Online-Users](ScreenShots/S-2_Online-Users.png)

![S-3_Users](ScreenShots/S-3_Users.png)

![S-4_Logs](ScreenShots/S-4_Logs.png)