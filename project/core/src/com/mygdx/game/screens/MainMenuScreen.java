package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.MyGdxGame;

public class MainMenuScreen implements Screen {
    private static final int EXIT_BUTTON_WIDTH = 240;
    private static final int EXIT_BUTTON_HEIGHT = 80;
    private static final int NEWGAME_BUTTON_WIDTH = 350;
    private static final int NEWGAME_BUTTON_HEIGHT = 120;
    private static final int CONTINUE_BUTTON_WIDTH = 240;
    private static final int CONTINUE_BUTTON_HEIGHT = 80;
    private static final int LEADERBOARD_BUTTON_WIDTH = 246;
    private static final int LEADERBOARD_BUTTON_HEIGHT = 70;
    private static final int SETTINGS_BUTTON_WIDTH = 250;
    private static final int SETTINGS_BUTTON_HEIGHT = 70;
    private static final int STORE_BUTTON_WIDTH = 250;
    private static final int STORE_BUTTON_HEIGHT = 70;

    private static final int EXIT_BUTTON_Y = 50;
    private static final int NEWGAME_BUTTON_Y = 350;
    private static final int CONTINUE_BUTTON_Y = 250;
    private static final int LEADERBOARD_BUTTON_Y = 180;
    private static final int SETTINGS_BUTTON_Y = 105;
    private static final int STORE_BUTTON_Y = 30;

    MyGdxGame game;
    Texture newgameButtonActive;
    Texture newgameButtonInActive;
    Texture continueButtonActive;
    Texture continueButtonInActive;
    Texture leaderboardButtonActive;
    Texture leaderboardButtonInActive;
    Texture settingsButtonActive;
    Texture settingsButtonInActive;
    Texture storeButtonActive;
    Texture storeButtonInActive;

    Texture exitButtonActive;
    Texture exitButtonInActive;

    public MainMenuScreen(MyGdxGame game) {
        this.game = game;

        newgameButtonActive = new Texture("NewgameActive.PNG");
        newgameButtonInActive = new Texture("NewgameInactive.PNG");
        continueButtonActive = new Texture("ContinueActive.PNG");
        continueButtonInActive = new Texture("ContinueInactive.PNG");
        leaderboardButtonActive = new Texture("LeaderboardActive.PNG");
        leaderboardButtonInActive = new Texture("LeaderboardInactive.PNG");
        settingsButtonActive = new Texture("SettingsActive.PNG");
        settingsButtonInActive = new Texture("SettingsInactive.PNG");
        storeButtonActive = new Texture("StoreActive.PNG");
        storeButtonInActive = new Texture("StoreInactive.PNG");


        exitButtonActive = new Texture("ExitActive.png");
        exitButtonInActive = new Texture("ExitInActive.png");



    }
    @Override
    public void show() {

    }

