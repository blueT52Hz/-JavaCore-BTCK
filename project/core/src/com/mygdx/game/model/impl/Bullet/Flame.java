package com.mygdx.game.model.impl.Bullet;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.controller.BoxManager;
import com.mygdx.game.model.Bullet;
import com.mygdx.game.model.Enemy;
import com.mygdx.game.model.EnemyBullet;
import com.mygdx.game.view.GameMap;

import static com.mygdx.game.model.constant.Constants.PPM;

public class Flame extends EnemyBullet {
    public float stateTime;
    private Animation flyAnimation;
    private Animation flyAnimation2;
    public boolean started;
    public Flame(float x, float y, Enemy enemy) {
        super(new Texture("Entities/flame/flame_5.png"), enemy);
        this.width=20;
        this.height=10;
        this.speed = 100;
        this.stateTime = 0;
        this.started = false;
        this.body = BoxManager.createBox(x, y, width, height, false, GameMap.world, 0);
        this.body.getFixtureList().first().setUserData(this);
        body.setGravityScale(0);
        loadAnimation();
    }

    @Override
    public void draw(Batch spriteBatch) {
        Texture tmp ;
        tmp = (Texture) flyAnimation2.getKeyFrame(stateTime, true);
        stateTime += Gdx.graphics.getDeltaTime();
        this.setTexture(tmp);
        super.draw(spriteBatch);
    }




    private void loadAnimation() {
        Texture[] tmp = new Texture[4];
        for(int i=0;i<4;++i) {
            tmp[i] = new Texture(String.format("Entities/flame/flame_%d.png",i+1));
        }
        flyAnimation = new Animation<>(0.2f, tmp);

        Texture[] tmp2 = new Texture[21];
        for(int i=0;i<21;++i) {
            tmp2[i] = new Texture(String.format("Entities/flame/%02d.png",i));
        }
        flyAnimation2 = new Animation<>(0.2f, tmp2);
    }
}