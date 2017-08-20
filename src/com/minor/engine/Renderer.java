package com.minor.engine;

import java.awt.image.DataBufferInt;

import com.minor.engine.gfx.Image;

public class Renderer 
{

	private int pW, pH; // pixel width and pixel height
	private int[] p; // array of all the pixels
	
	
	public Renderer(GameContainer gc)
	{
		pW = gc.getWidth();
		pH = gc.getHeight();
		
		p = ((DataBufferInt) gc.getWindow().getImage().getRaster().getDataBuffer()).getData();
		// p is given pixel data access , if we change p it change the pixels on the screen
		
	}
	
	public void clear()
	{
		for(int i = 0 ; i< p.length; i++)
		{
			p[i] = 0;
		}
	}
	
	public void setPixel(int x, int y , int value)
	{
		if(x < 0 || x >= pW || y < 0 | y >= pH || value == 0xffff00ff)
		{
			
			//0xffff00ff is gonna be our alpha i.e dont render this color
			return;
		}
		p[x + y * pW] = value;
	}
	
	public void drawImage(Image image, int offX, int offY)
	{
		for(int y = 0; y <image.getH(); y++)
		{
			for(int x = 0; x <image.getW();x++)
			{
				setPixel(x + offX, y + offY, image.getP()[x + y *image.getW()]);
			}
		}
	}
	
	
}
