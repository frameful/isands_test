package ru.isands.test.estore.model;


import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import java.util.Set;

import javax.persistence.*;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

@Getter
@Setter
@Entity
@Table(name = "store_employee")
public class Employee implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "employee_counter")
	@TableGenerator(name = "employee_counter", pkColumnName = "name",
			pkColumnValue = "ru.isands.test.estore.entity.Employee",
			table = "counter", valueColumnName = "currentid",
			allocationSize = 1)
	@Column(name = "id", unique = true, nullable = false)
	Long id;

	@Column(name = "lastname", nullable = false, length = 100)
	String lastName;

	@Column(name = "firstname", nullable = false, length = 100)
	String firstName;

	@Column(name = "patronymic", nullable = false, length = 100)
	String patronymic;

	@Column(name = "birth_date", nullable = false)
	Date birthDate;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "position_id")
	private PositionType positionType;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "shop_id")
	private Shop shop;
	
	/**
	 * Пол сотрудника (true - мужской, false - женский)
	 */
	@Column(name = "gender", nullable = false)
	boolean gender;

	/**
	 * Типы электроники
	 */
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
			name = "store_eemployee",
			joinColumns = @JoinColumn(name = "employee_id"),
			inverseJoinColumns = @JoinColumn(name = "electro_type_id")
	)
	private Set<ElectroType> electroTypeSet;


	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
		Employee employee = (Employee) o;
		return id != null && Objects.equals(id, employee.id);
	}
}