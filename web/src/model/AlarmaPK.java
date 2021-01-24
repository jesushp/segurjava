package model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the alarmas database table.
 * 
 */
@Embeddable
public class AlarmaPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private int idSensor;

	@Temporal(TemporalType.TIMESTAMP)
	private java.util.Date fecha;

	public AlarmaPK() {
	}
	
	public AlarmaPK(int idSensor, java.util.Date fecha) {
		super();
		this.idSensor = idSensor;
		this.fecha = fecha;
	}

	public int getIdSensor() {
		return this.idSensor;
	}
	public void setIdSensor(int idSensor) {
		this.idSensor = idSensor;
	}
	public java.util.Date getFecha() {
		return this.fecha;
	}
	public void setFecha(java.util.Date fecha) {
		this.fecha = fecha;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof AlarmaPK)) {
			return false;
		}
		AlarmaPK castOther = (AlarmaPK)other;
		return 
			(this.idSensor == castOther.idSensor)
			&& this.fecha.equals(castOther.fecha);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.idSensor;
		hash = hash * prime + this.fecha.hashCode();
		
		return hash;
	}
}