package model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the alarmas database table.
 * 
 */
@Entity
@Table(name="alarmas")
@NamedQuery(name="Alarma.findAll", query="SELECT a FROM Alarma a")
public class Alarma implements Serializable, Comparable<Alarma> {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private AlarmaPK id;

	@Column(name="idSensor",insertable = false, updatable = false)
	private int idSensor;

	public Alarma() {
	}

	public Alarma(AlarmaPK id) {
		super();
		this.id = id;
	}

	public AlarmaPK getId() {
		return this.id;
	}

	public void setId(AlarmaPK id) {
		this.id = id;
	}

	@Override
	public int compareTo(Alarma alarma) {
		return (this.getId().getFecha().before(alarma.getId().getFecha()) ? -1 : 
		(this.getId().getFecha().equals(alarma.getId().getFecha()) ? 0 : 1)); 
	}

}