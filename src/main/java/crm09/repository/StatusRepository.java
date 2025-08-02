package crm09.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import crm09.config.MySQLConfig;
import crm09.entity.Status;

public class StatusRepository {
	public List<Status> findAll() {
		String query = "SELECT * FROM status s";
		Connection connection = MySQLConfig.getConnection();
		
		List<Status> listStatus = new ArrayList<Status>();
		
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			ResultSet resultSet = statement.executeQuery();
			
			while(resultSet.next()) {
				Status status = new Status();
				status.setId(resultSet.getInt("id"));
				status.setName(resultSet.getString("name"));
				
				listStatus.add(status);
			}
		} catch(Exception e) {
			System.out.println("lỗi findAll status: " + e.getMessage());
		}
		
		return listStatus;
	}
}
