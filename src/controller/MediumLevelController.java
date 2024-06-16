package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import model.MediumLevel;
public class MediumLevelController {

    @FXML
    private GridPane gridPane;

    private MediumLevel mediumLevel;

    @FXML
    public void initialize() {
        mediumLevel = new MediumLevel();
        loadButtons();
    }

    private void loadButtons() {
        Button[][] buttons = mediumLevel.getButtons();
        for (int i = 0; i < mediumLevel.getRows(); i++) {
            for (int j = 0; j < mediumLevel.getCols(); j++) {
                gridPane.add(buttons[i][j], j, i);
            }
        }
    }
}
