package model;

import java.util.ArrayList;
import java.util.List;

public class DtoClienteAlarmas {
	
	private String dni;
	private String usuario;
	private String nombre;
//	private int idSensor;
//	private String ubicacion;
//	private Date fecha;
	private List<DtoAlarma> alarmas;

	public DtoClienteAlarmas() {
		super();
	}
	public DtoClienteAlarmas(String dni, String usuario, String nombre) {
		super();
		this.dni = dni;
		this.usuario = usuario;
		this.nombre = nombre;
		this.alarmas = new ArrayList<DtoAlarma>();
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
/*	public int getIdSensor() {
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
	}*/
	public List<DtoAlarma> getAlarmas() {
		return alarmas;
	}
	public void setAlarmas(List<DtoAlarma> alarmas) {
		this.alarmas = alarmas;
	}

	public void addAlarma(DtoAlarma alarma) {
		this.alarmas.add(alarma);
	}
}
