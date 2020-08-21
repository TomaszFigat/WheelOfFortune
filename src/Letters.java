import java.util.ArrayList;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
@SuppressWarnings("restriction")
public class Letters extends VBox implements RollingListener, NewWordListener{
	VBox vBox;
	Label []lab;
	char word[];
	String actualWord;
	FlowPane line[];
	int wordsCounter;
	ArrayList<CheckLetterListener> checkLetterListener = new ArrayList<>();
	ArrayList<NewWordListener> newWordListener = new ArrayList<>();
	
	ObservableList<String> wordsList;
	
	public Letters(ObservableList<String> wordsList) {
		this.wordsList=wordsList;
		constructor(wordsList);
		
	}
	public boolean checkWord() {
		for(int i=0; i<this.lab.length;i++) {
			System.out.println(lab[i].getText());
			if(!lab[i].getText().equals(" ")) {
				
				System.out.println(lab[i]);
				if(!lab[i].isVisible()) {
					return false;
				}
			}
			
		}
		return true;
	}
	@Override
	public void checkSign(ActionEvent evt, char sign,String prize) {
		boolean status=false;
		for(int i=0; i<this.lab.length;i++) {
			if(word[i]==sign||word[i]==sign+32||word[i]==sign-32) {
				lab[i].setVisible(true);
				status=true;
			}
			
		}
		if(!status) {
			 for(CheckLetterListener x : checkLetterListener){       	
	                x.substrucktLife();
	            }	
		}
		else
			for(CheckLetterListener x : checkLetterListener){       	
                x.addPrize(prize);
            }
		if(checkWord()) {
			showAlertWithHeaderText();
			for(NewWordListener x : newWordListener){       	
                x.newWord(this.actualWord);
            }	
		}

	}
	public void addcheckLetterListener(CheckLetterListener checkLetterListener){
	    this.checkLetterListener.add(checkLetterListener);
	}
	public void addNewWordListener(NewWordListener newWordListener){
	    this.newWordListener.add(newWordListener);
	}
	private void showAlertWithHeaderText() {
	    Alert alert = new Alert(AlertType.INFORMATION);
	    alert.setTitle("CONGRATULATION");
	    alert.setHeaderText("YOU GUESSED IT");
	    alert.setContentText("Word: " + this.actualWord);

	    alert.showAndWait();
	}
	public void constructor(ObservableList<String> wordsList) {
		//this.vBox=new VBox();
		
		this.wordsCounter=1;
		this.actualWord = wordsList.get((int)(Math.random()*(wordsList.size()-1)));
		this.word=this.actualWord.toCharArray();
		
		for(NewWordListener x : newWordListener){       	
            x.actualWord(this.actualWord);
        }	
		
		this.setSpacing(5);
	
		this.setCenterShape(true);
		this.setVgrow(this, Priority.ALWAYS);
		//this.setPadding(new Insets(10,10,10,10));
		
		int spacing=0;
		for(int i=0;i<word.length;i++) {
			if(word[i]==' ')
				wordsCounter++;
		}
		line = new FlowPane[wordsCounter];	        
		lab = new Label[word.length];
		int whichLine=0;
		for(int j=0;j<line.length;j++) {
			line[j] = new FlowPane();
			
		}
		for(int i=0; i<word.length;i++) {
			lab[i] = new Label((Character.toString(word[i])));
			

			StackPane stack = new StackPane();
			lab[i].visibleProperty().set(false);
			Font font = new Font("Courier",30);
			lab[i].setFont(font);
			Rectangle rec =new Rectangle(wordsCounter<3 ? 60 : 35,wordsCounter<3 ? 60 : 35,Color.LIGHTGREY);
			rec.setArcHeight(3);
			rec.setArcWidth(3);
			rec.setStroke(Color.BLACK);
			rec.setStrokeWidth(3);
			rec.setStrokeType(StrokeType.OUTSIDE);
			if(word[i]!=' ') {
				stack.getChildren().addAll(rec,lab[i]);
				line[whichLine].getChildren().add(stack);
			spacing++;
			}
			//stack.getChildren().add(new Rectangle(30,30,Color.WHITE));
			if(word[i]==' '||i==word.length-1) {
				
				line[whichLine].setPadding(new Insets(0,0,0,330-(wordsCounter<3 ? 35 : 17.5)*(spacing)));
				whichLine++;
				spacing=0;
			}
			//t.getChildren().add(stack);

		}
		System.out.println("to jest line.length = =  ==" + line.length);
		//this.setAlignment(Pos.CENTER);
		for(int i=0;i<line.length;i++) {
			
			if(line.length==1)
				this.setPadding(new Insets(40,0,40,0));
			else
				this.setPadding(new Insets(0,0,0,0));
			this.getChildren().add(line[i]);
			
			
			
		}

	}
	@Override
	public void newWord(String word) {
		this.wordsList.remove(word);
		for(int i=0; i<this.line.length;i++) {
			this.getChildren().remove(line[i]);
			System.out.println("0--------------------0-");
		}
		if(wordsList.isEmpty()) {
			for(NewWordListener x : newWordListener){       	
	            x.win();
	        }	
		}
		else
		constructor(this.wordsList);
		
	}
	public void startWord() {
		for(NewWordListener x : newWordListener){       	
            x.actualWord(this.actualWord);
        }	
		
	}
	@Override
	public void actualWord(String actualWord) {
		
		
	}
	@Override
	public void win() {
		// TODO Auto-generated method stub
		
	}
}
