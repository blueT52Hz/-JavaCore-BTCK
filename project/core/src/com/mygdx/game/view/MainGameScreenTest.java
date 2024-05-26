package com.mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.controller.BoxManager;
import com.mygdx.game.controller.CustomContactListener;
import com.mygdx.game.controller.MouseHandler;
import com.mygdx.game.model.constant.PlayerState;
import com.mygdx.game.model.impl.Player.Ninja;

import static com.mygdx.game.model.constant.Constants.PPM;

public class MainGameScreenTest implements Screen {
    private final float SCALE = 2.0f;
    private Box2DDebugRenderer b2dr;
    private OrthographicCamera camera;
    MyGdxGame game;
    GameMap gameMap;
    MouseHandler mouseHandler;
    Ninja ninja;
    CustomContactListener contactListener;

    private BitmapFont lvFont, completeFont, taptocontinueFont, nameFont, coinFont;
    private FreeTypeFontGenerator fontGenerator;
    private Texture tableTexture;
    private Texture coinTexture;

    private GlyphLayout layout;
    private boolean levelComplete;

    private FreeTypeFontGenerator.FreeTypeFontParameter parameter;

    public MainGameScreenTest(MyGdxGame game) {
        this.game = game;
        this.fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("MinecraftRegular-Bmg3.otf"));
    }

    @Override
    public void show() {
        gameMap = new GameMap();
        ninja = (Ninja) gameMap.getLevelManager().getPlayer();

        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, w, h);
        contactListener = new CustomContactListener();
        GameMap.world.setContactListener(contactListener);
        b2dr = new Box2DDebugRenderer();

        layout = new GlyphLayout();

        gameMap.downWall = BoxManager.createBox(200, 0, 400, 32, true, GameMap.world, 0);
        gameMap.downWall.getFixtureList().first().setUserData("platform");
        gameMap.leftWall = BoxManager.createBox(8, 720 / 2, 16, 720, true, GameMap.world, 0);
        gameMap.leftWall.getFixtureList().first().setUserData("wall");
        gameMap.rightWall = BoxManager.createBox(400 - 8, 720 / 2, 16, 720, true, GameMap.world, 0);
        gameMap.rightWall.getFixtureList().first().setUserData("wall");
        mouseHandler = new MouseHandler();
        Gdx.input.setInputProcessor(mouseHandler);

        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 50;
        parameter.color = Color.YELLOW;
        completeFont = fontGenerator.generateFont(parameter);

        parameter.size = 20;
        parameter.color = Color.WHITE;
        taptocontinueFont = fontGenerator.generateFont(parameter);

        parameter.size = 50;
        parameter.color = Color.WHITE;
        lvFont = fontGenerator.generateFont(parameter);

        parameter.size = 20;
        parameter.color = Color.WHITE;
        nameFont = fontGenerator.generateFont(parameter);

        parameter.size = 20;
        parameter.color = Color.YELLOW;
        coinFont = fontGenerator.generateFont(parameter);

        tableTexture = new Texture(Gdx.files.internal("Button/TableHighscore.PNG"));
        coinTexture = new Texture(Gdx.files.internal("Coin/Coin(5).png"));

        Gdx.app.log("Font", "Font generated successfully.");
    }

    @Override
    public void render(float deltaTime) {
        update(Gdx.graphics.getDeltaTime());
        Gdx.gl.glClearColor(0.15f, 0.15f, 0.3f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();
        gameMap.draw(game.batch);

        ninja.kunai.update();

        if (ninja.kunai.isAppear()) ninja.kunai.draw(game.batch);
        if (mouseHandler.isDrag()) {
            ninja.kunai.body.setTransform(ninja.body.getPosition(), 90);
            ninja.navigationArrow.setOriginCenter();
            ninja.navigationArrow.setBounds(ninja.body.getPosition().x * PPM - 75, ninja.body.getPosition().y * PPM - 10, 150, 20);
            ninja.navigationArrow.setRotation(ninja.kunai.getRotation());
            ninja.navigationArrow.draw(game.batch);

            ninja.kunai.updateRotation();
            ninja.kunai.setAppear(true);

            ninja.throwed = false;
        }

        if (mouseHandler.isTouchDown()) {
            ninja.kunai.setAppear(false);
            ninja.kunai.body.setLinearVelocity(0, 0);
            ninja.body.setTransform(ninja.kunai.body.getPosition(), 0);
            ninja.setPlayerState(PlayerState.FLASH);
        }

        if (!mouseHandler.isDrag() && !mouseHandler.isTouchDown()) {
            if (ninja.kunai.isAppear()) {
                ninja.kunai.updateSpeed();
            } else {
                ninja.kunai.body.setTransform(ninja.body.getPosition(), 0);
            }
            if (!ninja.throwed) ninja.setPlayerState(PlayerState.THROW);
        }

        ninja.draw(game.batch, gameMap.getStateTime());

        if (gameMap.allEnemiesDefeated()) {
            game.batch.draw(tableTexture, MyGdxGame.WIDTH / 2 - 180, 200, 360, 300);

            String completeText = "COMPLETE";
            layout.setText(completeFont, completeText);
            float completeTextWidth = layout.width;
            float completeTextHeight = layout.height;
            float completeTextX = (Gdx.graphics.getWidth() - completeTextWidth) / 2;
            float completeTextY = (Gdx.graphics.getHeight() + completeTextHeight) / 2 + 50;
            completeFont.draw(game.batch, completeText, completeTextX, completeTextY);

            String continueText = "Tap to continue";
            layout.setText(taptocontinueFont, continueText);
            float continueTextWidth = layout.width;
            float continueTextHeight = layout.height;
            float continueTextX = (Gdx.graphics.getWidth() - continueTextWidth) / 2;
            float continueTextY = (Gdx.graphics.getHeight() - continueTextHeight) / 2 - 30;
            taptocontinueFont.draw(game.batch, continueText, continueTextX, continueTextY);

            if (Gdx.input.justTouched()) {
                gameMap.getLevelManager().nextLevel();
                levelComplete = false;
            }
        } else {
            String levelText = "LV " + gameMap.getLevelManager().currentLevel;
            layout.setText(lvFont, levelText);
            float textWidth = layout.width;
            float x = (Gdx.graphics.getWidth() - textWidth) / 2;
            float y = gameMap.downWall.getPosition().y * PPM + 130; // Đặt ở dưới màn hình, trên downWall một chút

            lvFont.setColor(1, 1, 1, 0.1f); // Đặt độ mờ là 50%
            lvFont.draw(game.batch, levelText, x, y);

            // Vẽ tên người chơi
            String playerName = "Name"; // Thay thế bằng tên thực tế của người chơi
            nameFont.draw(game.batch, playerName, 20, Gdx.graphics.getHeight() - 10);

            // Vẽ hình ảnh coin và số lượng coin
            game.batch.draw(coinTexture, 12, Gdx.graphics.getHeight() - 40 - 26, 35, 35);
            String coinCount = "x 9999"; // Thay thế bằng số coin thực tế của người chơi
            coinFont.draw(game.batch, coinCount, 50, Gdx.graphics.getHeight() - 40);
        }

        game.batch.end();
    }

    @Override
    public void resize(int width, int height) {
        camera.setToOrtho(false, width, height);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        b2dr.dispose();
        GameMap.world.dispose();
        game.batch.dispose();
        if (fontGenerator != null) {
            fontGenerator.dispose();
        }
        tableTexture.dispose();
        coinTexture.dispose();
    }

    public void update(float deltaTime) {
        GameMap.world.step(1 / 60f, 6, 2);
        cameraUpdate();
        mouseHandler.update();
        ninja.update(deltaTime);
    }

    private void cameraUpdate() {
        b2dr.render(GameMap.world, camera.combined.scl(PPM));
    }
}
