package com.crazychat.dao;

import java.util.List;

/**
 * 基础的数据访问类接口
 * 
 * @author RuanYaofeng
 * @date 2018年4月15日
 */
public interface BaseDAO<T, U> {

    /**
     * 添加该对象
     * 
     * @param obj 要添加的对象
     * @return 操作是否成功
     * @throws Exception 处理过程中发生错误均抛出异常到业务层处理
     */
    boolean add(T obj) throws Exception;

    /**
     * 删除该对象
     * 
     * @param obj 要删除的对象
     * @return 操作是否成功
     * @throws Exception 处理过程中发生错误均抛出异常到业务层处理
     */
    boolean delete(T obj) throws Exception;

    /**
     * 更新该对象
     * 
     * @param obj 要修改的对象
     * @return 操作是否成功
     * @throws Exception 处理过程中发生错误均抛出异常到业务层处理
     */
    boolean update(T obj) throws Exception;

    /**
     * 查找所有该对象的数据
     * 
     * @return 该对象的所有数据的集合
     * @throws Exception 处理过程中发生错误均抛出异常到业务层处理
     */
    List<T> findAll() throws Exception;

    /**
     * 根据ID查找对象
     * 
     * @param id 对象的ID
     * @return 查找到的对象
     * @throws Exception 处理过程中发生错误均抛出异常到业务层处理
     */
    T findById(U id) throws Exception;
}
