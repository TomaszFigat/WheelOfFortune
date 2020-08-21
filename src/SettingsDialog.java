import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Orientation;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;


import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import javafx.scene.control.Button;
@SuppressWarnings("restriction")
public class SettingsDialog{
	GridPane gp;
	TextArea tA;
	String actualWord;
	Button b1,b2,b3;
	ObservableList<String> OblUsedPhrases;
	 ObservableList<String> OblPhrases;
	 ListView<String> usedPhrases;
	 ListView<String> phrases;
	 Stage newWindow;
public SettingsDialog( ObservableList<String> wordsList) {
	
	gp=new GridPane();
	Label uPhrasesLb = new Label("Solved Phrases: ");
    Label phrasesLb = new Label("Phrases: ");
	OblUsedPhrases =FXCollections.<String>observableArrayList();
    OblPhrases = wordsList;//FXCollections.<String>observableArrayList();
    
    usedPhrases = new ListView<>(OblUsedPhrases);
    usedPhrases.setOrientation(Orientation.VERTICAL); 
    usedPhrases.setPrefSize(200, 250);
    
    phrases = new ListView<>(OblPhrases);
    phrases.setOrientation(Orientation.VERTICAL);
    phrases.setPrefSize(200, 250);
    
    VBox uphrSelection = new VBox();
    uphrSelection.setSpacing(10);
    uphrSelection.getChildren().addAll(uPhrasesLb,usedPhrases);
    

    VBox phrSelection = new VBox();
    phrSelection.setSpacing(10);
    phrSelection.getChildren().addAll(phrasesLb,phrases);
    
    tA = new TextArea();
    tA.setPrefSize(160, 90);
    b1 = new Button("Add");
    b2 = new Button("<");
    b3 = new Button(">");
    b1.setOnAction((e)->{
    	String potential = tA.getText();
    	boolean status=false;
    	for(String x : wordsList) {
    		if(x.equals(tA.getText())){
    			showAlertWithHeaderText();
//    			JOptionPane.showMessageDialog(null, "Takie haslo juz istnieje");
    			status=true;
    			break;
    		}
    		
    	}
    	
    	for(String x : OblUsedPhrases) {
    		if(x.equals(tA.getText())){
  //  			JOptionPane.showMessageDialog(null, "Takie haslo juz istnieje");
    			status=true;
    			break;
    		}
    	}
    	 if(!status)
    	OblPhrases.add(potential);
    });
    b2.setOnAction((e)->{
    	String potential = phrases.getSelectionModel().getSelectedItem();
    	if(potential!=null) {
    		if(!potential.equals(this.actualWord)) {
    		phrases.getSelectionModel().clearSelection();
    		OblPhrases.remove(potential);
    		OblUsedPhrases.add(potential);
    		}
    		else
    			showAlert();
    	}
    });
    
    b3.setOnAction((e)->{
    	String potential = usedPhrases.getSelectionModel().getSelectedItem();
    	if(potential!=null) {
    		if(!potential.equals(this.actualWord)) {
    		usedPhrases.getSelectionModel().clearSelection();
    		OblUsedPhrases.remove(potential);
    		OblPhrases.add(potential);
    		}
    		else
    			showAlert();
    	}
    });
    
    VBox functions = new VBox();
    functions.setSpacing(10);
    functions.getChildren().addAll(tA,b1,b2,b3);
    
    gp.setHgap(10);
    gp.setVgap(5);        
    gp.addColumn(0, uphrSelection);
    gp.addColumn(1, phrSelection);
    gp.addColumn(2, functions);
    
    gp.setStyle("-fx-padding: 10;" +
            "-fx-border-style: solid inside;" +
            "-fx-border-width: 2;" +
            "-fx-border-insets: 5;" +
            "-fx-border-radius: 5;" +
            "-fx-border-color: blue;");
    Scene scene = new Scene(gp);
    newWindow = new Stage();
    newWindow.setTitle("Settings");
    newWindow.setScene(scene);
    newWindow.setX(400 + 200);
    newWindow.setY(400 + 100);

//    newWindow.show();
}
private void showAlertWithHeaderText() {
    Alert alert = new Alert(AlertType.INFORMATION);
    alert.setTitle("Adding new phrase");
    alert.setContentText("This phrase already exists");

    alert.showAndWait();
}
private void showAlert() {
    Alert alert = new Alert(AlertType.INFORMATION);
    alert.setTitle("Phrase settings");
    alert.setContentText("This phrase is active");

    alert.showAndWait();
}


public void newWord(String word) {
		
		OblPhrases.remove(word);
		OblUsedPhrases.add(word);
		
}
}
