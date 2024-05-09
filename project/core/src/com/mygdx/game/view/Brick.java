package com.mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.model.Enemy;

public class Brick {
    private int x;
    private int y;
    private int width;
    private int height;
    private float xSpeed=120;
    private Enemy enemy;
    private Texture brickImage = new Texture("tiles/brick.png");
    public Brick(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public Brick(int col, int row, int pixel) {
        this.x = col*pixel;
        this.y = row*pixel;
        setWidth(pixel*6);
        setHeight(pixel*1);
    }
    public void draw(SpriteBatch spriteBatch) {
        update();
        spriteBatch.draw(brickImage, x, y, 16*6, 16);
    }
    public void update() {
        x+=xSpeed * Gdx.graphics.getDeltaTime();
        if(x<=16) {
            xSpeed = -xSpeed;
            x = 16;
        }
        if(x>=Gdx.graphics.getWidth()-16*7) {
            xSpeed = -xSpeed;
            x = Gdx.graphics.getWidth()-16*7;
        }
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

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public float getxSpeed() {
        return xSpeed;
    }
}
