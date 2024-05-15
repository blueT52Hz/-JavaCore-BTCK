package com.mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
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
    private World world;
    private BoxManager boxManager;
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
        ninja = new Ninja();
        //ninja.kunai.setSize(64 / PPM, 64 / PPM);
        //ninja.kunai.setOrigin(ninja.kunai.getWidth() / 2, ninja.kunai.getHeight() / 2);
        boxManager = new BoxManager();
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, w, h);
        contactListener = new CustomContactListener();
        world = new World(new Vector2(0, 0f), false);
        world.setContactListener(contactListener);
        b2dr = new Box2DDebugRenderer();

        ninja.body = boxManager.createBox(ninja.getX(), ninja.getY(), ninja.getWidth(), ninja.getHeight(), false, world, 0);
        ninja.body.getFixtureList().first().setUserData("ninja");

        ninja.kunai.body = boxManager.createBox(ninja.kunai.x, ninja.kunai.y, 40, 8, false, world, 0);
        ninja.kunai.body.getFixtureList().first().setUserData("kunai");

        System.out.println(ninja.kunai.getHeight() + " " +ninja.kunai.getWidth() );
        // tạo box cho 3 góc trái phải trên dưới
        gameMap = new GameMap(world);
        gameMap.downWall = boxManager.createBox(200, 0, 400, 32, true, world, 0);
        gameMap.downWall.getFixtureList().first().setUserData("downWall");
        gameMap.leftWall = boxManager.createBox(8, 720/2, 16, 720, true, world, 0);
        gameMap.leftWall.getFixtureList().first().setUserData("leftWall");
        gameMap.rightWall = boxManager.createBox(400-8, 720/2, 16, 720, true, world, 0);
        gameMap.rightWall.getFixtureList().first().setUserData("rightWall");
        mouseHandler = new MouseHandler();
        Gdx.input.setInputProcessor(mouseHandler);
    }

    @Override
    public void render (float deltaTime) {
        update(Gdx.graphics.getDeltaTime());
        Gdx.gl.glClearColor(0.15f, 0.15f, 0.3f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();
        gameMap.draw(game.batch);

        ninja.kunai.setRotation(ninja.kunai.body.getAngle() * MathUtils.radiansToDegrees);

        System.out.println(ninja.kunai.getRotation());
        ninja.kunai.setBounds(ninja.kunai.body.getPosition().x * PPM -20,ninja.kunai.body.getPosition().y * PPM - 4, 40 , 8);
        if (ninja.kunai.appear) ninja.kunai.draw(game.batch);
        if(mouseHandler.isDrag()) {
            ninja.body.setTransform(ninja.getX() / PPM, ninja.getY() / PPM, 0);
            //ninja.kunai.body.setTransform(ninja.body.getPosition(), ninja.kunai.rotation * MathUtils.degreesToRadians);
            ninja.navigationArrow.setOriginCenter();
            ninja.navigationArrow.setBounds(ninja.getX() - 35, ninja.getY() - 26, 100, 20);
            ninja.navigationArrow.setRotation(ninja.kunai.rotation);
            ninja.navigationArrow.draw(game.batch);
            ninja.setX(ninja.body.getPosition().x * PPM);
            ninja.setY(ninja.body.getPosition().y * PPM);
//            ninja.kunai.x = ninja.getX();
//            ninja.kunai.y = ninja.getY();
            ninja.kunai.updateRotation();
            ninja.kunai.setAppear(true);
            ninja.setPlayerState(PlayerState.GLIDE);
            ninja.throwed = false;
        }

        if(mouseHandler.isTouchDown()) {
            ninja.kunai.xSpeed = 0;
            ninja.kunai.ySpeed = 0;
            ninja.kunai.setAppear(false);
            ninja.body.setTransform(ninja.kunai.body.getPosition(), 0);
            ninja.setX(ninja.kunai.body.getPosition().x * PPM);
            ninja.setY(ninja.kunai.body.getPosition().y * PPM);
            ninja.setPlayerState(PlayerState.FLASH);
            ninja.kunai.body.setLinearVelocity(0, 0);
            ninja.kunai.x = ninja.kunai.body.getPosition().x * PPM;
            ninja.kunai.y = ninja.kunai.body.getPosition().y * PPM;
        }
        if(!mouseHandler.isDrag() && !mouseHandler.isTouchDown()) {
            if(ninja.kunai.appear) {
                ninja.kunai.update();
                ninja.kunai.body.setLinearVelocity(ninja.kunai.xSpeed/PPM, ninja.kunai.ySpeed/PPM);
                ninja.kunai.body.setTransform(ninja.kunai.body.getPosition(), ninja.kunai.rotation * MathUtils.degreesToRadians);
                if (contactListener.isKunaiAndDownWallContact()) {
                    ninja.kunai.body.setTransform(ninja.kunai.body.getPosition().x, ninja.kunai.body.getPosition().y, 2 * ninja.kunai.body.getAngle() - 360);
                    ninja.kunai.body.setLinearVelocity(ninja.kunai.body.getLinearVelocity().x, -ninja.kunai.body.getLinearVelocity().y);
                }
                //ninja.kunai.update();
                //ninja.kunai.draw(game.batch);
            }else {
                ninja.kunai.x = ninja.kunai.body.getPosition().x;
                ninja.kunai.y = ninja.kunai.body.getPosition().y;
            }
            // nếu đang trên bục thì ninja.setPlayerState(PlayerState.IDLE);
            if(ninja.throwed) {
                if(ninja.getPlayerState() != PlayerState.FLASH) ninja.setPlayerState(PlayerState.GLIDE);
            }
            else ninja.setPlayerState(PlayerState.THROW);
        }
//        ninja.update();
//        ninja.body.setTransform(ninja.getX()/PPM, ninja.getY()/PPM, 0);
//        ninja.kunai.body.setTransform(ninja.kunai.x/PPM, ninja.kunai.y/PPM, 0);

        ninja.draw(game.batch, gameMap.getStateTime());

        game.batch.end();

        b2dr.render(world, camera.combined.scl(PPM));
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
        world.dispose();
        game.batch.dispose();
    }
    public void cameraUpdate(float delta) {
        Vector3 position = camera.position;
        position.x = Gdx.graphics.getWidth()/2;
        position.y = Gdx.graphics.getHeight()/2;
        camera.position.set(position);
        camera.update();
    }
    public void inputUpdate(float delta) {
        int horizontalForce = 0;
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            horizontalForce --;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            horizontalForce ++;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            System.out.println(1);
            ninja.body.applyForceToCenter(0, 300, false);
        }
        ninja.body.setLinearVelocity(horizontalForce * 5, ninja.body.getLinearVelocity().y);
//        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
//            player.applyForceToCenter(0, -10, false);
//        }
//        player.setLinearVelocity(0, ninja.getySpeed());
    }

    public void update(float delta) {
        world.step(1 / 60f, 6, 2);
        inputUpdate(delta);
        cameraUpdate(delta);
        game.batch.setProjectionMatrix(camera.combined);
    }
}