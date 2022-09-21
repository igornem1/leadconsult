package eu.leadconsult.interview.task.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import eu.leadconsult.interview.task.data.model.CourseType;
import eu.leadconsult.interview.task.data.repository.CourseRepository;

@RestController
@RequestMapping("courses")
public class CoursesCountController {
	@Autowired
	private CourseRepository repo;
	
	@GetMapping( "/countByType")
	public long count(CourseType type) {
		return repo.countByType(type);
	}
}
