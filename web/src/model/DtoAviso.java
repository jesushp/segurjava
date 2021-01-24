package model;

public class DtoAviso {
	private String direccion;
	private String fecha;
	
	public DtoAviso() {
		super();
	}
	public DtoAviso(String direccion, String fecha) {
//		super();
		this.direccion = direccion;
		this.fecha = fecha;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

}
