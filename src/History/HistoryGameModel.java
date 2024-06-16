package History;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class HistoryGameModel {
	private static int id;
	private int score;
	private int time;
	private String timestamp;
	public HistoryGameModel(int score, int time) {
		HistoryGameModel.id = id++;
		this.score = score;
		this.time = time;
		LocalDateTime localDateTime = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy'T'HH:mm:ss");
        this.timestamp = localDateTime.format(formatter);
	}
	public static int getId() {
		return id;
	}
	public static void setId(int id) {
		HistoryGameModel.id = id;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public int getTime() {
		return time;
	}
	public void setTime(int time) {
		this.time = time;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
}
