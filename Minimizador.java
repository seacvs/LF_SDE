package proyecto.LF_SDE;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Minimizador {
	public static int trans = 42; //elemntos en la matriz estados * alfabeto
	public static int est = 7; // cantidad de estados totales NUMERO DE ESTADOS
	public static int alf = 6; // cantidad de elementos en el alfabeto
	public static String alfabeto[]={"0","1","2","3","4","5"}; // arreglo con los elemntos del alfabeto
	public static String states[]={"0","1","2","3","4","5","6"};// arreglo con los elementos del alfabeto
	public static int tabla[][];// tabla de transiciones
	public static String primerLetra = "";// letra a mostrar en la tabla
	public static int intStates[]={0,1,2,3,4,5,6}; // los estados solo con la etiqueta en una
	// posicion del areglo
	public static String strStates = "";// la cadena de estados pero
	// concatenados para nombrar etioqutas
	public static int intFinal=0;// arreglos finales etiquetados
	public static int intInicial=0;// arreglos finales etiquetados
	public static Equ clasesEqu[];// arreglo de clases de equivalencia
	public static int tempClases = 0;// numero de clases que hay al momento.
	public static boolean repeat = true;// bandera para repetir hasta que no
	// haya cambios en los estados, en el
	// proceso de reducir
	public static String[] nombres; 
	public static int tablaMin[][];
	

	// recorre el arreglo de estados finales y compara para ver si el dato del
	// par es final o no
	private static boolean marcarFinal(int a) {
			if (a == intFinal)
				return true;
		return false;
	}

	// busca en la tabla de transiciones, la transición resultante de (p,q)
	public static int obtenerT(int q, int a) {
		return tabla[q][a];
	}
	
	// funcion para obtener los resultados, hacer las transiciones y imprimir
	// cada transicion final, todo se concatena a un string final
	private static void res() {
		tablaMin=new int[tempClases][alf];
		//System.out.print("\nOrden de la tabla minimizada\n");
		int c=0;
		for (Equ e : clasesEqu){
			if(e!= null){
				nombres[c]=e.name;
			//System.out.print(e.name + "  ");
			c++;
			}
		}
		System.out.println();
		String finalStr = "\nTransiciones AFD Minimizado";
		String results[] = new String[tempClases];
		for (int i = 0; i < tempClases; i++) {
			results[i] = "";
			for (int q = 0; q < alf; q++) { 
					int p = Integer.parseInt(String.valueOf(clasesEqu[i].name.charAt(0)));
					results[i] += "( " + clasesEqu[i].name + " ," + alfabeto[q] + " ) ---> " + findClass(obtenerT(p, q))+ "\n";
					tablaMin[i][Integer.valueOf(alfabeto[q])] =  findClassIterator(obtenerT(p, q));
					//System.out.println("PRUEBAS: "+clasesEqu[i].name  +"  tablaMin["+i+"]["+Integer.valueOf(alfabeto[q])+ ")] = "+ findClassIterator(obtenerT(p, q)));
			}
		}

		finalStr += ("\nTotal de Estados: {");
		try {
			for (Equ e : clasesEqu)
				finalStr += (e.name + "  ");
		} catch (Exception e) {
		}

		finalStr += ("}\nEstado Inicial: ");
		finalStr += (findClass(Integer.valueOf(Minimizador.intInicial)) + "\n");

		String temp = "";
		finalStr += ("Estado Final: {");
		 int es = intFinal;
			temp += findClass(es) + ",";
		
		String tempFinals[] = temp.split(",");// obtenemos los finales
		Stream<String> stream = Stream.of(tempFinals).distinct();// unicamente los distintos
		java.util.List<String> lista = stream.collect(Collectors.toList());
		for (String s : lista)
			finalStr += s + " "; // se añaden a la cadena final de resultados
		finalStr += ("}\nTransiciones:\n");
		for (int i = results.length - 1; i >= 0; i--) {
			finalStr += (results[i] + "\n");
		}
		
		System.out.println(finalStr);
		//JOptionPane.showMessageDialog(null, finalStr, "Resultados del AFD reducido", 1);
		
	}

	// esta funcion me sirvio para saber encontrar el nombre de la clase que
	// contenga el elemento buscaodo
	private static String findClass(int a) {
		for (int i = 0; i < tempClases; i++) {
			try {
				if (clasesEqu[i].findElement(a)) {
					return clasesEqu[i].name;
				}
			} catch (Exception e) {
			}
		}
		return "Not Found";
	}
	
	private static int findClassIterator(int a) {
		for (int i = 0; i < tempClases; i++) {
			try {
				if (clasesEqu[i].findElement(a)) {
					return i;
				}
			} catch (Exception e) {
			}
		}
		return 7;
	}

	// esta funcion fue implementada para crear una nueva clase de equiv.
	// es llamada cuando se divide una clase ya creada, y la parte dividida
	// se agrega al final de las clases de equvivalencia, como la siguiente
	// clase que seguiria usando el algoritmo a mano
	private static void createEquClass(String s) {
		boolean ready = false;
		for (int i = 0; i < clasesEqu.length; i++) {
			if (ready)
				break;
			if (clasesEqu[i] == null) {
				clasesEqu[i] = new Equ();
				clasesEqu[i].setArrayElement(s);
				tempClases++;
				ready = true;
			}
		}
	}

	// Se crean las clases de equivalencia, las finales y no finales
	private static void clasesEq() {
		String eqF = "";
		String eqN = "";

		// clases de equivalencia finales
			eqF += String.valueOf(intFinal);

		for (int i = 0; i < intStates.length; i++) { // clases de equivalencia
			// no finales
			int temp = intStates[i];
			if (marcarFinal(temp) == false)
				eqN += temp;
		} // se llenan las clases creadas como las finales y no finales
		clasesEqu = new Equ[est];
		clasesEqu[0] = new Equ();
		clasesEqu[0].setArrayElement(eqF);// el nombre se asigna en este momento
		tempClases++;
		clasesEqu[1] = new Equ();
		clasesEqu[1].setArrayElement(eqN);// el nombre se asigna en este momento
		tempClases++;
	}

	/*
	 * en esta funcion de reducir, ya con las clases creadas empieza a ver si
	 * las transiciiones de cada elemento de la clase pertence a la clase a la
	 * cual eprtenece, ya que si no pertenece significa que esa no debe de su
	 * clase y se divide la clas en dos, aquellos a los que si pertene y los que
	 * no. Termina llamandoa calcular las transiciones de las clases creadas.
	 */
	public static void reducir() {
		repeat = false;
		int size = 0;
		Equ eq = new Equ();
		String s = "";
		int transitions[] = null;

		for (int i = 0; i < est; i++) {

			if (clasesEqu[i] == null)// punto de quiebre del ciclo
				break;

			//System.out.println("Estados = " + est + " y tempClases = " + tempClases);
			for (Equ q : clasesEqu) {
				if (q == null)
					break;
				//System.out.println(q.toString());
			}

			eq.elements = clasesEqu[i].elements;
			eq.name = clasesEqu[i].name;
			size = eq.name.length();
			transitions = new int[size * alf];

			if (size == 1 || size == 0)
				continue;
			else {
				s = eq.name;
				for (int j = 0, x = 0; j < size; j++)
					for (int k = 0; k < alf; k++, x++) {
						int p = Integer.parseInt(s.substring(j, j + 1));
						transitions[x] = obtenerT(p, k);
						//System.out.println("transición de " + s + ": (" + p + ", " + alfabeto[k] + ") -> " + transitions[x]);
					}
			}
			tranEqu(transitions, eq, i);
		}
	}

	// cacula por cada transicion d ela clase se obtiene cada elemento de la
	// clase y se calcula ss nuevos elementos si no pertenece a esa clase
	private static void tranEqu(int[] trans, Equ eq, int claseEquiID) {
		int estados[] = new int[eq.name.length()];
		String resultados[] = new String[trans.length];

		for (int j = 0; j < eq.name.length(); j++) {
			int num = Integer.parseInt(eq.name.substring(j, j + 1));
			estados[j] = num;
		}
		int contadorIguales = 0;
		int estDiferente = -1;
		String strDif = "";
		// si el estado de la clase, pertenece a su misma clasecontinuea, sino
		// se pone el nombre del que no pertenece
		for (int i = 0; i < trans.length; i++) {
			if (eq.findElement(trans[i])) {
				resultados[i] = eq.name;
			} else {
				if (estDiferente == -1) {
					estDiferente = trans[i];
					strDif += eq.name.substring(i / alf, (i / alf) + 1);
				} else if (estDiferente == trans[i]) {
					contadorIguales++;
					if (strDif.indexOf(eq.name.substring(i / alf, (i / alf) + 1)) == -1)
						strDif += eq.name.substring(i / alf, (i / alf) + 1);
				}
				resultados[i] = "odd"; // valor de p, no la transicion
			}
		}
		if (contadorIguales == trans.length)
			return;

		for (int i = 0; i < resultados.length; i++) {
			if (resultados[i] == "odd")
				resultados[i] = strDif;
		}
		String clasesResultantes[];
		String a = eq.name;
		for (int k = 0; k < resultados.length; k++) {
			if (a.equals(resultados[k])) {

			} else {
				clasesResultantes = new String[2];
				clasesResultantes[0] = a.replaceAll(resultados[k], "");
				clasesResultantes[1] = resultados[k];
				clasesEqu[claseEquiID] = new Equ();// se removio el estado que
													// no pertenecia a la clase
				clasesEqu[claseEquiID].setArrayElement(clasesResultantes[0]);
				// los elementos de la clase
				createEquClass(clasesResultantes[1]);// agrega una neuva clase
														// al final de la ultima
				repeat = true;// hubo cambios por lo que se debe volver a
								// repetir el proceso
				break;
			}
		}
	}

	public Minimizador(int tab[][], int estFinal, int estInicial) {
		super();
		repeat=true;//volvemos a iniciar la minimización
		tempClases=0;
		tabla=tab;
		intFinal= estFinal;
		intInicial = estInicial;
		clasesEq(); // crea las primeras clases de quivalencia,(finales y no
					// finales).
		while (repeat)// se repite hasta que no haya cambios, en las clases de
			reducir(); // esas clases creadas, se comprueban sus transiciones y
					// se reduce las veces que se dividan las clases de quivalencia
		nombres=new String[tempClases];//con estos nombres, sabremos que elementos pertenecen
		res();							// a que columna de la tabla minimizada
	}
}