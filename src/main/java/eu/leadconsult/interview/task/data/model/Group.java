package eu.leadconsult.interview.task.data.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import eu.leadconsult.interview.task.data.model.base.BaseEntity;
import eu.leadconsult.interview.task.data.model.base.NamedEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "groups")
@SequenceGenerator(name = BaseEntity.ID_GEN, sequenceName = "group_seq", allocationSize = 1)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Group extends NamedEntity {

    @OneToMany(mappedBy = "group")
    private List<Student> students;

    public Group(String name) {
        super(name);
    }

}
