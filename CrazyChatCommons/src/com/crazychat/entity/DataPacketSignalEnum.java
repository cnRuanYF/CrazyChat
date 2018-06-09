package com.crazychat.entity;

/**
 * 网络通信数据包的信号类型枚举
 * 
 * @author RuanYaofeng
 * @date 2018-04-15 01:24
 */
public enum DataPacketSignalEnum {
    /*
     * 用户操作相关
     */
    USER_REGISTER, // 注册
    USER_LOGIN, // 登录
    USER_LOGOUT, // 注销
    USER_CHANGE_STATUS, // 更改状态

    /*
     * 用户操作相关
     */
    FIND_USER, // 查找用户
    GET_USER_PROFILE, // 获取用户资料
    UPDATE_USER_PROFILE, // 更新用户资料

    /*
     * 好友操作相关
     */
    LIST_FRIENDS, // 获取好友列表
    ADD_FRIEND, // 添加好友
    DELETE_FRIEND, // 删除好友
    REMARK_FRIEND, // 备注好友

    /*
     * 群操作相关
     */
    FIND_GROUP, // 查找群
    GET_GROUP_PROFILE, // 获取群资料
    UPDATE_GROUP_PROFILE, // 更新群资料
    LIST_GROUPS, // 获取群列表
    CREATE_GROUP, // 创建群
    JOIN_GROUP, // 加入群
    EXIT_GROUP, // 退出群
    KICK_OUT_GROUP, // 踢出群

    /*
     * 聊天消息相关
     */
    SEND_MESSAGE, // 发送消息
    LIST_MESSAGE_RECORDS, // 获取消息记录
    LIST_ALL_MESSAGE_RECORDS, // 获取所有消息记录

}
