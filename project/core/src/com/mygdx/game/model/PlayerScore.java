package com.mygdx.game.model;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class PlayerScore {
    private String name;
    private int level;
    private int score;

    public PlayerScore(){
        this.score = 0;
    }
    public PlayerScore(String name, int level, int score) {
        this.name = name;
        this.level = level;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public int getLevel() {
        return level;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setLevel(int level) {
        this.level = level + 1;
    }
    public int getScore() {
        return score;
    }

    public void addScore(int score) {
        this.score += score;
    }

    public void saveScore(String name) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("HighScores.txt", true))) {
            writer.write(name + " " + getLevel() + " " + getScore());
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
