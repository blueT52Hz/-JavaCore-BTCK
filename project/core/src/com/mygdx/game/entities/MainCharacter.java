package com.mygdx.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.model.constant.PlayerState;
import com.mygdx.game.model.impl.Bullet.Kunai;

public class MainCharacter {
    public static final float WIDTH_PIXEL = 32;
    public static final float HEIGHT_PIXEL = 38;
    public static final float WIDTH = WIDTH_PIXEL*2;
    public static final float HEIGHT = HEIGHT_PIXEL*2;
    public float x;
    public float y;
    private int frameCounter;
    public float width=WIDTH_PIXEL*2;
    public float height=HEIGHT_PIXEL*2;
    public static Animation throwAnimation;
    public static Animation glideAnimation;
    public static Texture waitImg;
    public PlayerState playerState;
    public Kunai kunai;
    public Sprite navigationArrow;
    public float speedFall = 10; // tốc độ rơi xuống
    public float speed;
    public boolean displaced;

    public MainCharacter() {
        this.x = 200;
        this.y = 50;
        this.kunai = new Kunai(x, y);
        this.playerState = PlayerState.IDLE;
        navigationArrow = new Sprite(new Texture("Arrow2.png"));
        frameCounter = 0;
        displaced = true;
    }

    public static void load() {
        Texture[] throwImg = new Texture[9];
        Texture[] glideImg = new Texture[9];
        for(int i=0;i<9;++i) {
            throwImg[i] = new Texture(String.format("Throw__00%d.png", i));
            glideImg[i] = new Texture(String.format("Glide_00%d.png", i));
        }
        throwAnimation = new Animation<>(0.05f, throwImg);
        glideAnimation = new Animation<>(0.1f, glideImg);
        waitImg = throwImg[0];
    }
    public void draw(SpriteBatch spriteBatch, float stateTime) {
        switch (playerState) {
            case IDLE:
                spriteBatch.draw(waitImg, x-width/2, y-height/2, width, height);
                break;
            case GLIDE:
                spriteBatch.draw((Texture) glideAnimation.getKeyFrame(stateTime, true), x-width/2, y-height/2, width, height);
                break;
            case THROW:
                frameCounter++;
                if(frameCounter>=30) {
                    frameCounter = 0;
                    playerState = PlayerState.GLIDE;
                } else spriteBatch.draw((Texture) throwAnimation.getKeyFrame(stateTime, false), x-width/2, y-height/2, width, height);
                break;
            case FLASH:
                break;
            case DEAD:
                break;
        }
    }
    public void update () {
        speed = -speedFall;
        //x += speed * delta;
        y += speed * Gdx.graphics.getDeltaTime();
    }
}