# CrazyChat Socket 通信文档

[TOC]

版本|修改者|日期|备注
-|-|-|-
1.0|RuanYF|2018.4.18|第一版
1.1|RuanYF|2018.4.18|遗漏补充
1.2|RuanYF|2018.4.23|增加查找所有消息指令

## DataPacket

是为了方便传输而定义的一个类，用于打包 C/S 之间的Socket通信 请求/响应 所需的数据

### 字段说明

* `DataPacketSignalEnum signal`

  信号类型，表示业务类型（详见下表）

* `Object content`

  数据包内容，根据具体业务需求设置对应对象（详见下表中的 `C请求内容` 列）

* `boolean success`

  用于服务器响应，表示请求操作是否成功

* `String message`

  用于服务器响应，描述请求操作不成功的原因

### 信号枚举&对应的内容对象类型

* `S响应内容` 指的是**操作成功**的响应
* ~~若响应内容类型为 `List<>` 则无结果也要返回空的集合，而不是null~~

#### 登录相关

Signal|业务|C请求内容|S响应内容|备注
-|-|-|-|-
~~USER_REGISTER~~| ~~注册~~ |||
USER_LOGIN| 登录|`User` (参数: username, password)|`User` 完整的User对象|
~~USER_LOGOUT~~| ~~注销~~ |~~`Integer` 用户ID~~||~~注销后，**客户端**主动关闭/返回登陆界面，**服务端**移除该用户的在线状态及关闭Socket连接，无需响应~~ **客户端**主动关闭Socket后，**服务端**可能发生异常，需要正确处理并移除该用户的在线状态
~~USER_CHANGE_STATUS~~| ~~更改状态~~ |||

#### 用户相关

Signal|业务|C请求内容|S响应内容|备注
-|-|-|-|-
FIND_USER| 查找用户 |`String` 关键字|`List<User>` 用户对象集合|关键字以字母开头则根据用户名精确查找，否则根据ID精确查找，都没找到再根据昵称模糊查询
GET_USER_PROFILE| 获取用户资料 |`Integer` 用户ID|`User` 完整的用户资料|
UPDATE_USER_PROFILE| 更新用户资料 |`User` 新的用户资料||操作成功后，响应给所有**在线**好友新的`User`对象

#### 好友相关

Signal|业务|C请求内容|S响应内容|备注
-|-|-|-|-
LIST_FRIENDS| 获取好友列表|`Integer` 用户ID|`List<Friend>` 好友对象集合|
ADD_FRIEND| 添加好友|`Friend` (参数: userId, friendUser.id)|`Friend` 完整的Friend对象|操作成功后，若对方在线，同时响应被添加用户，给对方发送**对方视角的`Friend`对象**）
DELETE_FRIEND| 删除好友|`Friend` (参数: userId, friendUser.id)||操作成功后，若对方在线，同时给被删的好友响应 `Integer` 删除者用户ID
REMARK_FRIEND| 备注好友|`Friend` 参数: userId, friendUser.id, friendRemark||

#### 群相关

（优先实现消息业务，再实现此类业务）

Signal|业务|C请求内容|S响应内容|备注
-|-|-|-|-
FIND_GROUP| 查找群|`String` 关键字|`List<Group>` 群对象集合|根据ID精确查找，没找到再根据群名模糊查询
GET_GROUP_PROFILE| 获取群资料|`Integer` 群ID|`Group` 完整的群对象|
UPDATE_GROUP_PROFILE| 更新群资料|`Group` 群对象||操作成功后，响应给所有该群**在线**成员新的 `Group` 对象
LIST_GROUPS| 获取群列表|`Integer` 用户ID|`List<Group>` 群对象集合|
CREATE_GROUP| 创建群|`Group` 群对象 (最小参数:groupName, creatorId)|`Group` 完整的群对象|
JOIN_GROUP| 加入群|`GroupMember` (参数: groupId, memberUser.id)||
EXIT_GROUP| 退出群|`GroupMember` (参数: groupId, memberUser.id)||
KICK_OUT_GROUP| 踢出群|`GroupMember` (参数: groupId, memberUser.id)||操作成功后，若对方在线，同时给被踢的用户响应 `Integer` 群ID

#### 消息相关

（优先实现私聊，最后实现群聊）

Signal|业务|C请求内容|S响应内容|备注
-|-|-|-|-
SEND_MESSAGE| 发送消息|`Message` 消息对象||若消息为私聊消息且对方在线，同时发送响应给对方该 `Message` 消息对象；若为群聊消息，则同时发送响应 `Message` 消息对象给**所有在线的群成员**（不包括发送者）
LIST_MESSAGE_RECORDS| 获取消息记录|`Message` 消息对象 (参数: messageType, receiverId)|`List<Message>` 消息对象集合|根据请求的 `Message`  消息对象的**消息类型**以及**接收者ID**查找消息记录
LIST_ALL_MESSAGE_RECORD| 获取所有消息记录 |`Integer` 用户ID|`List<Message>` 消息对象集合|根据**用户ID**查找所有该用户**发送的消息**、**接收的消息**，以及该用户**加入的群消息** (由于数据量庞大，仅作为初期测试用，后续使用未读消息替代)