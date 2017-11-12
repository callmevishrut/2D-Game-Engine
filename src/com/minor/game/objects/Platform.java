package com.minor.game.objects;

import com.minor.engine.GameContainer;
import com.minor.engine.Renderer;
import com.minor.game.GameManager;
import com.minor.game.components.AABBComponent;

public class Platform extends GameObject
{
	private int color;
	private boolean isFloatable;
	public Platform(int posX,int posY,int color,boolean isFloatable)
	{
		this.tag = "platform";
		this.width = 32;
		this.height = 16;
		this.padding = 0;
		this.paddingTop = 0;
		this.posX = posX;
		this.posY = posY;
		this.color = color;
		
		this.isFloatable = isFloatable;
		this.addComponent(new AABBComponent(this));
	}

	float temp = 0;
	@Override
	public void update(GameContainer gc, GameManager gm, float dt) 
	{
		if(isFloatable)
		{
			temp += dt;
			posY += Math.cos(temp) * 2;
		}
		this.updateComponents(gc, gm, dt);
	}

	@Override
	public void render(GameContainer gc, Renderer r) 
	{
		
		r.drawFillRect((int)posX,(int)posY, width, height, color * 12);
		this.renderComponents(gc,r);
	}

	@Override
	public void collision(GameObject other)
	{
		color = 0xff0000ff;
	}

}
