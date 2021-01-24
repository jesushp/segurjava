package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import model.DtoCliente;
import service.ServiceClientes;

@RestController
public class ClientesController {
	@Autowired
	ServiceClientes sPersonas;

	@GetMapping(value="/cliente/deshabilitar",produces=MediaType.TEXT_PLAIN_VALUE)
	public String deshabilitarCliente(@RequestParam("idCliente") String dni) {
		sPersonas.deshabilitarCliente(dni);
		return "Usuario con "+dni+" deshabilitado.";		
	}

	@GetMapping(value="/datos/cliente/{idCliente}",produces=MediaType.APPLICATION_JSON_VALUE)
	public DtoCliente datosCliente(@PathVariable("idCliente") String dni) {
		System.out.println("Usuario "+dni+" cargado.");
		return sPersonas.obtenerDtoCliente(dni);		
	}
}