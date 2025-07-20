package crm09.entity;

import java.sql.Date;

public class Task {
	private int id;
	private String nameTask;
	private Date startTask;
	private Date endTask;
	private String status;
	private Project project;
	private User user;

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNameTask() {
		return nameTask;
	}
	public void setNameTask(String name_task) {
		this.nameTask = name_task;
	}
	public Date getStartTask() {
		return startTask;
	}
	public void setStartTask(Date start_task) {
		this.startTask = start_task;
	}
	public Date getEndTask() {
		return endTask;
	}
	public void setEndTask(Date end_task) {
		this.endTask = end_task;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Project getProject() {
		return project;
	}
	public void setProject(Project project) {
		this.project = project;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
}
