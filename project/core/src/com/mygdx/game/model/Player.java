package com.mygdx.game.model;

import com.badlogic.gdx.graphics.g2d.Animation;

public abstract class Player extends Entity {
    private int xSpeed;
    private int ySpeed;
    // falling, flash
    private String playerState;
    private Animation glideAnimation;
    private Animation throwAnimation;
    private Animation idleAnimation;
    private Animation deadAnimation;

}
