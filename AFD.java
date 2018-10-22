package proyecto.LF_SDE;

import java.util.Arrays;
import javax.swing.JOptionPane;


public class AFD {

	public static int trans = 36; //cantidad de elemntos en la matriz estados * alfabeto
	public static int est = 6; // cantidad de estados totales
	public static int alf = 6; // cantidad de elementos en el alfabeto
	public static String alfabeto[]={"0","1","2","3","4","5"}; // arreglo con los elemntos del alfabeto
	public static String states[]={"0","1","2","3","4","5"};//arreglo con los elemntos del alfabeto
	public static int[][] tabla; //tabla de transiciones
	public static int estFinal;
	public static String cadenaSimon="";
	public static String cadenaUsuario="";

	public AFD(){
	}
	
	public  void rellenarTabla(){
		tabla= new int[est][alf];
		for(int i=0; i<est; i++){
			for(int j=0; j<alf; j++){
				tabla[i][j]=j;
			}
		}
	}
	
	public  int getEstFinal() {
		return estFinal;
	}

	public  void setEstFinal(int estFinal) {
		AFD.estFinal = estFinal;
	}
	public  void setCadenaUsuario(String s) {
		AFD.cadenaUsuario = s;
	}
	public  void addCadenaUsuario(String s) {
		AFD.cadenaUsuario += s;
	}

	public  String getCadenaSimon() {
		return cadenaSimon;
	}

	public  void setCadenaSimon(String cadenaSimon) {
		AFD.cadenaSimon = cadenaSimon;
	}

	// funcion que recibe la cadena y verifica que pertenezca al AFD, utliziando
	// el resto de lasfunciones para apoyarse
	private static boolean validar(String w) {
		// ir letra por letra de w
		System.out.println("");
		int actual = 0;
		for (int i = 0; i < w.length(); i++) {
			String dato = String.valueOf(w.charAt(i));
			if (esAlfabeto(dato)) { // primero verifica que este formado por
									// elementos del alfabeto
				System.out.print("btn" + actual + " , " + dato + ":");
				actual = siguiente(actual, dato); // obtiene el siguiente estado
				System.out.println(" -> " + actual);
			} else {
				System.out.println(dato + " No pertenece al alfabeto");
				return false;
			}
		}
		int len=cadenaSimon.length()-1;
		if(cadenaSimon.substring(0, len).equals(cadenaUsuario.substring(0, len))){
			return esFinal(actual);
		}
		
		return false; // verifica si el ultimo estado es un
											// estado final
	}

	// funcion para saber cual es estado resultante, obteniendo la posicion x de
	// la tabla[estados][alfabeto]
	private static int siguiente(int estadoActual, String c) {
		int pos = 0;
		for (int i = 0; i < alfabeto.length; i++) {
			if (c.equals(alfabeto[i]))
				pos = i; // a cual elemento del alfabeto
		}
		return tabla[estadoActual][pos]; // se obtiene el estado resultante
	}

	// Verifica que la cadena pertenzca a el alfabeto, letra por letra
	private static boolean esAlfabeto(String c) {
		for (int i = 0; i < alfabeto.length; i++) {
			String temp = String.valueOf(alfabeto[i].charAt(0));
			if (c.equals(temp)) {
				return true;
			}
		}
		return false;
	}

	// Función para validar si el estado final de la cadena(a) es un estado
	// final
	private static boolean esFinal(int a) {
			if (a == estFinal) {
				System.out.println(a + " = estado final(" + estFinal + ")");
				return true;
		}
		return false;
	}
	
	//main, el cual llama a validar las cadenas y es donde pide los datos al usuario
	public Boolean evaluar() {
		String w = cadenaUsuario;
				
			boolean resultado = validar(w);// valida la cadena y recibe un true o false de resultado
			
			if (resultado) {
				JOptionPane.showConfirmDialog(null, " w = " + cadenaUsuario + " fue ACEPTADA por el AFD Simon", "Resultado",
						JOptionPane.PLAIN_MESSAGE, 1);
			} else {
				JOptionPane.showConfirmDialog(null, " w = " + cadenaUsuario + " fue RECHAZADA por el AFD Simon", "Resultado",
						JOptionPane.PLAIN_MESSAGE, 1);

			}

		System.out.println("Alfabeto: " + Arrays.toString(alfabeto));
		System.out.println("CadenaSimon w: " + cadenaSimon);
		System.out.println("Estado Final : " + estFinal);
		System.out.println("Usuario "+cadenaUsuario);
		return resultado;

	}

	
}
