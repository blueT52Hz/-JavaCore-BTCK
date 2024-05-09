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
    public static Animation throwAnimation;
    public static Animation glideAnimation;
    public static Texture waitImg;
    public static Texture kunaiImg;
    public static Texture navigationImg;
    public boolean throwed;
    public float stateTime;
    public Kunai kunai;
    public NavigationArrow navigationArrow;
    public float speedFall = 10; // tốc độ rơi xuống
    public float speed;

    public MainCharacter() {
        kunai = new Kunai();
        navigationArrow = new NavigationArrow();
        stateTime = 0f;
        throwed = false;
        x = 100;
        y = 100;
    }

    public static void load() {
        Texture[] throwImg = new Texture[9];
        Texture[] glideImg = new Texture[9];
        for(int i=0;i<9;++i) {
            throwImg[i] = new Texture(String.format("Throw__00%d.png", i));
            glideImg[i] = new Texture(String.format("Glide_00%d.png", i));
        }
        throwAnimation = new Animation<>(0.05f, throwImg);
        glideAnimation = new Animation<>(0.2f, glideImg);

        waitImg = new Texture("Throw__000.png");
        kunaiImg = new Texture("Kunai.png");
        navigationImg = new Texture("Arrow2.png");
    }

    public void draw(SpriteBatch batch, Animation animation, boolean looping) {
        batch.draw((TextureRegion) animation.getKeyFrame(stateTime, looping), x-MainCharacter.WIDTH/2, y - MainCharacter.HEIGHT/2, MainCharacter.WIDTH, MainCharacter.HEIGHT);
    }
    public void draw(SpriteBatch batch, Texture texture, boolean looping) {
        batch.draw(texture, x-MainCharacter.WIDTH/2, y - MainCharacter.HEIGHT/2, MainCharacter.WIDTH, MainCharacter.HEIGHT);
    }
    public void update (float delta) {
        speed = -speedFall;
        //x += speed * delta;
        y += speed * delta;
    }
}
