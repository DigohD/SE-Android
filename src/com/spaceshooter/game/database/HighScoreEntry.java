package com.spaceshooter.game.database;

import java.util.Date;

public class HighScoreEntry {

	private String user;
	private Date date;
	private double score;
	private long gameId;

	public HighScoreEntry(String user, Date date, double score, long gameId) {
		this.setUser(user);
		this.setDate(date);
		this.setScore(score);
		this.setGameId(gameId);
	}

	private void setUser(String user) {
		this.user = user;
	}

	public String getUser() {
		return user;
	}

	private void setDate(Date date) {
		this.date = date;
	}

	public Date getDate() {
		return date;
	}

	private void setScore(double score) {
		this.score = score;
	}

	public double getScore() {
		return score;
	}

	public void setGameId(long gameId) {
		this.gameId = gameId;
	}

	public long getGameId() {
		return gameId;
	}

	public String toString() {
		return "User:" + getUser() + " Date:" + getDate() + " Score:" + getScore() + " GameId:" + getGameId();
	}

}
