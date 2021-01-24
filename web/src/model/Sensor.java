package model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
//import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import model.validator.Tipos.EstadoSensor;
import model.validator.Tipos.ModoSensor;


/**
 * The persistent class for the sensores database table.
 * 
 */
@Entity
@Table(name="sensores")
//@NamedQuery(name="Sensor.findAll", query="SELECT s FROM Sensor s")
public class Sensor implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idSensor;

    @Enumerated(EnumType.ORDINAL)
	private EstadoSensor estado;

	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaAlta;

    @Enumerated(EnumType.ORDINAL)
	private ModoSensor modo;

	private String ubicacion;

	@OneToMany(cascade = {CascadeType.REMOVE,CascadeType.REFRESH}, fetch = FetchType.LAZY)
	@JoinColumn(name="idSensor", referencedColumnName = "idSensor", insertable = false, updatable = false)
	private List<Alarma> alarmas;

	//bi-directional many-to-one association to cliente
	@ManyToOne
	@JoinColumn(name="idCliente")
	private Cliente cliente;

	public Sensor() {
	}

	public Sensor(String ubicacion, Cliente cliente) {
		super();
		this.estado = EstadoSensor.DESACTIVADO;
		this.fechaAlta = new Date();
		this.modo = ModoSensor.NORMAL; //false;
		this.ubicacion = ubicacion;
		this.cliente = cliente;
	}

	public int getIdSensor() {
		return this.idSensor;
	}

	public void setIdSensor(int idSensor) {
		this.idSensor = idSensor;
	}

	public EstadoSensor getEstado() {
		return this.estado;
	}

	public void setEstado(EstadoSensor estado) {
		this.estado = estado;
	}

	public Date getFechaAlta() {
		return this.fechaAlta;
	}

	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public ModoSensor getModo() {
		return this.modo;
	}

	public void setModo(ModoSensor modo) {
		this.modo = modo;
	}

	public String getUbicacion() {
		return this.ubicacion;
	}

	public void setUbicacion(String ubicacion) {
		this.ubicacion = ubicacion;
	}

	public List<Alarma> getAlarmas() {
		return this.alarmas;
	}

	public void setAlarmas(List<Alarma> alarmas) {
		this.alarmas = alarmas;
	}

	public Cliente getCliente() {
		return this.cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

}