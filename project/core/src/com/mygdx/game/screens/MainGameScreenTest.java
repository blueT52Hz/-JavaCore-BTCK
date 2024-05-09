package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.mygdx.game.view.GameMap;
import com.mygdx.game.MyGdxGame;

public class MainGameScreenTest implements Screen {
    MyGdxGame game;
    GameMap gameMap;
    public MainGameScreenTest(MyGdxGame game) {
        this.game = game;
    }
    @Override
    public void show () {
        gameMap = new GameMap();
    }

    @Override
    public void render (float deltaTime) {
        Gdx.gl.glClearColor(0.15f, 0.15f, 0.3f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();

        if(Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            gameMap.getLevelManager().currentLevel++;
            if(gameMap.getLevelManager().currentLevel>gameMap.getLevelManager().maxLevel) {
                gameMap.getLevelManager().spawnBrick();
                gameMap.getLevelManager().spawnEnemy();
                gameMap.getLevelManager().maxLevel++;
            }
            System.out.println(gameMap.getLevelManager().currentLevel + " " + gameMap.getLevelManager().maxLevel);
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
            gameMap.getLevelManager().currentLevel--;
            if(gameMap.getLevelManager().currentLevel<0) gameMap.getLevelManager().currentLevel=0;
            System.out.println(gameMap.getLevelManager().currentLevel + " " + gameMap.getLevelManager().maxLevel);
        }
        gameMap.draw(game.batch);
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
