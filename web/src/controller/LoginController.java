package controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpSession;
//import model.Cliente;
//import model.Usuario;
import service.ServiceClientes;
import service.ServiceUsuarios;

@Controller
public class LoginController {
	private static final Logger LOG = LoggerFactory.getLogger(LoginController.class);
	@Autowired
	ServiceUsuarios sUsuarios;
	@Autowired
	ServiceClientes sClientes;

	@GetMapping(value = "/login.do") //usuario autenticado por Spring Security.
	public String login(HttpServletRequestWrapper request, HttpSession sesion){
		if(request.isUserInRole("ROLE_USER")) {//return "cliente-menu";	
			try {
				sesion.setAttribute("persona", sClientes.getCliente(request.getRemoteUser()));
				return "cliente-menu";	
			} catch(Exception e) {
				LOG.error("Error log message: {} \n User: {}", e.getMessage(), request.getRemoteUser());
				return "error";
			}
		} else if(request.isUserInRole("ROLE_ADMIN")) {
			LOG.info("Info log message: {}",request.getRemoteUser());
//			sesion.setAttribute("persona", sUsuarios.getUsuario(request.getRemoteUser()));
			return "admin-menu";
		}
		return "login";
	}
/*	
	@ExceptionHandler(Exception.class)
	public String exception(HttpServletRequestWrapper request, Model model, Exception e) {
	    LOG.error("Request: " + request.getRequestURL() + " raised " + e);
	    model.addAttribute("url", request.getRequestURI());
		model.addAttribute("exception", e.getMessage());
		return "error";
	}

	@PostMapping(value = "/doLogin")
	public String login(@RequestParam("username") String user, 
						@RequestParam("password") String pwd,
						HttpSession sesion){
		Usuario persona=sUsuarios.getUsuario(user, pwd);
		if(persona!=null) {
			sesion.setAttribute("persona", persona);
			if(persona.getRol() == Rol.ADMIN)) {
				return "admin-menu";
			}else {
				return "cliente-menu";	
			}
		}else {
			return "error";
		}
	}*/

}