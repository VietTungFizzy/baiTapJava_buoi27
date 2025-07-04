package crm09.services;

import java.util.List;

import crm09.entity.Role;
import crm09.repository.RolesRepository;
import crm09.repository.UserRepository;

public class UserServices {
	
	private RolesRepository rolesRepository = new RolesRepository();
	private UserRepository userRepository = new UserRepository();
	
	public List<Role> getAllRole() {
		return rolesRepository.findAll();
	}
	
	public boolean insertUser(String email, String password, int roleId, String fullName, String phone) {
		return userRepository.save(email, password, roleId, fullName, phone) > 0;
	}
}
