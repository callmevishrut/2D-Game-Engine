package com.minor.game.objects;

import java.awt.event.KeyEvent;

import com.minor.engine.GameContainer;
import com.minor.engine.Renderer;
import com.minor.engine.gfx.ImageTile;
import com.minor.game.GameManager;
import com.minor.game.components.AABBComponent;

public class Player extends GameObject 
{
	private ImageTile playerImage = new ImageTile("/Platformer Assets/player_sprites.png", 16, 16);
	
	
	
	private int direction = 0;
	private float anim = 0;
	
	int tileX, tileY;
	private float offX, offY;
	
	private float speed = 100;
	private float fallSpeed = 10;
	private float jump = -4; //anything going up should be negative
	private boolean ground = false;
	private boolean groundLast = false;
		
	private float fallDistance = 0;
	
	
	public Player(int posX, int posY)
	{
		this.tag = "player";
		this.tileX = posX;
		this.tileY = posY;
		this.offX = 0;
		this.offY = 0;
		this.posX = posX * GameManager.TS;
		this.posY = posY * GameManager.TS;
		this.width = GameManager.TS;
		this.height = GameManager.TS;
		this.padding = 5;
		this.paddingTop = 2;
		
		this.addComponent(new AABBComponent(this));
	}

	@Override
	public void update(GameContainer gc, GameManager gm,float dt) 
	{
		//LEFT AND RIGHT 
		if(gc.getInput().isKey(KeyEvent.VK_D))
		{
			
			if(gm.getCollision(tileX + 1, tileY) || gm.getCollision(tileX + 1, tileY + (int) Math.signum((int)offY)))
			{
				
					offX += dt * speed;
					if(offX > padding)
					{
						offX = padding;
					}
			}
			else
			{
				offX += dt * speed;
			}
		}
		if(gc.getInput().isKey(KeyEvent.VK_A))
		{
			if(gm.getCollision(tileX - 1, tileY) || gm.getCollision(tileX - 1, tileY + (int) Math.signum((int)offY)))
			{
				
				offX -= dt * speed;
				if(offX < -padding)
				{
					offX = -padding;
				}
			}
			else
			{
				offX -= dt * speed;
			}
		}
		
		//ENDING LEFT AND RIGHT
		//Beginning of JUMP and GRAVITY
		fallDistance += dt * fallSpeed; // quadratic movement for gravity
		
	
		
		
		//while in air
		if(fallDistance < 0)
		{
			if((gm.getCollision(tileX, tileY - 1) || gm.getCollision(tileX + (int)Math.signum((int)Math.abs(offX) > padding ? offX : 0)
			,tileY - 1)) && offY < -paddingTop)
			{
				fallDistance = 0;
				offY = -paddingTop;
				
			}
		}
		
		if(fallDistance > 0)
		{
			if((gm.getCollision(tileX, tileY + 1) || gm.getCollision(tileX + (int)Math.signum((int)Math.abs(offX) > padding ? offX : 0)
			,tileY + 1)) && offY > 0)
			{
				fallDistance = 0;
				offY = 0;
				ground = true;
			}
		}
		if(gc.getInput().isKeyDown(KeyEvent.VK_W) && ground)
		{
			fallDistance = jump;
			ground = false;
		}
		offY += fallDistance;
		// END of JUMP AND GRAVITY
		
		//FINAL Position
		if(offY > GameManager.TS / 2)
		{
			tileY++;
			offY -= GameManager.TS;
		}
		if(offY < -GameManager.TS / 2)
		{
			tileY--;
			offY += GameManager.TS;
		}
		if(offX > GameManager.TS / 2)
		{
			tileX++;
			offX -= GameManager.TS;
		}
		if(offX < -GameManager.TS / 2)
		{
			tileX--;
			offX += GameManager.TS;
		}
		posX = tileX * GameManager.TS + offX;
		posY = tileY * GameManager.TS + offY;
		//Did i shoot a bullet
		if(gc.getInput().isKeyDown(KeyEvent.VK_UP))
		{
			gm.addObject(new Bullet(tileX,tileY,offX + width / 2,offY + height / 2,0));
		}
		if(gc.getInput().isKeyDown(KeyEvent.VK_RIGHT))
		{
			gm.addObject(new Bullet(tileX,tileY,offX + width / 2,offY + height / 2,1));
		}
		if(gc.getInput().isKeyDown(KeyEvent.VK_DOWN))
		{
			gm.addObject(new Bullet(tileX,tileY,offX + width / 2,offY + height / 2,2));
		}
		if(gc.getInput().isKeyDown(KeyEvent.VK_LEFT))
		{
			gm.addObject(new Bullet(tileX,tileY,offX + width / 2,offY + height / 2,3));
		}
		if(gc.getInput().isKey(KeyEvent.VK_D))
		{
			direction = 0;
			anim += dt * 8;
			if(anim >= 4)
			{
				anim = 0;
			}
		}
		else if(gc.getInput().isKey(KeyEvent.VK_A))
		{
			direction = 1;	
			anim += dt * 8;
			if(anim >= 4)
			{
				anim = 0;
			}
		}
		else
		{
			anim = 0;
		}
		
		if((int)fallDistance != 0)
		{
			anim = 1;
			ground = false;
		}
		if(ground && !groundLast)
		{
			anim = 2;
		}
		groundLast = ground;
			
		this.updateComponents(gc,gm,dt);
		
		
		
	}

