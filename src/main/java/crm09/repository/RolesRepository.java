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
}
