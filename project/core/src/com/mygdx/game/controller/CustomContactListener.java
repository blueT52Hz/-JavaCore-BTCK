package com.mygdx.game.controller;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.model.impl.Bullet.Kunai;

import static com.mygdx.game.model.constant.Constants.PPM;

public class CustomContactListener implements ContactListener {
    public void setKunaiAndDownWallContact(boolean kunaiAndDownWallContact) {
        this.kunaiAndDownWallContact = kunaiAndDownWallContact;
    }

    public boolean isKunaiAndLeftWallContact() {
        return kunaiAndLeftWallContact;
    }

    public void setKunaiAndLeftWallContact(boolean kunaiAndLeftWallContact) {
        this.kunaiAndLeftWallContact = kunaiAndLeftWallContact;
    }

    public boolean isKunaiAndRightWallContact() {
        return kunaiAndRightWallContact;
    }

    public void setKunaiAndRightWallContact(boolean kunaiAndRightWallContact) {
        this.kunaiAndRightWallContact = kunaiAndRightWallContact;
    }

    public boolean isKunaiAndBrickContact() {
        return kunaiAndBrickContact;
    }

    public void setKunaiAndBrickContact(boolean kunaiAndBrickContact) {
        this.kunaiAndBrickContact = kunaiAndBrickContact;
    }

    private boolean kunaiAndDownWallContact = false;
    private boolean kunaiAndLeftWallContact = false;
    private boolean kunaiAndRightWallContact = false;
    private boolean kunaiAndBrickContact = false;
    public boolean isKunaiAndDownWallContact() {
        return kunaiAndDownWallContact;
    }

    @Override
    public void beginContact(Contact contact) {
        // Xử lý khi bắt đầu va chạm
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();
        if ((fixtureA.getUserData() == "kunai" && fixtureB.getUserData() == "downWall") || (fixtureA.getUserData() == "downWall" && fixtureB.getUserData() == "kunai")) {
            kunaiAndDownWallContact = true;
//            Vector2 kunaiPosition = bodyA.getPosition();
//            Vector2 downWallPosition = bodyB.getPosition();
//            Vector2 pushDirection = kunaiPosition.sub(downWallPosition).nor(); // Xác định hướng đẩy
//            float pushForceMagnitude = 1000f; // Độ lớn của lực đẩy
//            Vector2 pushForce = pushDirection.scl(pushForceMagnitude); // Tính toán lực đẩy
//
//            // Áp dụng lực đẩy vào kunai
//            bodyA.applyForceToCenter(pushForce, true);
        }
    }

    @Override
    public void endContact(Contact contact) {

        // Xử lý khi kết thúc va chạm
    }

    @Override
    public void preSolve(Contact contact, Manifold manifold) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        // Kiểm tra xem fixture nào là của ninja và kunai
        if ((fixtureA.getUserData() == "ninja" && fixtureB.getUserData() == "kunai") ||
                (fixtureA.getUserData() == "kunai" && fixtureB.getUserData() == "ninja")) {
            contact.setEnabled(false); // Vô hiệu hóa va chạm giữa ninja và kunai
        }

    }
    @Override
    public void postSolve(Contact contact, ContactImpulse contactImpulse) {

    }

}

