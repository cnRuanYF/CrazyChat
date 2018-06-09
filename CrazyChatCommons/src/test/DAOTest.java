package test;

import com.crazychat.dao.FriendDAO;
import com.crazychat.dao.GroupDAO;
import com.crazychat.dao.GroupMemberDAO;
import com.crazychat.dao.MessageDAO;
import com.crazychat.dao.UserDAO;
import com.crazychat.dao.impl.FriendDAOImpl;
import com.crazychat.dao.impl.GroupDAOImpl;
import com.crazychat.dao.impl.GroupMemberDAOImpl;
import com.crazychat.dao.impl.MessageDAOImpl;
import com.crazychat.dao.impl.UserDAOImpl;
import com.crazychat.entity.Group;
import com.crazychat.entity.Message;
import com.crazychat.entity.User;

/**
 * DAO测试类
 * 
 * @author RuanYaofeng
 * @date 2018-04-18 00:18
 */
public class DAOTest {

    /**
     * @param args
     */
    public static void main(String[] args) {
        testFriendDAO();
    }

    /**
     * UserDAO测试
     */
    public static void testUserDAO() {
        UserDAO dao = new UserDAOImpl();
        try {
            // 添加
            System.out.println(dao.add(new User("lalala", "12321")));
            // 修改
            User user = dao.findById(10011);
            user.setNickname("哈哈哈哈");
            System.out.println(dao.update(user));
            // 查询所有
            for (User u : dao.findAll()) {
                System.out.println(u);
            }
            // 精确查询
            for (User u : dao.findByKeyword("的")) {
                System.out.println(u);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * GroupDAO测试
     */
    public static void testGroupDAO() {
        GroupDAO dao = new GroupDAOImpl();
        try {
            // 查所有
            for (Group g : dao.findAll()) {
                System.out.println(g);
            }
            // 添加
            dao.add(new Group("起义军", "项目", 10000));
            // 删除
            dao.delete(new Group(10002));
            // 按id查询群
            System.out.println(dao.findById(10001));
            // 群名模糊查找
            System.out.println(dao.findByName("等"));
            // 查找所有群员
            for (User u : dao.listMembers(10000)) {
                System.out.println(u);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * MessageDAO测试
     */
    public static void testMessageDAO() {
        MessageDAO dao = new MessageDAOImpl();
        try {
//            // 增加一条信息
//            System.out.println(dao.add(new Message(1, 10000, 10001, "aaaaaaaaaa")));
//            // 删除
//            System.out.println(dao.delete(new Message(4)));
//            // 查询全部聊天信息
//            for (Message i : dao.findAll()) {
//                System.out.println(i);
//            }
//            // 按照聊天类型查询
//            for (Message i : dao.findByType(1)) {
//                System.out.println(i);
//            }
//            // 按照发送者id查询聊天信息
//            for (Message i : dao.findBySender(10001)) {
//                System.out.println(i);
//            }
//            // 按照接受者id和信息类型查询聊天信息
//            for (Message i : dao.findByReceiver(0, 10000)) {
//                System.out.println(i);
//            }
//            // 按照发送者和接收者查询聊天信息
//            for (Message i : dao.findBySenderAndReceiver(10000, 1, 10001)) {
//                System.out.println(i);
//            }
            // 
          for (Message i : dao.findAllMessage(10003)) {
              System.out.println(i);
          }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * GroupMemberDAO测试
     */
    public static void testGroupMemberDAO() {
        GroupMemberDAO dao = new GroupMemberDAOImpl();
        try {
            // 添加群成员
            System.out.println(dao.add(new Group(10000), new User(10000, null)));
            // 删除群成员
            System.out.println(dao.delete(new Group(10000), new User(10000, null)));
            // 更新群昵称
            System.out.println(dao.update(new Group(10001), new User(10000, null), "我是管理员"));
            // 查找全部群员
            for (User u : dao.listMembers(new Group(10000))) {
                System.out.println(u);
            }
            // 获取用户的群列表
            for (Group g : dao.listGroups(new User(10003, null))) {
                System.out.println(g);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * FriendDAO测试
     */
    public static void testFriendDAO() {
        FriendDAO dao = new FriendDAOImpl();
        try {
//             添加好友，双向
//            System.out.println(dao.add(10003, 10004));
//            // 删除好友
//            System.out.println(dao.delete(10005, 10004));
            // 查找单条好友信息
            System.out.println(dao.findFriend(10000, 10002));
//            // 修改昵称
//            System.out.println(dao.update(10002, 10003, "bbb"));
//            // 查询该账户所有好友对象
//            for (User u : dao.findAll(10003)) {
//                System.out.println(u);
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
