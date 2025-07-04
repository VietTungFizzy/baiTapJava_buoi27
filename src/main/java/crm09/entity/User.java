package crm09.entity;

public class User {
	private int id;
	private String email;
	private Role roles;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	public Role getRoles() {
		return roles;
	}
	public void setRoles(Role roles) {
		this.roles = roles;
	}
	
	
}
