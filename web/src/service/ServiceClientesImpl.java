package service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dao.DaoClientes;
import model.Cliente;
import model.DtoCliente;
import service.util.Encriptacion;
import service.util.Formato;

@Service
public class ServiceClientesImpl implements ServiceClientes {
	@Autowired
	DaoClientes daoClientes;

	@Override
	public List<Cliente> getClientes() {
		List<Cliente> clientes = daoClientes.findAll();
/*		for (Cliente persona : clientes) {
			if(persona.getRol().equalsIgnoreCase("ROLE_ADMIN")) {
				clientes.remove(persona);
			}
		}*/
		return clientes;
	}
		
	@Override
	public Cliente getCliente(String remoteUser) {
//		System.out.println(remoteUser);
		return daoClientes.findByUsuario(remoteUser);
	}
		
	@Override
	public Cliente getCliente(String user, String pwd) {
		return daoClientes.findByUsuarioAndPassword(user, pwd);
	}

	@Override
	public void guardarCliente(Cliente p) {
//		if (!daoClientes.existsById(p.getDni())) 
		p.setDni(Formato.formatDNI(p.getDni())); //Añade un 0 a la izquierda si le falta al DNI.
		p.setPassword(Encriptacion.encriptarPwd(p.getPassword()));
		daoClientes.save(p);

	}

	@Override
	public DtoCliente obtenerDtoCliente(String dni) {
		Cliente cl = daoClientes.findById(dni).orElse(null);
		if (cl != null) return new DtoCliente(cl.getDni(), cl.getNombre(), cl.getDireccion(), cl.getEmail(), cl.getCuenta(), cl.getAvisoPolicia(), 
				cl.getUsuario(), cl.getPassword(), cl.getRol(), cl.getHabilitado());
		else return null;
	}

	@Override
	public List<String> getDniClientes() {
		List<Cliente> clientes = daoClientes.findAll();
		List<String> dnis = new ArrayList<>();
		for (Cliente persona : clientes) {
			dnis.add(persona.getDni());
		}
		return dnis;
	}

	@Override
	public List<DtoCliente> getDtoClientes() {
		List<Cliente> clientes = daoClientes.findAll();
		List<DtoCliente> dtoClientes = new ArrayList<>();
		for (Cliente persona : clientes) {
			dtoClientes.add(new DtoCliente(persona.getDni(), persona.getUsuario(), persona.getNombre()));
		}
		return dtoClientes;
	}

	@Override
	public void deshabilitarCliente(String dni) {
		Cliente cliente = daoClientes.findById(dni).orElse(null);
//		System.out.println(cliente.getRol());
		if(cliente.getRol().equals("ROLE_USER")) cliente.setHabilitado(false);
		daoClientes.save(cliente);
	}

	@Override
	public void actualizarUsuario(Cliente cliente) {
		daoClientes.save(cliente);
	}

}