package service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dao.DaoClientes;
import dao.DaoSensores;
import model.Cliente;
import model.Sensor;
import model.validator.Tipos.EstadoSensor;
import model.validator.Tipos.ModoSensor;

@Service
public class ServiceSensoresImpl implements ServiceSensores {
	@Autowired
	DaoSensores daoSensores;
	@Autowired
	DaoClientes daoClientes;

	@Override
	public String buscarDireccion(int idSensor) {
		Sensor sensor = daoSensores.findById(idSensor).orElse(null);
		if (sensor == null) return "sin dirección";
		return sensor.getCliente().getDireccion();
	}

	@Override
	public void actualizarEstado(int idSensor, EstadoSensor estado) {
		Sensor sensor = daoSensores.findById(idSensor).orElse(null);
		sensor.setEstado(estado);
		daoSensores.save(sensor);
	}

	@Override
	public void actualizarModo(int idSensor, ModoSensor modo) {
		Sensor sensor = daoSensores.findById(idSensor).orElse(null);
	System.out.println("Actualizando sensor al modo "+modo);
		sensor.setModo(modo);
		daoSensores.save(sensor);
	}

	@Override
	public List<Sensor> obtenerSensoresPorCliente(Cliente cliente) {
		return daoSensores.findByCliente(cliente);
	}

	@Override
	public void eliminarSensorPorIdSensor(int idSensor) {
		if(daoSensores.existsById(idSensor)) {
			daoSensores.deleteById(idSensor);
		System.out.println("Sensor "+idSensor+" eliminado.");
		}
	}

	@Override
	@Transactional
	public void eliminarSensorPorId(Cliente cliente, int idSensor) {
		Sensor sensor = daoSensores.findById(idSensor).orElse(null);
		if(sensor != null) {// MySQLIntegrityConstraintViolationException: Column 'idCliente' cannot be null
			cliente.removeSensor(sensor);
			daoClientes.save(cliente);
		System.out.println("Sensor "+idSensor+" eliminado.");
		}
	}

	@Override
	public Sensor agregarSensor(Cliente cliente, String ubicacion) {
		Sensor sensor = new Sensor(ubicacion, cliente);
		daoSensores.save(sensor);
		return sensor;
	}

	@Override
	public int contratarSensor(String dni, String ubicacion) {
		Optional<Cliente> cl = daoClientes.findById(dni);
		return cl.isPresent()? agregarSensor(cl.get(), ubicacion).getIdSensor(): -1;
	}

}
