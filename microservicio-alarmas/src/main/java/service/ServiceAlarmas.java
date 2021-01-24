package service;

import java.util.List;

import model.Sensor;


public interface ServiceAlarmas {
	List<Integer> obtenerIdSensores();
	List<Integer> obtenerIdSensoresDeteccionPorCliente(String dni);
	List<Sensor> obtenerSensoresActivosPorCliente(String dni);
}
