package com.mygdx.game.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public abstract class Bullet extends Sprite {
    public float x;
    public float y;
    public float speed;
    public float xSpeed;
    public float ySpeed;
    public boolean speedChanged;
    protected int width=40;
    protected int height=8;
    protected boolean canBounce=false;
    public float rotation;
    public Bullet(Texture texture, float x, float y) {
        super(texture);
        this.x = x;
        this.y = y;
    }
    protected abstract void update();
    protected abstract void updateRotation(float xWeapon, float yWeapon);




}
