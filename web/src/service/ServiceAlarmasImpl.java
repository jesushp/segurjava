package service;

//import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import dao.DaoAlarmas;
import dao.DaoClientes;
import dao.DaoSensores;
import model.Alarma;
import model.DtoAlarma;
import model.DtoAviso;
import model.Cliente;
import model.Sensor;
import model.validator.Tipos.ModoSensor;
import service.util.Formato;

@PropertySource("classpath:resources/config.properties")
@Service
public class ServiceAlarmasImpl implements ServiceAlarmas {
	@Autowired
	DaoAlarmas daoAlarmas;
	@Autowired
	DaoSensores daoSensores;
	@Autowired
	DaoClientes daoPersonas;
	RestTemplate template = new RestTemplate();
	@Value("${url.servicio.policia}")
	String urlPolicia; //="http://localhost:8000/sensores/aviso";

	@Override
	public boolean registrarAlarma(int idSensor, Date fecha) {
		Alarma alarma = new Alarma(idSensor, fecha); //Crea una nueva alarma.
	System.out.println("Alarma registrada en el sensor "+alarma.getIdSensor()+" con fecha: "+alarma.getId().getFecha());
		daoAlarmas.save(alarma); //Guarda la alarma en la BBDD.
		Sensor sensor = daoSensores.findById(idSensor).orElse(null);
		sensor.setModo(ModoSensor.DETECCION); //Cambia el modo del sensor a detección.
	System.out.println("Alarma registrada en el/a "+sensor.getUbicacion()+" - modo: "+sensor.getModo());
		daoSensores.save(sensor);
		return daoSensores.hasAvisoPolicia(idSensor);
	}

	@Override
	public void enviarAviso(String direccion, Date fecha) {
	System.out.println(direccion+", "+fecha);
		String dateString = Formato.formatDate(new Date(), "dd-MM-YYYY HH:mm");
		DtoAviso aviso = new DtoAviso(direccion,dateString);
		String acuse;
		try {
			acuse = template.postForObject(urlPolicia, aviso, String.class);
	System.out.println("Aviso enviado");
	System.out.println(acuse);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

	}

	@Override
	public List<DtoAlarma> obtenerAlarmasPorSensores(List<Sensor> sensores) {
		if(sensores.size() == 0) return null;
		int[] lista = new int[sensores.size()]; // Crea un array con del tamaño de la colección.
		// sensores.forEach((s) -> lista.add(s.getIdSensor()));
		for (int i = 0; i < sensores.size(); i++)
			lista[i] = sensores.get(i).getIdSensor(); // Rellena el array con los ids correspondientes a cada sensor.
		List<Alarma> alarmas = daoAlarmas.findAllByIdSensor(lista);
		// System.out.println(alarmas);
		List<DtoAlarma> listado = new ArrayList<>();
		DtoAlarma alarm;
		String lugarSensor = null;
		for (Alarma a : alarmas) {
			for (Sensor sensor : sensores)
				if (sensor.getIdSensor() == a.getIdSensor()) {
					lugarSensor = sensor.getUbicacion();
					break;
				}
			alarm = new DtoAlarma(a.getIdSensor(), a.getId().getFecha(), lugarSensor);
			// System.out.println(alarm.getFecha());
			listado.add(alarm);
		}
		// System.out.println(listado);
		return listado;
	}
			

	@Override
	public List<DtoAlarma> obtenerAlarmasPorIdCliente(String dni) {
		Cliente cliente = daoPersonas.findById(dni).orElse(null);
		return obtenerAlarmasPorSensores(cliente.getSensores()); // Recupera su colección de sensores.
	}

	@Override
	public List<Integer> obtenerIdSensores() {
		List<Alarma> alarmas = daoAlarmas.findAll();
		List<Integer> listaIds = new ArrayList<Integer>();
		alarmas.forEach(alarm -> listaIds.add(alarm.getId().getIdSensor()));
		return listaIds;
	}

	@Override
	public List<Integer> obtenerIdSensoresPorCliente(String dni) {
		if (!daoPersonas.existsById(dni)) return null; //Comprueba que existe un cliente con ese DNI.
		List<DtoAlarma> alarmas = obtenerAlarmasPorIdCliente(dni);
		Collections.sort(alarmas); // , Collections.reverseOrder()       
		List<Integer> listaIds = new ArrayList<Integer>(); //Crea una lista para recoger los sensores que han tenido alarmas.
		alarmas.forEach(alarm -> listaIds.add(alarm.getIdSensor()));
		return listaIds;
	}

}
