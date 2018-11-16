package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        Scene s=new Scene(root, 945, 714);
        primaryStage.setScene(s);
        s.setFill(Color.TRANSPARENT);
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("mivicon.png")));
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.show();


    }


    public static void main(String[] args) {
        launch(args);
    }
}