	@Override
	public void render(GameContainer gc, Renderer r) 
	{
		r.drawImageTile(playerImage,(int)posX,(int)posY,(int)anim,direction);
		//r.drawFillRect((int)posX, (int)posY, width, height, 0xff00ff00);
		this.renderComponents(gc, r);
	}

	@Override
	public void collision(GameObject other)
	{
		if(other.getTag().equalsIgnoreCase("platform")) //dealing with the top and bottom collision
		{
			AABBComponent myC = (AABBComponent)this.findComponent("aabb");
			AABBComponent otherC = (AABBComponent)other.findComponent("aabb");
			if(Math.abs(myC.getLastCenterX() - otherC.getLastCenterX()) < myC.getHalfWidth() + otherC.getHalfWidth())
			{
				if(myC.getCenterY() < otherC.getCenterY())
				{
					int distance = (myC.getHalfHeight() + otherC.getHalfHeight()) - (otherC.getCenterY() - myC.getCenterY());
					offY -= distance;
					posY -= distance;
					fallDistance = 0;
					ground = true;
					//but our box is still colliding we need to throw out the box
					myC.setCenterY(myC.getCenterY() - distance);
				}	
				//Bottom side
				if(myC.getCenterY() > otherC.getCenterY())
				{
					int distance = (myC.getHalfHeight() + otherC.getHalfHeight()) - (myC.getCenterY() - otherC.getCenterY());
					offY += distance;
					posY += distance;
					fallDistance = 0;
					myC.setCenterY(myC.getCenterY() + distance);

				
				}	
			}// dealing with side to side collision
			else
			{
				//LEFT
				if(myC.getCenterX() < otherC.getCenterX()) //LEFT
				{
					int distance = (myC.getHalfWidth() + otherC.getHalfWidth()) - (otherC.getCenterX() - myC.getCenterX());
					offX -= distance;
					posX -= distance;
					myC.setCenterX(myC.getCenterX() - distance);
					
				}	
				//RIGHT
				if(myC.getCenterX() > otherC.getCenterX())
				{
					int distance = (myC.getHalfWidth() + otherC.getHalfWidth()) - (myC.getCenterX() - otherC.getCenterX());
					offX += distance;
					posX += distance;
					myC.setCenterX(myC.getCenterX() + distance);

				
				}
			}
		}
		
	}
	
}
