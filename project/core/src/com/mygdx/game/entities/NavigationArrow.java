package com.mygdx.game.entities;

import com.badlogic.gdx.math.MathUtils;

public class NavigationArrow {
    public float rotation; // góc tọa với Ox
    public static final float WIDTH = 100;
    public static final float HEIGHT = 20;
    public NavigationArrow() {

    }
    public void update(float xWeapon, float yWeapon, float MOUSE_X, float MOUSE_Y) {
        rotation = (float) Math.toDegrees(MathUtils.acos((float) ((double) (MOUSE_X - xWeapon) / (Math.sqrt((MOUSE_Y - yWeapon) * (MOUSE_Y - yWeapon) + (MOUSE_X - xWeapon) * (MOUSE_X - xWeapon))))));
        if (MOUSE_Y < yWeapon) rotation = 360 - rotation;
    }
}