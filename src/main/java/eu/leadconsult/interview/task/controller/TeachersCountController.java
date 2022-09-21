package eu.leadconsult.interview.task.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import eu.leadconsult.interview.task.data.repository.TeacherRepository;

@RestController
@RequestMapping("teachers")
public class TeachersCountController {
	@Autowired
	private TeacherRepository repo;
	
	@GetMapping( "/count")
	public long count() {
		return repo.count();
	}
}
