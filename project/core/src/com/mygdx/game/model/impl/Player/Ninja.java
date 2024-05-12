package com.mygdx.game.model.impl.Player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.controller.HitBox;
import com.mygdx.game.model.Player;
import com.mygdx.game.model.constant.PlayerState;
import com.mygdx.game.model.impl.Bullet.Kunai;

public class Ninja extends Player {
    private static Animation glideAnimation;
    private static Animation throwAnimation;
    private static Texture waitImg;
    public Kunai kunai;

    public Ninja() {
        super();
        this.x = 200;
        this.y = 50;
        width = 64;
        height = 76;
        ySpeed = -10;
        stateTime = 0;
        this.kunai = new Kunai(x, y);
        this.playerState = PlayerState.IDLE;
        navigationArrow = new Sprite(new Texture("Arrow2.png"));
        loadAnimation();
    }

    @Override
    public HitBox getHitBox() {
        return null;
    }

    @Override
    public void draw(SpriteBatch spriteBatch, float gameMapStateTime) {
        switch (playerState) {
            case IDLE:
                spriteBatch.draw(waitImg, x-width/2, y-height/2, width, height);
                break;
            case GLIDE:
                spriteBatch.draw((Texture) glideAnimation.getKeyFrame(gameMapStateTime, true), x-width/2, y-height/2, width, height);
                break;
            case THROW:
                stateTime+=Gdx.graphics.getDeltaTime();
                System.out.println(stateTime);
                if(stateTime>=50*Gdx.graphics.getDeltaTime()) {
                    stateTime = 0;
                    playerState = PlayerState.GLIDE;
                } else spriteBatch.draw((Texture) throwAnimation.getKeyFrame(stateTime, false), x-width/2, y-height/2, width, height);
                break;
            case FLASH:
                break;
            case DEAD:
                break;
        }
    }

    @Override
    public void update() {
        //x += speed * delta;
        if(playerState == PlayerState.GLIDE) y += ySpeed * Gdx.graphics.getDeltaTime();
    }

    @Override
    public void loadAnimation() {
        Texture[] throwImg = new Texture[9];
        Texture[] glideImg = new Texture[9];
        for(int i=0;i<9;++i) {
            throwImg[i] = new Texture(String.format("Throw__00%d.png", i));
            glideImg[i] = new Texture(String.format("Glide_00%d.png", i));
        }
        throwAnimation = new Animation<>(0.075f, throwImg);
        glideAnimation = new Animation<>(0.1f, glideImg);
        waitImg = throwImg[0];
    }


}