package service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dao.DaoAlarmas;
import dao.DaoClientes;
import dao.DaoSensores;
import model.Alarma;
import model.Sensor;

@Service
public class ServiceAlarmasImpl implements ServiceAlarmas {
	@Autowired
	DaoAlarmas daoAlarmas;
	@Autowired
	DaoSensores daoSensores;
	@Autowired
	DaoClientes daoPersonas;


	@Override
	public List<Integer> obtenerIdSensores() {
		List<Alarma> alarmas = daoAlarmas.findAll();
		List<Integer> listaIds = new ArrayList<Integer>();
		alarmas.forEach(alarm -> listaIds.add(alarm.getId().getIdSensor()));
		return listaIds;
	}

	@Override
	public List<Integer> obtenerIdSensoresDeteccionPorCliente(String dni) {
		if (!daoPersonas.existsById(dni)) return null; //Comprueba que existe un cliente con ese DNI.
		List<Sensor> alarmas = daoSensores.findAlarmsByCliente(dni, true);
		List<Integer> listaIds = new ArrayList<Integer>(); //Crea una lista para recoger los sensores que han tenido alarmas.
		alarmas.forEach(alarm -> listaIds.add(alarm.getIdSensor()));
		// System.out.println(listaIds.size());
		return listaIds;
	}

	@Override
	public List<Sensor> obtenerSensoresActivosPorCliente(String dni) {
		if (!daoPersonas.existsById(dni)) return null; //Comprueba que existe un cliente con ese DNI.
		return daoSensores.findActiveByCliente(dni, true); //Obtiene el listado de sensores activos.
	}

}
