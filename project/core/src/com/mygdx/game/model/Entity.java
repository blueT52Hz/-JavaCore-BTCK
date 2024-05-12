package com.mygdx.game.model;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.controller.HitBox;

public abstract class Entity {
    protected float x, y;
    protected int width;
    protected int height;
    protected float stateTime;
    protected boolean dead=false;
    public abstract HitBox getHitBox();
    public abstract void draw(SpriteBatch spriteBatch, float gameMapStateTime);
    public abstract void update();
    public abstract void loadAnimation();

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }

    public void setStateTime(float stateTime) {
        this.stateTime = stateTime;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean isDead() {
        return dead;
    }
}
