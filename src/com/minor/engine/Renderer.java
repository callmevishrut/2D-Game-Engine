package com.minor.engine;

import java.awt.image.DataBufferInt;

import com.minor.engine.gfx.Image;
import com.minor.engine.gfx.ImageTile;

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
		
		//DONT RENDER UNNECESSARY PIXELS
		if(offX < -image.getW()) return; //if the image is completely off screen don't not render it
		if(offY < image.getH()) return;
		if(offX >= pW) return;
		if(offY >= pH) return;
		
		
		//not render any pixels which are not on the screen
		int newX = 0;
		int newY = 0;
		int newWidth = image.getW();
		int newHeight = image.getH();
		
	
		//CLIP OFFSCREEN PIXELS
		if( offX < 0 ) // if the image is going off screen
			newX -= offX;
		
		if( offY < 0 ) // if the image is going off screen
			newY -= offY;
		if(newWidth +  offX >= pW) newWidth = newWidth - (newWidth + offX - pW);
			//System.out.println(newWidth);
		if(newHeight +  offY >= pH)newHeight = newHeight - (newHeight + offY - pH);
			//System.out.println(newHeight);

		//Actually drawing pixels
		
		for(int y = newY; y < newHeight; y++)
		{
			for(int x = newX; x < newWidth;x++)
			{
				setPixel(x + offX, y + offY, image.getP()[x + y *image.getW()]);
			}
		}
	}
	
	
	public void drawImageTile(ImageTile image, int offX, int offY, int tileX, int tileY)
	{
		//DONT RENDER UNNECESSARY PIXELS
			if(offX < -image.getTileW()) return; //if the image is completely off screen don't not render it
			if(offY < image.getTileH()) return;
			if(offX >= pW) return;
			if(offY >= pH) return;
			
			
			//not render any pixels which are not on the screen
			int newX = 0;
			int newY = 0;
			int newWidth = image.getTileW();
			int newHeight = image.getTileH();
			
		
			//CLIP OFFSCREEN PIXELS
			if( offX < 0 ) // if the image is going off screen
				newX -= offX;
			
			if( offY < 0 ) // if the image is going off screen
				newY -= offY;
			if(newWidth +  offX >= pW) newWidth = newWidth - (newWidth + offX - pW);
				//System.out.println(newWidth);
			if(newHeight +  offY >= pH)newHeight = newHeight - (newHeight + offY - pH);
				//System.out.println(newHeight);
	
			//Actually drawing pixels
			
			for(int y = newY; y < newHeight; y++)
			{
				for(int x = newX; x < newWidth;x++)
				{
					setPixel(x + offX, y + offY, image.getP()[(x + tileX * image.getTileW()) + (y + tileY * image.getTileH()) * image.getW()]);
				}
			}
		}
	
}
