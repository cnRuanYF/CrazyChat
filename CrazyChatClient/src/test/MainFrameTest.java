package test;

import java.util.ArrayList;
import java.util.Date;

import com.crazychat.client.ClientService;
import com.crazychat.entity.Group;
import com.crazychat.entity.Message;
import com.crazychat.entity.User;

/**
 * 用于测试客户端主界面的测试类
 * 
 * @author RuanYaofeng
 * @date 2018-04-19 12:55
 */
public class MainFrameTest {

    public static void main(String[] args) {

        /*
         * 创建测试用户
         */
        User user = new User();
        user.setId(666);
        user.setNickname("用户Nickname");
        user.setBirthday(new Date());
        ClientService.currentUser = user;

        ClientService.dialogs = new ArrayList<>();
        ClientService.friends = new ArrayList<>();
        ClientService.groups = new ArrayList<>();
        ClientService.messages = new ArrayList<>();
        
        for (int i = 0; i < 10; i++) {
            User u = new User();
            u.setId(i);
            u.setPictureId(i * 8);
            u.setNickname((char) ('A' + i) + "用户昵称");
            u.setIntroduction((char) ('A' + i) + (char) ('b' + i) + (char) ('c' + i) + "用户Introduction介绍" + i
                    + "很长很长很长Solonglonglonglonglong Verylonglonglong");
            ClientService.friends.add(u);
            System.out.println("Test> " + u);

            Group g = new Group();
            g.setId(i);
            g.setPictureId(i);
            g.setGroupName((char) ('A' + i) + "群名称");
            g.setGroupDesc((char) ('A' + i) + (char) ('b' + i) + (char) ('c' + i) + "群Introduction介绍" + i
                    + "很长很长很长Solonglonglonglonglong Verylonglonglong");
            ClientService.groups.add(g);
            System.out.println("Test> " + g);

        }

        for (int i = 0; i < 100; i++) {
            Message m = new Message();
            m.setSenderId(i % 2 == 0 ? ClientService.currentUser.getId() : i / 16);
            m.setMessageType(i % 4 == 1 || i % 4 == 2 ? Message.TYPE_FRIEND : Message.TYPE_GROUP);
            m.setReceiverId(i / 16);
            m.setMessageContent((char) ('A' + i) + (char) ('b' + i) + (char) ('c' + i) + "消息内容" + i
                    + "很长很长很长Solonglonglonglonglong Verylonglonglong");
            ClientService.messages.add(m);
            System.out.println("Test> " + m);
        }

        // 显示主界面
        ClientService.showMainFrame();
    }

}
