package model.validator;

import java.math.BigInteger;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class IBANValidator implements ConstraintValidator<IBAN, String> {

	@Override
	public void initialize(IBAN paramIBAN) {
	}

	@Override
	public boolean isValid(String cuenta, ConstraintValidatorContext ctx) {
		if(cuenta == null || cuenta.length() == 0) {
			return true;
		} else if (cuenta.length() < 24){
			return false;
		}
		System.out.println("CCC: "+cuenta+" - "+calcularDcIBAN("ES", cuenta.substring(4,24)));
		System.out.println(calcularDc(cuenta)+" - DC: "+cuenta.substring(12, 14));
		//validate account numbers of format "ES6712341234110123456789, ES 39 1234 1234 25 0123456789, ES	39	1234	123425	0123456789"
        if (cuenta.matches("([a-zA-Z]{2})\\s*\\t*(\\d{2})\\s*\\t*(\\d{4})\\s*\\t*(\\d{4})\\s*\\t*(\\d{2})\\s*\\t*(\\d{10})")
        		 && calcularDcIBAN(cuenta.substring(0, 2), cuenta.substring(4,24)).equals(cuenta.substring(2,4))
        		 && calcularDc(cuenta).equals(cuenta.substring(12, 14))) return true;
        else return false;
	}

	private String calcularDc(String cuenta) {
	    int[] pesos = {6, 3, 7, 9, 10, 5, 8, 4, 2, 1};
	 
	    String entidadInv = inv(cuenta.substring(4,8));
	    String oficinaInv = inv(cuenta.substring(8,12));
	    String cuentaInv = inv(cuenta.substring(14,24));
	     
	    String ofient = "" + oficinaInv + entidadInv;
	    int suma = 0;
	    for(int a=0; a<8; a++)
	        suma += Integer.parseInt(ofient.substring(a,a+1))*pesos[a];
	 
	    int sumaCCC = 0;
	    for(int a=0; a<10; a++)
	        sumaCCC += Integer.parseInt(cuentaInv.substring(a,a+1))*pesos[a];
	 
	    int dc = 11 - (suma % 11);
	    int dcCcc = 11 - (sumaCCC % 11);
	 
	    if (dc == 10) dc = 1;
	    if (dc == 11) dc = 0;
	    if (dcCcc == 10) dcCcc = 1;
	    if (dcCcc == 11) dcCcc = 0;
	 
	    return Integer.toString(dc) + Integer.toString(dcCcc);
	    }
	 
	private static String inv(String item) {
	    String ret = "";
	    for (int a=0; a < item.length(); a++)
	        ret = ret + item.substring(item.length()-a-1,item.length()-a);
	 
	 System.out.println("Item: "+item+ " - inverso: "+ret);
	    return ret;
	    }
	
	private static String calcularDcIBAN(String codigoPais,String cuenta){
        System.out.println("preIBAN: "+codigoPais+" "+cuenta);
        String preIban = cuenta+
                PesoIBAN(codigoPais.charAt(0))+
                PesoIBAN(codigoPais.charAt(1))+
                "00";
        BigInteger ccc = new BigInteger(preIban);
        BigInteger noventaysiete = new BigInteger("97");
        ccc = ccc.mod(noventaysiete);
        int dcIb = ccc.intValue();
        dcIb = 98 - dcIb;
        return conCerosIzquierda(Integer.toString(dcIb),2);
    }
    
    private static String conCerosIzquierda(String str,int longitud){
        String ceros = "";
        if(str.length()<longitud){
            for(int i=0;i<(longitud-str.length());i++){
                ceros = ceros + '0';
            }
            str = ceros + str;
        }
        
        return str;
    }
    
    private static String PesoIBAN(char letra){
        String peso = "";
        letra = Character.toUpperCase(letra);
        switch (letra){
            case 'A': peso = "10"; 
                break;
            case 'B': peso = "11"; 
                break;
            case 'C': peso = "12"; 
                break;
            case 'D': peso = "13"; 
                break;
            case 'E': peso = "14"; 
                break;
            case 'F': peso = "15"; 
                break;
            case 'G': peso = "16"; 
                break;
            case 'H': peso = "17"; 
                break;
            case 'I': peso = "18"; 
                break;
            case 'J': peso = "19"; 
                break;
            case 'K': peso = "20"; 
                break;
            case 'L': peso = "21"; 
                break;
            case 'M': peso = "22"; 
                break;
            case 'N': peso = "23"; 
                break;
            case 'O': peso = "24"; 
                break;
            case 'P': peso = "25"; 
                break;
            case 'Q': peso = "26"; 
                break;
            case 'R': peso = "27"; 
                break;
            case 'S': peso = "28"; 
                break;
            case 'T': peso = "29"; 
                break;
            case 'U': peso = "30"; 
                break;
            case 'V': peso = "31"; 
                break;
            case 'W': peso = "32"; 
                break;
            case 'X': peso = "33"; 
                break;
            case 'Y': peso = "34"; 
                break;
            case 'Z': peso = "35"; 
                break;
        }
        return peso;
    }
}