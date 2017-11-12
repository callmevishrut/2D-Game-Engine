package com.minor.game.components;

import com.minor.engine.GameContainer;
import com.minor.engine.Renderer;
import com.minor.game.GameManager;


public abstract class Component 
{
	protected String tag;
	public abstract void update(GameContainer gc, GameManager gm,float dt);
	public abstract void render(GameContainer gc,Renderer r);
	
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	
}
