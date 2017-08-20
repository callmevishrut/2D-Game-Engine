package com.minor.game;

import java.awt.event.KeyEvent;

import com.minor.engine.AbstractGame;
import com.minor.engine.GameContainer;
import com.minor.engine.Renderer;
import com.minor.engine.gfx.Image;
import com.minor.engine.gfx.ImageTile;

public class GameManager extends AbstractGame
{
	private Image image;
	//private ImageTile imageTile;


	public GameManager()
	{
		image = new Image("/text.png");
		//imageTile = new ImageTile("/Anim_test.png", 16, 16);

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
	public void render(GameContainer gc, Renderer r) 
	{
		r.drawImage(image, gc.getInput().getMouseX(), gc.getInput().getMouseY());
		//r.drawImageTile with anim
		
	}
	public static void main(String args[])
	{
		GameContainer gc = new GameContainer(new GameManager());
		gc.start();
		
	}

}
