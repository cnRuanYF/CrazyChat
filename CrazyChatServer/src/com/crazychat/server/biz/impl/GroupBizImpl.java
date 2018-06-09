package com.crazychat.server.biz.impl;

import java.net.Socket;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.crazychat.dao.GroupDAO;
import com.crazychat.dao.GroupMemberDAO;
import com.crazychat.dao.impl.GroupDAOImpl;
import com.crazychat.dao.impl.GroupMemberDAOImpl;
import com.crazychat.dao.impl.UserDAOImpl;
import com.crazychat.entity.DataPacket;
import com.crazychat.entity.DataPacketSignalEnum;
import com.crazychat.entity.Group;
import com.crazychat.entity.GroupMember;
import com.crazychat.entity.User;
import com.crazychat.server.ServerService;
import com.crazychat.server.biz.GroupBiz;

/**
 * 群相关业务实现类
 * 
 * @author VisonSun
 * @date 2018-04-19 10:10
 */
public class GroupBizImpl implements GroupBiz {

    /*
     * (non-Javadoc)
     * 
     * @see com.crazychat.server.biz.BaseBiz#handleRequest(java.net.Socket,
     * com.crazychat.entity.DataPacket)
     */
    @Override
    public void handleRequest(Socket socket, DataPacket packet) {
        switch (packet.getSignal()) {
        case FIND_GROUP:
            findGroup(socket, (String) packet.getContent());
            break;
        case GET_GROUP_PROFILE:
            getProfile(socket, (int) packet.getContent());
            break;
        case UPDATE_GROUP_PROFILE:
            updateProfile(socket, (Group) packet.getContent());
            break;
        case LIST_GROUPS:
            User user = new User((int) packet.getContent(), null);
            listGroups(socket, user);
            break;
        case CREATE_GROUP:
            createGroup(socket, (Group) packet.getContent());
            break;
        case JOIN_GROUP:
            joinGroup(socket, (GroupMember) packet.getContent());
            break;
        case EXIT_GROUP:
            exitGroup(socket, (GroupMember) packet.getContent());
            break;
        case KICK_OUT_GROUP:
            kickoutGroup(socket, (GroupMember) packet.getContent());
            break;

        default:
            break;
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.crazychat.server.biz.GroupBiz#listGroups()
     */
    @Override
    public void listGroups(Socket socket, User user) {
        DataPacket packet = new DataPacket();
        packet.setSignal(DataPacketSignalEnum.LIST_GROUPS);

        // 数据库操作
        List<Group> listGroup;
        GroupMemberDAO dao = new GroupMemberDAOImpl();

        try {
            listGroup = dao.listGroups(user);
        } catch (Exception e) {
            // TODO 异常！DAO异常提示优化
            packet.setSuccess(false);
            packet.setMessage(e.getMessage());
            sendResponse(socket, packet);
            return;
        }

        if (listGroup == null) {
            // 若群列表为空,则操作失败并返回信息
            packet.setSuccess(false);
            packet.setMessage("获取失败或者还没有群");// XXX 常量
        } else {
            // 若群列表不为空,则存入数据包并发送响应
            packet.setSuccess(true);
            packet.setContent(listGroup);
        }
        sendResponse(socket, packet);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.crazychat.server.biz.GroupBiz#findGroup(java.net.Socket,
     * java.lang.String)
     */
    @Override
    public void findGroup(Socket socket, String keyword) {
        DataPacket packet = new DataPacket();
        packet.setSignal(DataPacketSignalEnum.FIND_GROUP);

        // 数据库操作
        List<Group> listGroup;
        GroupDAO dao = new GroupDAOImpl();

        try {
            listGroup = dao.findBykeyword(keyword);
        } catch (Exception e) {
            // TODO 异常！DAO异常提示优化
            packet.setSuccess(false);
            packet.setMessage(e.getMessage());
            sendResponse(socket, packet);
            return;
        }

        if (listGroup == null) {
            packet.setSuccess(false);
            packet.setMessage("获取失败或者没有符合查询的群");// XXX 常量
        } else {
            packet.setSuccess(true);
            packet.setContent(listGroup);
        }
        sendResponse(socket, packet);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.crazychat.server.biz.GroupBiz#getProfile(java.net.Socket,
     * java.lang.Integer)
     */
    @Override
    public void getProfile(Socket socket, Integer groupId) {
        DataPacket packet = new DataPacket();
        packet.setSignal(DataPacketSignalEnum.GET_GROUP_PROFILE);

        // 数据库操作
        try {
            GroupDAO dao = new GroupDAOImpl();
            Group group = dao.findById(groupId);

            // 若操作失败
            if (group == null) {
                packet.setSuccess(false);
                packet.setMessage("获取群资料失败或者该群不存在");// XXX 常量
            }
            // 若成功, 响应一个group对象
            else {
                packet.setSuccess(true);
                packet.setContent(group);
            }
            sendResponse(socket, packet);

        } catch (Exception e) {
            // TODO 异常！DAO异常提示优化
            packet.setSuccess(false);
            packet.setMessage(e.getMessage());
            sendResponse(socket, packet);
            return;
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.crazychat.server.biz.GroupBiz#updateProfile(java.net.Socket,
     * com.crazychat.entity.Group)
     */
    @Override
    public void updateProfile(Socket socket, Group group) {
        DataPacket packet = new DataPacket();
        packet.setSignal(DataPacketSignalEnum.UPDATE_GROUP_PROFILE);

        boolean flag = false;

        try {
            // 数据库操作
            GroupDAO dao = new GroupDAOImpl();
            flag = dao.update(group);

            // 若修改失败
            if (!flag) {
                packet.setSuccess(false);
                packet.setMessage("修改失败或者该群不存在");// XXX 常量
                sendResponse(socket, packet);
            }

            // 若修改成功, 发送给所有在线成员该群的对象
            else {
                packet.setSuccess(true);
                // 找群该群成员
                List<User> memberList = dao.listMembers(group.getId());
                // 把群对象放入数据包
                packet.setContent(group);

                // 获取并遍历在线用户ID的集合
                Set<Integer> loginId = ServerService.onlineUserSockets.keySet();
                Iterator<Integer> iteratorId = loginId.iterator();
                // 循环在线用户
                while (iteratorId.hasNext()) {
                    // 循环群员
                    for (User members : memberList) {
                        // 满足条件 = 在线 の 群员
                        if (iteratorId.next() == members.getId()) {
                            // 向所有在线群员发送群对象的数据包
                            sendResponse(ServerService.onlineUserSockets.get(members.getId()), packet);
                        }
                    }
                }
            }
        } catch (Exception e) {
            // TODO 异常！DAO异常提示优化
            packet.setSuccess(false);
            packet.setMessage(e.getMessage());
            sendResponse(socket, packet);
            return;
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.crazychat.server.biz.GroupBiz#createGroup(java.net.Socket,
     * com.crazychat.entity.Group)
     */
    @Override
    public void createGroup(Socket socket, Group group) {
        DataPacket packet = new DataPacket();
        packet.setSignal(DataPacketSignalEnum.CREATE_GROUP);

        try {
            boolean flag = false;
            // 数据库操作
            GroupDAO dao = new GroupDAOImpl();
            flag = dao.add(group);

            if (!flag) {
                packet.setSuccess(false);
                packet.setMessage("创建失败或者已有该群");// XXX 常量
            } else {
                packet.setSuccess(true);
                packet.setContent(group);
            }
            sendResponse(socket, packet);

        } catch (Exception e) {
            // TODO 异常！DAO异常提示优化
            packet.setSuccess(false);
            packet.setMessage(e.getMessage());
            sendResponse(socket, packet);
            return;
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.crazychat.server.biz.GroupBiz#joinGroup(java.net.Socket,
     * com.crazychat.entity.GroupMember)
     */
    @Override
    public void joinGroup(Socket socket, GroupMember groupMember) {
        DataPacket packet = new DataPacket();
        packet.setSignal(DataPacketSignalEnum.JOIN_GROUP);

        // 数据库操作
        boolean flag = false;
        try {
            GroupMemberDAO dao = new GroupMemberDAOImpl();
            // 获取用户对象和群对象
            User user = groupMember.getMemberUser();
            Group group = new GroupDAOImpl().findById(groupMember.getGroupId());
            flag = dao.add(group, user);

            if (!flag) {
                packet.setSuccess(false);
                packet.setMessage("加入失败或者没有找到该群");// XXX 常量
                sendResponse(socket, packet);
            }
        } catch (Exception e) {
            // TODO 异常！DAO异常提示优化
            packet.setSuccess(false);
            packet.setMessage(e.getMessage());
            sendResponse(socket, packet);
            return;
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.crazychat.server.biz.GroupBiz#exitGroup(java.net.Socket,
     * com.crazychat.entity.GroupMember)
     */
    @Override
    public void exitGroup(Socket socket, GroupMember groupMember) {
        DataPacket packet = new DataPacket();
        packet.setSignal(DataPacketSignalEnum.EXIT_GROUP);

        // 数据库操作
        boolean flag = false;

        try {
            GroupMemberDAO dao = new GroupMemberDAOImpl();
            // 获取用户对象和群对象
            Group group = new GroupDAOImpl().findById(groupMember.getGroupId());
            User user = groupMember.getMemberUser();
            flag = dao.delete(group, user);

            if (!flag) {
                packet.setSuccess(false);
                packet.setMessage("退出失败或者没加入该群");// XXX 常量
                sendResponse(socket, packet);
            }
        } catch (Exception e) {
            // TODO 异常！DAO异常提示优化
            packet.setSuccess(false);
            packet.setMessage(e.getMessage());
            sendResponse(socket, packet);
            return;
        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.crazychat.server.biz.GroupBiz#kickoutGroup(java.net.Socket,
     * com.crazychat.entity.GroupMember)
     */
    @Override
    public void kickoutGroup(Socket socket, GroupMember groupMember) {
        DataPacket packet = new DataPacket();
        packet.setSignal(DataPacketSignalEnum.KICK_OUT_GROUP);

        try {
            boolean flag = false;
            // 数据库操作
            GroupMemberDAO dao = new GroupMemberDAOImpl();
            // 获取群和用户对象传入delete
            Group group = new GroupDAOImpl().findById(groupMember.getGroupId());
            User user = new UserDAOImpl().findById(groupMember.getMemberUser().getId());
            flag = dao.delete(group, user);

            // 如果失败
            if (!flag) {
                packet.setSuccess(false);
                packet.setMessage("操作失败");// XXX 常量
                sendResponse(socket, packet);
            }

            // 如果成功
            else {
                packet.setSuccess(true);
                // 找出在线用户，遍历该用户是否在线，在线则相应群ID
                // 获取并遍历在线用户ID的集合
                Set<Integer> loginId = ServerService.onlineUserSockets.keySet();
                Iterator<Integer> iteratorId = loginId.iterator();
                // 循环在线用户
                while (iteratorId.hasNext()) {
                    // 若该被T的用户在线, 给其一个群ID响应
                    if (iteratorId.next() == groupMember.getMemberUser().getId()) {
                        packet.setContent(groupMember.getGroupId());
                        sendResponse(ServerService.onlineUserSockets.get(groupMember.getMemberUser().getId()), packet);
                        break;
                    }
                }
            }
        } catch (Exception e) {
            // TODO 异常！DAO异常提示优化
            packet.setSuccess(false);
            packet.setMessage(e.getMessage());
            sendResponse(socket, packet);
            return;
        }

    }
}
