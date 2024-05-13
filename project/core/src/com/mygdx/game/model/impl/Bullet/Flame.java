package com.mygdx.game.model.impl.Bullet;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.model.Bullet;

public class Flame extends Bullet {
    public float stateTime;
    private Animation flyAnimation;
    private Animation flyAnimation2;
    public boolean started;
    public Flame(float x, float y) {
        super(new Texture("Entities/flame/flame_5.png"), x, y);
        this.width=15*3;
        this.height=10*3;
        this.speed = 100;
        this.stateTime = 0;
        this.started = false;
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
    @Override
    public void updateRotation(float xTarget, float yTarget) {
        this.rotation = (float) Math.toDegrees(MathUtils.acos((float) ((double) (xTarget - x) / (Math.sqrt((yTarget - y) * (yTarget - y) + (xTarget - x) * (xTarget - x))))));
        if (yTarget < y) rotation = 360 - rotation;
        setRotation(rotation);
    }
    public void updateRotation() {
        float xTarget = Gdx.input.getX();
        float yTarget = Gdx.graphics.getHeight() - Gdx.input.getY();
        this.rotation = (float) Math.toDegrees(MathUtils.acos((float) ((double) (xTarget - x) / (Math.sqrt((yTarget - y) * (yTarget - y) + (xTarget - x) * (xTarget - x))))));
        if (yTarget < y) rotation = 360 - rotation;
        setRotation(rotation);
    }

    @Override
    public void update() {
        setBounds(x-4, y-20, width, height);

        // kiểm tra xem đã cập nhật vận tốc trục x, y theo góc xoay hay chưa
//        if(!speedChanged) {
////            speedChanged = true;
//            xSpeed = speed *  (float) Math.cos(Math.toRadians(rotation));
//            ySpeed  = speed * (float) Math.sin(Math.toRadians(rotation));
//        }

        xSpeed = speed *  (float) Math.cos(Math.toRadians(rotation));
        ySpeed = speed * (float) Math.sin(Math.toRadians(rotation));

        x += xSpeed * Gdx.graphics.getDeltaTime();
        y += ySpeed * Gdx.graphics.getDeltaTime();

        // xử lí collision đập vào viền màn hình
        // cần tạo 1 hàm riêng kiểm tra collision với các vật thế khác
        if (x < 20) {
            x = 20;
            xSpeed = -xSpeed;
            rotation = 180 - rotation;
        }
        if(x > MyGdxGame.WIDTH - 24 - 16 - 10) {
            x = MyGdxGame.WIDTH - 24 - 16 - 10;
            xSpeed = -xSpeed;
            rotation = 180- rotation;
        }
        if (y < 20) {
            y = 20;
            rotation = 360 - rotation;
            ySpeed = -ySpeed;
        }
        if (y > MyGdxGame.HEIGHT - 5) {
            y = MyGdxGame.HEIGHT - 5;
            rotation = 360 - rotation;
            ySpeed = -ySpeed;
        }
        setOriginCenter();
        setRotation(rotation);
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
