package com.mygdx.game.controller;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.model.impl.Bullet.Kunai;

import static com.mygdx.game.model.constant.Constants.PPM;

public class CustomContactListener implements ContactListener {
    public boolean isKunaiAndDownWallContact() {
        return kunaiAndDownWallContact;
    }
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
    public boolean isninjaAndDownWallContact() {
        return ninjaAndDownWallContact;
    }
    public void setninjaAndDownWallContact(boolean ninjaAndDownWallContact) {
        this.ninjaAndDownWallContact = ninjaAndDownWallContact;
    }
    private boolean kunaiAndDownWallContact = false;
    private boolean kunaiAndLeftWallContact = false;
    private boolean kunaiAndRightWallContact = false;
    private boolean kunaiAndBrickContact = false;
    private boolean ninjaAndDownWallContact = false;

    @Override
    public void beginContact(Contact contact) {
        // Xử lý khi bắt đầu va chạm
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
        if ((fixtureA.getUserData() == "kunai" && fixtureB.getUserData() == "downWall") ||
                (fixtureA.getUserData() == "downWall" && fixtureB.getUserData() == "kunai")) {
            kunaiAndDownWallContact = true;
        }
        if ((fixtureA.getUserData() == "kunai" && fixtureB.getUserData() == "leftWall") ||
                (fixtureA.getUserData() == "leftWall" && fixtureB.getUserData() == "kunai")) {
            kunaiAndLeftWallContact = true;
        }
        if ((fixtureA.getUserData() == "kunai" && fixtureB.getUserData() == "rightWall") ||
                (fixtureA.getUserData() == "rightWall" && fixtureB.getUserData() == "kunai")) {
            kunaiAndRightWallContact = true;
        }
        if ((fixtureA.getUserData() == "ninja" && fixtureB.getUserData() == "downWall") ||
                (fixtureB.getUserData() == "ninja" && fixtureA.getUserData() == "downWall") ) {
            ninjaAndDownWallContact = true;
        }
    }
    @Override
    public void postSolve(Contact contact, ContactImpulse contactImpulse) {

    }

}

