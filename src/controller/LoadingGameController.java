package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import utils.FXMLLink;

import java.io.IOException;

public class LoadingGameController {

    @FXML
    private Button buttonPlay;

    @FXML
    private Button buttonLevel;

    @FXML
    private Button buttonInstruct;

    private static String selectedLevel = FXMLLink.EASY_LEVEL;

    public static void setSelectedLevel(String level) {
        selectedLevel = level;
    }
    
    @FXML
    private void initialize() {
        updatePlayButtonText();
    }
    
    private void updatePlayButtonText() {
        switch (selectedLevel) {
            case FXMLLink.EASY_LEVEL:
                buttonLevel.setText("CHỌN MỨC ĐỘ: DỄ");
                break;
            case FXMLLink.MEDIUM_LEVEL:
                buttonLevel.setText("CHỌN MỨC ĐỘ: TRUNG BÌNH");
                break;
//            case FXMLLink.HARD_LEVEL:
//                buttonInstruct.setText("CHỌN MỨC ĐỘ: KHÓ");
//                break;
            default:
                buttonInstruct.setText("CHỌN MỨC ĐỘ");
        }
    }
    
    @FXML
    public void playScene() {
        try {
            loadScene(selectedLevel);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void instructScene() throws IOException {
        loadScene("/resources/fxml/Instruction.fxml");
    }

    @FXML
    public void chooseLevelScene() throws IOException {
        loadScene("/resources/fxml/ChooseLevel.fxml");
    }

    private void loadScene(String fxmlFileName) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFileName));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) buttonPlay.getScene().getWindow();
        stage.setScene(scene);
    }
}
