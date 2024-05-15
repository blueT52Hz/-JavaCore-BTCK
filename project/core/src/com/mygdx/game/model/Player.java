package com.mygdx.game.model;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.game.model.constant.PlayerState;

public abstract class Player extends Entity {
    protected float xSpeed;
    protected float ySpeed;
    protected PlayerState playerState;
    public Sprite navigationArrow;
    private Animation glideAnimation;
    private Animation throwAnimation;
    private Animation idleAnimation;
    private Animation deadAnimation;

    public void setxSpeed(float xSpeed) {
        this.xSpeed = xSpeed;
    }

    public void setySpeed(float ySpeed) {
        this.ySpeed = ySpeed;
    }

    public void setPlayerState(PlayerState playerState) {
        this.playerState = playerState;
    }

    public float getxSpeed() {
        return xSpeed;
    }

    public float getySpeed() {
        return ySpeed;
    }

    public PlayerState getPlayerState() {
        return playerState;
    }
}
