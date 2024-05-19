package com.mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.controller.BoxManager;
import com.mygdx.game.controller.CustomContactListener;
import com.mygdx.game.controller.MouseHandler;
import com.mygdx.game.model.constant.PlayerState;
import com.mygdx.game.model.impl.Player.Ninja;

import static com.mygdx.game.model.constant.Constants.PPM;

public class MainGameScreenTest implements Screen {
    private final float SCALE  = 2.0f;
    private Box2DDebugRenderer b2dr;
    private OrthographicCamera camera;
    MyGdxGame game;
    GameMap gameMap;
    MouseHandler mouseHandler;
    Ninja ninja;
    CustomContactListener contactListener;
    private Body platform;
    public MainGameScreenTest(MyGdxGame game) {
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
        contactListener = new CustomContactListener();
        GameMap.world.setContactListener(contactListener);
        b2dr = new Box2DDebugRenderer();



        // tạo box cho 3 góc trái phải trên dưới
        gameMap.downWall = BoxManager.createBox(200, 0, 400, 32, true, GameMap.world, 0);
        gameMap.downWall.getFixtureList().first().setUserData("platform");
        gameMap.leftWall = BoxManager.createBox(8, 720/2, 16, 720, true, GameMap.world, 0);
        gameMap.leftWall.getFixtureList().first().setUserData("wall");
        gameMap.rightWall = BoxManager.createBox(400-8, 720/2, 16, 720, true, GameMap.world, 0);
        gameMap.rightWall.getFixtureList().first().setUserData("wall");
        mouseHandler = new MouseHandler();
        Gdx.input.setInputProcessor(mouseHandler);
    }

    @Override
    public void render (float deltaTime) {
        update(Gdx.graphics.getDeltaTime());
        Gdx.gl.glClearColor(0.15f, 0.15f, 0.3f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        // Test sinh level
        if(Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            gameMap.getLevelManager().nextLevel();
            System.out.println(gameMap.getLevelManager().currentLevel + " " + gameMap.getLevelManager().maxLevel);
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
            gameMap.getLevelManager().preLevel();
            System.out.println(gameMap.getLevelManager().currentLevel + " " + gameMap.getLevelManager().maxLevel);
        }

        game.batch.begin();
        gameMap.draw(game.batch);

        ninja.kunai.update();

        if (ninja.kunai.isAppear()) ninja.kunai.draw(game.batch);
        if(mouseHandler.isDrag()) {
            ninja.kunai.body.setTransform(ninja.body.getPosition(), 90);
            ninja.navigationArrow.setOriginCenter();
            ninja.navigationArrow.setBounds(ninja.body.getPosition().x*PPM - 75, ninja.body.getPosition().y*PPM - 10, 150, 20);
            ninja.navigationArrow.setRotation(ninja.kunai.getRotation());
            ninja.navigationArrow.draw(game.batch);

            ninja.kunai.updateRotation();
            ninja.kunai.setAppear(true);


            ninja.throwed = false;
        }


        // xử lí khi nhấn xuống
        if(mouseHandler.isTouchDown()) {
            ninja.kunai.setAppear(false);
            ninja.kunai.body.setLinearVelocity(0,0);
            ninja.body.setTransform(ninja.kunai.body.getPosition(), 0);
            ninja.setPlayerState(PlayerState.FLASH);
        }



        // xử lí khi không làm gì
        if(!mouseHandler.isDrag() && !mouseHandler.isTouchDown()) {

            // nếu kunai đang bay
            if(ninja.kunai.isAppear()) {
                ninja.kunai.updateSpeed();
            } else {
                ninja.kunai.body.setTransform(ninja.body.getPosition(), 0);
            }


            if(!ninja.throwed) ninja.setPlayerState(PlayerState.THROW);

        }


        ninja.draw(game.batch, gameMap.getStateTime());

        game.batch.end();

        b2dr.render(GameMap.world, camera.combined.scl(PPM));
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