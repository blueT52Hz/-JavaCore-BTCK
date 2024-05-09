package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.mygdx.game.MyGdxGame;

public class Kunai {
    public static final float WIDTH = 40;
    public static final float HEIGHT = 8;
    public float x;
    public float y;
    public float speed; // speed của kunai
    public float xSpeed, ySpeed; // speed theo 2 chiều
    public boolean speedChanged; // trạng thái để thay đổi speed của Kunai
    public float rotation;// góc tạo bởi Kunai và trục Ox
//    public Texture img;
    public Kunai() {
        x = 40;
        y = 50;
        speed = 100;
        speedChanged = false;
//        img = new Texture("Kunai.png");
    }

    public void updateSpeedStartFly(float MOUSE_X, float MOUSE_Y) {
//        if (MOUSE_X >= x) xSpeed = speed;
//        else xSpeed = -speed;
        xSpeed = speed *  (float) Math.cos(Math.toRadians(rotation));
//        if (MOUSE_Y >= y) ySpeed = speed;
//        else ySpeed = -speed;
        ySpeed  = speed * (float) Math.sin(Math.toRadians(rotation));
    }
    public void updateCollision() {
        if (x <= 4 || x >= MyGdxGame.WIDTH - 24) {
            xSpeed = -xSpeed;
            rotation = 180 - rotation;
        }
        if (y <= 20 || y >= MyGdxGame.HEIGHT - 5) {
            rotation = 360 - rotation;
            ySpeed = -ySpeed;
        }
    }
    public void updatePosition(float delta) {
        x += xSpeed * delta;
        y += ySpeed * delta;
    }

    public float getSpeed() {
        return speed;
    }
}
