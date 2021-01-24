package service;

//import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dao.DaoUsuarios;
import model.Usuario;
import service.util.Encriptacion;
import service.util.Formato;

@Service
public class ServiceUsuariosImpl implements ServiceUsuarios {
	@Autowired
	DaoUsuarios daoUsuarios;

	@Override
	public Usuario getUsuario(String user, String pwd) {
		return daoUsuarios.findByUsuarioAndPassword(user, pwd);
	}

	@Override
	public Usuario getUsuario(String user) {
		return daoUsuarios.findByUsuario(user);
	}

	@Override
	public Usuario obtenerUsuario(String dni) {
		return daoUsuarios.findById(dni).orElse(null);
	}

	@Override
	public void guardarUsuario(Usuario p) {
//		if (!daoUsuarios.existsById(p.getDni())) 
		p.setDni(Formato.formatDNI(p.getDni())); //Añade un 0 a la izquierda si le falta al DNI.
		p.setPassword(Encriptacion.encriptarPwd(p.getPassword()));
		daoUsuarios.save(p);
	}

	@Override
	public boolean actualizarUsuario(String dni, Usuario p) {
		if (daoUsuarios.existsById(dni)) {
			daoUsuarios.save(p);
			return true;
		}
		return false;
	}

	@Override
	public List<Usuario> getUsuarios() {
		return daoUsuarios.findAll();
	}

/*	@Override
	public List<Usuario> decodificUsuarios() {
		List<Usuario> p = daoUsuarios.findAll();
		List<Usuario> pDescodificada = new ArrayList<Usuario>();
		for (Usuario persona : p) {
			String pwdCodificada = persona.getPassword();
			String pwdDescodificada = Encriptacion.descodificarPwd(pwdCodificada);
			persona.setPassword(pwdDescodificada);
			pDescodificada.add(persona);
		}
		return pDescodificada;
	}*/

	@Override
	public void encriptarPwdUsuario(Usuario p) {
		String pwdDescodificada = p.getPassword();
		p.setPassword(Encriptacion.encriptarPwd(pwdDescodificada));
		daoUsuarios.save(p);
	}
	
	@Override
	public boolean comprobarPwdUsuario(String pwdEncriptada, String dni) { 
		return Encriptacion.comprobarPwdEncriptada(pwdEncriptada, daoUsuarios.findById(dni).get().getPassword());  
	}
	
	@Override
	public void encriptarBBDD() { // Encripta las contraseñas que no están encriptadas.
		List<Usuario> p = daoUsuarios.findAll();
		for (Usuario persona : p) {
			String pwd = persona.getPassword();
			if(pwd.length()<60) { // Comprueba que las contraseñas tengan una longitud mínima de 60 caracteres (Bycript).
				Usuario pSinCodificar = persona;
				String pCodificada = Encriptacion.encriptarPwd(pwd);
				pSinCodificar.setPassword(pCodificada);
				daoUsuarios.save(pSinCodificar);
			}
		}
	}

	@Override
	public void encriptarBBDDConPwd(String pwdPropuesta) {
		// ejecutar para codificar la misma pwd a todas las personas
		List<Usuario> p = daoUsuarios.findAll();
		for (Usuario persona : p) {
			String pwd = "admin"; // Contraseña por defecto.
			if(pwdPropuesta.length()>3) pwd = pwdPropuesta;
			// No está codificada
			Usuario pSinCodificar = persona;
			String pCodificada = Encriptacion.encriptarPwd(pwd);
			pSinCodificar.setPassword(pCodificada);
			//grabar esta persona
			daoUsuarios.save(pSinCodificar);
		}
	}	

}
