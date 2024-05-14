package com.mygdx.game.model;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.mygdx.game.model.constant.EnemyState;
import com.mygdx.game.view.Brick;

public abstract class Enemy extends Entity{
    protected float speed;
    protected EnemyState enemyState;
    protected Animation moveRightAnimation;
    protected Animation moveLeftAnimation;
    protected Animation deadAnimation;
    protected Brick brick;
    protected int level;
    public Enemy(float x,  float y, int level, Brick brick) {
        this.x = x;
        this.y = y;
        this.level = level;
        this.brick = brick;
    }


}
