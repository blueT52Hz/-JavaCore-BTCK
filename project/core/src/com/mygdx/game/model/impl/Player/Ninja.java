package com.mygdx.game.model.impl.Player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.mygdx.game.controller.HitBox;
import com.mygdx.game.model.Player;
import com.mygdx.game.model.constant.PlayerState;
import com.mygdx.game.model.impl.Bullet.Flame;
import com.mygdx.game.model.impl.Bullet.Kunai;

public class Ninja extends Player {
    private static Animation glideAnimation;
    private static Animation throwAnimation;
    private static Animation flashAnimation;
    private static Texture waitImg;
    public Kunai kunai;
    public boolean throwed;

    public Ninja() {
        super();
        this.x = 200;
        this.y = 50;
        width = 64;
        height = 76;
        ySpeed = -10;
        stateTime = 0;
        throwed = true;
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
                Texture throwImg = (Texture) throwAnimation.getKeyFrame(stateTime, false);
                stateTime+=Gdx.graphics.getDeltaTime();
                if(throwImg == null || stateTime >= Gdx.graphics.getDeltaTime()*30) {
                    stateTime = 0;
                    playerState = PlayerState.GLIDE;
                    throwed = true;
                }
                spriteBatch.draw(throwImg, x-width/2, y-height/2, width, height);
                break;
            case FLASH:
                Texture tmp = (Texture) flashAnimation.getKeyFrame(stateTime, true);
                stateTime+=Gdx.graphics.getDeltaTime();
                if(tmp == null || stateTime >= Gdx.graphics.getDeltaTime()*30) {
                    stateTime = 0;
                    playerState = PlayerState.GLIDE;
                    tmp = waitImg;
                }
                spriteBatch.draw(tmp, x-width/2, y-height/2, width, height);
                break;
            case DEAD:
                break;
        }
    }

    @Override
    public void update() {
        //x += speed * delta;
        if(playerState == PlayerState.GLIDE)
        {
            y += ySpeed * Gdx.graphics.getDeltaTime();
            body.setTransform(x, y, 0);
        }
    }

    @Override
    public void loadAnimation() {
        Texture[] throwImg = new Texture[9];
        Texture[] glideImg = new Texture[9];
        Texture[] flashImg = new Texture[14];
        for(int i=0;i<9;++i) {
            throwImg[i] = new Texture(String.format("Entities/ninja/Throw__00%d.png", i));
            glideImg[i] = new Texture(String.format("Entities/ninja/Glide_00%d.png", i));
            flashImg[i] = new Texture(String.format("Entities/ninja/Lightning_%02d.png", i + 1));
        }
        for (int i = 10;  i<= 13; i++) flashImg[i] = new Texture(String.format("Entities/ninja/Lightning_%02d.png", i+1));
        throwAnimation = new Animation<>(0.075f, throwImg);
        glideAnimation = new Animation<>(0.1f, glideImg);
        flashAnimation = new Animation<>(0.025f, flashImg);
        waitImg = throwImg[0];
    }


}