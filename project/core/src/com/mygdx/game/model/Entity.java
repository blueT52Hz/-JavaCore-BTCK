package com.mygdx.game.model;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.controller.HitBox;

import static com.mygdx.game.model.constant.Constants.PPM;

public abstract class Entity {
    protected float x, y;
    protected int width;
    protected int height;
    protected float stateTime;
    protected boolean dead=false;
    public Body body;
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
