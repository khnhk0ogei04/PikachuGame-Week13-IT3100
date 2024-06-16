package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import utils.ChangeScene;
import utils.FXMLLink;

import java.io.IOException;

public class ChooseLevelController {

    @FXML
    private Button backButton;

    @FXML
    private Button easyButton;

    @FXML
    private Button mediumButton;

    @FXML
    private Button hardButton;

    @FXML
    private void backToPreviousScene() {
        Stage stage = (Stage) backButton.getScene().getWindow();
        try {
            ChangeScene.changeScene(stage, FXMLLink.LOADING_GAME);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void chooseEasyLevel() {
        LoadingGameController.setSelectedLevel(FXMLLink.EASY_LEVEL);
        backToPreviousScene();
    }

    @FXML
    public void chooseMediumLevel() {
        LoadingGameController.setSelectedLevel(FXMLLink.MEDIUM_LEVEL);
        backToPreviousScene();
    }

    @FXML
    public void chooseHardLevel() {
//        LoadingGameController.setSelectedLevel(FXMLLink.HARD_LEVEL);
//        backToPreviousScene();
    }
}
