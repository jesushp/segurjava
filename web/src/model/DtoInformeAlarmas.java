package model;

import java.util.Date;

public class DtoInformeAlarmas {
	
	private String dni;
	private String usuario;
	private String nombre;
	private int idSensor;
	private String ubicacion;
	private Date fecha;

	public DtoInformeAlarmas() {
		super();
	}
/*	public DtoInformeAlarmas(String dni, String usuario, String nombre) {
		super();
		this.dni = dni;
		this.usuario = usuario;
		this.nombre = nombre;
	}*/
	public DtoInformeAlarmas(String dni, String usuario, String nombre, int idSensor, String ubicacion, Date fecha) {
		super();
		this.dni = dni;
		this.usuario = usuario;
		this.nombre = nombre;
		this.idSensor = idSensor;
		this.ubicacion = ubicacion;
		this.fecha = fecha;
	}

	public String getDni() {
		return dni;
	}
	public void setDni(String dni) {
		this.dni = dni;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public int getIdSensor() {
		return idSensor;
	}
	public void setIdSensor(int idSensor) {
		this.idSensor = idSensor;
	}
	public String getUbicacion() {
		return ubicacion;
	}
	public void setUbicacion(String ubicacion) {
		this.ubicacion = ubicacion;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
}
