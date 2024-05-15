package com.mygdx.game.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;

public class MouseHandler implements InputProcessor {
    private float waitTimer = 0;
    private boolean touchDown = false;
    private boolean drag = false;
    public boolean isTouchDown() {
        return this.touchDown;
    }
    public boolean isDrag() {
        return this.drag;
    }

    public void setTouch(boolean touch) {
        this.touchDown = touch;
    }

    public void setDrag(boolean drag) {
        this.drag = drag;
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        this.touchDown = true;
        this.drag = false;
        System.out.println("Chạm");
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        this.drag = false;
        this.touchDown = false;
        waitTimer = 0;
        return false;
    }

    @Override
    public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        waitTimer += Gdx.graphics.getDeltaTime();
        if(waitTimer < 3* Gdx.graphics.getDeltaTime()) return false;
        this.drag = true;
        this.touchDown = false;
        System.out.println("Giữ chuột");
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}