package com.minor.engine;

import java.awt.image.DataBufferInt;

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
	
	
}
