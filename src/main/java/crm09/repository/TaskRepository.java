package crm09.repository;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import crm09.config.MySQLConfig;
import crm09.entity.Project;
import crm09.entity.Status;
import crm09.entity.Task;
import crm09.entity.User;

public class TaskRepository {
	public List<Task> findAll() {
		String query = "SELECT t.*, p.name as project_name, u.fullname as user_fullname, s.name as status "
				+ "FROM tasks t "
				+ "JOIN projects p ON t.id_project = p.id "
				+ "JOIN users u ON t.id_user = u.id "
				+ "JOIN status s ON t.id_status = s.id";
		Connection connection = MySQLConfig.getConnection();
		List<Task> listTask = new ArrayList<Task>();
		List<Project> listProject = new ArrayList<Project>();
		List<User> listUser = new ArrayList<User>();
		List<Status> listStatus = new ArrayList<Status>();
		
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			ResultSet resultSet = statement.executeQuery();
			
			while(resultSet.next()) {
				int projectId = resultSet.getInt("id_project");
				Project project = listProject.stream().filter(_project -> projectId == _project.getId()).findAny().orElse(null);
				if(project == null) {
					project = new Project();
					project.setId(projectId);
					project.setName(resultSet.getString("project_name"));
					listProject.add(project);
				}
				
				int userId = resultSet.getInt("id_user");
				User user = listUser.stream().filter(_user -> userId == _user.getId()).findAny().orElse(null);
				if(user == null) {
					user = new User();
					user.setId(userId);
					user.setFullname(resultSet.getString("user_fullname"));
					
					listUser.add(user);
				}
				
				int statusId = resultSet.getInt("id_status");
				Status status = listStatus.stream().filter(_status -> statusId == _status.getId()).findAny().orElse(null);
				if(status == null) {
					status = new Status();
					status.setId(statusId);
					status.setName(resultSet.getString("status"));
				}
				
				Task task = new Task();
				task.setId(resultSet.getInt("id"));
				task.setNameTask(resultSet.getString("name_task"));
				task.setStartTask(resultSet.getDate("start_task"));
				task.setEndTask(resultSet.getDate("end_task"));
				task.setStatus(status);
				task.setProject(project);
				task.setUser(user);
				
				listTask.add(task);
			}
		} catch(Exception e) {
			System.out.println("lỗi findAll task: " + e.getMessage());
		}
		
		return listTask;
	}
	
	public Task get(int id) {
		String query = "SELECT t.*, p.name as project_name, u.fullname as user_fullname, s.name as status "
				+ "FROM tasks t "
				+ "JOIN projects p ON t.id_project = p.id "
				+ "JOIN users u ON t.id_user = u.id "
				+ "JOIN status s ON t.id_status = s.id "
				+ "WHERE t.id = ?";
		Connection connection = MySQLConfig.getConnection();
		Task task = new Task();
		
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setInt(1, id);
			ResultSet resultSet = statement.executeQuery();
			
			while(resultSet.next()) {
				Project project = new Project();
				project.setId(resultSet.getInt("id_project"));
				project.setName(resultSet.getString("project_name"));
				
				User user = new User();
				user.setId(resultSet.getInt("id_user"));
				user.setFullname(resultSet.getString("user_fullname"));
				
				Status status = new Status();
				status.setId(resultSet.getInt("id_status"));
				status.setName(resultSet.getString("status"));
				
				task.setId(resultSet.getInt("id"));
				task.setNameTask(resultSet.getString("name_task"));
				task.setStartTask(resultSet.getDate("start_task"));
				task.setEndTask(resultSet.getDate("end_task"));
				task.setStatus(status);
				task.setProject(project);
				task.setUser(user);
			}
		} catch(Exception e) {
			System.out.println("lỗi get task: " + e.getMessage());
		}
		
		return task;
	}
	
	public int save(String nameTask, Date startTask, Date endTask, int statusId, int projectId, int userId) {
		String query = "INSERT INTO tasks(name_task, start_task, end_task, id_status, id_project , id_user) "
				+ "VALUES(?, ?, ?, ?, ?, ?)";
		Connection connection = MySQLConfig.getConnection();
		int count = 0;
		
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, nameTask);
			statement.setDate(2, startTask);
			statement.setDate(3, endTask);
			statement.setInt(4, statusId);
			statement.setInt(5, projectId);
			statement.setInt(6, userId);
			
			count = statement.executeUpdate();
		} catch(Exception e) {
			System.out.println("lỗi save task: " + e.getMessage());
		}
		
		return count;
	}
	
	public int update(int id, String nameTask, Date startTask, Date endTask, int statusId, int projectId, int userId) {
		String query = "UPDATE tasks "
				+ "SET name_task=?, start_task=?, end_task=?, id_status=?, id_project=?, id_user=? "
				+ "WHERE id=?";
		Connection connection = MySQLConfig.getConnection();
		int count = 0;
		
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, nameTask);
			statement.setDate(2, startTask);
			statement.setDate(3, endTask);
			statement.setInt(4, statusId);
			statement.setInt(5, projectId);
			statement.setInt(6, userId);
			statement.setInt(7, id);
			
			count = statement.executeUpdate();
		} catch(Exception e) {
			System.out.println("lỗi update task: " + e.getMessage());
		}
		
		return count;
	}
	
	public int delete(int id) {
		String query = "DELETE FROM tasks WHERE id = ?";
		Connection connection = MySQLConfig.getConnection();
		int count = 0;
		
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setInt(1, id);
			
			count = statement.executeUpdate();
		} catch(Exception e) {
			System.out.println("lỗi delete task: " + e.getMessage());
		}
		
		return count;
	}
	
	public List<Task> findAllByUserId(int userId) {
		String query = "SELECT t.*, p.name as project_name, u.fullname as user_fullname, s.name as status "
				+ "FROM tasks t "
				+ "JOIN projects p ON t.id_project = p.id "
				+ "JOIN users u ON t.id_user = u.id "
				+ "JOIN status s ON t.id_status = s.id\n"
				+ "WHERE t.id_user = ?";
		Connection connection = MySQLConfig.getConnection();
		List<Task> listTask = new ArrayList<Task>();
		List<Project> listProject = new ArrayList<Project>();
		List<Status> listStatus = new ArrayList<Status>();
		
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setInt(1, userId);
			ResultSet resultSet = statement.executeQuery();
			
			while(resultSet.next()) {
				int projectId = resultSet.getInt("id_project");
				Project project = listProject.stream().filter(_project -> projectId == _project.getId()).findAny().orElse(null);
				if(project == null) {
					project = new Project();
					project.setId(projectId);
					project.setName(resultSet.getString("project_name"));
					listProject.add(project);
				}
				
				User user = new User();
				user.setId(userId);
				user.setFullname(resultSet.getString("user_fullname"));
				
				int statusId = resultSet.getInt("id_status");
				Status status = listStatus.stream().filter(_status -> statusId == _status.getId()).findAny().orElse(null);
				if(status == null) {
					status = new Status();
					status.setId(statusId);
					status.setName(resultSet.getString("status"));
				}
				
				Task task = new Task();
				task.setId(resultSet.getInt("id"));
				task.setNameTask(resultSet.getString("name_task"));
				task.setStartTask(resultSet.getDate("start_task"));
				task.setEndTask(resultSet.getDate("end_task"));
				task.setStatus(status);
				task.setProject(project);
				task.setUser(user);
				
				listTask.add(task);
			}
		} catch(Exception e) {
			System.out.println("lỗi findAllByUserId task: " + e.getMessage());
		}
		
		return listTask;
	}
}
