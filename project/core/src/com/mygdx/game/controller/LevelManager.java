package com.mygdx.game.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.model.Enemy;
import com.mygdx.game.model.impl.Medusa;
import com.mygdx.game.view.Brick;

import java.util.ArrayList;
import java.util.Random;

public class LevelManager {
    public static LevelManager instance;
    public int currentLevel=0;
    public int maxLevel=0;
    public ArrayList<ArrayList<Brick>> bricks;
    public ArrayList<ArrayList<Enemy>> enemies;
    public Texture startMapImage = new Texture("tiles/mapStart.png");
    public Texture mapImage = new Texture("tiles/map.png");
    private LevelManager(){
        bricks = new ArrayList<>();
        enemies = new ArrayList<>();
        currentLevel = 0;
        maxLevel = 0;
        spawnBrick();
    }
    public static LevelManager getInstance() {
        if(instance==null) instance = new LevelManager();
        return instance;
    }
    public void drawMap(SpriteBatch spriteBatch) {
        Texture img = mapImage;
        if(currentLevel==0) img = startMapImage;
        spriteBatch.draw(img, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }
    public void spawnBrick() {
        ArrayList<Brick> tmp = new ArrayList<>();
        ArrayList<Enemy> tmp1 = new ArrayList<>();
        tmp.add(new Brick(new Random(System.currentTimeMillis()).nextInt(18)+1, 31, 16));
        tmp.add(new Brick(new Random(System.currentTimeMillis()).nextInt(18)+1, 21, 16));
        tmp.add(new Brick(new Random(System.currentTimeMillis()).nextInt(18)+1, 10, 16));
        for (Brick brick : tmp) {
            tmp1.add(new Medusa(brick.getX(), brick.getY()+ brick.getHeight(), 1, brick));
        }
        enemies.add(tmp1);
        bricks.add(tmp);
    }

    public void spawnEnemy() {
    }
    public void nextLevel() {
        this.currentLevel++;
        if(this.currentLevel>this.maxLevel) {
            maxLevel = currentLevel;
            spawnBrick();
        }
    }
    public void preLevel() {
        currentLevel--;
        if(currentLevel<0) currentLevel=0;
    }


}