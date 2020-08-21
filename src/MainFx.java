
import javafx.application.Application;
import javafx.stage.Stage;
@SuppressWarnings("restriction")
public class MainFx extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
    	new StartingWindow();
    }
}
