package service;

import java.util.Date;
import java.util.List;

import model.Alarma;
import model.DtoClienteAlarmas;
import model.DtoInformeAlarmas;
import model.Sensor;

public interface ServiceInformes {
	List<Alarma> getInformeActividad(String dni);
	List<DtoClienteAlarmas> getInformeTemporalClientes(String fecIni, String fecFin);
	List<DtoInformeAlarmas> getInformeTemporal(String fecIni, String fecFin);
//	List<Object[]> getInformeFullTemporal(String fecIni, String fecFin);
	List<Sensor> getInformeFullActividad(String dni);
	List<DtoInformeAlarmas> getInformeTemporal(Date fechaInicio, Date fechaFinJ);
	List<DtoClienteAlarmas> getInformeTemporalClientes(Date fechaIni, Date fechaFin);
	List<DtoInformeAlarmas> getInformeTemporalByPage(Date fechaInicio, Date fechaFin, int pageid, int total);
	int recuentoInformeTemporal(Date fechaInicio, Date fechaFin);
}
