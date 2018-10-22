package proyecto.LF_SDE;

import java.net.URL;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Random;
import java.util.ResourceBundle;
import javax.swing.JOptionPane;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

public class Controller implements Initializable {

	@FXML
	private RadioButton rb[] = new RadioButton[50];
	@FXML
	private Button btn_0;
	@FXML
	private Button btn_1;
	@FXML
	private Button btn_2;
	@FXML
	private Button btn_3;
	@FXML
	private Button btn_4;
	@FXML
	private Button btn_5;
	@FXML
	private Button play;
	@FXML
	private Button info;
	@FXML
	private HBox hboxrb;

	public String[] mColors = { "#39add1", "#3079ab", "#c25975", "#e15258", "#f9845b", "#838cc7", "#7d669e", "#53bbb4",
			"#51b46d", "#e0ab18", "#637a91", "#f092b0", "#b7c0c7", "#449353", "#b5ddbd", "#4f7cac", "#80e4ed",
			"#6b6b6b", "#2c7cff", "#3a606e", "#ab968e", "#735144", "#c0c0c0", "#6ddcdc", "#747474", "#575757",
			"#ae4517", "#87351a", "#e1bd4e", "#ab9781", "#818286" };
	private int counter = 0;
	private int round = 5;
	private String btnColors[] = new String[6];
	private int[] finalSequence = new int[50];
	private int[] userSequence = new int[50];
	private String strFinal = "", strUser = "";
	
	AFD afd = new AFD();
	private int perRound=1;


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		for (int i = 0; i < 50; i++) {
			rb[i] = new RadioButton();
			rb[i].setPrefWidth(20);
			rb[i].setId("rb" + (i + 1));
			rb[i].setDisable(true);
			hboxrb.getChildren().add(rb[i]);
		}
		startColor();

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

	private void start() {
		afd.rellenarTabla();
		strFinal = "";
		strUser = "";
		afd.setCadenaSimon(strFinal);
		afd.setCadenaUsuario(strUser);
		round = 5;
		counter = 0;
		randomColorBtns();
		createSequence();
		try {
			showSequence();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void repeat() {
		strUser = "";
		afd.setCadenaUsuario(strUser);
		counter = 0;
		refreshRadioBtns();
		try {
			showSequence();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void createSequence() {
		Random random = new Random();
		for (int i = 0; i < 50; i++) {
			int r =  random.nextInt(((5 - 0)) + 1);
			finalSequence[i] = r;
			strFinal += String.valueOf(r);
		}
	}

	private void showSequence()throws InterruptedException  {
		System.out.print("\nRonda " + round + " : ");

		for (int i = 0; i < round; i++) {
			//iluminati(finalSequence[i]);
			System.out.print(finalSequence[i]);
		}
		reColor();

	}

	public void iluminati(int btnNumber) throws InterruptedException {
		System.out.print(btnNumber);
		Thread hilo = new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					Thread.sleep(1500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				switch (btnNumber) {
				case 0:
					btn_0.setStyle("-fx-background-color: #000000 ");
					break;
				case 1:
					btn_1.setStyle("-fx-background-color: #000000");
					break;
				case 2:
					btn_2.setStyle("-fx-background-color: #000000 ");
					break;
				case 3:
					btn_3.setStyle("-fx-background-color: #000000 ");
					break;
				case 4:
					btn_4.setStyle("-fx-background-color: #000000 ");
					break;
				default:
					btn_5.setStyle("-fx-background-color: #000000 ");
					break;
				}
				
			}
		}) {
		};
		hilo.start();
		
	}
	
	
	public void iluminatiNoThread(int btnNumber) {
		System.out.print(btnNumber);
		Instant before =  Instant.now();
		switch (btnNumber) {
		case 0:
			btn_0.setStyle("-fx-background-color: #000000 ");
			break;
		case 1:
			btn_1.setStyle("-fx-background-color: #000000");
			break;
		case 2:
			btn_2.setStyle("-fx-background-color: #000000 ");
			break;
		case 3:
			btn_3.setStyle("-fx-background-color: #000000 ");
			break;
		case 4:
			btn_4.setStyle("-fx-background-color: #000000 ");
			break;
		default:
			btn_5.setStyle("-fx-background-color: #000000 ");
			break;
		}
		boolean flag=false;
		while(flag==false){
			Instant after =  Instant.now();
			long dif= ChronoUnit.MILLIS.between(before,after);
			if(dif==1500)
				flag=true;
		}
		//reColor();
		
	}


	private void reColor() {
		btn_0.setStyle(btnColors[0]);
		btn_1.setStyle(btnColors[1]);
		btn_2.setStyle(btnColors[2]);
		btn_3.setStyle(btnColors[3]);
		btn_4.setStyle(btnColors[4]);
		btn_5.setStyle(btnColors[5]);
	}

	private void startColor() {
		btn_0.setStyle("-fx-background-color: #006823");
		btn_1.setStyle("-fx-background-color: #006823");
		btn_2.setStyle("-fx-background-color: #006823");
		btn_3.setStyle("-fx-background-color: #006823");
		btn_4.setStyle("-fx-background-color: #006823");
		btn_5.setStyle("-fx-background-color: #006823");
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
			strUser += number;
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
			repeat();
			if (round == 50) {
				JOptionPane.showConfirmDialog(null, "Ganaste el juego", "Simon Dice y Evalua", JOptionPane.OK_OPTION,
						JOptionPane.INFORMATION_MESSAGE);
				play();
			}
		}else{
			play();

		}
		
	/*	System.out.println("\nStrFinal = " + strFinal + "\nstrUser = " + strUser);
		if (strFinal.substring(0, round).equals(strUser)) {
			JOptionPane.showConfirmDialog(null, "Cadena correcta", "Simon evaluo", JOptionPane.OK_OPTION,
					JOptionPane.INFORMATION_MESSAGE);
			round = (round + 5 <= 50) ? round + 5 : 50;
			refreshRadioBtns();
			repeat();
			if (round == 50) {
				JOptionPane.showConfirmDialog(null, "Ganaste el juego", "Simon Dice y Evalua", JOptionPane.OK_OPTION,
						JOptionPane.INFORMATION_MESSAGE);
				play();
			}
		} else {
			JOptionPane.showConfirmDialog(null, "Cadena incorrecta", "Simon evaluo", JOptionPane.OK_OPTION,
					JOptionPane.ERROR_MESSAGE);
			play();
		}
	*/	
	}

	private void fillRadioButtons(int s) {
		if (counter <= 50) {
			rb[counter].setSelected(true);
			rb[counter].setStyle(btnColors[s]);
		}
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

	private void changeColor(String s) {
		btn_0.setOpacity((btn_0.getId().equals(s)) ? 0.8 : 1);
		btn_1.setOpacity((btn_1.getId().equals(s)) ? 0.8 : 1);
		btn_2.setOpacity((btn_2.getId().equals(s)) ? 0.8 : 1);
		btn_3.setOpacity((btn_3.getId().equals(s)) ? 0.8 : 1);
		btn_4.setOpacity((btn_4.getId().equals(s)) ? 0.8 : 1);
		btn_5.setOpacity((btn_5.getId().equals(s)) ? 0.8 : 1);
		play.setOpacity((play.getId().equals(s)) ? 0.65 : 1);
	}

	@FXML
	private void overButton(MouseEvent event) {
		String s = event.getPickResult().getIntersectedNode().getId();
		changeColor(s);
	}

	@FXML
	private void exitButton(MouseEvent event) {
		fillOpacity();
	}
}

