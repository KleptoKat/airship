package com.mitch.flyship.screens;

import com.mitch.flyship.Assets;
import com.mitch.framework.Game;
import com.mitch.framework.Graphics;
import com.mitch.framework.Screen;

public class Menu extends Screen {
	
	public Menu(Game game)
	{
		super(game);
		
	}
	
	@Override
	public void update(float deltaTime) {
		// TODO Auto-generated method stub

	}

	@Override
	public void paint(float deltaTime) {
		Graphics g = game.getGraphics();
		g.drawARGB( 255, 0, 0, 0);
		g.drawImage(Assets.getImage("locale"), 100, 250);
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	@Override
	public void backButton() {
		// TODO Auto-generated method stub

	}

}
