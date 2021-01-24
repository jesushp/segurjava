package model;

import java.util.Date;

public class DtoAlarma implements Comparable<DtoAlarma> {
	private int idSensor;
	private Date fecha;
	private String ubicacion;
	
	public DtoAlarma() {
		super();
	}
	public DtoAlarma(int idSensor, Date fecha) {
		super();
		this.idSensor = idSensor;
		this.fecha = fecha;
	}
	public DtoAlarma(int idSensor, Date fecha, String ubicacion) {
		super();
		this.idSensor = idSensor;
		this.fecha = fecha;
		this.ubicacion = ubicacion;
	}
	public int getIdSensor() {
		return idSensor;
	}
	public void setIdSensor(int idSensor) {
		this.idSensor = idSensor;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getUbicacion() {
		return ubicacion;
	}
	public void setUbicacion(String ubicacion) {
		this.ubicacion = ubicacion;
	}
	@Override
	public int compareTo(DtoAlarma alarma) {
		return (this.getFecha().before(alarma.getFecha()) ? -1 : 
		(this.getFecha().equals(alarma.getFecha()) ? 0 : 1)); 
	}
}