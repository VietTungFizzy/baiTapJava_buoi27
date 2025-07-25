package crm09.services;

import java.util.List;

import crm09.entity.Role;
import crm09.entity.User;
import crm09.repository.RolesRepository;
import crm09.repository.UserRepository;

public class UserServices {
	
	private RolesRepository rolesRepository = new RolesRepository();
	private UserRepository userRepository = new UserRepository();
	
	public Role getRole(int id) {
		return rolesRepository.get(id);
	}
	
	public List<Role> getAllRole() {
		return rolesRepository.findAll();
	}
	
	public boolean updateRole(int id, String name, String description) {
		return rolesRepository.update(id, name, description) > 0;
	}
	
	public boolean deleteRole(int id) {
		return rolesRepository.delete(id) > 0;
	}
	
	public boolean insertRole(String name, String description) {
		return rolesRepository.save(name, description) > 0;
	}
	
	public boolean insertUser(String email, String password, int roleId, String fullName, String phone) {
		return userRepository.save(email, password, roleId, fullName, phone) > 0;
	}
	
	public List<User> getAllUser() {
		return userRepository.findAll();
	}
	
	public User getUser(int id) {
		return userRepository.get(id);
	}
	
	public boolean updateUser(int id, String email, String password, int idRole, String fullname, String phone) {
		return userRepository.update(id, email, password, idRole, fullname, phone) > 0;
	}
	
	public boolean deleteUser(int id) {
		return userRepository.delete(id) > 0;
	}
}
