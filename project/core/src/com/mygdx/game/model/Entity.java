package com.mygdx.game.model;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.controller.HitBox;

public abstract class Entity {
    protected float x, y;
    protected int width;
    protected int height;
    protected String direction;
    protected boolean dead=false;
    public abstract HitBox getHitBox();
    public abstract void draw(SpriteBatch spriteBatch, float stateTime);
    public abstract void update();
    public abstract void loadAnimation();

}
