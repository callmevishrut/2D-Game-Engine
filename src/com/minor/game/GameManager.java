package com.minor.game;

import java.awt.event.KeyEvent;

import com.minor.engine.AbstractGame;
import com.minor.engine.GameContainer;
import com.minor.engine.Renderer;

public class GameManager extends AbstractGame
{

	public GameManager()
	{
		
	}
	
	@Override
	public void update(GameContainer gc, float dt)
	{
		if(gc.getInput().isKeyDown(KeyEvent.VK_A))
		{
			System.out.println("A was pressed down");
		}
	}

	@Override
	public void render(GameContainer gc, Renderer renderer) {
		
		
	}
	public static void main(String args[])
	{
		GameContainer gc = new GameContainer(new GameManager());
		gc.start();
		
	}

}
