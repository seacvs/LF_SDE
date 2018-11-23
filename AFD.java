package proyecto.LF_SDE;

import java.util.Arrays;
import javax.swing.JOptionPane;

public class AFD {

	public static int trans = 42; //cantidad de elemntos en la matriz estados * alfabeto
	public static int est = 7; // cantidad de estados totales
	public static int alf = 6; // cantidad de elementos en el alfabeto
	public static String alfabeto[]={"0","1","2","3","4","5"}; // arreglo con los elementos del alfabeto
	public static String states[]={"0","1","2","3","4","5","6"};//arreglo con los estados y el error (6)
	public static int[][] tabla; //tabla con todas las transiciones del automata
	public static int estFinal; // estado final
	public static String cadenaSimon="";
	public static String cadenaUsuario="";
	public static int[][] tablaMin; //tabla de transiciones mnimizada
	public static String names[]; //nombres de las etiquetas de los estados minimizados
	static int ronda =3;
	
	public AFD(){
	}
	
	public void inicializarTransiciones(){// al inicio todo va al error, para que no esten en nulas
		int error= 6;					  // el estado 6 es el estado de error
		tabla= new int[est][alf];
		for(int i=0; i<est; i++){
			for(int j=0; j<alf; j++){
				tabla[i][j]= error;
			}
		}
	}
	
	public void agregarTransiciones(String secuencia, int round){//funcion para agregar transiciones al automata,
		System.out.println("secuencia "+secuencia.substring(0, round)+" ronda "+round);
		ronda=round;
		for(int i = 1; i< round; i++){							// agrega la transicion ala tabla de transiciones
			int estado = Integer.parseInt(secuencia.substring(i-1, i));
			int simbolo = Integer.parseInt(secuencia.substring(i, i+1));
			tabla[estado][simbolo]=simbolo;
			System.out.println("Recibio "+ estado+" -> " + simbolo);
		}
///////////////////////////////MINIMIZACION///////////////////////////////////////////////////
		System.out.println("Tabla Transiciones : "+imprimirTabla(tabla));
		int finalTemp = Integer.parseInt(secuencia.substring(round-1, round));
		int inicialTemp = Integer.parseInt(secuencia.substring(0,1));
		Minimizador min = new Minimizador(tabla, finalTemp, inicialTemp );
		tablaMin = new int[Minimizador.nombres.length][alf];
		tablaMin = Minimizador.tablaMin;
		System.out.println("Tabla Transiciones Minimizada: "+imprimirTabla(tablaMin));
		names = Minimizador.nombres;
		int p=0;
		for(String s: names){
			if(s!=null){
			System.out.println("Nombres en AFD: ["+p+"]  elementos =  "+s);
			p++;
			}
		}
			
	}
	
	public String imprimirTabla(int[][] t){//imprime la tabla de transiciones para verificar
		String s="";					// que las transiciones sean correctas
		for(int i=0; i<t.length; i++){
			s+="\n";
			for(int j=0; j< t[0].length; j++)
				s+="["+i+"]"+"["+j+"]"+" = "+t[i][j]+",   ";
		}
		return s;
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

	// funcion que recibe la cadena y verifica que pertenezca al AFD, utlizando
	// el resto de las funciones para apoyarse en la valdación
	private static boolean validar(String wMin,String w) {
		// ir letra por letra de w
		System.out.println("");
		int actual = Integer.parseInt(wMin.substring(0, 1));//letra actual
		for (int i = 1; i < w.length(); i++) {
			String dato = String.valueOf(w.charAt(i));
			if (esAlfabeto(dato)) { // primero verifica que este formado por
									// elementos del alfabeto
				System.out.print("validando estado " + actual + " , " + dato + ":");
				int estadoSiguiente = siguiente(actual, dato);
				actual = estadoSiguiente; // obtiene el siguiente estado
				System.out.println(" -> " + actual);
			} else {
				System.out.println(dato + " No pertenece al alfabeto");
				return false;
			}
		}
		return esFinal(actual);// estado final
	}

	// funcion para saber cual es estado resultante, obteniendo la posicion x de
	// la tabla[estados][alfabeto]
	private static int siguiente(int estadoActual, String c) {
		int pos = 0;
		for (int i = 0; i < alfabeto.length; i++) {
			if (c.equals(alfabeto[i]))
				pos = i; // a cual elemento del alfabeto
		}
		return tablaMin[estadoActual][pos]; // se obtiene el estado resultante
	}
	
	
	//funcion para encontrar el estado en la clase minimizada correspondiente
	private static int findStateMin(int estadoActual){
		for(int j=0; j< names.length; j++){//recorre el numero de clases que hay
			String n = names[j];
			for (int i = 0; i < n.length(); i++) { // el largo de cada clase,( cuantos estados tiene)
				int tempo = Integer.parseInt(n.substring(i, i + 1));
				if (estadoActual == tempo)
					return j;
			}
		}
		return 6;
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
		String secFinalMin = convertirCadena(cadenaUsuario);

		estFinal=Integer.valueOf(String.valueOf(secFinalMin.charAt(ronda-1)));
			if (a == estFinal) {
				System.out.println(a + " = estado final(" + estFinal + ")");
				return true;
		}
		return false;
	}
	
	public Boolean evaluar() {//mostraba un mensaje de aceptado, pero mejor solo mostrara si el usuario se equivoca
		String w = cadenaUsuario;
		
			w=convertirCadena(w);
		
			boolean resultado = validar(w,cadenaUsuario);// valida la cadena y recibe un true o false de resultado
			
			if (resultado) {
				//JOptionPane.showConfirmDialog(null, " w = " + cadenaUsuario + " fue ACEPTADA por el AFD Simon", "Resultado",
				//		JOptionPane.PLAIN_MESSAGE, 1);
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
	
	//como la tabla de transiciones esta minimizada, esta funcion sirve para cambiar el numero del boton 
	//presionado por el indice en la tabla minmizada.
	private static String convertirCadena(String w) {
		String w2="";
		for(int i=0; i<w.length(); i++){
			w2 += String.valueOf(findStateMin(Integer.parseInt(w.substring(i, i + 1))));
			System.out.println("w["+i+"] = "+w.substring(i, i + 1)+" - w2 = "+w2.substring(i, i + 1));
		}
		return w2;
	}

	public void limpiar(){
		estFinal=6;
		cadenaUsuario="";
		cadenaSimon="";
	}
}
