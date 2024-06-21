package com.mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.MyGdxGame;

public class EnterNameScreen extends BaseScreen {
    private final TextField nameInput;
    private final Texture background;
    private final BitmapFont font;

    public EnterNameScreen(MyGdxGame game) {
        super(game);
        background = new Texture("Button/TableHighscore.png");
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("MinecraftRegular-Bmg3.otf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 24;
        font = generator.generateFont(parameter);
        generator.dispose();

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = font;
        Label nameLabel = new Label("Enter your name:", labelStyle);
        nameLabel.setPosition(Gdx.graphics.getWidth() / 2f - 100, Gdx.graphics.getHeight() / 2f + 50);
        stage.addActor(nameLabel);

        TextField.TextFieldStyle textFieldStyle = new TextField.TextFieldStyle();
        textFieldStyle.font = font;
        textFieldStyle.fontColor = Color.BLACK;

        Texture whiteTexture = new Texture("white.png");
        textFieldStyle.background = new TextureRegionDrawable(new TextureRegion(whiteTexture));

        textFieldStyle.cursor = new TextureRegionDrawable(new Texture(Gdx.files.internal("cursor.png")));
        textFieldStyle.cursor.setMinWidth(1f); // Độ rộng của con trỏ
        textFieldStyle.cursor.setMinHeight(20f); // Chiều cao của con trỏ
        textFieldStyle.cursor.setLeftWidth(1f);

        nameInput = new TextField("", textFieldStyle);
        nameInput.setSize(200, 40);
        nameInput.setPosition(Gdx.graphics.getWidth() / 2f - 100, Gdx.graphics.getHeight() / 2f);
        nameInput.setAlignment(Align.center);
        nameInput.setMaxLength(7);
        stage.addActor(nameInput);

        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.font = font;
        TextButton submitButton = new TextButton("Enter", buttonStyle);
        submitButton.setSize(150, 50);
        submitButton.setPosition(Gdx.graphics.getWidth() / 2f - 75, Gdx.graphics.getHeight() / 2f - 60);
        submitButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                String playerName = nameInput.getText();
                if(!playerName.isEmpty()) game.setScreen(new MainGameScreenTest(game, playerName));
            }
        });
        stage.addActor(submitButton);
    }

    @Override
    protected void onBackButtonPressed() {
        game.setScreen(new MainMenuScreen(game));
    }
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.15f, 0.15f, 0.3f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();
        float backgroundX = (float) (Gdx.graphics.getWidth() - 360) / 2;
        float backgroundY = (float) (Gdx.graphics.getHeight() - 200) / 2;
        game.batch.draw(background, backgroundX, backgroundY, 360, 200);
        game.batch.end();

        stage.act(delta);
        stage.draw();
    }
}
