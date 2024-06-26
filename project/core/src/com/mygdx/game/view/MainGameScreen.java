package com.mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.controller.BoxManager;
import com.mygdx.game.controller.CustomContactListener;
import com.mygdx.game.controller.MouseHandler;
import com.mygdx.game.model.constant.PlayerState;
import com.mygdx.game.model.impl.Player.Ninja;

import static com.mygdx.game.model.constant.Constants.PPM;

public class MainGameScreen implements Screen {
    private final float SCALE  = 2.0f;
    private Box2DDebugRenderer b2dr;
    private OrthographicCamera camera;
    MyGdxGame game;
    GameMap gameMap;
    MouseHandler mouseHandler;
    Ninja ninja;
    CustomContactListener contactListener;
    private Body platform;
    public MainGameScreen(MyGdxGame game) {
        this.game = game;
    }
    @Override
    public void show () {

        gameMap = new GameMap();
        ninja = (Ninja) gameMap.getLevelManager().getPlayer();


        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, w, h);
        contactListener = new CustomContactListener(this.gameMap);
        GameMap.world.setContactListener(contactListener);
        b2dr = new Box2DDebugRenderer();



        // tạo box cho 3 góc trái phải trên dưới
        gameMap.bottomWall = BoxManager.createBox(200, 0, 400, 32, true, GameMap.world, 0);
        gameMap.bottomWall.getFixtureList().first().setUserData("platform");
        gameMap.topWall = BoxManager.createBox(200, 720+16, 400, 32, true, GameMap.world, 0);
        gameMap.topWall.getFixtureList().first().setUserData("platform");
        gameMap.leftWall = BoxManager.createBox(8, 720/2, 16, 720, true, GameMap.world, 0);
        gameMap.leftWall.getFixtureList().first().setUserData("wall");
        gameMap.rightWall = BoxManager.createBox(400-8, 720/2, 16, 720, true, GameMap.world, 0);
        gameMap.rightWall.getFixtureList().first().setUserData("wall");
        mouseHandler = new MouseHandler();
        Gdx.input.setInputProcessor(mouseHandler);
    }

    @Override
    public void render (float deltaTime) {
        Body body = BoxManager.createBox(12, 12, 20, 20, false, GameMap.world, 0);
        GameMap.world.destroyBody(body);
        body = null;
        if(body == null) System.out.println("heheh");
        else System.out.println("nônno");
    }
    @Override
    public void resize (int width, int height) {
        camera.setToOrtho(false, width, height);
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
    public void dispose() {
        b2dr.dispose();
        GameMap.world.dispose();
        game.batch.dispose();
    }
    public void cameraUpdate(float delta) {
        Vector3 position = camera.position;
        position.x = Gdx.graphics.getWidth()/2;
        position.y = Gdx.graphics.getHeight()/2;
        camera.position.set(position);
        camera.update();
    }

    public void update(float delta) {
        GameMap.world.step(1 / 60f, 6, 2);
        cameraUpdate(delta);
        game.batch.setProjectionMatrix(camera.combined);
    }
}