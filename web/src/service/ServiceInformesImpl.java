package service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import dao.DaoAlarmas;
import dao.DaoClientes;
import dao.DaoSensores;
import model.Alarma;
import model.DtoAlarma;
import model.DtoClienteAlarmas;
import model.DtoInformeAlarmas;
import model.Sensor;
import service.util.Formato;
@Service
public class ServiceInformesImpl implements ServiceInformes {
	@Autowired
	DaoClientes daoClientes;
	@Autowired
	DaoSensores daoSensores;
	@Autowired
	DaoAlarmas daoAlarmas;

	@Override
	public List<Alarma> getInformeActividad(String dni){
		// devolver todos los Sensores de un cliente
		List<Sensor> sensores = daoSensores.findByIdCliente(dni);
		List<Alarma> alarmas = new ArrayList<>();
		// buscar las alarmas de cada sensor
		if(sensores.size()>0) {
			for(Sensor sensorCliente : sensores) {
				List<Alarma> alarmasSensor = daoAlarmas.findByIdSensor((Integer)sensorCliente.getIdSensor()); 
				if(alarmasSensor.size()>0) {
					for (Alarma alarmaSen : alarmasSensor) {
						alarmas.add(alarmaSen);
					}
					System.out.println("Nª alarmas: "+alarmas.size());
				}
			}
		}
		return alarmas;
	}
	
	@Override
	public List<DtoClienteAlarmas> getInformeTemporalClientes(String fecIni, String fecFin) {
		Date fechaInicio = Formato.parseDate(fecIni, "yyyyMMdd");
		Date fechaFin = Formato.parseDate(fecFin, "yyyyMMdd");
		return getInformeTemporalClientes(fechaInicio, fechaFin);
	}

	@Override
	public List<Sensor> getInformeFullActividad(String dni) {
		return daoSensores.findByIdCliente(dni); //buscarActividadPorCliente
	}

	@Override
	public List<DtoInformeAlarmas> getInformeTemporal(String fecIni, String fecFin) {
		Date fechaInicio = Formato.parseDate(fecIni, "yyyyMMdd");
		Date fechaFin = Formato.parseDate(fecFin, "yyyyMMdd");
		return getInformeTemporal(fechaInicio, fechaFin);
	}
/*	public List<Object[]> getInformeFullTemporal(String fecIni, String fecFin) {
		Date fechaInicio = Fechas.parseDate(fecIni, "yyyyMMdd");
		Date fechaFin =  Fechas.parseDate(fecFin, "yyyyMMdd");
		System.out.println("********************************************************");
		System.out.println("fechaInicio = "+fechaInicio);
		System.out.println("fechaFin = "+fechaFin);
		return daoClientes.informeTemporal(fechaInicio,fechaFin);
	}*/

	@Override
	public List<DtoInformeAlarmas> getInformeTemporal(Date fechaInicio, Date fechaFin) {
		if(fechaInicio == null || fechaFin == null || fechaInicio.after(fechaFin)) return null;
		List<Alarma> alarmas = daoAlarmas.informeTemporal(fechaInicio, fechaFin);
		// System.out.println(alarmas.size());
		List<DtoInformeAlarmas> listado = new ArrayList<>();
		DtoInformeAlarmas dto = null;
		Sensor sensor;
		for (Alarma a : alarmas) {
			sensor = daoSensores.findById(a.getIdSensor()).orElse(null); //recupera el sensor correspondiente a la alarma.
			if (sensor != null) 
			dto = new DtoInformeAlarmas(sensor.getCliente().getDni(), 
					sensor.getCliente().getUsuario(),
					sensor.getCliente().getNombre(),
					a.getIdSensor(), sensor.getUbicacion(), a.getId().getFecha());
			// System.out.println(dto.getFecha());
				listado.add(dto);
			}
		return listado;
	}

