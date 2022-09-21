package eu.leadconsult.interview.task.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import eu.leadconsult.interview.task.data.model.Course;
import eu.leadconsult.interview.task.data.model.CourseType;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
	long countByType(CourseType type);
}
