package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class MainCharacter {
    public static final float WIDTH_PIXEL = 32;
    public static final float HEIGHT_PIXEL = 38;
    public static final float WIDTH = WIDTH_PIXEL*8/3;
    public static final float HEIGHT = HEIGHT_PIXEL*8/3;
    public float x;
    public float y;
    public float next_x;
    public float next_y;
    public static Animation throwAnimation;
    public static Animation glideAnimation;
    public static Texture waitImg;
    public static Texture kunaiImg;
    public float stateTime;
    public boolean throwed;
    public boolean displacement;
    public Kunai kunai;

    public MainCharacter() {
        this.kunai = new Kunai();
        this.stateTime = 0f;
        this.throwed = true;
        displacement = false;
        x = 100;
        y = 100;
    }

    public static void load() {
        Texture[] throwImg = new Texture[9];
        Texture[] glideImg = new Texture[9];
        for(int i=0;i<9;++i) {
            throwImg[i] = new Texture(String.format("MainCharacter\\Throw__00%d.png", i));
            glideImg[i] = new Texture(String.format("MainCharacter\\Glide_00%d.png", i));
        }
        throwAnimation = new Animation<>(0.05f, throwImg);
        glideAnimation = new Animation<>(0.2f, glideImg);

        waitImg = new Texture("MainCharacter\\Throw__000.png");
        kunaiImg = new Texture("Kunai.png");
    }

    public void draw(SpriteBatch batch, Animation animation, boolean looping) {
        batch.draw((TextureRegion) animation.getKeyFrame(stateTime, looping), x-MainCharacter.WIDTH/2, y - MainCharacter.HEIGHT/2, MainCharacter.WIDTH, MainCharacter.HEIGHT);
    }
    public void draw(SpriteBatch batch, Texture texture, boolean looping) {
        batch.draw(texture, x-MainCharacter.WIDTH/2, y - MainCharacter.HEIGHT/2, MainCharacter.WIDTH, MainCharacter.HEIGHT);
    }
}