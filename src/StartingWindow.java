import javafx.scene.control.TextField;
import javafx.collections.FXCollections;
	import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
	import javafx.scene.control.Label;
	import javafx.scene.control.ListView;
	import javafx.scene.layout.GridPane;
	import javafx.scene.layout.VBox;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import javafx.scene.Scene;
	import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
	@SuppressWarnings("restriction")
	public class StartingWindow{
		 GridPane gp;
		
		 Button b1,b2,b3,b4;
		 ObservableList<String> phrasesInGame;
		 ObservableList<String> allPhrases;
		 ListView<String> phrInGame;
		 ListView<String> phrases;
		 TextField tf;
		 ArrayList<String> wordsAll;
	public StartingWindow() {
		wordsAll = new ArrayList<>();
		tf = new TextField();
		tf.setPrefSize(200, 100);
		gp=new GridPane();
		Label uPhrasesLb = new Label("Pick Words: ");
	    Label phrasesLb = new Label("All Words: ");
	    
	    
	    try {
			BufferedReader br = Files.newBufferedReader(Paths.get("Phrases.txt"));
			String line;
			while((line = br.readLine())!=null){
				wordsAll.add(line);
			}
		} catch (IOException e1) {
			System.out.println("brak takiego pliku");
			e1.printStackTrace();
		}
	    
	    allPhrases = FXCollections.<String>observableArrayList(wordsAll);
		phrasesInGame = FXCollections.<String>observableArrayList();
	    
	    
	    phrInGame = new ListView<>(phrasesInGame);
	    phrInGame.setOrientation(Orientation.VERTICAL); 
	    phrInGame.setPrefSize(200, 250);
	    
	    phrases = new ListView<>(allPhrases);
	    phrases.setOrientation(Orientation.VERTICAL);
	    phrases.setPrefSize(200, 250);
	       
	    b1 = new Button("Ok");
	    b2 = new Button("<");
	    b2.setOnAction((e)->{
	    	String potential = phrases.getSelectionModel().getSelectedItem();
	    	if(potential!=null) {
	    		phrases.getSelectionModel().clearSelection();
	    		allPhrases.remove(potential);
	    		phrasesInGame.add(potential);
	    		
	    	}
	    });
	    b3 = new Button(">");
	    b3.setOnAction((e)->{
	    	String potential = phrInGame.getSelectionModel().getSelectedItem();
	    	if(potential!=null) {
	    		phrInGame.getSelectionModel().clearSelection();
	    		phrasesInGame.remove(potential);
	    		allPhrases.add(potential);
	    	}
	    });
	    b4 = new Button("Add");
	    b4.setPrefWidth(200);
	    b4.setOnAction((e)->{
	    	FileWriter file;
			try {
				file = new FileWriter("Phrases.txt", true);
			
	    	BufferedWriter out = new BufferedWriter(file);
	    	if(tf.getText()!=null) {
	    		out.write("\r\n");
	    		out.write(tf.getText());
	    		
	    		allPhrases.add(tf.getText());
	    		tf.setText("");
	    	}
	    	out.close();} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	    });
	    b1.setPrefWidth(40);
	    b2.setPrefWidth(40);
	    b3.setPrefWidth(40);
	    
	    VBox uphrSelection = new VBox();
	    uphrSelection.setSpacing(10);
	    uphrSelection.getChildren().addAll(uPhrasesLb,phrInGame);
	    
	    VBox buttons = new VBox();
	    buttons.setSpacing(5);
	    buttons.setPadding(new Insets(50,0,0,0));
	    buttons.getChildren().addAll(b2,b3,b1);
	    
	    VBox phrSelection = new VBox();
	    phrSelection.setSpacing(10);
	    phrSelection.getChildren().addAll(phrasesLb,phrases);
	    
	    VBox addSection = new VBox();
	    addSection.setSpacing(10);
	    addSection.setPadding(new Insets(50,0,0,0));
	    addSection.getChildren().addAll(b4,tf);
	    

	    
	    gp.setHgap(10);
	    gp.setVgap(5);        
	    gp.addColumn(0, uphrSelection);
	    gp.addColumn(1, buttons);
	    gp.addColumn(2, phrSelection);
	    gp.addColumn(3, addSection);
	    
	    
	    gp.setStyle("-fx-padding: 10;" +
	            "-fx-border-style: solid inside;" +
	            "-fx-border-width: 2;" +
	            "-fx-border-insets: 5;" +
	            "-fx-border-radius: 5;" +
	            "-fx-border-color: blue;");
	    Scene scene = new Scene(gp);
	    
	    Stage newWindow = new Stage();
	    newWindow.setTitle("Settings");
	    newWindow.setScene(scene);
	    newWindow.setX(200);
	    newWindow.setY(200);
	   
	    newWindow.show();
	    
	    b1.setOnAction((e)->{
	    	if(phrasesInGame.isEmpty())
	    		showAlertWithHeaderText();
	    	else {
	    	newWindow.close();
	    	  new MainWindow(phrasesInGame);
	    	
	    	}
	    });
	    
	
	}
	private void showAlertWithHeaderText() {
	    Alert alert = new Alert(AlertType.INFORMATION);
	    alert.setTitle("Choosing words");
	    alert.setContentText("List is empty!");

	    alert.showAndWait();
	}
	}


