package proyecto.LF_SDE;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

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
	private  int contador = 0;
	private int round=5;
	private String btnColors[]= new String[6];

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
			for (int i = 0; i < 50; i++) {
				rb[i] = new RadioButton();
				rb[i].setPrefWidth(20);
				rb[i].setId("rb" + (i + 1));
				rb[i].setDisable(true);
				hboxrb.getChildren().add(rb[i]);
			}
			
			randomColorBtns();
		
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
		contador=0;
		play.setText("Reiniciar");
		randomColorBtns();
		for (int i = 0; i < 50; i++) {
			rb[i].setSelected(false);
			rb[i].setStyle("");
		
		}
		for(int i=49; i>=round;i--){
			rb[i].setVisible(false);
		}
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

	@FXML
	private void selected(MouseEvent event) {
		String s = event.getPickResult().getIntersectedNode().getId();
		int colorRb =Integer.parseInt(s.substring(4));
		fillRadioButtons(colorRb-1);
		contador++;
	}

	private void fillRadioButtons(int s) {
		if(contador<=50){
			rb[contador].setSelected(true);
			rb[contador].setStyle(btnColors[s]);
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

}
