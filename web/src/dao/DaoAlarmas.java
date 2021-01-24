package dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import model.Alarma;

public interface DaoAlarmas extends JpaRepository<Alarma,Integer>{

	List<Alarma> findByIdSensor(int idSensor);

	@Query("SELECT a FROM Alarma a WHERE a.idSensor IN ?1")
	List<Alarma> findAllByIdSensor(int[] sensores);
	
	@Query("select a From Alarma a where a.id.fecha>=?1 and a.id.fecha<=?2 ORDER BY a.id.fecha")
	List<Alarma> informeTemporal(java.util.Date fecIni, java.util.Date fecFin);

	@Query("select a From Alarma a where a.id.fecha>=?1 and a.id.fecha<=?2") //ORDER BY a.id.fecha
	Page<Alarma> informeTemporalByPage(Date fechaInicio, Date fechaFin, Pageable pageable);
}
