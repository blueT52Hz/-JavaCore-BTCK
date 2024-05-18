package com.mygdx.game.model;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.mygdx.game.view.Brick;

public abstract class Enemy extends Entity{
    protected float speed;
    protected Animation moveRightAnimation;
    protected Animation moveLeftAnimation;
    protected Animation deadAnimation;
    protected String enemyTilePath;
    protected Brick brick;
    protected int level;
    public Enemy(float x,  float y, int level, Brick brick) {
        this.x = x;
        this.y = y;
        this.level = level;
        this.brick = brick;
    }

    protected abstract void setEnemyTilePath();
    protected abstract void setHeight(int level);
    protected abstract void setWidth(int level);


}
