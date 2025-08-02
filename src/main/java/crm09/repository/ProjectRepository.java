package crm09.repository;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import crm09.config.MySQLConfig;
import crm09.entity.Project;

public class ProjectRepository {
	public List<Project> findAll() {
		String query = "SELECT p.*, s.name as status \n"
				+ "FROM projects p\n"
				+ "JOIN status s ON p.id_status = s.id";
		Connection connection = MySQLConfig.getConnection();
		List<Project> listProjects = new ArrayList<Project>();
		
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			ResultSet resultSet = statement.executeQuery();
			
			while(resultSet.next()) {
				Project project = new Project();
				project.setId(resultSet.getInt("id"));
				project.setName(resultSet.getString("name"));
				project.setStartDate(resultSet.getDate("start_day"));
				project.setEndDate(resultSet.getDate("end_day"));
				project.setStatus(resultSet.getString("status"));
				
				listProjects.add(project);
			}
		} catch(Exception e) {
			System.out.println("lỗi findAll projects: " + e.getMessage());
		}
		
		return listProjects;
	}
	
	public Project get(int id) {
		String query = "SELECT p.*, s.name as status \n"
				+ "FROM projects p\n"
				+ "JOIN status s ON p.id_status = s.id\n"
				+ "WHERE p.id = ?";
		Connection connection = MySQLConfig.getConnection();
		Project project = new Project();
		
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setInt(1, id);
			
			ResultSet resultSet = statement.executeQuery();
			
			while(resultSet.next()) {
				project.setId(resultSet.getInt("id"));
				project.setName(resultSet.getString("name"));
				project.setStartDate(resultSet.getDate("start_day"));
				project.setEndDate(resultSet.getDate("end_day"));
				project.setStatus(resultSet.getString("status"));
			}
		} catch(Exception e) {
			System.out.println("lỗi get project: " + e.getMessage());
		}
		
		return project;
	}
	
	public int save(String name, Date startDate, Date endDate) {
		String query = "INSERT INTO projects(name, start_day, end_day)"
				+ "VALUES(?, ?, ?)";
		Connection connection = MySQLConfig.getConnection();
		int count = 0;
		
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, name);
			statement.setDate(2, startDate);
			statement.setDate(3, endDate);
			
			count = statement.executeUpdate();
		} catch(Exception e) {
			System.out.println("lỗi save project: " + e.getMessage());
		}
		
		return count;
	}
	
	public int update(int id, String name, Date startDate, Date endDate) {
		String query = "UPDATE projects"
				+ "SET name=?, start_day=?, end_day=?"
				+ "WHERE id=?";
		Connection connection = MySQLConfig.getConnection();
		int count = 0;
		
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, name);
			statement.setDate(2, startDate);
			statement.setDate(3, endDate);
			statement.setInt(4, id);
			
			count = statement.executeUpdate();
		} catch(Exception e) {
			System.out.println("lỗi update project: " + e.getMessage());
		}
		
		return count;
	}
	
	public int delete(int id) {
		String query = "DELETE FROM projects WHERE id = ?";
		Connection connection = MySQLConfig.getConnection();
		int count = 0;
		
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setInt(1, id);
			
			count = statement.executeUpdate();
		} catch(Exception e) {
			System.out.println("lỗi delete project: " + e.getMessage());
		}
		
		return count;
	}
}
