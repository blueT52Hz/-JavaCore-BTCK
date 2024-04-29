package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.MyGdxGame;

public class MainGameScreen implements Screen {
    public static final float SPEED = 120;
    public static final float NINJA_WIDTH_PIXEL = 32;
    public static final float NINJA_HEIGHT_PIXEL = 38;
    public static float NINJA_X = 100;
    public static float NINJA_Y = 100;
    public static float NINJA_NEXT_X = 100;
    public static float NINJA_NEXT_Y = 100;
    public static float KUNAI_X = 100;
    public static float KUNAI_Y = 100;
    public static final float NINJA_WIDTH = NINJA_WIDTH_PIXEL*3;
    public static final float NINJA_HEIGHT = NINJA_HEIGHT_PIXEL*3;


//    public static final float CHAR_ANIMATION_SPEED = 0.5f;
//    public static final int CHAR_WIDTH_PIXEL = 17;
//    public static final int CHAR_HEIGHT_PIXEL = 32;
//    public static final int CHAR_WIDTH = CHAR_WIDTH_PIXEL * 3;
//    public static final int CHAR_HEIGHT = CHAR_HEIGHT_PIXEL * 3;
//    Animation[] rolls;

    float x, y;
    float preX, preY;
    int roll;
    float stateTime;
    boolean throwed;
    Texture img;
    Texture kunaiImage;

    Animation ninjaThrowAnimation;
    MyGdxGame game;
    public MainGameScreen(MyGdxGame game) {
        this.game = game;
        stateTime = 0f;
        throwed = false;
    }
    @Override
    public void show () {
        img = new Texture("char.png");
        kunaiImage = new Texture("kunai.png");
        Texture[] ninjaThrowImg = new Texture[9];
        for(int i=0;i<9;++i) {
            ninjaThrowImg[i] = new Texture(String.format("Throw__00%d.png", i));
        }
        ninjaThrowAnimation = new Animation(0.05f, ninjaThrowImg);
    }

    @Override
    public void render (float deltaTime) {
        Gdx.gl.glClearColor(0.15f, 0.15f, 0.3f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        float MOUSE_X = Gdx.input.getX();
        float MOUSE_Y = Gdx.graphics.getHeight() - Gdx.input.getY();

        game.batch.begin();


        if (Gdx.input.isTouched()){
            NINJA_NEXT_X = MOUSE_X;
            NINJA_NEXT_Y = MOUSE_Y;
            throwed = false;
            game.batch.draw(img, NINJA_X-NINJA_WIDTH/2, NINJA_Y - NINJA_HEIGHT/2, NINJA_WIDTH, NINJA_HEIGHT);
        } else {
            if(!throwed) {
                stateTime += deltaTime;
                game.batch.draw((Texture) ninjaThrowAnimation.getKeyFrame(stateTime, true), NINJA_X-NINJA_WIDTH/2, NINJA_Y-NINJA_HEIGHT/2, NINJA_WIDTH, NINJA_HEIGHT);
                if(stateTime >= 20 * deltaTime) {
                    throwed = true;
                    stateTime = 0f;
                }
            } else {
                game.batch.draw(img, NINJA_X-NINJA_WIDTH/2, NINJA_Y - NINJA_HEIGHT/2, NINJA_WIDTH, NINJA_HEIGHT);
            }
        }

        game.batch.end();

    }
    @Override
    public void resize (int i, int i1) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
