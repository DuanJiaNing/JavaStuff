package com.duan.javastuff.dbpool;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created on 2017/12/7.
 *
 * @author DuanJiaNing
 */
public class C3p0ConnectionManager {

    private ComboPooledDataSource dataSource;
    private static volatile C3p0ConnectionManager manager;

    public static C3p0ConnectionManager getInstance() {
        if (manager == null) {
            synchronized (C3p0ConnectionManager.class) {
                if (manager == null) {
                    try {
                        manager = new C3p0ConnectionManager();
                    } catch (PropertyVetoException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return manager;
    }

    private C3p0ConnectionManager() throws PropertyVetoException {
        dataSource = new ComboPooledDataSource();

        dataSource.setUser("root");
        dataSource.setPassword("root");
        dataSource.setJdbcUrl("jdbc:mysql://127.0.0.1:3306/test");
        dataSource.setDriverClass("com.mysql.jdbc.Driver");
        dataSource.setInitialPoolSize(5);
        dataSource.setMinPoolSize(1);
        dataSource.setMaxPoolSize(10);
        dataSource.setMaxStatements(50);
        dataSource.setMaxIdleTime(60);//最大空闲时间，单位毫秒
    }

    public synchronized Connection getConnection() {
        Connection conn = null;
        try {
            conn = dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

}
