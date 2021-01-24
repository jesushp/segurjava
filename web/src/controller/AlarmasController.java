package controller;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import dao.DaoAlarmas;
//import model.Alarma;
import model.DtoAlarma;
import reactor.core.publisher.Flux;
import service.ServiceAlarmas;
import service.ServiceSensores;

@RestController
public class AlarmasController {
	@Autowired
	ServiceAlarmas sAlarmas;
	@Autowired
	ServiceSensores sSensores;
	@Autowired
	DaoAlarmas daoAlarmas;
	private List<Integer> listAlarmas = new ArrayList<>(); //Actua como mensaje que observa el flujo de alarmas.
	
 /**
  * @author jesushp
  * This will send a notification to the Police {date-sensor_id} in response with HTTP status code 200.
  * @since 2019-12-01
  * @variable sensor_id
  * @param 
  * @return
  */
	@Transactional
	@CrossOrigin(origins = "*")
	@GetMapping(value="/alarma/sensor/{id}",produces=MediaType.TEXT_PLAIN_VALUE)
	public String recibirAlarma(@PathVariable("id") int idSensor) {
		listAlarmas.add(idSensor); //Integer.toString(idSensor)
		Date fecha = new Date();
		boolean avisoPolicia = sAlarmas.registrarAlarma(idSensor, fecha);
		if(avisoPolicia) {
		System.out.println("Avisando a la policía...");
			sAlarmas.enviarAviso(sSensores.buscarDireccion(idSensor), fecha);
//			String acuse = template.postForObject(urlPolicia, new DtoAviso("direccion",fecha), String.class);
//			System.out.println(acuse);
		}
		return avisoPolicia?"Se ha enviado un aviso a la Policía.": "Ha contactado con el servicio de Alarmas.";
	}

	@GetMapping(value="/alarmas/cliente/{id}",produces=MediaType.APPLICATION_JSON_VALUE)
	public List<DtoAlarma> mostrarAlarmas(@PathVariable("id") String dni) {
//		System.out.println(sAlarmas.obtenerAlarmasPorCliente(dni));
		return sAlarmas.obtenerAlarmasPorIdCliente(dni);
	}

	@Async("taskExecutor")
	@GetMapping(value="/alarmas",produces=MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<Integer> obtenerAlarmas(){
//	listAlarmas.forEach(System.out::print);
		List<Integer> lista = new ArrayList<Integer>();
		for(int idSensor:listAlarmas) {
			lista.add(idSensor);
		}
		listAlarmas.clear();
		return Flux.fromIterable(lista).delayElements(Duration.ofSeconds(1));
	}

}