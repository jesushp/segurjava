package service;

import java.util.List;

import model.Cliente;
import model.Sensor;
import model.validator.Tipos.EstadoSensor;
import model.validator.Tipos.ModoSensor;

public interface ServiceSensores {
	String buscarDireccion(int idSensor);
	void actualizarEstado(int idSensor, EstadoSensor estado);
	void actualizarModo(int idSensor, ModoSensor modo);
	List<Sensor> obtenerSensoresPorCliente(Cliente cliente);
	void eliminarSensorPorIdSensor(int idSensor);
	void eliminarSensorPorId(Cliente cliente, int idSensor);
	Sensor agregarSensor(Cliente cliente, String ubicacion);
	int contratarSensor(String dni, String ubicacion);
}