package dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import model.Cliente;
//import model.DtoClienteAlarmas;

public interface DaoClientes extends JpaRepository<Cliente,String>{
		Cliente findByUsuarioAndPassword(String user, String pwd);
		Cliente findByUsuario(String user);

		@Query("Select cl From Cliente cl Where cl.rol='ROLE_USER'") //Rol.CLIENTE
		List<Cliente> listaClientes();

		@Query(value = "Select p.dni as dni, p.nombre as nombre, s.idSensor as idSensor, s.ubicacion as ubicacion, a.fecha as fecha From clientes p, sensores s, alarmas a where p.dni=s.idCLiente and s.idSensor=a.idSensor and a.fecha>=?1 and a.fecha<=?2", nativeQuery = true) //, p.usuario as usuario
		List<Object[]> informeTemporal(java.util.Date FecIni, java.util.Date FecFin);		
//		List<DtoClienteAlarmas> informeTemporal(java.util.Date FecIni, java.util.Date FecFin);		
}