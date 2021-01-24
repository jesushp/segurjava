package model;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import model.validator.DNI;
import model.validator.FieldsValueMatch;
import model.validator.Tipos.Rol;


@FieldsValueMatch.List({ 
    @FieldsValueMatch(field = "password", fieldMatch = "verifyPassword", message = "Los valores introducidos en los campos {field} no coinciden.")/*, 
    @FieldsValueMatch(
      field = "email", 
      fieldMatch = "verifyEmail", 
      message = "Email addresses do not match!"
    )*/
})

/**
 * The persistent class for the personas database table.
 * 
 */
@Entity
@Table(name="usuarios")
@Inheritance(strategy = InheritanceType.JOINED)
//@NamedQuery(name="Usuario.findAll", query="SELECT p FROM Usuario p")
public class Usuario implements Serializable {
	private static final long serialVersionUID = 1L;

    @NotEmpty @DNI
	@Id
	private String dni;

	@Size(min=2, max=20) 
	private String usuario;

	@Size(min=5, max=15) 
	private String password;
    @NotEmpty
	@Transient
	private String verifyPassword;

    @NotNull
    @Enumerated(EnumType.STRING)
	private Rol rol;

    @NotNull
	private boolean habilitado;

	public Usuario() {
		super();
	}

	public Usuario(String dni, String usuario, String password, Rol rol, boolean habilitado) {
		this.dni = dni;
		this.usuario = usuario;
		this.password = password;
		this.rol = rol;
		this.habilitado = habilitado;
	}

	public String getDni() {
		return this.dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getUsuario() {
		return this.usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getVerifyPassword() {
		return verifyPassword;
	}

	public void setVerifyPassword(String verifyPassword) {
		this.verifyPassword = verifyPassword;
	}

	public Rol getRol() {
		return this.rol;
	}

	public void setRol(Rol rol) {
		this.rol = rol;
	}

	public boolean getHabilitado() {
		return this.habilitado;
	}

	public void setHabilitado(boolean habilitado) {
		this.habilitado = habilitado;
	}

/*	public boolean login(String usuario, String password) {
		if(this.usuario.equalsIgnoreCase(usuario) && this.password.equalsIgnoreCase(password)) return true;
		return false;
	}*/
}