package com.mygdx.game.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.mygdx.game.view.GameMap;

import static com.mygdx.game.model.constant.Constants.PPM;

public abstract class EnemyBullet extends Bullet{
    protected Enemy enemy;

    public EnemyBullet(Texture texture, Enemy enemy) {
        super(texture);
        this.enemy = enemy;
        this.appear = true;
        this.canBounce = false;
    }

    public Enemy getEnemy() {
        return enemy;
    }

    @Override
    public void updateRotation(float xTarget, float yTarget) {
        float x = body.getPosition().x*PPM;
        float y = body.getPosition().y*PPM;

        float rotation = (float) Math.toDegrees(MathUtils.acos((float) ((double) (xTarget - x) / (Math.sqrt((yTarget - y) * (yTarget - y) + (xTarget - x) * (xTarget - x))))));
        if (yTarget < y) rotation = 360 - rotation;
        setRotation(rotation);
        body.setTransform(body.getPosition(), rotation * MathUtils.degreesToRadians);
        updateSpeed();
    }

    @Override
    public void update() {
        if(!isHandledContact()) {
            updateBodyPosition();
            updateSpeed();
        }
        x = body.getPosition().x*PPM;
        y = body.getPosition().y*PPM;
        setBounds(x-width/2, y-height/2, width, height);
        setOriginCenter();
    }

    public void updateSpeed() {
        xSpeed = speed *  (float) Math.cos(Math.toRadians(getRotation()));
        ySpeed = speed * (float) Math.sin(Math.toRadians(getRotation()));
        body.setLinearVelocity(xSpeed/PPM, ySpeed/PPM);
    }
}
