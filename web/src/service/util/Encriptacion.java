package service.util;

//import org.apache.commons.codec.binary.Base64;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class Encriptacion {

/*	public static String descodificarPwd(String pwdCodificada) { //getDecoder().decode
		String pwdCodificadaTrim = pwdCodificada.trim();
		byte[] decodedBytes = Base64.decodeBase64(pwdCodificadaTrim);
		return new String(decodedBytes);
	}
	
	public static String codificarPwd(String pwd) { //getEncoder().encode
		byte[] encodedBytes = Base64.encodeBase64(pwd.getBytes());
		String sEncoded = new String(encodedBytes);
		int iEncoded = sEncoded.length();
		for (int i = iEncoded; i < 64; i++) {
			sEncoded = sEncoded + " ";
		}
		return new String(sEncoded);
	}*/

	public static String encriptarPwd(String pwd) { 
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String sEncoded = passwordEncoder.encode(pwd);
		return new String(sEncoded);
	}

	public static boolean comprobarPwdEncriptada(String pwdEncriptada, String password) { 
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();  
		return encoder.matches(pwdEncriptada, password);  
	}
}
