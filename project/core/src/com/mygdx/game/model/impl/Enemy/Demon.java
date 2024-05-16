package com.mygdx.game.model.impl.Enemy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.view.Brick;
import com.mygdx.game.controller.HitBox;
import com.mygdx.game.model.Enemy;

public class Demon extends Enemy {
    public Demon(float x, float y, int level, Brick brick) {
        super(x, y, level, brick);
        setHeight(level*50);
        setWidth(level*50);
        loadAnimation();
        this.stateTime = 0;
        this.speed = 10f + this.brick.getxSpeed();
    }

    @Override
    public HitBox getHitBox() {
        return new HitBox(this.x, this.y, this.width, this.height);
    }
    @Override
    public void draw(SpriteBatch spriteBatch, float gameMapStateTime) {
        update();
        stateTime += Gdx.graphics.getDeltaTime();
        if(this.speed>0) spriteBatch.draw((Texture) moveRightAnimation.getKeyFrame(gameMapStateTime, true), x, y, this.width, this.height);
        if (this.speed<=0) spriteBatch.draw((Texture) moveLeftAnimation.getKeyFrame(gameMapStateTime, true), x, y, this.width, this.height);
    }

    @Override
    public void update() {
        x += speed * Gdx.graphics.getDeltaTime();
        if(this.x<=this.brick.getX()) {
            this.x=this.brick.getX();
            speed = -speed;
        }
        if(this.x>=this.brick.getX()+this.brick.getWidth()-this.width) {
            this.x = this.brick.getX()+this.brick.getWidth()-this.width;
            speed = -speed;
        }
    }

    @Override
    public void loadAnimation() {
//        this.deadAnimation = new Animation<>();
        Texture[] moveRightImgs = new Texture[6];
        Texture[] moveLeftImgs = new Texture[6];
        for(int i=1;i<=6;++i) {
            moveRightImgs[i-1] = new Texture(String.format("entities/demon/moveRight%d.png", i));
            moveLeftImgs[i-1] = new Texture(String.format("entities/demon/moveLeft%d.png", i));
        }
        this.moveRightAnimation = new Animation<>(0.075f, moveRightImgs);
        this.moveLeftAnimation = new Animation<>(0.075f, moveLeftImgs);
    }
}
