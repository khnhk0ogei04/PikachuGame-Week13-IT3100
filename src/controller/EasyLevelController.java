package controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.EasyLevel;
import utils.ChangeScene;
import utils.FXMLLink;

import java.util.Optional;
import java.util.TimerTask;

public class EasyLevelController {

    @FXML
    private GridPane gridPane;
    
    @FXML
    private Label labelScore;
    
    @FXML
    private Label shuffleTurnLeft;
    
    @FXML
    private Label timeLeft;
    
    @FXML
    private ImageView heart1;
    
    @FXML
    private ImageView heart2;
    
    @FXML
    private ImageView heart3;
    
    @FXML
    private Button shuffleButton;

    @FXML
    private Button hintButton;

    private EasyLevel easyLevel;
    private TimerTask timerTask;
    private boolean hintUsed = false;

    @FXML
    public void initialize() {
        easyLevel = new EasyLevel();
        loadButtons();
        easyLevel.startGame();
        updateScore();
        updateLives();
        updateShuffleTurnLeft();
        updateTimeLeft();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    updateScore();
                    updateLives();
                    updateTimeLeft();

                    int hearts = easyLevel.getHearts();
                    if (easyLevel.checkWin()) {
                        showWinDialog();
                    }
                    if (hearts <= 0) {
                        heart1.setVisible(false);
                        System.out.println("Hearts are zero, showing lose dialog");
                        showLoseDialog();
                        timerTask.cancel();
                    } else if (easyLevel.getTimeLeft() <= 0) {
                        if (easyLevel.checkWin()) {
                            showWinDialog();
                        } else {
                            showLoseDialog();
                        }
                        timerTask.cancel();
                    }
                });
            }
        };
        easyLevel.timer.scheduleAtFixedRate(timerTask, 0, 1000);
    }

    private void loadButtons() {
        gridPane.getChildren().clear();
        Button[][] buttons = easyLevel.getButtons();
        for (int i = 0; i < easyLevel.getRows(); i++) {
            for (int j = 0; j < easyLevel.getCols(); j++) {
                gridPane.add(buttons[i][j], j, i);
            }
        }
    }

    @FXML
    private void shuffleButtons() {
        easyLevel.manualShuffle();
        loadButtons();
        updateShuffleTurnLeft();
    }

    @FXML
    private void hint() {
        if (!hintUsed) {
            int[] hintCoords = easyLevel.findHint();
            if (hintCoords != null) {
                Button[][] buttons = easyLevel.getButtons();
                Button button1 = buttons[hintCoords[0]][hintCoords[1]];
                Button button2 = buttons[hintCoords[2]][hintCoords[3]];
                easyLevel.setBorder(button1, Color.RED);
                easyLevel.setBorder(button2, Color.RED);
                hintUsed = true;
                hintButton.setDisable(true);
            }
        }
    }

    private void updateScore() {
        labelScore.setText(String.valueOf(easyLevel.getScore()));
    }

    private void updateLives() {
        int lives = easyLevel.getHearts();
        System.out.println("Updating lives, current lives: " + lives);
        heart1.setVisible(lives >= 1);
        heart2.setVisible(lives >= 2);
        heart3.setVisible(lives >= 3);
    }

    private void updateShuffleTurnLeft() {
        shuffleTurnLeft.setText("Shuffles left: " + easyLevel.getShuffleCount() + "/" + easyLevel.getMaxShuffleCount());
    }

    private void updateTimeLeft() {
        int remainingTime = easyLevel.getTimeLeft();
        int minutes = remainingTime / 60;
        int seconds = remainingTime % 60;
        timeLeft.setText(String.format("%02d:%02d", minutes, seconds));
    }

    private void showWinDialog() {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("You Win!");
            alert.setHeaderText(null);
            alert.setContentText("Congratulations! You have won the game.");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                try {
                    ChangeScene.changeScene((Stage)gridPane.getScene().getWindow(), FXMLLink.LOADING_GAME);
                } catch (Exception e) {
                    System.err.println("Error while changing scene.");
                    e.printStackTrace();
                }
            }
        });
    }

    private void showLoseDialog() {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("You Lose!");
            alert.setHeaderText(null);
            alert.setContentText("Sorry, you have lost the game.");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                try {
                    ChangeScene.changeScene((Stage)gridPane.getScene().getWindow(), FXMLLink.LOADING_GAME);
                } catch (Exception e) {
                    System.err.println("Error while changing scene.");
                    e.printStackTrace();
                }
            }
        });
    }
}
