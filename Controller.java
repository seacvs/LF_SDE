package proyecto.LF_SDE;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import javax.swing.JOptionPane;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class Controller implements Initializable {
	
	@FXML private RadioButton rb[] = new RadioButton[50];
	@FXML private Button btn_0;
	@FXML private Button btn_1;
	@FXML private Button btn_2;
	@FXML private Button btn_3;
	@FXML private Button btn_4;
	@FXML private Button btn_5;
	@FXML private Button play;
	@FXML private Button info;
	@FXML private HBox hboxrb;
	@FXML private HBox hBoxTitle;
	@FXML private VBox vBoxCenter;

	public String[] mColors = { "#39add1", "#3079ab", "#c25975", "#e15258", "#f9845b", "#838cc7", "#7d669e", "#53bbb4",
			"#51b46d", "#e0ab18", "#637a91", "#f092b0", "#b7c0c7", "#449353", "#b5ddbd", "#4f7cac", "#80e4ed",
			"#6b6b6b", "#2c7cff", "#3a606e", "#ab968e", "#735144", "#c0c0c0", "#6ddcdc", "#747474", "#575757",
			"#ae4517", "#87351a", "#e1bd4e", "#ab9781", "#818286" };
	private int counter = 0;
	private int round = 3;
	private String btnColors[] = new String[6];
	private int[] finalSequence = new int[50];
	private int[] userSequence = new int[50];
	private String strFinal = "";
	String instrucciones = "Simón es un juego educativo para probar el ingenio, los reflejos y la memoria.Un desafío permanente al azar, la habilidad y la observación.\n"+
							"A cada jugada mostrará un color nuevo que tú tendrás que repetir siempre desde el principio en el mismo orden formando una cadena de colores"+
							"\nPara ello harás clic con el ratón en el color adecuado, tratando de repetir la secuencia de Simón, y al final de cada ronda un AFD evaluara si la secuencia ingresada"+
							" fue la correcta.";
	AFD afd;
	private int perRound=1;

	@Override//funcion inicialq que da colores, crea los radioButtons y acomoda los contenedores
	public void initialize(URL arg0, ResourceBundle arg1) {
		hBoxTitle.setStyle("-fx-background-color: #738393");
		vBoxCenter.setStyle("-fx-background-color: #E5F1F9");
		for (int i = 0; i < 50; i++) {
			rb[i] = new RadioButton();
			rb[i].setPrefWidth(20);
			rb[i].setId("rb" + (i + 1));
			rb[i].setDisable(true);
			hboxrb.getChildren().add(rb[i]);
		}
		startColor();
	}

		//Funcion inicial donde se solo se ejecuta la primera vez para crear la secuencia, colores, inicializar las cadenas 
		// y agregar las transiciones del automata
	private void start() {
		afd= new AFD();
		afd.inicializarTransiciones(); //llena los estados hacia el error, ya que no hay transiciones aun.
		strFinal = "";
		afd.limpiar();
		userSequence = new int[50];
		finalSequence = new int[50];
		round = 3;//numero de botones que se mostraran de la secuencia
		counter = 0; //contador para saber cuantas veces se ha presionado un boton por el usuario
		randomColorBtns();// colores random a los botones
		createSequence();//crea la secuencia que se usara hasta el fin de ese juego		
		afd.agregarTransiciones(strFinal, round);//agrega las transiciones con la secuencia, hasta la ronda actual

		try {
			showSequence();//muestra con hilos la secuencia, bajando el atributo del opaco de los botones por 0.7 segundos
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	//Funcion iterativa la cual se ejecuta cada vez que el usuario acerte en la secuencia,dentro de esta funcion 
	// se agregan las nuevas transiciones como vamos avanzando de ronda, se actualizan los radiobuttons que marcan la ronda actual
	// y muestra la secuencia.
	private void repeat() {
		afd= new AFD();
		afd.inicializarTransiciones();
		afd.limpiar();

		//afd.setCadenaUsuario(strUser);//envia la cadena que el usuario ingreso mediante los botones
		afd.agregarTransiciones(strFinal, round);//agrega las transiciones con la secuencia, hasta la ronda actual
		counter = 0;//contador para saber cuantas veces se ha presionado un boton por el usuario
		refreshRadioBtns();//actualiza los botones superiores que indican el numero de la rona actual
		try {
			showSequence();//muestra con hilos la secuencia, bajando el atributo del opaco de los botones por 0.7 segundos
			cleanRadioButtons();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	//Función generadora de secuencia, de numero random entre 0-5
	private void createSequence() {
		
		Random random = new Random(); //crea la secuencia y la guarda en la variable de la clase
		for (int i = 0; i < 50; i++) { // strFinal donde se enviara al AFD, desde la funcion 
			int r =  random.nextInt(((5 - 0)) + 1); // de la clase AFD, AFD.agregartransiciones(secuencia, ronda actual)
			finalSequence[i] = r;
			strFinal += String.valueOf(r);
		}
	
		
		/*
		int r[]= {1,2,3,4,5,4,3,2,1,0,1,2,3,4,5,4,3,2,1,0,1,2,3,4,5,4,3,2,1,0,1,2,3,4,5,4,3,2,1,0,1,2,3,4,5,4,3,2,1,0};
		for(int i=0; i<50; i++){
			finalSequence[i] = r[i];
			strFinal += String.valueOf(r[i]);
		}
		*/
	}

	private void showSequence()throws InterruptedException  {
		System.out.print("\nRonda " + round + " : \n");
		Thread t1=new Thread();
			 t1= new Thread(()->{
				for (int i = 0; i < round; ++i){ 
					try {
						Thread.sleep(700);
						System.out.print(finalSequence[i]+"\n");
						String btn="btn_"+finalSequence[i];
						changeColor(btn,0.20);
						Thread.sleep(700);

					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					fillOpacity();
				}
			});
			t1.start();
			fillOpacity();
	}
	
	private void startColor() {
		btn_0.setStyle("-fx-background-color: #738393");
		btn_1.setStyle("-fx-background-color: #738393");
		btn_2.setStyle("-fx-background-color: #738393");
		btn_3.setStyle("-fx-background-color: #738393");
		btn_4.setStyle("-fx-background-color: #738393");
		btn_5.setStyle("-fx-background-color: #738393");
	}

	private void randomColorBtns() {
		Random random = new Random();
		btn_0.setStyle("-fx-background-color: " + mColors[random.nextInt(30 + 1)]);
		btnColors[0] = btn_0.getStyle();
		btn_1.setStyle("-fx-background-color: " + mColors[random.nextInt(30 + 1)]);
		btnColors[1] = btn_1.getStyle();
		btn_2.setStyle("-fx-background-color: " + mColors[random.nextInt(30 + 1)]);
		btnColors[2] = btn_2.getStyle();
		btn_3.setStyle("-fx-background-color: " + mColors[random.nextInt(30 + 1)]);
		btnColors[3] = btn_3.getStyle();
		btn_4.setStyle("-fx-background-color: " + mColors[random.nextInt(30 + 1)]);
		btnColors[4] = btn_4.getStyle();
		btn_5.setStyle("-fx-background-color: " + mColors[random.nextInt(30 + 1)]);
		btnColors[5] = btn_5.getStyle();
		play.setStyle("-fx-background-color: #cbff99");
	}

	@FXML
	private void play() {
		start();
		play.setText("Reiniciar");
		refreshRadioBtns();
	}

	@FXML
	private void selected(MouseEvent event) {
		if (counter == round) {
			checkSequence();
		} else {
			String s = event.getPickResult().getIntersectedNode().getId();
			int number = Integer.parseInt(s.substring(4));
			userSequence[counter] = number;
			afd.addCadenaUsuario(String.valueOf(number));
			fillRadioButtons(number);
			counter++;
			if (counter == round)
				checkSequence();
		}
	}

	private void checkSequence() {
		int seqFinal=Integer.parseInt(strFinal.substring(round-1, round));
		afd.setEstFinal(seqFinal);
		afd.setCadenaSimon(strFinal.substring(0, round));
		if(afd.evaluar()){
			round = (round + perRound <= 50) ? round + perRound : 50;
			refreshRadioBtns();
			correctRadioButtons();

			repeat();
			if (round == 50) {
				JOptionPane.showConfirmDialog(null, "Ganaste el juego", "Simon Dice y Evalua", JOptionPane.OK_OPTION,
						JOptionPane.INFORMATION_MESSAGE);
				play();
			}
		}else{
			play();
		}
	}

	private void refreshRadioBtns() {
		for (int i = 0; i < 50; i++) {
			rb[i].setSelected(false);
			rb[i].setStyle("");
			rb[i].setVisible(true);

		}
		for (int i = 49; i >= round; i--) {
			rb[i].setVisible(false);
		}
	}
	private void fillRadioButtons(int s) {
		if (counter <= 50) {
			rb[counter].setSelected(true);
			rb[counter].setStyle(btnColors[s]);
		}
	}
	
	private void correctRadioButtons() {
			 for(int i=0; i<round; i++)
					rb[i].setStyle("-fx-background-color: #738393");
	}
	
	private void cleanRadioButtons() {
		 for(int i=0; i<round; i++)
				rb[i].setStyle("");
	}

	private void fillOpacity() {
		btn_0.setOpacity(1);
		btn_1.setOpacity(1);
		btn_2.setOpacity(1);
		btn_3.setOpacity(1);
		btn_4.setOpacity(1);
		btn_5.setOpacity(1);
		play.setOpacity(1);
	}

	private void changeColor(String s,double op) {
		btn_0.setOpacity((btn_0.getId().equals(s)) ? op : 1);
		btn_1.setOpacity((btn_1.getId().equals(s)) ? op : 1);
		btn_2.setOpacity((btn_2.getId().equals(s)) ? op : 1);
		btn_3.setOpacity((btn_3.getId().equals(s)) ? op: 1);
		btn_4.setOpacity((btn_4.getId().equals(s)) ? op : 1);
		btn_5.setOpacity((btn_5.getId().equals(s)) ? op : 1);
		play.setOpacity((play.getId().equals(s)) ? op-.15 : 1);
	}

	@FXML
	private void overButton(MouseEvent event) {
		String s = event.getPickResult().getIntersectedNode().getId();
		changeColor(s,0.50);
	}

	@FXML
	private void exitButton(MouseEvent event) {
		fillOpacity();
	}

	@FXML	
	private void infoBtn( ) {
		JOptionPane.showMessageDialog(null, this.instrucciones, "Información  - Sebastián Aceves is702810", JOptionPane.INFORMATION_MESSAGE);
	}
}

