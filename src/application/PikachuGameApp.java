package application;

import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.util.Duration;
import utils.ChangeScene;
import utils.FXMLLink;

public class PikachuGameApp extends Application {

    public void start(Stage primaryStage) throws Exception {
        FXMLLoader splashLoader = new FXMLLoader(getClass().getResource(FXMLLink.SPLASH_VIEW));
        Scene splashScene = new Scene(splashLoader.load());

        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/resources/images/logogame.png")));
        primaryStage.setScene(splashScene);
        primaryStage.setTitle("Pikachu Game");
        primaryStage.show();
        PauseTransition delay = new PauseTransition(Duration.seconds(4));
        delay.setOnFinished(event -> {
            try {
                ChangeScene.changeScene(primaryStage, FXMLLink.LOADING_GAME);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        delay.play();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
