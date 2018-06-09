package test;

import com.crazychat.util.EncryptionUtils;

/**
 * 加密工具测试类
 * 
 * @author RuanYaofeng
 * @date 2018-04-15 15:19
 */
public class EncryptionUtilsTest {

    /**
     * @param args
     */
    public static void main(String[] args) {
//      String str = new String("RuanYaofeng");
      String str = new String("123456");
        System.out.println("Original：" + str);
        System.out.println("MD5：" + EncryptionUtils.getMD5(str));
        System.out.println("SHA-1：" + EncryptionUtils.getSHA1(str));
    }

}
