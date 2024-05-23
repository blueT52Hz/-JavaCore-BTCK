package com.mygdx.game.model.impl.Enemy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.controller.BoxManager;
import com.mygdx.game.model.Player;
import com.mygdx.game.view.Brick;
import com.mygdx.game.model.Enemy;
import com.mygdx.game.view.GameMap;

import static com.mygdx.game.model.constant.Constants.PPM;

public class Demon extends Enemy {
    public Demon(float x, float y, int level, Brick brick, Player target) {
        super(level, brick, target);
        setEnemyTilePath();
        setHeight(level*50);
        setWidth(level*50);
        loadAnimation();
        this.stateTime = 0;
        this.speed = 10f + this.brick.getxSpeed();
//        this.body = BoxManager.createBox(x, y, width, height, false, GameMap.world, 0);
//        this.body.getFixtureList().first().setUserData("enemy");
    }

    @Override
    protected void setEnemyTilePath() {}


    @Override
    public void draw(SpriteBatch spriteBatch, float gameMapStateTime) {
//        update();
        float x = this.body.getPosition().x*PPM - width/2;
        float y = this.body.getPosition().y*PPM - height/2;
        stateTime += Gdx.graphics.getDeltaTime();
        if(this.speed>0) spriteBatch.draw((Texture) moveRightAnimation.getKeyFrame(gameMapStateTime, true), x, y, this.width, this.height);
        if (this.speed<=0) spriteBatch.draw((Texture) moveLeftAnimation.getKeyFrame(gameMapStateTime, true), x, y, this.width, this.height);
    }

    @Override
    public void update() {
//        x += speed * Gdx.graphics.getDeltaTime();
//        if(this.x<=this.brick.getX()) {
//            this.x=this.brick.getX();
//            speed = -speed;
//        }
//        if(this.x>=this.brick.getX()+this.brick.getWidth()-this.width) {
//            this.x = this.brick.getX()+this.brick.getWidth()-this.width;
//            speed = -speed;
//        }
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
