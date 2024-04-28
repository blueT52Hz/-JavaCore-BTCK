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
    Texture img;
    MyGdxGame game;
    public MainGameScreen(MyGdxGame game) {
        this.game = game;
//        y = 15;
//        x = MyGdxGame.WIDTH/2 - CHAR_WIDTH /2;
//        roll = 2;
//        rolls = new Animation[5];
//        TextureRegion[][] rollSpriteSheet = TextureRegion.split(new Texture("char.png"), CHAR_WIDTH_PIXEL, CHAR_HEIGHT_PIXEL);
//
//        rolls[roll] = new Animation(CHAR_ANIMATION_SPEED, rollSpriteSheet[0]);
    }
    @Override
    public void show () {
        img = new Texture("char.png");
    }

    @Override
    public void render (float v) {
        Gdx.gl.glClearColor(0.15f, 0.15f, 0.3f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) y += SPEED * Gdx.graphics.getDeltaTime();
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) y-= SPEED * Gdx.graphics.getDeltaTime();
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) x -= SPEED * Gdx.graphics.getDeltaTime();
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) x+=SPEED * Gdx.graphics.getDeltaTime();

        stateTime += v;
        game.batch.begin();
        //game.batch.draw((TextureRegion) rolls[roll].getKeyFrame(stateTime, true), x, y, CHAR_WIDTH, CHAR_HEIGHT);
        if (Gdx.input.isTouched()){
            preX = Gdx.input.getX();
            preY = MyGdxGame.HEIGHT - Gdx.input.getY();
        }
        game.batch.draw(img, preX - img.getWidth()/2, preY - img.getHeight()/2);

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
