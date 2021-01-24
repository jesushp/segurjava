package service;

import java.util.List;

import model.Usuario;

public interface ServiceUsuarios {

	Usuario getUsuario(String user, String pwd);
	Usuario getUsuario(String user);

	void guardarUsuario(Usuario p);

	boolean actualizarUsuario(String dni, Usuario p);

	List<Usuario> getUsuarios();

//	List<Usuario> decodificUsuarios();

	void encriptarPwdUsuario(Usuario p);

	boolean comprobarPwdUsuario(String pwdEncriptada, String dni);

	void encriptarBBDD();

	void encriptarBBDDConPwd(String pwdPropuesta);

	Usuario obtenerUsuario(String dni);

}