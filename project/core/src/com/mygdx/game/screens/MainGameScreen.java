package com.mygdx.game.screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.async.ThreadUtils;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.entities.Kunai;
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
    private boolean isMousePressed;
    float stateTime;
    float cos;

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
        if (Gdx.input.isTouched())
        {
            float MOUSE_X = Gdx.input.getX();
            float MOUSE_Y = Gdx.graphics.getHeight() - Gdx.input.getY();
            cos = (float) ((double)(MOUSE_X - ninja.kunai.x)/(Math.sqrt((MOUSE_Y - ninja.kunai.y) * (MOUSE_Y - ninja.kunai.y) +(MOUSE_X- ninja.kunai.x) * (MOUSE_X - ninja.kunai.x))));
            ninja.kunai.timeMoving = 0;
            //System.out.println(MOUSE_X + " " + MOUSE_Y + " " + ninja.kunai.x + " " + ninja.kunai.y);
            float arcosValue = MathUtils.acos(cos);
            if (MOUSE_Y < ninja.kunai.y) ninja.navigationArrow.rotation = 360 - (float) Math.toDegrees(arcosValue);
            else ninja.navigationArrow.rotation = (float) Math.toDegrees(arcosValue);
            //System.out.println(ninja.navigationArrow.rotation);
            sprite.setOriginCenter();
            sprite.setRotation(ninja.navigationArrow.rotation);
            sprite.setBounds(ninja.kunai.x - 35, ninja.kunai.y - 26, NavigationArrow.WIDTH, NavigationArrow.HEIGHT);
            sprite.draw(batch);
            spriteKunai.setOriginCenter();
            spriteKunai.setRotation(ninja.navigationArrow.rotation);
        }else {
                ninja.kunai.update(deltaTime, cos);
            //System.out.println(deltaTime);
            ninja.kunai.xSpeed = ninja.kunai.speed*cos;
            ninja.kunai.ySpeed = ninja.kunai.speed*MathUtils.sin(MathUtils.acos(cos));
                //ninja.kunai.timeMoving += deltaTime;
                float time = ninja.kunai.timeMoving;
                System.out.println(ninja.kunai.x + " " + ninja.kunai.y);
                ninja.kunai.next_x +=  ninja.kunai.xSpeed*deltaTime;

            System.out.println(Math.toDegrees(MathUtils.acos(cos)));
                System.out.println(ninja.kunai.next_y);
                if (ninja.kunai.speed*MathUtils.sin(MathUtils.acos(cos)) - 10 * time > 0)
                {
                    ninja.kunai.next_y += ninja.kunai.ySpeed *deltaTime - 5*deltaTime*deltaTime;
                }
                else ninja.kunai.y = 5*deltaTime*deltaTime;
                //System.out.println(ninja.kunai.x + " " + ninja.kunai.y)

            spriteKunai.setBounds(ninja.kunai.x + ninja.kunai.next_x - 4, ninja.kunai.y + ninja.kunai.next_y-20, 40, 8);
            spriteKunai.draw(batchKunai);
        }
        //System.out.println(deltaTime);

        if (Gdx.input.justTouched()){
            float MOUSE_X = Gdx.input.getX();
            float MOUSE_Y = Gdx.graphics.getHeight() - Gdx.input.getY();
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
            }

        } else {
            if(!ninja.throwed) {
                ninja.stateTime += deltaTime;
                spriteKunai.setBounds(ninja.x+MainCharacter.WIDTH/2, ninja.y, 40, 8);

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
                spriteKunai.setBounds(ninja.kunai.x-4, ninja.kunai.y-20, 40, 8);
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
