package controller;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import model.DtoAlarma;
import model.Cliente;
import model.Sensor;
import service.ServiceAlarmas;
import service.ServiceClientes;
import service.ServiceSensores;

@PropertySource("classpath:resources/config.properties")
@Controller
public class ClienteController {
	@Autowired
	ServiceSensores sSensores;
	@Autowired
	ServiceAlarmas sAlarmas;
	@Autowired
	ServiceClientes sPersonas;
	@Value("${url.servicio.alarmas}")
	private String urlAlarmas;

	@GetMapping(value="/clienteDashboard.to")
	public String dashboard(@SessionAttribute("persona") Cliente cliente,
						HttpServletRequest request) {
		List<Sensor> sensores = sSensores.obtenerSensoresPorCliente(cliente); //cliente.getSensores();
		Iterator<Sensor> iter = sensores.iterator();
		iter.forEachRemaining(System.out::println);
		request.setAttribute("sensores", sensores);
		System.out.println("endPoint: "+urlAlarmas);
		request.setAttribute("urlAlarmas", urlAlarmas);
		return "cliente-menu";
	}
	
	@RequestMapping(value="/clienteGestionarSensores.to",method = {RequestMethod.GET,RequestMethod.POST})
	public String listadoSensores(@SessionAttribute("persona") Cliente cliente, HttpServletRequest request) {
		List<Sensor> sensores = sSensores.obtenerSensoresPorCliente(cliente); //cliente.getSensores();
		if(!sensores.isEmpty()) {
		//	Iterator<Sensor> iter = sensores.iterator();
		//	iter.forEachRemaining(System.out::println);
			request.setAttribute("sensores", sensores);
			List<DtoAlarma> alarmas = sAlarmas.obtenerAlarmasPorSensores(sensores);
			request.setAttribute("alarmas", alarmas);
		} //System.out.println("El cliente "+ cliente.getNombre() +" no tiene sensores.");
		return "cliente-sensores";
	}

	@PostMapping(value="/clienteBajaSensor.do")
	public String eliminarSensor(@RequestParam("idSensor") int idSensor,
			@SessionAttribute("persona") Cliente cliente, HttpServletRequest request) {
		System.out.println("Eliminando sensor "+idSensor+"...");
		sSensores.eliminarSensorPorIdSensor(idSensor);
//		sSensores.eliminarSensorPorId(cliente, idSensor);
//		List<Sensor> sensores = sSensores.obtenerSensoresPorCliente(cliente); 
//		request.setAttribute("sensores", sensores);
//		List<DtoAlarma> alarmas = sAlarmas.obtenerAlarmasPorCliente(cliente);
//		request.setAttribute("alarmas", alarmas);
		return "forward:/clienteGestionarSensores.to";
	}

	@PostMapping(value="/clienteAltaSensor.do")
	public String contratarSensor(@RequestParam("lugar") String ubicacion,
			@SessionAttribute("persona") Cliente cliente, HttpServletRequest request) {
		sSensores.agregarSensor(cliente, ubicacion);
//		List<Sensor> sensores = sSensores.obtenerSensoresPorCliente(cliente); 
//		request.setAttribute("sensores", sensores);
//		List<DtoAlarma> alarmas = sAlarmas.obtenerAlarmasPorCliente(cliente);
//		request.setAttribute("alarmas", alarmas);
		return "forward:/clienteGestionarSensores.to";
	}

	@GetMapping(value="/clienteBaja.do")
	public String deshabilitarCliente(@RequestParam("dni") String dni,
			HttpSession sesion) {
		sPersonas.deshabilitarCliente(dni);
		sesion.invalidate();
		return "redirect:/login"; //cliente-menu";		
	}
}