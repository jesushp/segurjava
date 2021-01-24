package controller;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import dao.DaoAlarmas;
import model.Alarma;
import model.AlarmaPK;
import model.Sensor;
import reactor.core.publisher.Flux;
import service.ServiceAlarmas;

@CrossOrigin(origins="*")
@RestController
public class AlarmasController {
	@Autowired
	DaoAlarmas daoAlarmas;
	@Autowired
	ServiceAlarmas sAlarmas;
		
	
	@GetMapping(value="/lista",produces="text/event-stream") public
	Flux<Alarma> getAlarmas() { 
		List<Alarma> lista=daoAlarmas.findAll();
		lista.add(new Alarma(new AlarmaPK(0,new Date()))); //"EOF" return
		return Flux.fromIterable(lista).delayElements(Duration.ofSeconds(1));
	}
	
	@GetMapping(value="/alarmas",produces="text/event-stream")
	public Flux<Integer> obtenerAlarmas(){
		List<Integer> lista=sAlarmas.obtenerIdSensores();
		lista.add(new Integer(0)); //"EOF" return
	return Flux.fromIterable(lista).delayElements(Duration.ofSeconds(1));
	}

	@GetMapping(value="/alarmas/cliente/{id}",produces="text/event-stream")
	public Flux<List<Integer>> obtenerIdSensoresPorCliente(@PathVariable("id") String dni){
		return Flux.create(fs->{
			List<Sensor> anterior=null;
			List<Integer> lista=new ArrayList<>();
			while(true) {
				List<Sensor> actual=sAlarmas.obtenerSensoresActivosPorCliente(dni);
				// actual.forEach(System.out::print);
				if(cambio(anterior,actual)) {
					actual.forEach(sensor -> lista.add(sensor.getIdSensor()));
					fs.next(lista);
				}
				anterior=actual;
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}
	
	private boolean cambio(List<Sensor> anterior,List<Sensor> actual ) {
		
		if(anterior==null) {
			return false;
		} else {
			for(int i=0;i<actual.size();i++) {
				if(anterior.get(i).getModo() != actual.get(i).getModo()){
					return true;
				}
			}
		}
		return false;
	}
}