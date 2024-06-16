package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import utils.ChangeScene;
import utils.FXMLLink;

import java.io.IOException;

public class InstructionController {
    @FXML
    private Button buttonBack;

    @FXML
    private void backToLoadingScene() {
        Stage stage = (Stage) buttonBack.getScene().getWindow();
        try {
			ChangeScene.changeScene(stage, FXMLLink.LOADING_GAME);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
}
