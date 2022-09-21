package eu.leadconsult.interview.task.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import eu.leadconsult.interview.task.data.model.Group;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {

}
