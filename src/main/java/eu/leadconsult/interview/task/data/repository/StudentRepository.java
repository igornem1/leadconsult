package eu.leadconsult.interview.task.data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import eu.leadconsult.interview.task.data.model.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
	List<Student> findAllByCourses_Name(String courseName);
	List<Student> findAllByGroup_Name(String groupName);
	List<Student> findAllByGroup_NameAndCourses_Name(String groupName, String courseName);
	List<Student> findAllByAgeGreaterThanAndCourses_Name(Integer age, String groupName);
}
