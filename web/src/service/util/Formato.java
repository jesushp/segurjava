package service.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Formato {
	
	public static Date parseDate(String fecha, String formato) {
		SimpleDateFormat sdf = new SimpleDateFormat(formato);
		Date fechaFin = new Date();
		try {
			fechaFin = sdf.parse(fecha);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return fechaFin;
	}

	public static String formatDate(Date fecha, String formato) {
		SimpleDateFormat format = new SimpleDateFormat(formato);
		return format.format(fecha);
	}

	public static String formatDNI(String dni) {
		if (dni.matches("(\\d{7})([A-Z])"))
			dni = "0" + dni; //Añade un 0 a la izquierda si le falta al DNI antes de guardarlo.
		return dni;
	}

}