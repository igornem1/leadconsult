package eu.leadconsult.interview.task.data.model.base;

import javax.persistence.Index;
import javax.persistence.MappedSuperclass;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@MappedSuperclass
@Table(indexes = { @Index(columnList = "age") })
public abstract class Person extends NamedEntity {
	private int age;

	public Person(String name, int age) {
		super(name);
		this.age = age;
	}
	
}
