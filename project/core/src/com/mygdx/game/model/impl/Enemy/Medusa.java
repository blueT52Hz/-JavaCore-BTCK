package com.mygdx.game.model.impl.Enemy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.game.controller.BoxManager;
import com.mygdx.game.model.EnemyBullet;
import com.mygdx.game.model.Player;
import com.mygdx.game.model.constant.EnemyState;
import com.mygdx.game.model.impl.Bullet.Flame;
import com.mygdx.game.view.Brick;
import com.mygdx.game.model.Enemy;
import com.mygdx.game.view.GameMap;

import java.util.ArrayList;

import static com.mygdx.game.model.constant.Constants.PPM;

public class Medusa extends Enemy {
    private long lastTimeAttack = 0;
    public Medusa(float x, float y, int level, Brick brick, Player target) {
        super(level, brick, target);
        setEnemyTilePath();
        setHeight(level*40);
        setWidth(level*40);
        loadAnimation();
        this.x = x;
        this.y = y;
        this.enemyState = EnemyState.MOVE;
        this.speed = 10 + this.brick.getxSpeed();
        createBody();
        spawnEnemyBullet();
    }

    @Override
    public void createBody() {
        if(this.body != null) GameMap.world.destroyBody(body);
        this.body = BoxManager.createBox(x, y, width, height, false, GameMap.world, 0);
        this.body.getFixtureList().first().setUserData(this);
        this.body.setGravityScale(0);
    }

    @Override
    protected void setEnemyTilePath() {}

    @Override
    public void draw(SpriteBatch spriteBatch, float gameMapStateTime) {
        update();
        for (EnemyBullet enemyBullet : enemyBullets) {
            enemyBullet.draw(spriteBatch);
        }

        switch (enemyState) {
            case MOVE:
                if(this.speed>0) spriteBatch.draw((Texture) moveRightAnimation.getKeyFrame(gameMapStateTime, true), x, y, this.width, this.height);
                if (this.speed<=0) spriteBatch.draw((Texture) moveLeftAnimation.getKeyFrame(gameMapStateTime, true), x, y, this.width, this.height);
        }

    }



    @Override
    public void update() {
        ArrayList<EnemyBullet> enemyBulletsRemove = new ArrayList<>();
        for(EnemyBullet enemyBullet : enemyBullets) {
            if(enemyBullet.isAppear()) enemyBullet.update();
            else enemyBulletsRemove.add(enemyBullet);
        }
        for (EnemyBullet enemyBullet : enemyBulletsRemove) {
            GameMap.world.destroyBody(enemyBullet.getBody());
            this.enemyBulletsBodies.remove(enemyBullet.getBody());
            this.enemyBullets.remove(enemyBullet);
        }
        if(TimeUtils.millis() - lastTimeAttack > 3000) spawnEnemyBullet();



        x += speed * Gdx.graphics.getDeltaTime();
        if(this.x<=this.brick.getX()) {
            this.x=this.brick.getX();
            speed = -speed;
        }
        if(this.x>=this.brick.getX()+this.brick.getWidth()-this.width) {
            x = this.brick.getX()+this.brick.getWidth()-this.width;
            speed = -speed;
        }
        body.setTransform((x+width/2)/PPM, (y+height/2)/PPM, 0);
    }

    public void spawnEnemyBullet() {
        Flame flame = new Flame(x, y, this);
        flame.updateRotation(target.getX(), target.getY());
        enemyBullets.add(flame);
        enemyBulletsBodies.add(flame.getBody());
        lastTimeAttack = TimeUtils.millis();
    }

    @Override
    public void loadAnimation() {
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