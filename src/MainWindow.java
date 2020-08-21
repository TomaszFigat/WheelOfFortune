import javafx.scene.layout.BorderPane;

import javafx.collections.ObservableList;
import javafx.scene.Scene;

import javafx.stage.Stage;
@SuppressWarnings("restriction")
public class MainWindow extends BorderPane implements GameOverListener{
	Stage newWindow;
	Letters letters;
	FunctionButtons functionButtons;
	CircleRolling circleRolling;
	ObservableList<String> wordsList;
	public MainWindow( ObservableList<String> wordsList) {
		this.wordsList=wordsList;

		letters = new Letters(wordsList);
		functionButtons = new FunctionButtons(wordsList);
		circleRolling = new CircleRolling(letters);
		letters.addcheckLetterListener(functionButtons);
		letters.addNewWordListener(functionButtons);
		letters.startWord();
		letters.addNewWordListener(letters);
		letters.addNewWordListener(circleRolling);
		functionButtons.addGameOverListener(this);
		functionButtons.prefWidthProperty().bind(this.widthProperty());
		this.setTop(letters);
		this.setBottom(functionButtons);
		this.setCenter(circleRolling);
		Scene scene = new Scene(this);
		newWindow = new Stage();
		newWindow.setTitle("Wheel of Fortune");
		newWindow.setScene(scene);
		newWindow.setX(100);
		newWindow.setY(50);
		newWindow.setWidth(660);
		newWindow.setHeight(970);
		newWindow.show();
	}

	@Override
	public void gameOver() {

		newWindow.close();
		new StartingWindow();

	}





}
