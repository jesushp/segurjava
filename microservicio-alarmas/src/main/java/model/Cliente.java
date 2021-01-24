package model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
//import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
// import model.validator.Tipos.Rol;

/**
 * The persistent class for the personas database table.
 * 
 */
@Entity
@Table(name="clientes")
//@NamedQuery(name="Cliente.findAll", query="SELECT p FROM Cliente p")
public class Cliente extends Usuario implements Serializable {
	private static final long serialVersionUID = 1L;

//	@Id
//	private String dni;

	private boolean avisoPolicia;

	private String cuenta;

	private String direccion;

	private String email;

	private String nombre;

	//bi-directional many-to-one association to Sensor
	@OneToMany(mappedBy="cliente", fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE,CascadeType.REFRESH})
	private List<Sensor> sensores;

	public Cliente() {
		super();
	}

	public Cliente(String dni, String nombre, String direccion, String email, String cuenta, boolean avisoPolicia,
			String usuario, String password, String rol, boolean habilitado) {
		super(dni, usuario, password, rol, habilitado);
		this.avisoPolicia = avisoPolicia;
		this.cuenta = cuenta;
		this.direccion = direccion;
		this.email = email;
		this.nombre = nombre;
	}

	public boolean getAvisoPolicia() {
		return this.avisoPolicia;
	}

	public void setAvisoPolicia(boolean avisoPolicia) {
		this.avisoPolicia = avisoPolicia;
	}

	public String getCuenta() {
		return this.cuenta;
	}

	public void setCuenta(String cuenta) {
		this.cuenta = cuenta;
	}

	public String getDireccion() {
		return this.direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public List<Sensor> getSensores() {
		return this.sensores;
	}

	public void setSensores(List<Sensor> sensores) {
		this.sensores = sensores;
	}

	public void addSensor(Sensor sensor) {
		this.sensores.add(sensor);
	}

	public void removeSensor(Sensor sensor) {
		this.sensores.remove(sensor);
	}

}