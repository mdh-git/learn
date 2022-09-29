package com.mdh.mysql.jdbc;

import java.sql.*;

/**
 * @Author: madonghao
 * @Date: 2022/2/25 5:17 下午
 */
public class JdbcTest {
	public static void main(String[] args) {

		try {
			// 1.反射获取mysql驱动实例
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("找不到驱动程序类，加载驱动失败！");
			e.printStackTrace();
		}

		String url = "jdbc:mysql://10.231.139.74:9030/ssb";
		String username = "root";
		String password = "";
		try {
			// 2.驱动实例->Connection
			Connection conn = DriverManager.getConnection(url, username, password);

			// 3.Connection->Statement
			Statement stmt = conn.createStatement();

			// 4.Statement->ResultSet
			String sql = "select * from customer limit 10;";
			ResultSet rs = stmt.executeQuery(sql);

			// 5.通过ResultSet获取数据
			while (rs.next()) {
				System.out.println("c_name"+rs.getString("c_name"));
			}

			// 6.依次关闭ResultSet,Statement,Connection
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					System.out.println("ResultSet关闭时出现错误！");
					e.printStackTrace();
				}
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					System.out.println("Statement关闭时出现错误！");
					e.printStackTrace();
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					System.out.println("Connection关闭时出现错误！");
					e.printStackTrace();
				}
			}

		} catch (SQLException e) {
			System.out.println("数据库连接失败！");
			e.printStackTrace();
		}
	}
}
