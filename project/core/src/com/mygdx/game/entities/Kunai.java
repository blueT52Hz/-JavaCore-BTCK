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
        x = 200;
        y = 50;
        speed = 200;
        speedChanged = false;
//        img = new Texture("Kunai.png");
    }

    public void updateSpeedStartFly(float MOUSE_X, float MOUSE_Y) {
        xSpeed = speed *  (float) Math.cos(Math.toRadians(rotation));
        ySpeed  = speed * (float) Math.sin(Math.toRadians(rotation));
    }
    public void updateCollision() {
        if (x <= 4 + 16 || x >= MyGdxGame.WIDTH - 24 - 16) {
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