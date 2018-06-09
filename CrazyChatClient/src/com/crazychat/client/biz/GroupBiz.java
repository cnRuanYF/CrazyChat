package com.crazychat.client.biz;

/**
 * 群相关业务接口
 * 
 * @author RuanYaofeng
 * @date 2018-04-17 14:00
 */
public interface GroupBiz extends BaseBiz {

    /**
     * 获取群列表
     */
    void listGroups();

    /**
     * 获取群资料
     * 
     * @param groupId 群ID
     */
    void getGroupProfile(int groupId);
}
