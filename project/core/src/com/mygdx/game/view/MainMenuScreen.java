package com.mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.MyGdxGame;

public class MainMenuScreen implements Screen {
    private static final int EXIT_BUTTON_WIDTH = 240;
    private static final int EXIT_BUTTON_HEIGHT = 78;
    private static final int PLAY_BUTTON_WIDTH = 240;
    private static final int PLAY_BUTTON_HEIGHT = 78;
    private static final int EXIT_BUTTON_Y = 100;
    private static final int PLAY_BUTTON_Y = 200;




    MyGdxGame game;
    Texture exitButtonActive;
    Texture playButtonActive;
    Texture playButtonInActive;
    Texture exitButtonInActive;
    public MainMenuScreen(MyGdxGame game) {
        this.game = game;
        exitButtonActive = new Texture("Button\\ExitActive.png");
        playButtonActive = new Texture("Button\\StartActive.png");
        exitButtonInActive = new Texture("Button\\ExitInActive.png");
        playButtonInActive = new Texture("Button\\StartInActive.png");
    }
    @Override
    public void show() {

    }

    @Override
    public void render(float v) {

        Gdx.gl.glClearColor(0.15f, 0.15f, 0.3f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();
        int x = MyGdxGame.WIDTH/2 - EXIT_BUTTON_WIDTH/2;
        //System.out.println(Gdx.input.getY());
        if (Gdx.input.getX() < x + EXIT_BUTTON_WIDTH && Gdx.input.getX() > x && MyGdxGame.HEIGHT - Gdx.input.getY() < EXIT_BUTTON_Y + EXIT_BUTTON_HEIGHT && MyGdxGame.HEIGHT - Gdx.input.getY() > EXIT_BUTTON_Y ){
            game.batch.draw(exitButtonActive, MyGdxGame.WIDTH/2 - EXIT_BUTTON_WIDTH/2, EXIT_BUTTON_Y, EXIT_BUTTON_WIDTH, EXIT_BUTTON_HEIGHT);
            if (Gdx.input.isTouched()) {
                Gdx.app.exit();
            }
        }else{
            game.batch.draw(exitButtonInActive, MyGdxGame.WIDTH/2 - EXIT_BUTTON_WIDTH/2, EXIT_BUTTON_Y, EXIT_BUTTON_WIDTH, EXIT_BUTTON_HEIGHT);
        }
        x = MyGdxGame.WIDTH/2 - PLAY_BUTTON_WIDTH/2;

        if (Gdx.input.getX() < x + PLAY_BUTTON_WIDTH && Gdx.input.getX() > x && MyGdxGame.HEIGHT - Gdx.input.getY() < PLAY_BUTTON_Y + PLAY_BUTTON_HEIGHT && MyGdxGame.HEIGHT - Gdx.input.getY() > PLAY_BUTTON_Y ){
            game.batch.draw(playButtonActive, MyGdxGame.WIDTH/2 - PLAY_BUTTON_WIDTH/2, PLAY_BUTTON_Y, PLAY_BUTTON_WIDTH, PLAY_BUTTON_HEIGHT);
            if (Gdx.input.justTouched()) {
                this.dispose();
//                game.setScreen(new MainGameScreen(game));
                game.setScreen(new MainGameScreenTest(game));
            }
        }else{
            game.batch.draw(playButtonInActive, MyGdxGame.WIDTH/2 - PLAY_BUTTON_WIDTH/2, PLAY_BUTTON_Y, PLAY_BUTTON_WIDTH, PLAY_BUTTON_HEIGHT);

        }
        game.batch.end();
    }

    @Override
    public void resize(int i, int i1) {

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