package com.minor.game;

import com.minor.engine.GameContainer;
import com.minor.engine.Renderer;

public class Bullet extends GameObject
{
	int tileX, tileY;
	private float offX, offY;
	
	private float speed = 200;
	private int direction;

	public Bullet(int tileX, int tileY, float offX, float offY,int direction)
	{
		this.direction = direction;
		this.tileX = tileX;
		this.tileY = tileY;
		this.offX = offX;
		this.offY = offY;
		posX = tileX * GameManager.TS + offX;
		posY = tileY * GameManager.TS + offY;
	}
	@Override
	public void update(GameContainer gc, GameManager gm, float dt)
	{
		switch(direction)
		{
		case 0: offY -= speed * dt; break;
		case 1: offX += speed * dt; break;
		case 2: offY += speed * dt; break;
		case 3: offX -= speed * dt; break;
		}
		if(offY > GameManager.TS )
		{
			tileY++;
			offY -= GameManager.TS;
		}
		if(offY < 0)
		{
			tileY--;
			offY += GameManager.TS;
		}
		if(offX > GameManager.TS)
		{
			tileX++;
			offX -= GameManager.TS;
		}
		if(offX < 0)
		{
			tileX--;
			offX += GameManager.TS;
		}
		
		posX = tileX * GameManager.TS + offX;
		posY = tileY * GameManager.TS + offY;
		
		//has the bullet collided with a black tile?
		if(gm.getCollision(tileX,  tileY))
		{
			this.dead = true;
		}
	}

	@Override
	public void render(GameContainer gc, Renderer r) 
	{
		r.drawFillRect((int)posX-2, (int)posY-2, 4, 4, 0xffff0000);
	}
	
}
