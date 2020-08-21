import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import javafx.geometry.Insets;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import java.util.ArrayList;

import javafx.animation.Animation;

@SuppressWarnings("restriction")
public class CircleRolling extends GridPane implements NewWordListener{

	private Image img1,img2;
	private ImageView iV1,iV2;
	private Timeline timeLine;

	private FlowPane fp;
	private Button roll;
	private boolean[] running;
	private Letters letters;
	private String prize;
	private ArrayList<String> usedLetters = new ArrayList<>();
	public CircleRolling(Letters letters) {
		this.letters = letters;
		running= new boolean[1];
		running[0]=false;
		timeLine = new Timeline();
		img1 = new Image("file:WheelKP.png");
		img2 = new Image("file:arrowPP.png");
		iV1 = new ImageView();
		iV1.setImage(img1);
		iV2 = new ImageView();
		iV2.setImage(img2);
		iV2.setFitWidth(100);
		fp = new FlowPane();
		fp.getChildren().add(iV2);
		fp.getChildren().add(iV1);
		roll = new Button("Roll");
		Font rollfont = new Font("Arial",60);
		roll.setFont(rollfont);
		roll.setPrefSize(640,110);

		roll.setOnAction((e)->{
			if(timeLine.getStatus()!=Animation.Status.RUNNING&&!running[0]) {				
				roll();
			}
			if(running[0]) {
				PickLetterWindow plw = new PickLetterWindow(running,this.prize,this.usedLetters);
				plw.addrollingListener(letters);
			}
		});
			
	
		this.setPadding(new Insets(4));
		this.setVgap(4);
		this.add(fp,0,0);
		this.add(roll,0,1);
	}

	
	public void roll() {
		timeLine = new Timeline();

		timeLine.setOnFinished((e)->{
			System.out.println("sajkjaksl");
			running[0]=true;
			this.prize=checkPrize(iV1);
			PickLetterWindow plw = new PickLetterWindow(running,this.prize,this.usedLetters);
			
			plw.addrollingListener(letters);
			
			
		});
		timeLine.getKeyFrames().add(new KeyFrame(Duration.seconds(5),
				new KeyValue(iV1.rotateProperty(), iV1.getRotate()+Math.random()*1500+500, Interpolator.EASE_BOTH)));
		timeLine.setCycleCount(1);
		timeLine.play();
		
	}//Math.random()*1500+500
	public String checkPrize(ImageView imgV) {
		System.out.println(imgV.getRotate()+ "  przed");
		while(imgV.getRotate()>=360) {
		 
			imgV.setRotate(imgV.getRotate()-360);
			
		}
		
		System.out.println(imgV.getRotate());
		int degrees =(int)iV1.getRotate();
		System.out.println("degresy  "+ degrees);
		if(degrees<=18)return "1000";
		if(degrees<=54)return "5000";
		if(degrees<=90)return "-1000";
		if(degrees<=126)return "500";
		if(degrees<=162)return "300";
		if(degrees<=198)return "-300";
		if(degrees<=234)return "+50";
		if(degrees<=270)return "200";
		if(degrees<=306)return "-50";
		if(degrees<=342)return "100";
		if(degrees<=360)return "1000";
			
			 
		return "asd";
	}


	@Override
	public void newWord(String word) {
		this.usedLetters.removeAll(usedLetters);
		
	}


	@Override
	public void actualWord(String actualWord) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void win() {
		// TODO Auto-generated method stub
		
	}

}
