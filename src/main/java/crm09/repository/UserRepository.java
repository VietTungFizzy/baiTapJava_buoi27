package crm09.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import crm09.config.MySQLConfig;
import crm09.entity.Role;
import crm09.utils.MD5Helper;

public class UserRepository {
	public int save(String email, String password, int roleId, String fullName, String phone) {
		String query = "INSERT INTO users(email, password, id_role, fullname, phone)" +
				"VALUES(?, ?, ?, ?, ?)";
		
		Connection connection = MySQLConfig.getConnection();
		int count = 0;
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, email);
			statement.setString(2, MD5Helper.getMd5(password));
			statement.setInt(3, roleId);
			statement.setString(4, fullName);
			statement.setString(5, phone);
			count = statement.executeUpdate();
		} catch(Exception e) {
			System.out.println("Lỗi save " + e.getMessage());
		}
		
		return count;
	}
}
