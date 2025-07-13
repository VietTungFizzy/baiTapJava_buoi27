package crm09.entity;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class User {
	private int id;
	private String email;
	private String fullname;
	private String phone;
	private String password;
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
	
	public String getFullname() {
		return fullname;
	}
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	
	public String getFirstname() {
		String[] words = fullname.split(" ");
		return words[words.length - 1];
	}
	
	public String getLastname() {
		String[] words = fullname.split(" ");
		return Arrays.stream(words).limit(words.length - 1).collect(Collectors.joining(" "));
	}

	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
