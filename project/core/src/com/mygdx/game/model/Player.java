package com.mygdx.game.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.game.model.constant.ArmState;
import com.mygdx.game.model.constant.PlayerState;
import com.mygdx.game.model.constant.SkinState;

import static com.mygdx.game.model.constant.Constants.PPM;

public abstract class Player extends Entity {
    protected float xSpeed;
    protected float ySpeed;
    protected PlayerState playerState;
    protected SkinState skinState;
    protected ArmState armState;
    protected boolean appear;
    protected PlayerBullet playerBullet;
    public Sprite navigationArrow;

    public void setxSpeed(float xSpeed) {
        this.xSpeed = xSpeed;
    }

    public void setySpeed(float ySpeed) {
        this.ySpeed = ySpeed;
    }

    public void setPlayerState(PlayerState playerState) {
        this.playerState = playerState;
    }

    public float getxSpeed() {
        return xSpeed;
    }

    public float getySpeed() {
        return ySpeed;
    }

    public PlayerState getPlayerState() {
        return playerState;
    }

    public boolean isAppear() {
        return appear;
    }

    public void setAppear(boolean appear) {
        this.appear = appear;
    }

    public SkinState getSkinState() {
        return skinState;
    }

    public void setSkinState(SkinState skinState) {
        this.skinState = skinState;
    }
    public ArmState getArmState() {
        return armState;
    }

    public void setArmState(ArmState armState) {
        this.armState = armState;
    }
    @Override
    public void createBody(){
        super.createBody();
        this.body.getFixtureList().first().setUserData(this);
    }

    public PlayerBullet getPlayerBullet() {
        return playerBullet;
    }
}
