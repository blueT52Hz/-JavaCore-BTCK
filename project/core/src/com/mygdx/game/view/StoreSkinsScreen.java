package com.mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.controller.CoinCounter;
import com.mygdx.game.model.PlayerInventory;
import com.mygdx.game.model.impl.Bullet.Weapon;
import com.badlogic.gdx.graphics.Pixmap;
import org.w3c.dom.Text;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StoreSkinsScreen extends BaseScreen {
    private final MyGdxGame game;
    private final Texture coinTexture;
    private final Texture ninjaTexture;
    private final Texture bombTexture;
    private final Texture bomerangTexture;
    private final Texture dartTexture;
    private final Texture foldingFanTexture;
    private final Texture hammerTexture;
    private final Texture shurikenTexture;
    private final Texture shieldTexture;
    private final Texture rugbyTexture;
    private final Texture skinsTexture;

    ArrayList<Weapon> weapons = new ArrayList<Weapon>();

    public StoreSkinsScreen(MyGdxGame game) {
        super(game);
        this.game = game;

        coinTexture = new Texture(Gdx.files.internal("Coin/Coin(5).png"));
        ninjaTexture = new Texture(Gdx.files.internal("Bullet/Kunai.png"));
        bombTexture = new Texture(Gdx.files.internal("Bullet/Bomb.png"));
        shieldTexture = new Texture(Gdx.files.internal("Bullet/Shield.png"));
        rugbyTexture = new Texture(Gdx.files.internal("Bullet/Rugby.png"));
        hammerTexture = new Texture(Gdx.files.internal("Bullet/Hammer.png"));
        bomerangTexture = new Texture(Gdx.files.internal("Bullet/Bomerang.png"));
        foldingFanTexture = new Texture(Gdx.files.internal("Bullet/FoldingFan.png"));
        shurikenTexture = new Texture(Gdx.files.internal("Bullet/Shuriken.png"));
        dartTexture = new Texture(Gdx.files.internal("Bullet/Dart.png"));
        skinsTexture = new Texture(Gdx.files.internal("Button/SkinsButton.png"));
    }

    @Override
    protected void onBackButtonPressed() {
        game.setScreen(new MainMenuScreen(game));
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        loadWeaponsFromJSON("Weapons.json");

        Texture[] weaponTextures = {ninjaTexture, bombTexture, bomerangTexture, dartTexture, foldingFanTexture, hammerTexture, shurikenTexture, shieldTexture, rugbyTexture};

        int width = 90;
        int height = 90;
        int radius = 15;
        Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);

        pixmap.setColor(Color.CLEAR);
        pixmap.fill();
        pixmap.setColor(Color.WHITE);
        pixmap.fillCircle(radius, radius, radius);
        pixmap.fillCircle(width - radius, radius, radius);
        pixmap.fillCircle(radius, height - radius, radius);
        pixmap.fillCircle(width - radius, height - radius, radius);
        pixmap.fillRectangle(radius, 0, width - 2 * radius, height);
        pixmap.fillRectangle(0, radius, width, height - 2 * radius);

        Texture whiteTexture = new Texture(pixmap);
        TextureRegionDrawable whiteBackground = new TextureRegionDrawable(new TextureRegion(whiteTexture));

        pixmap.setColor(Color.CLEAR);
        pixmap.fill();
        pixmap.setColor(0.85f, 0.85f, 0.85f, 1f);
        pixmap.fillCircle(radius, radius, radius);
        pixmap.fillCircle(width - radius, radius, radius);
        pixmap.fillCircle(radius, height - radius, radius);
        pixmap.fillCircle(width - radius, height - radius, radius);
        pixmap.fillRectangle(radius, 0, width - 2 * radius, height);
        pixmap.fillRectangle(0, radius, width, height - 2 * radius);

        Texture darkTexture = new Texture(pixmap);
        pixmap.dispose();
        TextureRegionDrawable darkBackground = new TextureRegionDrawable(new TextureRegion(darkTexture));

        ImageButton.ImageButtonStyle buttonStyle = new ImageButton.ImageButtonStyle();
        buttonStyle.imageUp = new TextureRegionDrawable(new TextureRegion(skinsTexture));
        ImageButton skinsButton = new ImageButton(buttonStyle);
        skinsButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new StoreSkinsScreen(game));
                return true;
            }
        });
        skinsButton.setBounds(MyGdxGame.WIDTH - 150, 570 , 90, 90);

        stage.addActor(skinsButton);

        for (int i = 0; i < weaponTextures.length; i++) {
            final int index = i;
            ImageButton.ImageButtonStyle style = new ImageButton.ImageButtonStyle();
            style.imageUp = new TextureRegionDrawable(new TextureRegion(weaponTextures[i]));
            style.up = whiteBackground;
            style.over = darkBackground;
            ImageButton weaponButton = new ImageButton(style);
            weaponButton.addListener(new InputListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    handleWeaponButtonClick(index);
                    return true;
                }
            });
            Table buttonTable = new Table();
            buttonTable.setBackground(whiteBackground);
            buttonTable.add(weaponButton).size(70, 70).center();

            table.add(buttonTable).pad(10).size(90, 90);
            if ((i + 1) % 3 == 0) {
                table.row();
            }

            Texture lockTexture = new Texture(Gdx.files.internal("lock.png"));

            if (!weapons.get(i).isUnlocked()) {
                // Thêm biểu tượng khóa
                Image lockImage = new Image(lockTexture);
                lockImage.setSize(20, 20);
                lockImage.setPosition(50, 50);
                Table lockTable = new Table();
                lockTable.add(lockImage).size(10, 10).top().right().padTop(-5).padRight(-5);
                weaponButton.addActor(lockTable);
            }
        }

    }

    private void handleWeaponButtonClick(int index) {
        // Handle button click logic here, such as buying or equipping the weapon
        Weapon weapon = weapons.get(index);
        if (CoinCounter.getCoinIngame() >= weapon.getPrice() && !weapon.isUnlocked()) {
            CoinCounter.updateCoin(-weapon.getPrice()); // Subtract coins
            weapon.setUnlocked(true); // Mark weapon as unlocked
            PlayerInventory.addItem(weapon.getName()); // Add weapon to inventory
        }
    }

    private void loadWeaponsFromJSON(String filename) {
        FileReader reader = null;
        try {
            reader = new FileReader(filename);
            Type classOfT = new TypeToken<ArrayList<Weapon>>(){}.getType();
            Gson gson = new Gson();
            weapons = gson.fromJson(reader, classOfT);
            for (Weapon weapon : weapons) {
                weapon.display();
            }
        } catch (FileNotFoundException e) {
            Logger.getLogger(StoreArmsScreen.class.getName()).log(Level.SEVERE,null, e);
        } finally {
            if (reader != null){
                try{
                    reader.close();
                }catch (IOException ex){
                    Logger.getLogger(StoreArmsScreen.class.getName()).log(Level.SEVERE,null, ex);
                }
            }
        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.15f, 0.15f, 0.3f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();
        game.batch.draw( new Texture("Button/TableHighscore.PNG"), (float) MyGdxGame.WIDTH / 2 - 180, 180, 360, 360);
        game.batch.end();
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }


    @Override
    public void dispose() {
        stage.dispose();
        coinTexture.dispose();
        ninjaTexture.dispose();
        bombTexture.dispose();
        shieldTexture.dispose();
        rugbyTexture.dispose();
        hammerTexture.dispose();
        bomerangTexture.dispose();
        foldingFanTexture.dispose();
        shurikenTexture.dispose();
        dartTexture.dispose();
    }
}
