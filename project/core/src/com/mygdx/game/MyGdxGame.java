package com.mygdx.game;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.view.GameMap;
import com.mygdx.game.view.MainGameScreenTest;
import com.mygdx.game.view.MainMenuScreen;


public class MyGdxGame extends Game {
	public static final int WIDTH = 400;
	public static final int HEIGHT = 720;
	public SpriteBatch batch;
	public BitmapFont font;
	private MainGameScreenTest mainGameScreen;
    @Override
	public void create () {
		batch = new SpriteBatch();
		font = new BitmapFont(); // Bạn cũng có thể tải font từ file .fnt nếu muốn
		font.getData().setScale(2); // Đặt kích thước font nếu cầ
		this.setScreen(new MainMenuScreen(this));
		mainGameScreen = new MainGameScreenTest(this);
	}

	@Override
	public void render () {
		super.render(); // Quan trọng để gọi render từ screen hiện tại
	}

	@Override
	public void dispose () {
		batch.dispose();
		font.dispose();
	}

	public MainGameScreenTest getMainGameScreen() {
		return mainGameScreen;
	}
}
