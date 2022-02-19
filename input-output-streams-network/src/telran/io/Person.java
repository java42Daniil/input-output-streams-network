package telran.io;

import java.io.Serializable;
import java.util.Objects;

public class Person implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long id;
	private String name;
	private Person anotherPerson;
	public Person(long id, String name, Person anotherPerson) {
		this.id = id;
		this.name = name;
		this.anotherPerson = anotherPerson;
	}
	public Person getAnotherPerson() {
		return anotherPerson;
	}
	public void setAnotherPerson(Person anotherPerson) {
		this.anotherPerson = anotherPerson;
	}
	public long getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	@Override
	public int hashCode() {
		return Objects.hash(id, name);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Person other = (Person) obj;
		return id == other.id && Objects.equals(name, other.name) && anotherPerson.id == other.anotherPerson.id;
	}
	
	
	

}