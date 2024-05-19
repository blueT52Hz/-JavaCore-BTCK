package com.mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.mygdx.game.controller.BoxManager;
import com.mygdx.game.model.Enemy;

import static com.mygdx.game.model.constant.Constants.PPM;

public class Brick {
    private float x;
    private float y;
    private int width;
    private int height;
    private float xSpeed=50;
    private Body body;
    private Texture brickImage = new Texture("tiles/brick.png");
    public Brick(float x, float y) {
        this.x = x;
        this.y = y;
    }
    public Brick(int col, int row, int pixel) {
        this.x = col*pixel;
        this.y = row*pixel;
        setWidth(pixel*6);
        setHeight(pixel);
        this.body = BoxManager.createBox(x, y, width, height, true, GameMap.world, 0);
        this.body.getFixtureList().first().setUserData(this);
        this.body.setGravityScale(0);
    }
    public void draw(SpriteBatch spriteBatch) {
        update();
        spriteBatch.draw(brickImage, x, y, width, height);
    }
    public void update() {
//        x+=xSpeed * Gdx.graphics.getDeltaTime();
//        if(x<=16) {
//            xSpeed = -xSpeed;
//            x = 16;
//        }
//        if(x>=Gdx.graphics.getWidth()-width-16) {
//            xSpeed = -xSpeed;
//            x = Gdx.graphics.getWidth()-width-16;
//        }
        body.setTransform((x+width/2)/PPM, (y+height/2)/PPM, 0);
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getxSpeed() {
        return xSpeed;
    }

    public Body getBody() {
        return body;
    }
}
