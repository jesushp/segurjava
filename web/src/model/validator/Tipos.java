package model.validator;

public class Tipos {
	
	public static enum Rol {
		ROLE_ADMIN/*("Administrador")*/,
		ROLE_USER/*("Cliente");
		
		private String nombre;
		
		private Rol(String nombre) {
			this.nombre = nombre;
		}

		public String getNombre() {
			return nombre;
		}
		*/
	};
	
	public static enum EstadoSensor {
		DESACTIVADO(false),
		ACTIVADO(true);
		
		private boolean estado;
		
		private EstadoSensor(boolean estado) {
			this.estado = estado;
		}
		public boolean getEstado() {
			return estado;
		}

	};
		
	public static enum ModoSensor {
		NORMAL(false),
		DETECCION(true);

		private boolean modo;
		
		private ModoSensor(boolean modo) {
			this.modo = modo;
		}

		public boolean getModo() {
			return modo;
		}
		
	};

	public static enum CombiSensor {
		DESACTIVADO(EstadoSensor.DESACTIVADO, ModoSensor.NORMAL),
		ACTIVADO(EstadoSensor.ACTIVADO, ModoSensor.NORMAL),
		NORMAL(EstadoSensor.ACTIVADO, ModoSensor.NORMAL),
		DETECCION(EstadoSensor.ACTIVADO, ModoSensor.DETECCION);
		
		private final EstadoSensor estado;
		private final ModoSensor modo;
		
		private CombiSensor(EstadoSensor estado, ModoSensor modo) {
			this.estado = estado;
			this.modo = modo;
		}
		public EstadoSensor getEstado() {
			return estado;
		}
		public ModoSensor getModo() {
			return modo;
		}

	    public boolean isActivo() {
	        if (getEstado() == EstadoSensor.DESACTIVADO) {
	            return false;
	        }
	        return true;
	    }

	    public boolean isSaltoAlarma() {
	        if (getModo() == ModoSensor.DETECCION) {
	            return true;
	        }
	        return false;
	    }
	};
		
}