	@Override
	public List<DtoInformeAlarmas> getInformeTemporalByPage(Date fechaInicio, Date fechaFin, int pageid, int limit) {
		if(fechaInicio == null || fechaFin == null || fechaInicio.after(fechaFin)) return null;
	System.out.print("Página: ");
	System.out.println(pageid);
//		Page<Alarma> pageAlarmas = daoAlarmas.findAll(PageRequest.of(pageid-1, limit, Sort.by(Sort.Direction.ASC, "id.fecha")));
//	System.out.println("Nº de Alarmas: "+pageAlarmas.getTotalPages());
		Page<Alarma> alarmas = daoAlarmas.informeTemporalByPage(fechaInicio, fechaFin, PageRequest.of(pageid-1, limit, Sort.by(Sort.Direction.ASC, "id.fecha"))); //new PageRequest(pageid-1, limit));query.setParameter("ids", aIds.subList(0,10));
	System.out.println("Nº de Alarmas: "+alarmas.getTotalElements()+ " - nº de páginas:" +alarmas.getTotalPages());
		List<DtoInformeAlarmas> listado = new ArrayList<>();
		DtoInformeAlarmas dto = null;
		Sensor sensor;
		for (Alarma a : alarmas) { //pageAlarmas.getContent()
			sensor = daoSensores.findById(a.getIdSensor()).orElse(null); //recupera el sensor correspondiente a la alarma.
			if (sensor != null) 
			dto = new DtoInformeAlarmas(sensor.getCliente().getDni(), 
					sensor.getCliente().getUsuario(),
					sensor.getCliente().getNombre(),
					a.getIdSensor(), sensor.getUbicacion(), a.getId().getFecha());
			 System.out.println(dto.getFecha());
				listado.add(dto);
			}
		return listado;
	}	

	@Override
	public List<DtoClienteAlarmas> getInformeTemporalClientes(Date fechaInicio, Date fechaFin) {
		List<Alarma> alarmas = daoAlarmas.informeTemporal(fechaInicio, fechaFin);
		// System.out.println(alarmas.size());
		List<DtoClienteAlarmas> listado = new ArrayList<>();
		DtoClienteAlarmas cliente = null;
		DtoAlarma alarmDto = null;
		int idx = -1;
		boolean existe = false;
		for (Alarma a : alarmas) {
//		System.out.println("Nº de Dtos: "+listado.size());
			Sensor sensor = daoSensores.findById(a.getIdSensor()).orElse(null); //recupera el sensor correspondiente a la alarma.
			if (sensor != null) {
				if (cliente != null && cliente.getDni().equals(sensor.getCliente().getDni())) {
/*						alarmDto = new DtoAlarma(a.getIdSensor(), a.getId().getFecha(), sensor.getUbicacion());
						cliente.addAlarma(alarmDto); //añade una nueva alarma al Dto.
						System.out.println("Alarma en el sensor "+a.getIdSensor()+" añadida con fecha: "+alarmDto.getFecha());*/
				} else {
				if (listado.size() > 0) {
					if (existe) { 
						System.out.println("El cliente "+cliente.getNombre()+" existe:"+idx);
						listado.set(idx, cliente); //añado las alarmas al listado antes de crear el siguiente Cliente.
						cliente = null;
					}
					for (DtoClienteAlarmas dto : listado) 
						if (dto.getDni().equals(sensor.getCliente().getDni())) { //comprueba si el Cliente ya está creado en el listado.
							if(cliente != null) listado.add(cliente); //añade el Dto al listado antes de recuperar el encontrado.
							cliente = dto;
							idx = listado.indexOf(dto);
							existe = true;
							break;
						} else {
							existe = false;
						}
				}
				if (!existe) {
					if (cliente != null) listado.add(cliente); //el Cliente necesariamente ha cambiado y añado las alarmas al listado antes de crear el siguiente.
					cliente = new DtoClienteAlarmas( // crea un Dto si no existe el Cliente.
													sensor.getCliente().getDni(), 
													sensor.getCliente().getUsuario(),
													sensor.getCliente().getNombre());
					}
//				if (!cliente.getDni().equals(sensor.getCliente().getDni())) {
					}
					alarmDto = new DtoAlarma(a.getIdSensor(), a.getId().getFecha(), sensor.getUbicacion());
					cliente.addAlarma(alarmDto); //añade una nueva alarma al Dto.
					System.out.println("Alarma en el sensor "+a.getIdSensor()+" añadida con fecha: "+alarmDto.getFecha());
				}
		}
		if (!existe && cliente != null) listado.add(cliente);
		else if (existe) listado.set(idx, cliente);
		return listado;
	}

	@Override
	public int recuentoInformeTemporal(Date fechaInicio, Date fechaFin) {
		if(fechaInicio == null || fechaFin == null || fechaInicio.after(fechaFin)) return 0;
		int countResult = daoAlarmas.informeTemporal(fechaInicio, fechaFin).size();
	System.out.print("Total de alarmas: "+countResult+" / ");
		return countResult;
	}

}