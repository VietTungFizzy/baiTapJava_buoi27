package crm09.entity;

import java.sql.Date;

public class Project {
	private int id;
	private String name;
	private String status;
	private Date startDate;
	private Date endDate;

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date start_date) {
		this.startDate = start_date;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date end_date) {
		this.endDate = end_date;
	}
	
	
}
