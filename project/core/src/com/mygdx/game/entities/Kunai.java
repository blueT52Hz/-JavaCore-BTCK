package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;

public class Kunai {
    public static final float WIDTH = 40;
    public static final float HEIGHT = 8;
    public float x;
    public float y;
    public float before_x;
    public float before_y;
    public float next_x;
    public float next_y;

    public float speed = 20;
    public float timeMoving = 0;
    public float xSpeed, ySpeed;
//    public Texture img;
    public Kunai() {
        next_x = 0;
        next_y = 0;
//        img = new Texture("Kunai.png");
    }
    public void update(float delta, float cos) {
        xSpeed = speed*cos;
        ySpeed = speed* MathUtils.sin(MathUtils.acos(cos)) - 10 * delta;
        next_x += xSpeed * delta;
        if (ySpeed > 0)
        {
            next_y +=  ySpeed*delta- 5*delta*delta;
        }else next_y += 5*delta*delta;
    }
}
