package com.duan.javastuff.dbpool;

import com.duan.javastuff.P;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created on 2017/12/7.
 *
 * @author DuanJiaNing
 */
public class Test {

    public static void main(String[] args) {

        P.out("测试结果 " + new Test().test1(100));

    }


    public int test1(int times) {
        C3p0ConnectionManager instance = C3p0ConnectionManager.getInstance();
        Set<Connection> sets = new TreeSet<>((o1, o2) -> o1 == o2 ? 0 : 1);
        for (int i = 0; i < times; i++) {
            sets.add(instance.getConnection());
        }

        return sets.size();
    }

    public void test() {

        Connection connection = C3p0ConnectionManager.getInstance().getConnection();
        PreparedStatement statement = null;
        ResultSet set = null;
        try {
            statement = connection.prepareStatement("SELECT * FROM wife");
            set = statement.executeQuery();
            while (set.next()) {
                P.out(set.getString("wife_name"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            if (set != null) {
                try {
                    set.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }


    }
}
