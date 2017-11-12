package com.minor.game.objects;

import com.minor.engine.GameContainer;
import com.minor.engine.Renderer;
import com.minor.game.GameManager;

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
		this.padding = 0;
		this.paddingTop = 0;
		this.width = 4;
		this.height = 4;
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
		r.drawFillRect((int)posX, (int)posY,width,height , 0xffff0000);
	}
	@Override
	public void collision(GameObject other) {
		// TODO Auto-generated method stub
		
	}
	
}
