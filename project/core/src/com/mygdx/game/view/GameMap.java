package com.mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.controller.BoxManager;
import com.mygdx.game.controller.LevelManager;
import com.mygdx.game.model.Enemy;

import java.util.ArrayList;

import static com.mygdx.game.model.constant.Constants.PPM;

public class GameMap extends Matrix4 {
    public static World world;
    private final LevelManager levelManager;
    private float stateTime;
    public Body leftWall, rightWall, downWall;
    public GameMap(World world) {
        GameMap.world = world;
        stateTime = 0;
        levelManager = LevelManager.getInstance();
    }
    public void draw(SpriteBatch spriteBatch) {
        stateTime += Gdx.graphics.getDeltaTime();
        drawBackground(spriteBatch);
        drawBricks(spriteBatch);
        drawEnemies(spriteBatch);
    }
    public void drawBricks(SpriteBatch spriteBatch) {
        for(int i=0;i<3;++i) {
            Brick temp = this.levelManager.bricks.get(this.levelManager.currentLevel).get(i);
            temp.draw(spriteBatch);
            this.levelManager.bodies.get(this.levelManager.currentLevel).get(i).setTransform(temp.getX()/PPM + 96/2/PPM, temp.getY()/PPM + 16/2/PPM, 0);
        }
//        for(Brick brick : this.levelManager.bricks.get(this.levelManager.currentLevel)) {
//            brick.draw(spriteBatch);
//            for (ArrayList<Body> body : levelManager.bodies) {
//                for (Body b : body) {
//                    b.
//                }
//            }
//        }
    }

    public void drawEnemies(SpriteBatch spriteBatch) {
        for(Enemy enemy : levelManager.enemies.get(levelManager.currentLevel)) {
            enemy.draw(spriteBatch, stateTime);
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
