package eu.leadconsult.interview.task.data.model.base;

import java.util.Objects;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Super class for all entities.
 *
 */
@Getter
@Setter
@NoArgsConstructor
@MappedSuperclass
public abstract class BaseEntity{

	public static final String ID_GEN = "sequence_generator";

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = ID_GEN)
	private Long id;

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		BaseEntity entity = (BaseEntity) o;
		if (entity.id == null || this.id == null) {
			return false;
		}
		return Objects.equals(entity.id, this.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(getClass().hashCode(), id);
	}

}
