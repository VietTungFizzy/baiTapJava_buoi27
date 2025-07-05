package crm09.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import crm09.config.MySQLConfig;
import crm09.entity.Role;

public class RolesRepository {
	public List<Role> findAll() {
		List<Role> listRole = new ArrayList<Role>();
		
		String query = "SELECT * FROM roles";
		
		Connection connection = MySQLConfig.getConnection();
		
		try {
			PreparedStatement statement = connection.prepareStatement(query);

			ResultSet resultSet = statement.executeQuery();
			
			while(resultSet.next()) {
				Role role = new Role();
				role.setId(resultSet.getInt("id"));
				role.setName(resultSet.getString("name"));
				role.setDescription(resultSet.getString("description"));
				
				listRole.add(role);
			}
		} catch(Exception e) {
			System.out.println("Lỗi findAll " + e.getMessage());
		}
		
		return listRole;
	}
	
	public int save(String name, String description) {
		String query = "INSERT INTO roles(name, description) "
				+ "VALUES(?, ?)";
		int count = 0;
		Connection connection = MySQLConfig.getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, name);
			statement.setString(2, description);
			count = statement.executeUpdate();
		} catch(Exception e) {
			System.out.println("Lỗi add role " + e.getMessage());
		}
		return count;
	}
	
	public Role get(int id) {
		String query = "SELECT * "
				+ "FROM roles r "
				+ "WHERE r.id = ?";
		Connection connection = MySQLConfig.getConnection();
		Role role = new Role();
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setInt(1, id);
			ResultSet resultSet = statement.executeQuery();
			
			while(resultSet.next()) {
				role.setId(resultSet.getInt("id"));
				role.setName(resultSet.getString("name"));
				role.setDescription(resultSet.getString("description"));
			}
		} catch(Exception e) {
			System.out.println("Lỗi get role " + e.getMessage());
		}
		
		return role;
	}
	
	public int update(int id, String name, String description) {
		String query = "UPDATE roles "
				+ "SET name=?, description=? "
				+ "WHERE id=?";
		int count = 0;
		Connection connection = MySQLConfig.getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, name);
			statement.setString(2, description);
			statement.setInt(3, id);

			count = statement.executeUpdate();
		} catch(Exception e) {
			System.out.println("Lỗi update role " + e.getMessage());
		}
		return count;
	}
	
	public int delete(int id) {
		String query = "DELETE FROM roles WHERE id = ?";
		int count = 0;
		
		Connection connection = MySQLConfig.getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setInt(1, id);
			count = statement.executeUpdate();
		} catch(Exception e) {
			System.out.println("Lỗi delete role " + e.getMessage());
		}
		return count;
	}
}
