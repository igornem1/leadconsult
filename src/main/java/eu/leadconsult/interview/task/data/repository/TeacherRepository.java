package eu.leadconsult.interview.task.data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import eu.leadconsult.interview.task.data.model.Teacher;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {
	@Query("SELECT t from Teacher AS t, eu.leadconsult.interview.task.data.model.Student AS s, eu.leadconsult.interview.task.data.model.Group as g, "
			+ "eu.leadconsult.interview.task.data.model.Course as c where c.name=:courseName AND g.name =:groupName AND c.teacher.id = t.id and s.group.id=g.id")
	List<Teacher> findAllByGroup_NameAndCourse_Name(String groupName, String courseName);
}
