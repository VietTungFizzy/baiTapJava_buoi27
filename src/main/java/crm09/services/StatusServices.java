package crm09.services;

import java.util.List;

import crm09.entity.Status;
import crm09.repository.StatusRepository;

public class StatusServices {
	private StatusRepository statusRepository = new StatusRepository();
	
	public List<Status> getAllStatus() {
		return statusRepository.findAll();
	}
}
