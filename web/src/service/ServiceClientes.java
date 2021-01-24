package service;

import java.util.List;

import model.Cliente;
import model.DtoCliente;

public interface ServiceClientes {
	Cliente getCliente(String user, String pwd);
	Cliente getCliente(String remoteUser);
	DtoCliente obtenerDtoCliente(String dni);
	List<Cliente> getClientes();
	List<String> getDniClientes();
	void guardarCliente(Cliente p);
	void deshabilitarCliente(String dni);
	void actualizarUsuario(Cliente cliente);
	List<DtoCliente> getDtoClientes();
}
