package com.mygdx.game.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Coin {
    private Animation spinning;
    private float x, y;
    private int size;
    public Coin(float x, float y, int level) {
        this.x = x;
        this.y = y;
        this.size = level * 45;
        load();
    }
    public void draw(SpriteBatch spriteBatch, float gameMapStateTime) {
        Texture tmp ;
        System.out.println("hehehe");
        tmp = (Texture) spinning.getKeyFrame(gameMapStateTime, true);
        spriteBatch.draw(tmp, x, y+size/2, size, size);
    }
    public void load() {
        Texture[] textures = new Texture[10];
        for(int i=0;i<10;++i) {
            textures[i] = new Texture(String.format("Entities/coin/Coin (%d).png", i+1));
        }
        spinning = new Animation<>(0.12f, textures);
    }
}