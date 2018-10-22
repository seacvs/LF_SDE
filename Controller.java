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

public class Controller implements Initializable {

	@FXML
	private RadioButton rb[] = new RadioButton[50];
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
	private Button btn_6;
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
	private  int counter = 0;
	private int round=5;
	private String btnColors[]= new String[6];
	private int[] finalSequence= new int[50];
	private int[] userSequence= new int[50];
	private String strFinal="",strUser="";
	

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
	
	private void refreshRadioBtns(){
		for (int i = 0; i < 50; i++) {
			rb[i].setSelected(false);
			rb[i].setStyle("");
			rb[i].setVisible(true);

		}
		for(int i=49; i>=round;i--){
			rb[i].setVisible(false);
		}
	}
	
	
	private void start() {
		strFinal="";
		strUser="";
		round=5;
		counter=0;
		randomColorBtns();
		createSequence();
		showSequence();		
	}
	private void repeat() {
		strUser="";
		counter=0;
		refreshRadioBtns();
		showSequence();		
	}

	private void createSequence() {
		Random random = new Random();
		for(int i=0; i<50; i++){
			int r=1+random.nextInt(((6-1))+1);
			finalSequence[i]=r;
			strFinal+=String.valueOf(r);
		}
		
	}
	private void showSequence() {
		System.out.print("\nRonda "+round+" : ");
		for(int i=0; i< round; i++){
			int btnNumber = finalSequence[i];
				iluminati(btnNumber);
		}
	}
	private void iluminati(int btnNumber) {
		System.out.print(btnNumber);
		
		switch(btnNumber){
		case 1: btn_1.setStyle("-fx-background-color: #ffffff ");	
				waitSimon();	
				break;
		case 2: btn_2.setStyle("-fx-background-color: #ffffff ");	
				waitSimon();	
				break;
		case 3: btn_3.setStyle("-fx-background-color: #ffffff ");	
				waitSimon();	
				break;
		case 4: btn_4.setStyle("-fx-background-color: #ffffff ");	
				waitSimon();	
				break;
		case 5: btn_5.setStyle("-fx-background-color: #ffffff ");	
				waitSimon();	
				break;
		default:btn_6.setStyle("-fx-background-color: #ffffff ");	
				waitSimon();	
					break;		
		}
	}
	
	
	private void waitSimon()  {
			/*try{
				Thread.sleep(1000);
			}catch (Exception e){
				System.out.println("No lo hizo");
			}
			*/
			reColor();
	}
	
	private void reColor() {
		btn_1.setStyle(btnColors[0]);
		btn_2.setStyle(btnColors[1]);
		btn_3.setStyle(btnColors[2]);
		btn_4.setStyle(btnColors[3]);
		btn_5.setStyle(btnColors[4]);
		btn_6.setStyle(btnColors[5]);
	}
	private void startColor() {
		btn_1.setStyle("-fx-background-color: #006823");
		btn_2.setStyle("-fx-background-color: #006823");
		btn_3.setStyle("-fx-background-color: #006823");
		btn_4.setStyle("-fx-background-color: #006823");
		btn_5.setStyle("-fx-background-color: #006823");
		btn_6.setStyle("-fx-background-color: #006823");
	}

	private void randomColorBtns(){
		Random random = new Random();
		btn_1.setStyle("-fx-background-color: " + mColors[random.nextInt(30 + 1)]);
		btnColors[0]=btn_1.getStyle();
		btn_2.setStyle("-fx-background-color: " + mColors[random.nextInt(30 + 1)]);
		btnColors[1]=btn_2.getStyle();
		btn_3.setStyle("-fx-background-color: " + mColors[random.nextInt(30 + 1)]);
		btnColors[2]=btn_3.getStyle();
		btn_4.setStyle("-fx-background-color: " + mColors[random.nextInt(30 + 1)]);
		btnColors[3]=btn_4.getStyle();
		btn_5.setStyle("-fx-background-color: " + mColors[random.nextInt(30 + 1)]);
		btnColors[4]=btn_5.getStyle();
		btn_6.setStyle("-fx-background-color: " + mColors[random.nextInt(30 + 1)]);
		btnColors[5]=btn_6.getStyle();
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
		if(counter==round){
			checkSequence();
		}else{
			String s = event.getPickResult().getIntersectedNode().getId();
			int number =Integer.parseInt(s.substring(4));
			userSequence[counter]=number;
			strUser+=number;
			fillRadioButtons(number-1);
			counter++;
			if(counter==round)
				checkSequence();
		}
	}

	private void checkSequence() {
		System.out.println("\nStrFinal = "+strFinal+"\nstrUser = "+strUser);
		if(strFinal.substring(0, round).equals(strUser)){
			JOptionPane.showConfirmDialog(null, "Cadena correcta", "Simon evaluo", JOptionPane.OK_OPTION, JOptionPane.INFORMATION_MESSAGE);
			round=(round+5<=50)?round+5:50;
			refreshRadioBtns();
			repeat();
			if(round==50){
				JOptionPane.showConfirmDialog(null, "Ganaste el juego", "Simon Dice y Evalua", JOptionPane.OK_OPTION, JOptionPane.INFORMATION_MESSAGE);
				play();
			}
		}else{
			JOptionPane.showConfirmDialog(null, "Cadena incorrecta", "Simon evaluo", JOptionPane.OK_OPTION, JOptionPane.ERROR_MESSAGE);
			play();
		}
	}
	private void fillRadioButtons(int s) {
		if(counter<=50){
			rb[counter].setSelected(true);
			rb[counter].setStyle(btnColors[s]);
		}
	}

	private void fillOpacity() {
		btn_1.setOpacity(1);
		btn_2.setOpacity(1);
		btn_3.setOpacity(1);
		btn_4.setOpacity(1);
		btn_5.setOpacity(1);
		btn_6.setOpacity(1);
		play.setOpacity(1);
	}

	private void changeColor(String s) {
		btn_1.setOpacity((btn_1.getId().equals(s)) ? 0.8 : 1);
		btn_2.setOpacity((btn_2.getId().equals(s)) ? 0.8 : 1);
		btn_3.setOpacity((btn_3.getId().equals(s)) ? 0.8 : 1);
		btn_4.setOpacity((btn_4.getId().equals(s)) ? 0.8 : 1);
		btn_5.setOpacity((btn_5.getId().equals(s)) ? 0.8 : 1);
		btn_6.setOpacity((btn_6.getId().equals(s)) ? 0.8 : 1);
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
