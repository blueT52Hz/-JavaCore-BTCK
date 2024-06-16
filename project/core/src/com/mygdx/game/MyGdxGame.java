package com.mygdx.game;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.view.MainMenuScreen;
import com.mygdx.game.model.constant.ConstantSound;


public class MyGdxGame extends Game {
	public static final int WIDTH = 400;
	public static final int HEIGHT = 720;
	public SpriteBatch batch;

	@Override
	public void create () {
		batch = new SpriteBatch();
		try {
			ConstantSound.load();
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.setScreen(new MainMenuScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}

}
