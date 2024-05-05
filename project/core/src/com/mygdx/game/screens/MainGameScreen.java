package com.mygdx.game.screens;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.entities.MainCharacter;
import com.mygdx.game.entities.NavigationArrow;
import jdk.tools.jmod.Main;
import com.badlogic.gdx.math.MathUtils;

public class MainGameScreen implements Screen {
    SpriteBatch batch, batchNinja, batchKunai;
    Texture texture;
    Sprite sprite, spriteNinja, spriteKunai;

    public static float KUNAI_X = 100;
    public static float KUNAI_Y = 100;
    public static float KUNAI_NEXT_X = 100;
    public static float KUNAI_NEXT_Y = 100;
    public MainCharacter ninja;
    float stateTime;

    MyGdxGame game;
    public MainGameScreen(MyGdxGame game) {
        this.game = game;
        ninja = new MainCharacter();
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        batchNinja = new SpriteBatch();
        batchKunai = new SpriteBatch();
        MainCharacter.load();
        sprite = new Sprite(MainCharacter.navigationImg);
        spriteNinja = new Sprite(MainCharacter.waitImg);
        spriteKunai = new Sprite(MainCharacter.kunaiImg);

        //sprite.setPosition(Gdx.graphics.getWidth() / 2 - sprite.getWidth() / 2,
                //Gdx.graphics.getHeight() / 2 - sprite.getHeight() / 2);
    }

    @Override
    public void render(float deltaTime) {
        Gdx.gl.glClearColor(0.15f, 0.15f, 0.3f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batchKunai.begin();
        batchNinja.begin();
        float MOUSE_X = Gdx.input.getX();
        float MOUSE_Y = Gdx.graphics.getHeight() - Gdx.input.getY();

        if (Gdx.input.isTouched())
        {
            System.out.println(MOUSE_X + " " + MOUSE_Y + " " + ninja.kunai.x + " " + ninja.kunai.y);
            float cos = (float) ((double)(MOUSE_X - ninja.kunai.x)/(Math.sqrt((MOUSE_Y - ninja.kunai.y) * (MOUSE_Y - ninja.kunai.y) +(MOUSE_X- ninja.kunai.x) * (MOUSE_X - ninja.kunai.x))));
            float arccosValue = MathUtils.acos(cos);
            if (MOUSE_Y < ninja.kunai.y) ninja.navigationArrow.rotation = 360 - (float) Math.toDegrees(arccosValue);
            else ninja.navigationArrow.rotation = (float) Math.toDegrees(arccosValue);
            System.out.println(ninja.navigationArrow.rotation);
            sprite.setOriginCenter();
            sprite.setRotation(ninja.navigationArrow.rotation);
            sprite.setBounds(ninja.kunai.x, ninja.kunai.y, NavigationArrow.WIDTH, NavigationArrow.HEIGHT);
            sprite.draw(batch);
            //game.batch.draw(MainCharacter.navigationImg, ,);
        }
        if (Gdx.input.justTouched()){
            if(ninja.throwed && ninja.displacement) {
                ninja.displacement = false;
                ninja.kunai.next_x = MOUSE_X;
                ninja.kunai.next_y = MOUSE_Y;
                ninja.throwed = false;
                spriteNinja.setBounds(ninja.x-MainCharacter.WIDTH/2, ninja.y - MainCharacter.HEIGHT/2, MainCharacter.WIDTH, MainCharacter.HEIGHT);
                spriteNinja.draw(batchNinja);

                ninja.stateTime = 0f;
            } else {
                ninja.displacement = true;
                ninja.x = ninja.kunai.x;
                ninja.y = ninja.kunai.y;
                spriteNinja.setBounds(ninja.x-MainCharacter.WIDTH/2, ninja.y - MainCharacter.HEIGHT/2, MainCharacter.WIDTH, MainCharacter.HEIGHT);
                spriteNinja.draw(batchNinja);
//                ninja.kunai.x=-1000;
//                ninja.kunai.y=-1000;
            }

        } else {
            if(!ninja.throwed) {
                ninja.stateTime += deltaTime;
                spriteKunai.setBounds(ninja.x+MainCharacter.WIDTH/2, ninja.y, 8, 40);
                spriteKunai.draw(batchKunai);
                //game.batch.draw((Texture) MainCharacter.throwAnimation.getKeyFrame(ninja.stateTime, false), ninja.x-MainCharacter.WIDTH/2, ninja.y-MainCharacter.HEIGHT/2, MainCharacter.WIDTH, MainCharacter.HEIGHT);
                if(ninja.stateTime >= 30 * deltaTime) {
                    ninja.throwed = true;
                    ninja.stateTime = 0f;
                }
            } else {
                ninja.kunai.before_x = ninja.kunai.x;
                ninja.kunai.before_y = ninja.kunai.y;
                ninja.kunai.x = ninja.kunai.next_x;
                ninja.kunai.y = ninja.kunai.next_y;
                ninja.stateTime += deltaTime;
                spriteKunai.setBounds(ninja.kunai.x-4, ninja.kunai.y-20, 8, 40);
                spriteKunai.draw(batchKunai);
                //game.batch.draw((Texture) MainCharacter.glideAnimation.getKeyFrame(ninja.stateTime, true), ninja.x-MainCharacter.WIDTH/2, ninja.y - MainCharacter.HEIGHT/2, MainCharacter.WIDTH, MainCharacter.HEIGHT);
            }
        }

        batch.end();
        batchKunai.end();
        batchNinja.end();
    }

    @Override
    public void resize(int i, int i1) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose () {
        batch.dispose();
        texture.dispose();
    }
}
