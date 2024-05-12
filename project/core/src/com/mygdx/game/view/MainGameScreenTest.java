package com.mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.mygdx.game.entities.MainCharacter;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.model.PlayerState;

public class MainGameScreenTest implements Screen {
    MyGdxGame game;
    GameMap gameMap;
    public MainCharacter ninja;
    int dem=0;
    boolean throw_to_glide;
    boolean flash_to_glide;

    public MainGameScreenTest(MyGdxGame game) {
        throw_to_glide = false;
        flash_to_glide = false;
        this.game = game;
        ninja = new MainCharacter();
    }
    @Override
    public void show () {
        gameMap = new GameMap();
        MainCharacter.load();
    }

    @Override
    public void render (float deltaTime) {
        Gdx.gl.glClearColor(0.15f, 0.15f, 0.3f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();
        gameMap.draw(game.batch);

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

        if (Gdx.input.isTouched()) {
            dem++;
            System.out.println(dem);
            // nếu đã dịch chuyển -> vẽ thanh định hướng
//            if(ninja.displaced && dem >= 10) {
//                ninja.navigationArrow.setOriginCenter();
//                ninja.navigationArrow.setRotation(ninja.kunai.rotation);
//                ninja.navigationArrow.setBounds(ninja.kunai.x - 35, ninja.kunai.y - 26, 100, 20);
//                ninja.navigationArrow.draw(game.batch);
//                ninja.kunai.speedChanged = false;
//            } else if(!ninja.displaced){
//
//            }
            ninja.navigationArrow.setOriginCenter();
            ninja.navigationArrow.setRotation(ninja.kunai.rotation);
            ninja.navigationArrow.setBounds(ninja.kunai.x - 35, ninja.kunai.y - 26, 100, 20);
            ninja.navigationArrow.draw(game.batch);
            throw_to_glide = false;
            flash_to_glide  = false;
            if (dem >= 15)
            {
                ninja.kunai.speedChanged = false;
                ninja.kunai.xSpeed = 0;
                ninja.kunai.ySpeed = 0;
                ninja.kunai.updateRotation();
                ninja.speed = 0;
                ninja.x = ninja.kunai.x;
                ninja.y = ninja.kunai.y;
                // nếu đang trên mặt đất thì ninja.playerState = PlayerState.IDLE;
                ninja.playerState = PlayerState.GLIDE;
                ninja.draw(game.batch, gameMap.getStateTime());
                ninja.playerState = PlayerState.THROW;
                throw_to_glide = true;
            }
        } else {
            String stateTemp = new String(ninja.playerState.getDisplayName());
            if (dem < 15 && dem > 10)
            {
                ninja.x = ninja.kunai.x;
                ninja.y = ninja.kunai.y;
                ninja.playerState = PlayerState.FLASH;
                flash_to_glide = true;
            }
            if (flash_to_glide) {
                ninja.update();
                ninja.kunai.x = ninja.x;
                ninja.kunai.y = ninja.y;
            }
            if (throw_to_glide) {
                if(ninja.playerState != PlayerState.IDLE && ninja.playerState != PlayerState.FLASH) {
                    ninja.update();
                    ninja.kunai.draw(game.batch);
                    ninja.kunai.update();
                }
            }
            if (ninja.playerState.getDisplayName()!=stateTemp) dem = 0;
            ninja.draw(game.batch, gameMap.getStateTime());
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
