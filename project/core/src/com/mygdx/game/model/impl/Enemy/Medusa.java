package com.mygdx.game.model.impl.Enemy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.view.Brick;
import com.mygdx.game.controller.HitBox;
import com.mygdx.game.model.Enemy;

public class Medusa extends Enemy {
    public Medusa(float x, float y, int level, Brick brick) {
        super(x, y, level, brick);
        setEnemyTilePath();
        setHeight(level*40);
        setWidth(level*40);
        loadAnimation();
        this.speed = 10 + this.brick.getxSpeed();
    }

    @Override
    protected void setEnemyTilePath() {}
    @Override
    public HitBox getHitBox() {
        return new HitBox(this.x, this.y, this.width, this.height);
    }
    @Override
    public void draw(SpriteBatch spriteBatch, float gameMapStateTime) {
        update();
        switch (enemyState) {
            case MOVE:
                if(this.speed>0) spriteBatch.draw((Texture) moveRightAnimation.getKeyFrame(gameMapStateTime, true), x, y, this.width, this.height);
                if (this.speed<=0) spriteBatch.draw((Texture) moveLeftAnimation.getKeyFrame(gameMapStateTime, true), x, y, this.width, this.height);
        }

    }

    @Override
    public void update() {
        x += speed * Gdx.graphics.getDeltaTime();
        if(this.x<=this.brick.getX()) {
            this.x=this.brick.getX();
            speed = -speed;
        }
        if(this.x>=this.brick.getX()+this.brick.getWidth()-this.width) {
            x = this.brick.getX()+this.brick.getWidth()-this.width;
            speed = -speed;
        }
    }

    @Override
    public void loadAnimation() {
//        this.deadAnimation = new Animation<>();
        Texture[] moveRightImgs = new Texture[4];
        Texture[] moveLeftImgs = new Texture[4];
        for(int i=1;i<=4;++i) {
            moveRightImgs[i-1] = new Texture(String.format("entities/medusa/moveRight%d.png", i));
            moveLeftImgs[i-1] = new Texture(String.format("entities/medusa/moveLeft%d.png", i));
        }
        this.moveRightAnimation = new Animation<>(0.05f, moveRightImgs);
        this.moveLeftAnimation = new Animation<>(0.05f, moveLeftImgs);
    }
}
