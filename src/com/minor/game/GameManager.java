package com.minor.game;

import java.util.ArrayList;

import com.minor.engine.AbstractGame;
import com.minor.engine.GameContainer;
import com.minor.engine.Renderer;
import com.minor.engine.gfx.Image;
import com.minor.game.objects.GameObject;
import com.minor.game.objects.Physics;
import com.minor.game.objects.Platform;
import com.minor.game.objects.Player;

public class GameManager extends AbstractGame
{
	public static final int TS = 16;
	
	private Image skyImage = new Image("/Platformer Assets/bg.png");
	private Image levelImage = new Image("/Platformer Assets/asset_level.png");
	
	private ArrayList<GameObject> objects = new ArrayList<GameObject>();
	private Camera camera;
	
	private boolean[] collision;
	private int levelW, levelH;
	
	public GameManager()
	{
		objects.add(new Player(4,2));
		objects.add(new Platform(32 * TS, 7 * TS,0xffff0000,false));
		objects.add(new Platform(26 * TS, 7 * TS,0xffff0000,false));
		objects.add(new Platform(36 * TS, 10* TS,0xffff0000,true));
		loadLevel("/Platformer Assets/level.png");
		camera = new Camera("player");
	}
	@Override
	public void init(GameContainer gc) {
		
		
	}
	@Override
	public void update(GameContainer gc, float dt)
	{
		
		for(int i = 0; i < objects.size(); i++)
		{
			objects.get(i).update(gc,this, dt);
			if(objects.get(i).isDead())
			{
				objects.remove(i);
				i--;
			}
		}
		Physics.update();
		camera.update(gc, this, dt);
	}

	@Override
	public void render(GameContainer gc, Renderer r) 
	{
		camera.render(r);
		//render the collison behind the actual objects
		//Drawing the levels
		r.drawImage(skyImage, 0, 0);
		r.drawImage(levelImage, 0, 0);
		
		//collision array
		/*for(int y = 0; y < levelH; y++)
		{
			for(int x = 0; x < levelW; x++)
			{
				if(collision[x + y * levelW] == true)
				{
					r.drawFillRect(x * TS, y *TS, TS, TS, 0xff0f0f0f);
				}
				else
				{
					r.drawFillRect(x * TS, y *TS, TS, TS, 0xfff9f9f9);

				}
			}
		}*/
		for(GameObject obj : objects)
		{
			obj.render(gc,r);
		}
	}
	public void loadLevel(String path)
	{
		Image levelImage = new Image(path);
		
		levelW = levelImage.getW();
		levelH = levelImage.getH();
		collision = new boolean[levelW * levelH];
		for(int y = 0; y< levelImage.getH(); y++)
		{
			for(int x = 0; x < levelImage.getW(); x++)
			{
				if(levelImage.getP()[x + y * levelImage.getW()] == 0xff000000)
				{
					collision[x + y * levelImage.getW()] = true;
				}
				else
				{
					collision[x + y * levelImage.getW()] = false;
				}
			}
		}
	}
	public void addObject(GameObject object)
	{
		objects.add(object);
	}
	public GameObject getObject(String tag)
	{
		for(int i = 0; i < objects.size(); i++)
		{
			if(objects.get(i).getTag().equals(tag))
			{
				return objects.get(i);
			}
		}
		return null;
	}
	public boolean getCollision(int x ,int y)
	{
		if(x < 0 || x >= levelW || y < 0 || y>= levelH)
			return true;
		//if we go outside our level everything will provide a collision
		return collision[x + y * levelW];
	}
	public static void main(String args[])
	{
				
		GameContainer gc = new GameContainer(new GameManager());
		gc.setWidth(320);
		gc.setHeight(240);
		gc.setScale(3f);
		gc.start();
	
	}
	public int getLevelW() {
		return levelW;
	}
	public void setLevelW(int levelW) {
		this.levelW = levelW;
	}
	public int getLevelH() {
		return levelH;
	}
	public void setLevelH(int levelH) {
		this.levelH = levelH;
	}



}
