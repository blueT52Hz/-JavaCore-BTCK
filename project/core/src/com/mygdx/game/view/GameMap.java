package com.mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.controller.LevelManager;
import com.mygdx.game.model.Enemy;
public class GameMap {
    private float stateTime;
    private LevelManager levelManager;
    public GameMap() {
        this.stateTime = 0;
        this.levelManager = LevelManager.getInstance();
    }
    public void draw(SpriteBatch spriteBatch) {
        this.stateTime += Gdx.graphics.getDeltaTime();
        drawBackground(spriteBatch);
        drawBricks(spriteBatch);
        drawEnemies(spriteBatch);
    }
    public void drawBricks(SpriteBatch spriteBatch) {
        for(int i=0;i<3;++i) {
            this.levelManager.bricks.get(this.levelManager.currentLevel).get(i).draw(spriteBatch);
        }
    }

    public void drawEnemies(SpriteBatch spriteBatch) {
        for(Enemy enemy : levelManager.enemies.get(levelManager.currentLevel)) {
            enemy.draw(spriteBatch, this.stateTime);
        }
    }

    public void drawBackground(SpriteBatch spriteBatch) {
        levelManager.drawMap(spriteBatch);
    }

    public LevelManager getLevelManager() {
        return levelManager;
    }

    public float getStateTime() {
        return stateTime;
    }
}
