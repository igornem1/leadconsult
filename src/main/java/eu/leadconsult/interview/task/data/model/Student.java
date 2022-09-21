package eu.leadconsult.interview.task.data.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import eu.leadconsult.interview.task.data.model.base.BaseEntity;
import eu.leadconsult.interview.task.data.model.base.Person;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@SequenceGenerator(name = BaseEntity.ID_GEN, sequenceName = "student_seq", allocationSize = 1)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Student extends Person {

    @ManyToOne
    private Group group;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "course_student", joinColumns = @JoinColumn(name = "course_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "student_id", referencedColumnName = "id"))
    private List<Course> courses;

    public Student(String name, int age) {
        super(name, age);
    }

}
