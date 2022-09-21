package eu.leadconsult.interview.task.data.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import eu.leadconsult.interview.task.data.model.base.BaseEntity;
import eu.leadconsult.interview.task.data.model.base.NamedEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@Entity
@SequenceGenerator(name = BaseEntity.ID_GEN, sequenceName = "course_seq", allocationSize = 1)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Course extends NamedEntity {

    @Enumerated(EnumType.ORDINAL)
    private CourseType type;

    @ManyToOne
    private Teacher teacher;

    @ManyToMany(mappedBy = "courses")
    private List<Student> students;

    public Course(String name, CourseType type) {
        super(name);
        this.type = type;
    }

}
