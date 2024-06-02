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
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.controller.BoxManager;
import com.mygdx.game.controller.CoinCounter;
import com.mygdx.game.controller.CustomContactListener;
import com.mygdx.game.controller.MouseHandler;
import com.mygdx.game.model.Coin;
import com.mygdx.game.model.constant.PlayerState;
import com.mygdx.game.model.impl.Player.Ninja;

import java.util.ArrayList;

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
    private BitmapFont lvFont, completeFont, taptocontinueFont, nameFont, coinFont, scoreFont;
    private FreeTypeFontGenerator fontGenerator;
    private Texture tableTexture;
    private Texture coinTexture;
    private GlyphLayout layout;
    private boolean levelComplete;
    private FreeTypeFontGenerator.FreeTypeFontParameter parameter;
    private String playerName;
    private Body platform;
    public MainGameScreenTest(MyGdxGame game, String playerName) {
        this.game = game;
        this.playerName = playerName;
        this.fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("MinecraftRegular-Bmg3.otf"));
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
        gameMap.bottomWall = BoxManager.createBox(200, 0-16, 400, 32, true, GameMap.world, 0);
        gameMap.bottomWall.getFixtureList().first().setUserData("bottomWall");
        gameMap.topWall = BoxManager.createBox(200, 720+16, 400, 32, true, GameMap.world, 0);
        gameMap.topWall.getFixtureList().first().setUserData("topWall");
        gameMap.leftWall = BoxManager.createBox(8, 720/2, 16, 720, true, GameMap.world, 0);
        gameMap.leftWall.getFixtureList().first().setUserData("wall");
        gameMap.rightWall = BoxManager.createBox(400 - 8, 720 / 2, 16, 720, true, GameMap.world, 0);
        gameMap.rightWall.getFixtureList().first().setUserData("wall");

        mouseHandler = new MouseHandler();
        Gdx.input.setInputProcessor(mouseHandler);

        layout = new GlyphLayout();
        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 50;
        parameter.color = Color.RED;
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

        parameter.size = 20;
        parameter.color = Color.WHITE;
        scoreFont = fontGenerator.generateFont(parameter);

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

        // vẽ UI
        String levelText = "LV " + gameMap.getLevelManager().currentLevel;
        layout.setText(lvFont, levelText);
        float textWidth = layout.width;
        float x = (Gdx.graphics.getWidth() - textWidth) / 2;
        float y = gameMap.bottomWall.getPosition().y * PPM + 130; // Đặt ở dưới màn hình, trên downWall một chút

        lvFont.setColor(1, 1, 1, 0.1f); // Đặt độ mờ là 50b%
        lvFont.draw(game.batch, levelText, x, y);

        // Vẽ tên người chơi
        nameFont.draw(game.batch, "Name: " + playerName, 20, Gdx.graphics.getHeight() - 10);

        // Vẽ hình ảnh coin và số lượng coin
        game.batch.draw(coinTexture, 12, Gdx.graphics.getHeight() - 40 - 26, 35, 35);
        String coinCount = String.valueOf(CoinCounter.getCoinIngame());
        coinFont.draw(game.batch, coinCount, 50, Gdx.graphics.getHeight() - 40);

        scoreFont.draw(game.batch, "Score: " + Integer.toString(gameMap.playerScore.getScore()), Gdx.graphics.getWidth() - 100, Gdx.graphics.getHeight() - 10);
        // end vẽ UI

        ninja.kunai.update();

        if (ninja.kunai.isAppear()) ninja.kunai.draw(game.batch);
        if(mouseHandler.isDrag()) {
            ninja.kunai.body.setTransform(ninja.getBody().getPosition(), 90);
            ninja.navigationArrow.setOriginCenter();
            ninja.navigationArrow.setBounds(ninja.getBody().getPosition().x*PPM - 75, ninja.getBody().getPosition().y*PPM - 10, 150, 20);
            ninja.navigationArrow.setRotation(ninja.kunai.getRotation());
            ninja.navigationArrow.draw(game.batch);
            ninja.kunai.updateRotation();
            ninja.kunai.setAppear(true);
            ninja.throwed = false;
        }

        // xử lí khi nhấn xuống
        if(ninja.getPlayerState() != PlayerState.DEAD && mouseHandler.isTouchDown()) {
            ninja.setAppear(true);
            ninja.getBody().setTransform(ninja.kunai.body.getPosition(), 0);
            ninja.setPlayerState(PlayerState.FLASH);
            ninja.setPlace(gameMap.getLevelManager().currentLevel);

            ninja.kunai.setAppear(false);
            ninja.kunai.body.setLinearVelocity(0,0);
        }

        // xử lí khi không làm gì
        if(!mouseHandler.isDrag() && !mouseHandler.isTouchDown()) {
            // nếu kunai đang bay
            if(ninja.kunai.isAppear())  ninja.kunai.updateSpeed();
            else                        ninja.kunai.body.setTransform(ninja.getBody().getPosition(), 90);
            if(!ninja.throwed) ninja.setPlayerState(PlayerState.THROW);
        }
        if(ninja.getPlace() == gameMap.getLevelManager().currentLevel) ninja.draw(game.batch, gameMap.getStateTime());
            // Vẽ tên người chơi
            String playerName = "Name: "; // Thay thế bằng tên thực tế của người chơi
            nameFont.draw(game.batch, playerName, 20, Gdx.graphics.getHeight() - 10);

        if (ninja.getPlayerState() == PlayerState.DEAD) {
            Array<Body> bodies = new Array<>();
            GameMap.world.getBodies(bodies);
            System.out.println(bodies.size);
            for (Body body : bodies) {
                GameMap.destroyBody(body);
            }

            GameMap.playerScore.saveScore(playerName);
            CoinCounter.updateCoin(CoinCounter.getCoinIngame());
            CoinCounter.resetCoinIngame();
            game.batch.draw(tableTexture, MyGdxGame.WIDTH / 2 - 180, 200, 360, 300);

            String completeText = "YOU LOSE";
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

            if(Gdx.input.justTouched()) {
            }
        }

            scoreFont.draw(game.batch, "Score: " + Integer.toString(gameMap.playerScore.getScore()), Gdx.graphics.getWidth() - 100, Gdx.graphics.getHeight() - 10);

        b2dr.render(GameMap.world, camera.combined.scl(PPM));
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
    public void cameraUpdate(float delta) {
        Vector3 position = camera.position;
        position.x = Gdx.graphics.getWidth()/2;
        position.y = Gdx.graphics.getHeight()/2;
        camera.position.set(position);
        camera.update();
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
