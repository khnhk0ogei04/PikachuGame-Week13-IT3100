package model;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import utils.PathFinder;

import java.util.*;

public class MediumLevel extends PikachuGame {

    private static final int ICONS_COUNT = 21;
    private int score;
    private int hearts;
    private int remainingTime;
    public Timer timer;

    public MediumLevel() {
        super(12, 12, 250, 2);
        initializeGrid();
        this.score = 0;
        this.hearts = 3;
        this.remainingTime = 250;
        this.timer = new Timer();
    }
    
    public int getCols() {
        return cols;
    }
    
    public int getRows() {
        return rows;
    }

    private void initializeGrid() {
        List<Integer> numbers = generateNumberList();
        Collections.shuffle(numbers);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                int number = numbers.remove(0);
                grid[i][j] = number;
                buttons[i][j] = new Button();
            }
        }

        addIconsToButtons();
    }

    private List<Integer> generateNumberList() {
        List<Integer> numbers = new ArrayList<>();
        for (int i = 0; i < (rows * cols) / 2; i++) {
            int iconIndex = i % ICONS_COUNT;
            numbers.add(iconIndex);
            numbers.add(iconIndex);
        }
        return numbers;
    }

    private void addIconsToButtons() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                int iconIndex = grid[i][j];
                String tmp = "../resources/images/ic_" + iconIndex + ".png";
                Image iconImage = new Image(getClass().getResourceAsStream(tmp));
                ImageView iconImageView = new ImageView(iconImage);
                StackPane stackPane = new StackPane();
                Rectangle border = new Rectangle(40, 40);
                border.setFill(null);
                border.setStroke(null);
                
                stackPane.getChildren().addAll(border, iconImageView);
                buttons[i][j].setGraphic(stackPane);
                
                buttons[i][j].setOnAction(e -> onButtonClick((Button) e.getSource()));
            }
        }
    }

    private Button firstSelectedButton = null;
    private Button secondSelectedButton = null;

    private void onButtonClick(Button button) {
        if (firstSelectedButton == null) {
            firstSelectedButton = button;
            setBorder(button, Color.YELLOW);
        } else {
            secondSelectedButton = button;
            int startX = -1, startY = -1, endX = -1, endY = -1;
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    if (buttons[i][j] == firstSelectedButton) {
                        startX = i;
                        startY = j;
                    }
                    if (buttons[i][j] == secondSelectedButton) {
                        endX = i;
                        endY = j;
                    }
                }
            }
            if (canMatch(firstSelectedButton, secondSelectedButton)) {
                firstSelectedButton.setVisible(false);
                secondSelectedButton.setVisible(false);
                clearBorder(firstSelectedButton);
                clearBorder(secondSelectedButton);
                grid[startX][startY] = -1; // Set value of matched cell to -1
                grid[endX][endY] = -1;
                this.score += 10;
                // Check if there are remaining possible moves
                if (!canPlay()) {
                    System.out.println("No more possible moves! Shuffling buttons...");
                    shuffleButtons();
                }
            } else {
                setBorder(firstSelectedButton, Color.RED); // Set border to red if not matched
                setBorder(secondSelectedButton, Color.RED); // Set border to red if not matched
                decreaseHeart();
//                System.out.println(hearts);
            }
            firstSelectedButton = null;
            secondSelectedButton = null;
        }
    }

    public void setBorder(Button button, Color color) {
        StackPane stackPane = (StackPane) button.getGraphic();
        Rectangle border = (Rectangle) stackPane.getChildren().get(0);
        border.setStroke(color);
    }

    public void clearBorder(Button button) {
        StackPane stackPane = (StackPane) button.getGraphic();
        Rectangle border = (Rectangle) stackPane.getChildren().get(0);
        border.setStroke(null);
    }

    @Override
    public void shuffleButtons() {
        List<Integer> iconList = new ArrayList<>();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (grid[i][j] != -1) { 
                    iconList.add(grid[i][j]);
                }
            }
        }
        Collections.shuffle(iconList);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (grid[i][j] != -1) {
                    grid[i][j] = iconList.remove(0);
                    int iconIndex = grid[i][j];
                    Image iconImage = new Image(getClass().getResourceAsStream("/resources/images/ic_" + iconIndex + ".png"));
                    ImageView iconImageView = new ImageView(iconImage);
                    StackPane stackPane = new StackPane();
                    Rectangle border = new Rectangle(40, 40);
                    border.setFill(null);
                    border.setStroke(null);
                    
                    stackPane.getChildren().addAll(border, iconImageView);
                    buttons[i][j].setGraphic(stackPane);
                    
                    buttons[i][j].setOnAction(e -> onButtonClick((Button) e.getSource()));
                }
            }
        }
    }
    public void manualShuffle() {
        if (shuffleCount < maxShuffleCount) {
            shuffleCount++;
            shuffleButtons();
        } else {
            System.out.println("You've used all your shuffle attempts.");
        }
    }

    @Override
    public boolean canMatch(Button btn1, Button btn2) {
        int startX = -1, startY = -1, endX = -1, endY = -1;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (buttons[i][j] == btn1) {
                    startX = i;
                    startY = j;
                }
                if (buttons[i][j] == btn2) {
                    endX = i;
                    endY = j;
                }
            }
        }
        if (grid[startX][startY] != grid[endX][endY]) {
            return false;
        }
        PathFinder pathFinder = new PathFinder(grid);
        return pathFinder.isPathAvailable(startX, startY, endX, endY) || pathFinder.isPathAvailable(endX, endY, startX, startY);
    }
    
    public Button[][] getButtons() {
        return buttons;
    }

    public boolean canPlay() {
        PathFinder pathFinder = new PathFinder(grid);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (grid[i][j] != -1) {
                    for (int m = 0; m < rows; m++) {
                        for (int n = 0; n < cols; n++) {
                            if ((m != i || n != j) && grid[m][n] != -1 && grid[m][n] == grid[i][j]) {
                                if (pathFinder.isPathAvailable(i, j, m, n)) {
                                    System.out.println("Possible move: (" + i + "," + j + ") -> (" + m + "," + n + ")");
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
    @Override
    public void decreaseHeart() {
        hearts--;
        if (hearts <= 0) {
            endGame();
        }
    }
    @Override
    public boolean checkWin() {
    	for (int i = 0; i < rows; i++) {
    		for (int j = 0 ; j < cols ; j++) {
    			if (grid[i][j] != -1)
    				return false;
    		}
    	}
    	return true;
    }
    @Override
    public int[] findHint() {
        PathFinder pathFinder = new PathFinder(grid);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (grid[i][j] != -1) {
                    for (int m = 0; m < rows; m++) {
                        for (int n = 0; n < cols; n++) {
                            if ((m != i || n != j) && grid[m][n] != -1 && grid[m][n] == grid[i][j]) {
                                if (pathFinder.isPathAvailable(i, j, m, n)) {
                                    return new int[]{i, j, m, n};
                                }
                            }
                        }
                    }
                }
            }
        }
        return null;
    }
    public int getHearts() {
        return hearts;
    }

    public int getTimeLeft() {
        return remainingTime;
    }
    public int getScore() {
    	return this.score;
    }
    public int getShuffleCount() {
    	return this.shuffleCount;
    }
    public int getMaxShuffleCount() {
    	return this.maxShuffleCount;
    }

    public void startGame() {
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (remainingTime > 0) {
                    remainingTime--;
                }
            }
        }, 0, 1000);
    }

//    private void endGame() {
//        timer.cancel();
//    }
}