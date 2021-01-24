package controller;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import model.validator.Tipos.EstadoSensor;
import model.validator.Tipos.ModoSensor;
//import model.Cliente;
//import model.DtoCliente;
//import service.ServiceClientes;
import service.ServiceSensores;

@RestController
//@RequestMapping("/sensor")
@Validated
public class SensoresController {
	@Autowired
	ServiceSensores sSensores;
	private static final Logger LOG = LoggerFactory.getLogger(SensoresController.class);

	@GetMapping(value = "/sensor/actualizar/estado")
	public ResponseEntity<String> actualizarEstado(@RequestParam("idSensor") int idSensor, @RequestParam("estado") @NotBlank boolean estado) {
		try {
			EstadoSensor estadoSensor = estado? EstadoSensor.ACTIVADO: EstadoSensor.DESACTIVADO; 
			sSensores.actualizarEstado(idSensor, estadoSensor);
			return new ResponseEntity<>(new String("Sensor " + idSensor + ", actualizado a modo: " + estadoSensor), HttpStatus.OK);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			 LOG.error(e.getMessage());
			return new ResponseEntity<>(new String(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(value = "/sensor/actualizar/modo")
	public ResponseEntity<String> cambiarModo(@RequestParam("idSensor") int idSensor, @RequestParam("modo") @Valid @Pattern(regexp = "^(NORMAL|DETECCION)$") ModoSensor modo) {
		try {
			sSensores.actualizarModo(idSensor, modo);
			return new ResponseEntity<>(new String("Sensor " + idSensor + ", actualizado a modo: " + modo), HttpStatus.OK);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			 LOG.error(e.getMessage());
			return new ResponseEntity<>(new String(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping(value = "/sensor/eliminar/{id}")
	public void eliminarSensor(@PathVariable("id") int idSensor) {
		sSensores.eliminarSensorPorIdSensor(idSensor);
	}

	@PostMapping(value = "/sensor/contratar/{id}", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Integer> altaSensor(@PathVariable("id") @Valid @Size(min= 8, max = 9, message = "DNI length must be between 8 and 9") String dni, @RequestParam("lugar") String ubicacion) {
		try {
//			LOG.info("Nuevo sensor contratado: {}",ubicacion);
			int idSensor = sSensores.contratarSensor(dni, ubicacion);
			return new ResponseEntity<>(idSensor, HttpStatus.OK);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			 LOG.error(e.getMessage());
			return new ResponseEntity<>(new Integer(-1), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}