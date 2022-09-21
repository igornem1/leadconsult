package eu.leadconsult.interview.task.data.model.base;

import javax.persistence.MappedSuperclass;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
@Table(uniqueConstraints={@UniqueConstraint(columnNames={"NAME"})})
public abstract class NamedEntity extends BaseEntity {
	private String name;
}
