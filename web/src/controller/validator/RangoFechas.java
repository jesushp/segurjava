package controller.validator;

import java.util.Date;

public class RangoFechas {
	private Date fechaIni;
	private Date fechaFin;
	public RangoFechas() {
		super();
	}
	public RangoFechas(Date fechaIni, Date fechaFin) {
		super();
		this.fechaIni = fechaIni;
		this.fechaFin = fechaFin;
	}
	public Date getFechaIni() {
		return fechaIni;
	}
	public void setFechaIni(Date fechaIni) {
		this.fechaIni = fechaIni;
	}
	public Date getFechaFin() {
		return fechaFin;
	}
	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

}
