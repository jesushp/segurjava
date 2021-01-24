package model;

import java.io.Serializable;

import model.validator.Tipos.Rol;


public class DtoCliente extends Usuario implements Serializable {
	private static final long serialVersionUID = 1L;

//	private String dni;

	private boolean avisoPolicia;

	private String cuenta;

	private String direccion;

	private String email;

	private String nombre;


	public DtoCliente() {
		super();
	}

	public DtoCliente(String dni, String nombre, String direccion, String email, String cuenta, boolean avisoPolicia,
			String usuario, String password, Rol rol, boolean habilitado) {
		super(dni, usuario, password, rol, habilitado);
		this.avisoPolicia = avisoPolicia;
		this.cuenta = cuenta;
		this.direccion = direccion;
		this.email = email;
		this.nombre = nombre;
	}

	public DtoCliente(String dni, String usuario, String nombre) {
		super(dni, usuario, "***", Rol.ROLE_USER, false);
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


}