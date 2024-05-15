package com.mygdx.game.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import static com.mygdx.game.model.constant.Constants.PPM;

public abstract class Bullet extends Sprite {
    public float x;
    public float y;
    public float speed;
    public float xSpeed;
    public float ySpeed;
    protected int width;
    protected int height;
    public Body body;
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