    @Override
    public void render(float v) {

        Gdx.gl.glClearColor(0.15f, 0.15f, 0.3f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();

        //exit button
        int x = MyGdxGame.WIDTH/2 - EXIT_BUTTON_WIDTH/2;
//        if (Gdx.input.getX() < x + EXIT_BUTTON_WIDTH && Gdx.input.getX() > x && MyGdxGame.HEIGHT - Gdx.input.getY() < EXIT_BUTTON_Y + EXIT_BUTTON_HEIGHT && MyGdxGame.HEIGHT - Gdx.input.getY() > EXIT_BUTTON_Y ){
//            game.batch.draw(exitButtonActive, MyGdxGame.WIDTH/2 - EXIT_BUTTON_WIDTH/2, EXIT_BUTTON_Y, EXIT_BUTTON_WIDTH, EXIT_BUTTON_HEIGHT);
//            if (Gdx.input.isTouched()) {
//                Gdx.app.exit();
//            }
//        } else {
//            game.batch.draw(exitButtonInActive, MyGdxGame.WIDTH/2 - EXIT_BUTTON_WIDTH/2, EXIT_BUTTON_Y, EXIT_BUTTON_WIDTH, EXIT_BUTTON_HEIGHT);
//        }
//
        //newgame button
        x = MyGdxGame.WIDTH/2 -NEWGAME_BUTTON_WIDTH/2;
        if (Gdx.input.getX() < x + NEWGAME_BUTTON_WIDTH && Gdx.input.getX() > x && MyGdxGame.HEIGHT - Gdx.input.getY() < NEWGAME_BUTTON_Y + NEWGAME_BUTTON_HEIGHT && MyGdxGame.HEIGHT - Gdx.input.getY() > NEWGAME_BUTTON_Y ){
            game.batch.draw(newgameButtonActive, MyGdxGame.WIDTH/2 - NEWGAME_BUTTON_WIDTH/2, NEWGAME_BUTTON_Y, NEWGAME_BUTTON_WIDTH, NEWGAME_BUTTON_HEIGHT);
            if (Gdx.input.isTouched()) {
                this.dispose();
                game.setScreen(new MainGameScreen(game));
            }
        } else {
            game.batch.draw(newgameButtonInActive, MyGdxGame.WIDTH/2 - NEWGAME_BUTTON_WIDTH/2, NEWGAME_BUTTON_Y, NEWGAME_BUTTON_WIDTH, NEWGAME_BUTTON_HEIGHT);
        }

        //continue button
        x = MyGdxGame.WIDTH/2 - CONTINUE_BUTTON_WIDTH/2;
        if (Gdx.input.getX() < x + CONTINUE_BUTTON_WIDTH && Gdx.input.getX() > x && MyGdxGame.HEIGHT - Gdx.input.getY() < CONTINUE_BUTTON_Y + CONTINUE_BUTTON_HEIGHT && MyGdxGame.HEIGHT - Gdx.input.getY() > CONTINUE_BUTTON_Y ){
            game.batch.draw(continueButtonActive, MyGdxGame.WIDTH/2 - CONTINUE_BUTTON_WIDTH/2, CONTINUE_BUTTON_Y, CONTINUE_BUTTON_WIDTH, CONTINUE_BUTTON_HEIGHT);
            if (Gdx.input.isTouched()) {
            //Do something
            }
        } else {
            game.batch.draw(continueButtonInActive, MyGdxGame.WIDTH/2 - CONTINUE_BUTTON_WIDTH/2, CONTINUE_BUTTON_Y, CONTINUE_BUTTON_WIDTH, CONTINUE_BUTTON_HEIGHT);
        }

        //leaderboard button
        x = MyGdxGame.WIDTH/2 - LEADERBOARD_BUTTON_WIDTH/2;
        if (Gdx.input.getX() < x + LEADERBOARD_BUTTON_WIDTH && Gdx.input.getX() > x && MyGdxGame.HEIGHT - Gdx.input.getY() < LEADERBOARD_BUTTON_Y + LEADERBOARD_BUTTON_HEIGHT && MyGdxGame.HEIGHT - Gdx.input.getY() > LEADERBOARD_BUTTON_Y ){
            game.batch.draw(leaderboardButtonActive, MyGdxGame.WIDTH/2 - LEADERBOARD_BUTTON_WIDTH/2, LEADERBOARD_BUTTON_Y, LEADERBOARD_BUTTON_WIDTH, LEADERBOARD_BUTTON_HEIGHT);
            if (Gdx.input.isTouched()) {
                //Do something
            }
        } else {
            game.batch.draw(leaderboardButtonInActive, MyGdxGame.WIDTH/2 - LEADERBOARD_BUTTON_WIDTH/2, LEADERBOARD_BUTTON_Y, LEADERBOARD_BUTTON_WIDTH, LEADERBOARD_BUTTON_HEIGHT);
        }

        //settings button
        x = MyGdxGame.WIDTH/2 - SETTINGS_BUTTON_WIDTH/2;
        if (Gdx.input.getX() < x + SETTINGS_BUTTON_WIDTH && Gdx.input.getX() > x && MyGdxGame.HEIGHT - Gdx.input.getY() < SETTINGS_BUTTON_Y + SETTINGS_BUTTON_HEIGHT && MyGdxGame.HEIGHT - Gdx.input.getY() > SETTINGS_BUTTON_Y ){
            game.batch.draw(settingsButtonActive, MyGdxGame.WIDTH/2 - SETTINGS_BUTTON_WIDTH/2, SETTINGS_BUTTON_Y, SETTINGS_BUTTON_WIDTH, SETTINGS_BUTTON_HEIGHT);
            if (Gdx.input.isTouched()) {
                //Do something
            }
        } else {
            game.batch.draw(settingsButtonInActive, MyGdxGame.WIDTH/2 - SETTINGS_BUTTON_WIDTH/2, SETTINGS_BUTTON_Y, SETTINGS_BUTTON_WIDTH, SETTINGS_BUTTON_HEIGHT);
        }

        //store button
        x = MyGdxGame.WIDTH/2 - STORE_BUTTON_WIDTH/2;
        if (Gdx.input.getX() < x + STORE_BUTTON_WIDTH && Gdx.input.getX() > x && MyGdxGame.HEIGHT - Gdx.input.getY() < STORE_BUTTON_Y + STORE_BUTTON_HEIGHT && MyGdxGame.HEIGHT - Gdx.input.getY() > STORE_BUTTON_Y ){
            game.batch.draw(storeButtonActive, MyGdxGame.WIDTH/2 - STORE_BUTTON_WIDTH/2, STORE_BUTTON_Y, STORE_BUTTON_WIDTH, STORE_BUTTON_HEIGHT);
            if (Gdx.input.isTouched()) {
                //Do something
            }
        } else {
            game.batch.draw(storeButtonInActive, MyGdxGame.WIDTH/2 - STORE_BUTTON_WIDTH/2, STORE_BUTTON_Y, STORE_BUTTON_WIDTH, STORE_BUTTON_HEIGHT);
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
