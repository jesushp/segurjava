package dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import model.Alarma;

public interface DaoAlarmas extends JpaRepository<Alarma, Integer>{

	List<Alarma> findByIdSensor(int idSensor);
	@Query("SELECT a FROM Alarma a WHERE a.idSensor IN ?1")
	List<Alarma> findAllByIdSensor(int[] sensores);
}
