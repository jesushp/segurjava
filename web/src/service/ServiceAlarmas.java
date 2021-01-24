package service;

import java.util.Date;
import java.util.List;

import model.Alarma;
//import model.Alarma;
import model.DtoAlarma;
import model.Sensor;
import model.Cliente;

public interface ServiceAlarmas {
	boolean registrarAlarma(int idSensor, Date fecha);
	void enviarAviso(String direccion, Date fecha);
	List<DtoAlarma> obtenerAlarmasPorSensores(List<Sensor> sensores);
	List<DtoAlarma> obtenerAlarmasPorIdCliente(String dni);
	List<Integer> obtenerIdSensores();
	List<Integer> obtenerIdSensoresPorCliente(String dni);
}
