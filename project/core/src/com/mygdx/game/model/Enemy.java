package com.mygdx.game.model;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.physics.box2d.Body;
import com.mygdx.game.model.constant.EnemyState;
import com.mygdx.game.view.Brick;

import java.util.ArrayList;

public abstract class Enemy extends Entity{
    protected float speed;
    protected EnemyState enemyState;
    protected Animation moveRightAnimation;
    protected Animation moveLeftAnimation;
    protected Animation deadAnimation;
    protected Brick brick;
    protected ArrayList<EnemyBullet> enemyBullets;
    protected ArrayList<Body> enemyBulletsBodies;
    protected Player target;
    protected int level;
    public Enemy(int level, Brick brick, Player target) {
//        this.x = x;
//        this.y = y;
        enemyBullets = new ArrayList<>();
        enemyBulletsBodies = new ArrayList<>();
        this.level = level;
        this.brick = brick;
        this.target = target;
    }

    public ArrayList<Body> getEnemyBulletsBodies() {
        return enemyBulletsBodies;
    }

    public ArrayList<EnemyBullet> getEnemyBullets() {
        return enemyBullets;
    }

    public Brick getBrick() {
        return brick;
    }

    protected abstract void setEnemyTilePath();

    private EnemyState state;

    public EnemyState getState() {
        return state;
    }

    public void setState(EnemyState state) {
        this.state = state;
    }

    public boolean isDefeated() {
        return state == EnemyState.DEAD;
    }
}
