package com.mygdx.game.model.impl.Bullet;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.model.Bullet;

public class Kunai extends Bullet {
    public boolean appear;
    public Kunai(float x, float y) {
        super(new Texture("Kunai.png"), x, y);
        this.width=40;
        this.height=8;
        this.speed = 200;
        this.appear = false;
    }

    public void setAppear(boolean appear) {
        this.appear = appear;
    }

    public void change() {
        this.appear = !this.appear;
        return;
    }

    @Override
    public void updateRotation(float xWeapon, float yWeapon) {
        float MOUSE_X = Gdx.input.getX();
        float MOUSE_Y = Gdx.graphics.getHeight() - Gdx.input.getY();
        this.rotation = (float) Math.toDegrees(MathUtils.acos((float) ((double) (MOUSE_X - xWeapon) / (Math.sqrt((MOUSE_Y - yWeapon) * (MOUSE_Y - yWeapon) + (MOUSE_X - xWeapon) * (MOUSE_X - xWeapon))))));
        if (MOUSE_Y < yWeapon) rotation = 360 - rotation;
        setRotation(rotation);
    }

    public void updateRotation() {
        float xWeapon = x;
        float yWeapon = y;
        float MOUSE_X = Gdx.input.getX();
        float MOUSE_Y = Gdx.graphics.getHeight() - Gdx.input.getY();
        this.rotation = (float) Math.toDegrees(MathUtils.acos((float) ((double) (MOUSE_X - xWeapon) / (Math.sqrt((MOUSE_Y - yWeapon) * (MOUSE_Y - yWeapon) + (MOUSE_X - xWeapon) * (MOUSE_X - xWeapon))))));
        if (MOUSE_Y < yWeapon) rotation = 360 - rotation;
        setRotation(rotation);
    }

    @Override
    public void update() {
        setBounds(x-4, y-20, width, height);

        xSpeed = speed *  (float) Math.cos(Math.toRadians(rotation));
        ySpeed = speed * (float) Math.sin(Math.toRadians(rotation));

//        x += xSpeed * Gdx.graphics.getDeltaTime();
//        y += ySpeed * Gdx.graphics.getDeltaTime();

        // xử lí collision đập vào viền màn hình
        // cần tạo 1 hàm riêng kiểm tra collision với các vật thế khác
//        if (x < 4 + 16 || x > MyGdxGame.WIDTH - 24 - 16 - 10) {
//            xSpeed = -xSpeed;
//            rotation = 180 - rotation;
//        }
//        if (y < 20 || y > MyGdxGame.HEIGHT - 5) {
//            rotation = 360 - rotation;
//            ySpeed = -ySpeed;
//        }
        setOriginCenter();
        setRotation(rotation);
    }
}