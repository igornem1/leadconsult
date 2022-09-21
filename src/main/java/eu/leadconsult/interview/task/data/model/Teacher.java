package eu.leadconsult.interview.task.data.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
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
@SequenceGenerator(name = BaseEntity.ID_GEN, sequenceName = "teacher_seq", allocationSize = 1)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Teacher extends Person {

    @OneToMany(mappedBy = "teacher", orphanRemoval = true)
    private List<Course> courses;

    public Teacher(String name, int age) {
        super(name, age);
    }

}
