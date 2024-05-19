package com.mygdx.game.model;

import com.badlogic.gdx.graphics.Texture;

public abstract class PlayerBullet extends Bullet{
    public PlayerBullet(Texture texture) {
        super(texture);
        this.speed = 200;
        this.appear = false;
        this.handledContact = true;
    }

    @Override
    protected void update() {

    }

    @Override
    protected void updateRotation(float xWeapon, float yWeapon) {

    }
}
