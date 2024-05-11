package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.entities.MainCharacter;
import jdk.tools.jmod.Main;

public class MainGameScreen implements Screen {

    public static float KUNAI_X = 100;
    public static float KUNAI_Y = 100;
    public static float KUNAI_NEXT_X = 100;
    public static float KUNAI_NEXT_Y = 100;
    public MainCharacter ninja;
    float stateTime;

    MyGdxGame game;
    public MainGameScreen(MyGdxGame game) {
        this.game = game;
        ninja = new MainCharacter();
    }
    @Override
    public void show () {
        MainCharacter.load();
    }

    @Override
    public void render (float deltaTime) {
        Gdx.gl.glClearColor(0.15f, 0.15f, 0.3f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        float MOUSE_X = Gdx.input.getX();
        float MOUSE_Y = Gdx.graphics.getHeight() - Gdx.input.getY();

        game.batch.begin();


        if (Gdx.input.justTouched()){
            if(ninja.throwed && ninja.displacement) {
                ninja.displacement = false;
                ninja.kunai.next_x = MOUSE_X;
                ninja.kunai.next_y = MOUSE_Y;
                ninja.throwed = false;
                game.batch.draw(MainCharacter.waitImg, ninja.x-MainCharacter.WIDTH/2, ninja.y - MainCharacter.HEIGHT/2, MainCharacter.WIDTH, MainCharacter.HEIGHT);
                ninja.stateTime = 0f;
            } else {
                ninja.displacement = true;
                ninja.x = ninja.kunai.x;
                ninja.y = ninja.kunai.y;
                game.batch.draw(MainCharacter.waitImg, ninja.x-MainCharacter.WIDTH/2, ninja.y - MainCharacter.HEIGHT/2, MainCharacter.WIDTH, MainCharacter.HEIGHT);
//                ninja.kunai.x=-1000;
//                ninja.kunai.y=-1000;
            }

        } else {
            if(!ninja.throwed) {
                ninja.stateTime += deltaTime;
                game.batch.draw(MainCharacter.kunaiImg, ninja.x+MainCharacter.WIDTH/2, ninja.y, 8, 40);
                game.batch.draw((Texture) MainCharacter.throwAnimation.getKeyFrame(ninja.stateTime, false), ninja.x-MainCharacter.WIDTH/2, ninja.y-MainCharacter.HEIGHT/2, MainCharacter.WIDTH, MainCharacter.HEIGHT);
                if(ninja.stateTime >= 30 * deltaTime) {
                    ninja.throwed = true;
                    ninja.stateTime = 0f;
                }
            } else {
                ninja.kunai.x = ninja.kunai.next_x;
                ninja.kunai.y = ninja.kunai.next_y;
                ninja.stateTime += deltaTime;
                game.batch.draw(MainCharacter.kunaiImg, ninja.kunai.x-4, ninja.kunai.y-20, 8, 40);
                game.batch.draw((Texture) MainCharacter.glideAnimation.getKeyFrame(ninja.stateTime, true), ninja.x-MainCharacter.WIDTH/2, ninja.y - MainCharacter.HEIGHT/2, MainCharacter.WIDTH, MainCharacter.HEIGHT);
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
