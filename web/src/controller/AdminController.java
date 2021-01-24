package controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
//import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import model.Cliente;
import model.Sensor;
import model.Usuario;
import service.ServiceClientes;
import service.ServiceInformes;
import service.ServiceSensores;
import service.ServiceUsuarios;
//import service.util.Encriptacion;

@Controller
public class AdminController {
	private static final Logger LOG = LoggerFactory.getLogger(LoginController.class);

	@Autowired
	ServiceUsuarios sUsuarios;
	@Autowired
	ServiceClientes sClientes;
	@Autowired
	ServiceSensores sSensores;
	@Autowired
	ServiceInformes sInformes;

	@RequestMapping(value="/adminAltaCliente.to",method = {RequestMethod.GET,RequestMethod.POST})
	public String recuperarListaClientes(HttpServletRequest request, Model model) {
		request.setAttribute("clientes", sClientes.getDniClientes());
		Cliente persona = new Cliente();
		persona.setHabilitado(true);
		model.addAttribute("cliente", persona);
		return "admin-alta_cliente";
	}
	
	@PostMapping(value = "/adminAltaCliente.do", params= {"rol=ROLE_ADMIN"}) //adminAltaUsuario.do
	public String altaUsuario(@ModelAttribute("cliente") @Valid Usuario persona, BindingResult result) {
		if (result.hasErrors())
			return "admin-alta_cliente";
		sUsuarios.guardarUsuario(persona);
		return "forward:/adminAltaCliente.to";
	}

	@PostMapping(value = "/adminAltaCliente.do", params= {"rol=ROLE_USER"})
	public String altaCliente(@ModelAttribute("cliente") @Valid Cliente cliente, BindingResult result) {
		if (result.hasErrors())
			return "admin-alta_cliente";
/*		if (cliente.getDni().matches("(\\d{7})([A-Z])"))
			cliente.setDni("0" + cliente.getDni()); //Añade un 0 a la izquierda si le falta al DNI antes de guardarlo.
		cliente.setPassword(Encriptacion.encriptarPwd(cliente.getPassword()));*/
//		if(cliente.getRol() == Rol.CLIENTE) {
		sClientes.guardarCliente(cliente);
		return "forward:/adminContratarSensores.to";
/*		} else {
			sUsuarios.guardarUsuario(new Usuario(cliente.getDni(), cliente.getUsuario(), cliente.getPassword(), cliente.getRol(), cliente.getHabilitado()));
			return "forward:/adminAltaCliente.to";
		}*/
	}

	@RequestMapping(value="/adminContratarSensores.to",method = {RequestMethod.GET,RequestMethod.POST})
	public String listadoSensores(@ModelAttribute("cliente") Cliente cliente, HttpServletRequest request) {
		request.setAttribute("cliente", cliente);
		List<Sensor> sensores = sSensores.obtenerSensoresPorCliente(cliente);
//		System.out.println("Sensores añadidos: "+sensores);
		if(!sensores.isEmpty()) {
			request.setAttribute("sensores", sensores);
		} //System.out.println("El cliente "+ cliente.getNombre() +" no tiene sensores.");
		return "admin-alta_sensores";
	}
	
//	@RequestMapping(value="/adminEncript.do",method = {RequestMethod.GET,RequestMethod.POST})
	@GetMapping(value="/adminEncript.do")
	public String codificarBBDD(HttpServletRequest request) {// Encripta las contraseñas de la BBDD que no lo están.
		sUsuarios.encriptarBBDD();
		request.setAttribute("encript_toast", true); //Establece un flag para mostrar un mensaje de éxito.
		return "admin-menu";
	}

//	@RequestMapping(value = "/adminPwd.do", method = { RequestMethod.GET, RequestMethod.POST })
	@GetMapping(value = "/adminPwd.do")
	/* Encripta la BBDD con una contraseña genérica (admin) si no la recibe como parámetro: "/adminPwd.do?pwd=xxxxx" */
	public String codificarBBDDPwd(@RequestParam("pwd") String password, HttpServletRequest request) {
		sUsuarios.encriptarBBDDConPwd(password);
		request.setAttribute("encript_toast", true); //Establece un flag para mostrar un mensaje de éxito.
		return "admin-menu";
	}

	@ExceptionHandler(Exception.class)
	public String exception(HttpServletRequest request, Model model, Exception e) {
	    LOG.error("Request: " + request.getRequestURL() + " raised " + e);
	    model.addAttribute("url", request.getRequestURI());
		model.addAttribute("exception", e.getMessage());
		return "error";
	}
}