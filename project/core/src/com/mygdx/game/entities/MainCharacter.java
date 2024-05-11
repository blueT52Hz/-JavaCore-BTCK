package com.mygdx.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.model.PlayerState;
import com.mygdx.game.model.impl.Kunai;

public class MainCharacter {
    public static final float WIDTH_PIXEL = 32;
    public static final float HEIGHT_PIXEL = 38;
    public static final float WIDTH = WIDTH_PIXEL*2;
    public static final float HEIGHT = HEIGHT_PIXEL*2;
    public float x;
    public float y;
    private int frameCounter;
    private float stateTime;
    public float width=WIDTH_PIXEL*2;
    public float height=HEIGHT_PIXEL*2;
    public static Animation throwAnimation;
    public static Animation glideAnimation;
    public static Animation flashAnimation;
    public static Texture waitImg;
    public static Texture kunaiImg;
    public static Texture navigationImg;
    public PlayerState playerState;
    public boolean throwed;
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
        throwed = false;
        frameCounter = 0;
        displaced = true;
    }

    public static void load() {
        Texture[] throwImg = new Texture[10];
        Texture[] glideImg = new Texture[10];
        Texture[] flashImg = new Texture[14];
        for(int i=0;i<=9;++i) {
            throwImg[i] = new Texture(String.format("Throw__00%d.png", i));
            glideImg[i] = new Texture(String.format("Glide_00%d.png", i));
            flashImg[i] = new Texture(String.format("Lightning_%02d.png", i + 1));
        }
        for (int i = 10;  i<= 13; i++) flashImg[i] = new Texture(String.format("Lightning_%d.png", i+1));
        throwAnimation = new Animation<>(0.01f, throwImg);
        glideAnimation = new Animation<>(0.2f, glideImg);
        flashAnimation = new Animation<>(0.3f, flashImg);

        waitImg = new Texture("Throw__000.png");
        kunaiImg = new Texture("Kunai.png");
        navigationImg = new Texture("Arrow2.png");
    }
    public void drawWaitImg(SpriteBatch batch) {
        batch.draw(waitImg, x-MainCharacter.WIDTH/2, y - MainCharacter.HEIGHT/2, MainCharacter.WIDTH, MainCharacter.HEIGHT);
    }
    public void draw(SpriteBatch spriteBatch) {
        switch (playerState) {
            case IDLE:
                spriteBatch.draw(waitImg, x-width/2, y-height/2, width, height);
                break;
            case GLIDE:
                stateTime+=Gdx.graphics.getDeltaTime();
                //System.out.println(stateTime);
                if(stateTime>=10*Gdx.graphics.getDeltaTime()) {
                    stateTime = 0;
                } else spriteBatch.draw((Texture) glideAnimation.getKeyFrame(stateTime, false), x-width/2, y-height/2, width, height);
                break;
            case THROW:
                stateTime+=Gdx.graphics.getDeltaTime();
                //System.out.println(stateTime);
                if(stateTime>=50*Gdx.graphics.getDeltaTime()) {
                    stateTime = 0;
                    playerState = PlayerState.GLIDE;
                } else spriteBatch.draw((Texture) throwAnimation.getKeyFrame(stateTime, false), x-width/2, y-height/2, width, height);
                break;
            case FLASH:
                stateTime+=Gdx.graphics.getDeltaTime();
                if (stateTime>=50*Gdx.graphics.getDeltaTime()) {
                    frameCounter = 0;
                    playerState = PlayerState.GLIDE;
                } else spriteBatch.draw((Texture) flashAnimation.getKeyFrame(stateTime, false), x-width/2, y-height/2, width, height);
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