package com.minor.game;

import com.minor.engine.GameContainer;
import com.minor.engine.Renderer;
import com.minor.game.objects.GameObject;

public class Camera 
{
	private float offX, offY;
	
	private GameObject target;
	private String targetTag = null;
	public Camera(String tag)
	{
		this.targetTag = tag;
	}
	public void update(GameContainer gc,GameManager gm,float dt)
	{
		if(target == null)
		{
			target = gm.getObject(targetTag);
			
		}
		if(target == null)
			return;
		
		
		float targetX = (target.getPosX() + target.getWidth() / 2) - gc.getWidth() / 2;
		float targetY = (target.getPosY() + target.getHeight() / 2) - gc.getHeight() /2;
		
		//flow y camera
		//offX = targetX;
		//offY = targetY;
		offX -= dt * (offX - targetX) * 8 ;
		offY -= dt * (offY - targetY) * 8 ;
		
		//dont go out of bounds
		if( offX < 0) offX = 0;
		if( offY < 0) offY = 0;
		
		if(offX + gc.getWidth() > gm.getLevelW() * GameManager.TS) offX = gm.getLevelW() * GameManager.TS - gc.getWidth();
		if(offY + gc.getHeight() > gm.getLevelH() * GameManager.TS) offY = gm.getLevelH() * GameManager.TS - gc.getHeight();
		
		
		
	}
	public void render(Renderer r)
	{
		r.setCamX((int)offX);
		r.setCamY((int)offY);
	}
	public float getOffX() {
		return offX;
	}
	public void setOffX(float offX) {
		this.offX = offX;
	}
	public float getOffY() {
		return offY;
	}
	public void setOffY(float offY) {
		this.offY = offY;
	}
	public GameObject getTarget() {
		return target;
	}
	public void setTarget(GameObject target) {
		this.target = target;
	}
	public String getTargetTag() {
		return targetTag;
	}
	public void setTargetTag(String targetTag) {
		this.targetTag = targetTag;
	}
}
