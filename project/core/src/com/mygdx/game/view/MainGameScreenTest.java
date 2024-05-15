package com.mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.controller.MouseHandler;
import com.mygdx.game.model.constant.PlayerState;
import com.mygdx.game.model.impl.Player.Ninja;

public class MainGameScreenTest implements Screen {
    MyGdxGame game;
    GameMap gameMap;
    MouseHandler mouseHandler;
    Ninja ninja;
    public MainGameScreenTest(MyGdxGame game) {
        this.game = game;
    }
    @Override
    public void show () {
        //gameMap = new GameMap();
        mouseHandler = new MouseHandler();
        ninja = new Ninja();
        Gdx.input.setInputProcessor(mouseHandler);
    }

    @Override
    public void render (float deltaTime) {
        Gdx.gl.glClearColor(0.15f, 0.15f, 0.3f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();
        //gameMap.draw(game.batch, world);


        if(mouseHandler.isDrag()) {
            ninja.navigationArrow.setOriginCenter();
            ninja.navigationArrow.setBounds(ninja.getX() - 35, ninja.getY() - 26, 100, 20);
            ninja.navigationArrow.setRotation(ninja.kunai.rotation);
            ninja.navigationArrow.draw(game.batch);
            ninja.kunai.updateRotation();
            ninja.kunai.setAppear(true);
            ninja.setPlayerState(PlayerState.GLIDE);
            ninja.throwed = false;
        }

        if(mouseHandler.isTouchDown()) {
            ninja.kunai.xSpeed = 0;
            ninja.kunai.ySpeed = 0;
            ninja.setX(ninja.kunai.x);
            ninja.setY(ninja.kunai.y);
            ninja.setPlayerState(PlayerState.FLASH);
            ninja.kunai.setAppear(false);
        }

        if(!mouseHandler.isDrag() && !mouseHandler.isTouchDown()) {
            if(ninja.kunai.appear) {
                ninja.kunai.update();
                ninja.kunai.draw(game.batch);
            }
            // nếu đang trên bục thì ninja.setPlayerState(PlayerState.IDLE);
            if(ninja.throwed) {
                if(ninja.getPlayerState() != PlayerState.FLASH) ninja.setPlayerState(PlayerState.GLIDE);
            }
            else ninja.setPlayerState(PlayerState.THROW);
        }
        ninja.update();

        ninja.draw(game.batch, gameMap.getStateTime());

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