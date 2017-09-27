package com.minor.game;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import com.minor.engine.AbstractGame;
import com.minor.engine.GameContainer;
import com.minor.engine.Renderer;
import com.minor.engine.audio.SoundClip;
import com.minor.engine.gfx.Image;
//import com.minor.engine.gfx.ImageTile;

public class GameManager extends AbstractGame
{
	private Image image;
	//private ImageTile imageTile;
	private SoundClip clip;
	


	public GameManager()
	{
		image = new Image("/text.png");
		//imageTile = new ImageTile("/Anim_test.png", 16, 16);
		clip = new SoundClip("/Sounds/test.wav");

	}
	
	@Override
	public void update(GameContainer gc, float dt)
	{
		if(gc.getInput().isKeyDown(KeyEvent.VK_A))
		{
			//System.out.println("A was pressed down");
			clip.play();
			
		}
		if(gc.getInput().isKeyDown(KeyEvent.VK_S))
		{
			//System.out.println("A was pressed down");
			clip.stop();
			
		}
		//we can take a temp variable and add the delta time * speed to it 
		// we can loop the temp to the index to where we want the image to draw
	}

	@Override
	public void render(GameContainer gc, Renderer r) 
	{
		
		r.setzDepth(0);
    	r.drawText("FPS:" + gc.getFPS(), 0, 0, 0xff00ffff);
    	r.setzDepth(1000);
    	//r.drawRect(10,10,32,32,0xffffccff); //draw a rectangle
    	r.drawFillRect(gc.getInput().getMouseX()-16, gc.getInput().getMouseY()-16,32,32,0xffffffff);
    	
    	
    	r.setzDepth(100);
    	r.drawImage(image, gc.getInput().getMouseX(), gc.getInput().getMouseY());
		//r.drawImageTile with anim
		//r.drawImageTile(image, offx, offY, tileX, tileY); // tile x , tile y give the index of the grid displayed right now
		
	}
	public static void main(String args[])
	{
		GameContainer gc = new GameContainer(new GameManager());
		gc.start();
		
	}

}
