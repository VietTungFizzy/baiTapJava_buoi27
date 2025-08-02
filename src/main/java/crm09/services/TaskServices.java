package crm09.services;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import crm09.entity.Task;
import crm09.repository.TaskRepository;

public class TaskServices {
	private TaskRepository taskRepository = new TaskRepository();
	
	public List<Task> getAllTasks() {
		return taskRepository.findAll();
	}
	
	public Task getTask(int id) {
		return taskRepository.get(id);
	}
	
	public boolean insertTask(String nameTask, String startTaskStr, String endTaskStr, int status, int projectId, int userId) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
		try {
			Date startTask = new Date(formatter.parse(startTaskStr).getTime());
			Date endTask = endTaskStr.isBlank() ? null : new Date(formatter.parse(endTaskStr).getTime());
			return taskRepository.save(nameTask, startTask, endTask, status, projectId, userId) > 0;
		} catch(Exception e) {
			System.out.println("Lỗi format Date: " + e.getMessage());
		}
		return false;
	}
	
	public boolean updateTask(int id, String nameTask, String startTaskStr, String endTaskStr, int status, int projectId, int userId) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
		try {
			Date startTask = new Date(formatter.parse(startTaskStr).getTime());
			Date endTask = new Date(formatter.parse(endTaskStr).getTime());
			return taskRepository.update(id, nameTask, startTask, endTask, status, projectId, userId) > 0;
		} catch(Exception e) {
			System.out.println("Lỗi format Date: " + e.getMessage());
		}
		return false;
	}
	
	public boolean deleteTask(int id) {
		return taskRepository.delete(id) > 0;
	}
	
	public List<Task> getAllByUser(int userId) {
		return taskRepository.findAllByUserId(userId);
	}
	
	public Map<Integer, List<Task>> groupTasksByStatus(int userId) {
		Map<Integer, List<Task>> tasksGroupByStatus = new HashMap<Integer, List<Task>>();
		List<Task> listTasks = getAllByUser(userId);
		
		tasksGroupByStatus = listTasks.stream().collect(Collectors.groupingBy(Task::getStatusId));
		
		return tasksGroupByStatus;
	}
	
	public int totalTasks(Map<Integer, List<Task>> tasksGroupByStatus) {
		int count = 0;
		for (Map.Entry<Integer, List<Task>> entry : tasksGroupByStatus.entrySet()) {
            count = count + (entry.getValue().size());
        }
		
		return count;
	}
}
