package com.crazychat.util;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.sql.rowset.CachedRowSet;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;

import com.sun.rowset.CachedRowSetImpl;

/**
 * 数据库操作的辅助类
 * 
 * @author VisonSun, RuanYaofeng
 * @date 2018-04-17 00:11
 */
public class DBUtil {

    /*
     * 异常信息常量
     */
    private static final String ERROR_READ_PROPERTIES = "读取数据库配置文件失败";
    private static final String ERROR_LOAD_DB_DRIVERS = "加载数据库驱动失败";
    private static final String ERROR_GET_DB_CONNECTION = "获取数据库连接失败";
    private static final String ERROR_RELEASE_RESOURCE = "释放资源失败";
    private static final String ERROR_EXECUTE = "数据库操作失败";
    private static final String ERRER_QUERY = "数据库查询失败";
    private static final String ERROR_ATTRIBUTE_MAPPING = "属性映射失败";

    /** 数据库连接配置 */
    private static Properties properties = null;

    /*
     * 数据库连接参数
     */
    private static String driver = null;
    private static String url = null;
    private static String username = null;
    private static String password = null;

    /**
     * 初始化加载数据库需要的资源
     */
    static {
        try {
            // 读取配置文件
            properties = new Properties();
            properties.load(DBUtil.class.getClassLoader().getResourceAsStream("db.properties"));

            // 获取参数
            driver = properties.getProperty("jdbc.driver");
            url = properties.getProperty("jdbc.url");
            username = properties.getProperty("jdbc.username");
            password = properties.getProperty("jdbc.password");

            // 加载驱动
            Class.forName(driver);
        } catch (IOException e) {
            throw new RuntimeException(ERROR_READ_PROPERTIES, e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(ERROR_LOAD_DB_DRIVERS, e);
        }
    }

    /**
     * 获取连接数据库连接对象
     * 
     * @return 数据库连接对象
     */
    public static Connection getConn() {

        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            throw new RuntimeException(ERROR_GET_DB_CONNECTION, e);
        }
        return conn;
    }

    /**
     * 释放资源
     * 
     * @param rs 结果集
     * @param pstmt PreparedStatement对象
     * @param conn 连接对象
     */
    public static void close(ResultSet rs, PreparedStatement pstmt, Connection conn) {
        // 关闭结果集
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                throw new RuntimeException(ERROR_RELEASE_RESOURCE, e);
            }
        }

        // 关闭PreparedStatement
        if (pstmt != null) {
            try {
                pstmt.close();
            } catch (SQLException e) {
                throw new RuntimeException(ERROR_RELEASE_RESOURCE, e);
            }
        }

        // 关闭连接
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                throw new RuntimeException(ERROR_RELEASE_RESOURCE, e);
            }
        }
    }

    /**
     * 设置操作的参数
     * 
     * @param sql SQL语句
     * @param conn 连接对象
     * @param pstmt PreparedStatement对象
     * @param params SQL语句参数列表
     * @return PreparedStatement对象
     * @throws SQLException
     */
    private static PreparedStatement setPstmt(String sql, Connection conn, PreparedStatement pstmt, Object... params)
            throws SQLException {
        // 通过Connection对象得到PreparedStatement
        pstmt = conn.prepareStatement(sql);
        // 给所有参数赋值
        if (params != null) {
            for (int i = 0; i < params.length; i++) {
                pstmt.setObject(i + 1, params[i]);
            }
        }
        return pstmt;
    }

    /**
     * 执行数据库更新
     * 
     * @param sql SQL语句
     * @param params SQL语句参数列表
     * @return 受影响行数
     */
    public static int execUpdate(String sql, Object... params) {
        Connection conn = getConn();
        PreparedStatement pstmt = null;
        try {
            pstmt = setPstmt(sql, conn, pstmt, params);
            return pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(ERROR_EXECUTE, e);
        } finally {
            close(null, pstmt, conn);
        }
    }

    /**
     * 执行数据库查询<br>
     * 直接返回查询的对象集合, 适用于单表查询
     * 
     * @param sql SQL语句
     * @param cla 要查询的实体类
     * @param params SQL语句参数列表
     * @return 要查询的实体类对象集合
     */
    @SuppressWarnings("rawtypes")
    public static Object execQuery(String sql, Class cla, Object... params) {
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        Connection conn = null;
        List<Object> list = new ArrayList<Object>();
        try {
            conn = getConn();
            pstmt = setPstmt(sql, conn, pstmt, params);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                // 转换结果集为对应实体类对象
                Object object = convert(rs, cla);
                list.add(object);
            }
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(ERRER_QUERY, e);
        } finally {
            close(rs, pstmt, conn);
        }
    }

    /**
     * 执行数据库查询<br>
     * 返回CachedRowSet, 需要手动取值, 适用于多表查询
     * 
     * @param sql 查询语句
     * @param params 参数列表
     * @return 离线结果集CachedRowSet
     */
    public static CachedRowSet execQuery(String sql, Object... params) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        CachedRowSet crs = null;
        try {
            // 实例化CacheRowSet实现类对象
            crs = new CachedRowSetImpl();
            conn = getConn();
            pstmt = setPstmt(sql, conn, pstmt, params);
            // 获取结果集
            rs = pstmt.executeQuery();
            // resultSet转成cachedRowset
            crs.populate(rs);
        } catch (SQLException e) {
            throw new RuntimeException(ERRER_QUERY, e);
        } finally {
            close(rs, pstmt, conn);
        }
        return crs;
    }

    /**
     * 查询结果的转换
     * 
     * @param rs 要转换的结果集
     * @param cla 要转换成为的实体类
     * @return 转换后的实体类对象
     */
    @SuppressWarnings("rawtypes")
    public static Object convert(ResultSet rs, Class cla) {
        try {
            if (cla.getName() == "java.lang.Object") {
                return rs.getObject(1);
            }
            // Date类型可能出现转化错误，所以使用之前先注册实现转化Date方法的Converter给BeanUtils
            ConvertUtils.register(new DateConverter(null), java.util.Date.class);
            Object object = cla.newInstance(); // 创建实体类的实例
            ResultSetMetaData metaData = rs.getMetaData();// 结果集头信息对象
            for (int i = 1; i <= metaData.getColumnCount(); i++) {// 循环为实体类的实例的属性赋值
                String name = metaData.getColumnLabel(i);// 获取列名
                BeanUtils.setProperty(object, name, rs.getObject(i));// 注：列名与属性名必须一致。
            }
            return object;
        } catch (Exception e) {
            throw new RuntimeException(ERROR_ATTRIBUTE_MAPPING, e);
        }
    }

}