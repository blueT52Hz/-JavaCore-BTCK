package com.mygdx.game.controller;

import com.badlogic.gdx.physics.box2d.*;

import static com.mygdx.game.model.constant.Constants.PPM;

public class BoxManager {
    public static Body createBox(float x, float y, int width, int height, boolean isStatic, World world, float friction) {
        Body pBody;
        BodyDef def = new BodyDef();
        if (isStatic) def.type = BodyDef.BodyType.StaticBody;
        else def.type = BodyDef.BodyType.DynamicBody;
        def.position.set(x/PPM, y/PPM);
        def.fixedRotation = true;
        pBody = world.createBody(def);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox((float) width/2/PPM, (float) height/2/PPM);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.friction = friction;
//        fixtureDef.restitution = 0.5f;
        pBody.createFixture(fixtureDef);
        //pBody.createFixture(shape, 1.0f);
        shape.dispose();
        return pBody;
    }
}
