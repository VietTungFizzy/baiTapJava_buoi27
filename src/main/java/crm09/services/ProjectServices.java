package crm09.services;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import crm09.entity.Project;
import crm09.repository.ProjectRepository;

public class ProjectServices {
	private ProjectRepository projectRepository = new ProjectRepository();
	
	public List<Project> getAllProjects() {
		return projectRepository.findAll();
	}
	
	public Project getProject(int id) {
		return projectRepository.get(id);
	}
	
	public boolean insertProject(String name, String startDateStr, String endDateStr) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
		try {
			Date startDate = new Date(formatter.parse(startDateStr).getTime());
			Date endDate = new Date(formatter.parse(endDateStr).getTime());
			return projectRepository.save(name, startDate, endDate) > 0;
		} catch(Exception e) {
			System.out.println("Lỗi format Date: " + e.getMessage());
		}
		return false;
	}
	
	public boolean updateProject(int id, String name, String startDateStr, String endDateStr) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
		try {
			Date startDate = new Date(formatter.parse(startDateStr).getTime());
			Date endDate = new Date(formatter.parse(endDateStr).getTime());
			return projectRepository.update(id, name, startDate, endDate) > 0;
		} catch(Exception e) {
			System.out.println("Lỗi format Date: " + e.getMessage());
		}
		return false;
	}
	
	public boolean deleteProject(int id) {
		return projectRepository.delete(id) > 0;
	}
	
}
