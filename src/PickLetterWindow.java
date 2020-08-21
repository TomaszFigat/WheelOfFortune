
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.util.ArrayList;
import java.util.regex.Pattern;

import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
@SuppressWarnings("restriction")
public class PickLetterWindow extends VBox {
	private Label label;
	private TextField text;
	private Button button;
	private Pattern pattern;

	ArrayList<RollingListener> rollingListener = new ArrayList<>();
	public PickLetterWindow(boolean []running,String prize,ArrayList<String> usedLetters) {
		this.setWidth(400);
		this.setHeight(300);
		Font font = new Font("Arial",40);
		Font font1 = new Font("MONOSPACED",60);
		label = new Label("Enter the letter: ");
		label.setPrefSize(300,100);
		label.setFont(font);
		text = new TextField();
		text.setPrefSize(100, 100);
		text.setFont(font1);
		button = new Button("Ok");
		button.setPrefSize(300, 100);

		this.getChildren().add(label);
		this.getChildren().add(text);
		this.getChildren().add(button);
		Scene scene = new Scene(this);

		Stage newWindow = new Stage();
		newWindow.setTitle("Pick a letter");
		newWindow.setScene(scene);
		newWindow.setX(750);
		newWindow.setY(300);		    
		newWindow.show();
		button.setOnAction((e)->{


			pattern = Pattern.compile("[a-zA-Z]");
			if(pattern.matcher(text.getText()).matches()) {
				boolean b=true;
				for(String x : usedLetters) {
					if(x.charAt(0)==text.getText().charAt(0)) {
						b=false;
						showAlertWithHeaderText();
						break;
					}
				}
				if(b) {
					System.out.println("iqw");
					usedLetters.add(text.getText());
					for(RollingListener x : rollingListener){

						x.checkSign(e,text.getText().charAt(0),prize);
					}	
					newWindow.close();

					running[0]=false;}

			}
			else
				showAlert();

		});	

	}
	public void addrollingListener(RollingListener rollingListener){
		this.rollingListener.add(rollingListener);
	}

	private void showAlertWithHeaderText() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Writing letter");
		//    alert.setHeaderText("GAME OVER");
		alert.setContentText("This letter was already used");
		alert.showAndWait();
	}
	private void showAlert() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Writing letter");
		//    alert.setHeaderText("GAME OVER");
		alert.setContentText("Enter only one letter");
		alert.showAndWait();
	}
}
