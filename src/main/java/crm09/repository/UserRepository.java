package crm09.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import crm09.config.MySQLConfig;
import crm09.entity.Role;
import crm09.entity.User;
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
	
	public List<User> findAll() {
		String query = "SELECT * "
				+ "FROM users u "
				+ "JOIN roles r ON u.id_role = r.id";
		Connection connection = MySQLConfig.getConnection();
		List<User> listUser = new ArrayList<User>();
		List<Role> listRole = new ArrayList<Role>();
		
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			
			ResultSet resultSet = statement.executeQuery();
			
			while(resultSet.next()) {
				int roleId = resultSet.getInt("id_role");
				Role role = listRole.stream().filter(_role -> roleId == _role.getId()).findAny().orElse(null);
				if(role == null) {
					role = new Role();
					role.setId(resultSet.getInt("id_role"));
					role.setName(resultSet.getString("name"));
					role.setDescription(resultSet.getString("description"));
					listRole.add(role);
				}
				
				User user = new User();
				user.setId(resultSet.getInt("id"));
				user.setEmail(resultSet.getString("email"));
				user.setFullname(resultSet.getString("fullname"));
				user.setRoles(role);
				listUser.add(user);
			}
		} catch(Exception e) {
			System.out.println("Lỗi findAll user: " + e.getMessage());
		}
		
		return listUser;
	}
	
	public User get(int id) {
		String query = "SELECT * FROM users u JOIN roles r ON u.id_role = r.id WHERE u.id=?";
		Connection connection = MySQLConfig.getConnection();
		User user = new User();
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setInt(1, id);
			
			ResultSet resultSet = statement.executeQuery();
			
			while(resultSet.next()) {
				Role role = new Role();
				role.setId(resultSet.getInt("id_role"));
				role.setName(resultSet.getString("name"));
				role.setDescription(resultSet.getString("description"));
				
				user.setId(resultSet.getInt("id"));
				user.setEmail(resultSet.getString("email"));
				user.setFullname(resultSet.getString("fullname"));
				user.setPhone(resultSet.getString("phone"));
				user.setPassword(resultSet.getString("password"));
				user.setRoles(role);
			}
		} catch(Exception e) {
			System.out.println("Lỗi get user: "+ e.getMessage());
		}
		return user;
	}
	
	public int update(int id, String email, String password, int idRole, String fullname, String phone) {
		String query = "UPDATE users "
				+ "SET email=?, password=?, id_role=?, fullname=?, phone=? "
				+ "WHERE id = ?";
		
		Connection connection = MySQLConfig.getConnection();
		int count = 0;
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, email);
			statement.setString(2, password);
			statement.setInt(3, idRole);
			statement.setString(4, fullname);
			statement.setString(5, phone);
			statement.setInt(6, idRole);
			
			count = statement.executeUpdate();
		} catch(Exception e) {
			System.out.println("Lỗi update user: " + e.getMessage());
		}
		
		return count;
	}
	
	public int delete(int id) {
		String query = "DELETE FROM users WHERE id = ?";
		
		Connection connection = MySQLConfig.getConnection();
		int count = 0;
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setInt(1, id);
			
			count = statement.executeUpdate();
		} catch(Exception e) {
			System.out.println("Lỗi delete user: " + e.getMessage());
		}
		return count;
	}
}
