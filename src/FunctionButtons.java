import java.util.ArrayList;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.control.Label;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
@SuppressWarnings("restriction")
public class FunctionButtons extends GridPane implements CheckLetterListener,NewWordListener{
	Button settings;
	TextField points;
	Rectangle[] lifes;
	Label point;
	FlowPane fp = new FlowPane();
	int money;
	SettingsDialog settingsDialog;
	ArrayList<GameOverListener> gameOverListener = new ArrayList<>();
public FunctionButtons( ObservableList<String> wordsList) {
	money=0;
	settingsDialog = new SettingsDialog(wordsList);
	Font font = new Font("Courier",30);
	 this.setPadding(new Insets(5,5,5,5));
	point = new Label("Money: ");
	
	settings = new Button("Settings");
	points = new TextField();
	
	//roll.setStyle(("-fx-background-color: #aa2af3"));
	
	
	settings.setFont(font);
	point.setFont(font);
	fp.setPadding(new Insets(10));
	lifes = new Rectangle[3];
	for (int i=0; i<lifes.length;i++) {
		lifes[i]= new Rectangle(60,60,Color.GREEN);
		lifes[i].setStroke(Color.BLACK);
		lifes[i].setStrokeWidth(3);
	}
	point.setPrefSize(120, 50);
	points.setPrefSize(300, 50);
	
	points.setEditable(false);
	fp.getChildren().addAll(point,points,lifes[0],lifes[1],lifes[2]);

	
	fp.setPrefSize(640,50);
	settings.setPrefSize(640, 50);
	//point.setPrefSize(50, 60);	
	//points.setPrefSize(320, 60);
	settings.setOnAction((e)->{
		settingsDialog.newWindow.show();
	});
	this.setVgap(1);
		this.add(fp, 0, 0);
	this.add(settings, 0,1);
	
}

@Override
public void substrucktLife() {
	boolean status=false;
	for(int i=lifes.length-1;i>0;i--) {
		if(lifes[i].getFill()==Color.GREEN) {
			lifes[i].setFill(Color.RED);
			status=true;
			break;
		}
	}
	if(!status) {
		showAlertWithHeaderText();
	}
	
}
@Override
public void addPrize(String prize) {
	switch(prize) {
	case "-50":
		this.money=this.money/2;
		this.points.setText(Integer.toString(this.money));
		break;
	case "+50":
		this.money=this.money+this.money/2;
		this.points.setText(Integer.toString(this.money));
		break;
	default :
		this.money+=Integer.parseInt(prize);
		this.points.setText(Integer.toString(this.money));
	}
	
	if(this.money<0) {
		this.money=0;
		this.points.setText(Integer.toString(this.money));
	}
}
private void showAlertWithHeaderText() {
    Alert alert = new Alert(AlertType.INFORMATION);
    alert.setTitle("Adding new phrase");
    alert.setHeaderText("GAME OVER");
    alert.setContentText("GAME OVER");

    alert.showAndWait();
    for(GameOverListener x : gameOverListener) {
    	x.gameOver();
    }
    
}
public void addGameOverListener(GameOverListener gameOver) {
	gameOverListener.add(gameOver);
}

@Override
public void newWord(String word) {
	settingsDialog.newWord(word);
	
}

@Override
public void actualWord(String actualWord) {
	System.out.println("tu powinno byc actualen");
	settingsDialog.actualWord=actualWord;
	
}



@Override
public void win() {
	 Alert alert = new Alert(AlertType.INFORMATION);
	    alert.setTitle("WIN WIN WIN");
	    alert.setHeaderText("CONGRATULATION");
	    alert.setContentText("THIS IS YOUR MONEY NOW!!!!  "+this.money);

	    alert.showAndWait();
	    for(GameOverListener x : gameOverListener) {
	    	x.gameOver();
	    }
	
}


}
