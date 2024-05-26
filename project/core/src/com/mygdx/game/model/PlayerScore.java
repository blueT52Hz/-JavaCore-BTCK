package com.mygdx.game.model;

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

    public int getScore() {
        return score;
    }

    public void addScore(int score) {
        this.score += score;
    }

}
