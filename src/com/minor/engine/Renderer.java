package com.minor.engine;

import java.awt.image.DataBufferInt;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import com.minor.engine.gfx.Font;
import com.minor.engine.gfx.Image;
import com.minor.engine.gfx.ImageTile;

public class Renderer 
{
	private Font font = Font.STANDARD;
	
	private ArrayList<ImageRequest> imageRequest = new ArrayList<ImageRequest>();
	
	private int pW, pH; // pixel width and pixel height
	private int[] p; // array of all the pixels
	
	private int[] zb; // for zx buffer
	
	private int zDepth = 0;

	private boolean processing = false;
	
	
	
	
	public Renderer(GameContainer gc)
	{
		pW = gc.getWidth();
		pH = gc.getHeight();
		
		p = ((DataBufferInt) gc.getWindow().getImage().getRaster().getDataBuffer()).getData();
		// p is given pixel data access , if we change p it change the pixels on the screen
		
		zb = new int[p.length];
		
	}
	
	public void clear()
	{
		for(int i = 0 ; i< p.length; i++)
		{
			p[i] = 0;
			zb[i] = 0;
		}
	}
	public void process()
	{
		processing = true;
		//we need to sort the imageRequest array by the z depth as , it is neccessary or the compiler
		//to know which image is to be drawn first and in order
		Collections.sort(imageRequest, new Comparator<ImageRequest>(){

			@Override
			public int compare(ImageRequest i0, ImageRequest i1) {
				// TODO Auto-generated method stub
				
				if(i0.zDepth < i1.zDepth)
					return -1;
				if(i0.zDepth > i1.zDepth)
					return 1;
				
				return 0;
			}
			
		}
				
				
				);
				
		for(ImageRequest ir : imageRequest)
		{
			setzDepth(ir.zDepth);
			
			drawImage(ir.image,ir.offX,ir.offY);
		}
		processing = false;
		imageRequest.clear();
	}
	
	
	public void setPixel(int x, int y , int value)
	{
		
		int alpha = ((value >> 24 ) & 0xff);
		if(x < 0 || x >= pW || y < 0 | y >= pH || alpha == 0 )
		{
			
			//0xffff00ff is not gonna be our alpha i.e dont render this color
			//modified :: 
			return;
		}
		int index = x + y * pW;
		if( zb[index] > zDepth)
			return;
		zb[index] = zDepth;
		if(alpha == 255)
			p[index] = value;
		else
		{
			//int color = 0; //color for blending the alpha channels
			int pixelColor = p[index];
			int newRed = ((pixelColor >> 16) & 0xff) - (int)((((pixelColor >> 16)& 0xff) - ((value >> 16) &0xff)) *(alpha / 255f)) ;
			int newGreen = ((pixelColor >> 8) & 0xff) -(int)((((pixelColor >> 8)& 0xff) - ((value >> 8) &0xff)) *(alpha / 255f)) ;
			int newBlue = (pixelColor & 0xff) - (int)((((pixelColor)& 0xff) - ((value) &0xff)) *(alpha / 255f)) ;
			p[index] = (255 << 24 | newRed << 16 | newGreen << 8 | newBlue);
		}
	}
	
	public void drawImage(Image image, int offX, int offY)
	{
		
		if(image.isAlpha() && !processing)
		{
			imageRequest.add(new ImageRequest(image,zDepth,offX,offY));
			return;
		}
		
		//DONT RENDER UNNECESSARY PIXELS
		if(offX < -image.getW()) return; //if the image is completely off screen don't not render it
		if(offY < -image.getH()) return;
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
	
	public void drawText(String text, int offX, int offY, int color)
	{
	
		
		//since we dont ahve lowercase we will change every text to upper case
		text = text.toUpperCase();
		int offset = 0;
		
		for(int i = 0; i < text.length(); i++) 
		{
			int unicode = text.codePointAt(i) - 32; // make space 0 
						
			for ( int y = 0; y < font.getFontImage().getH(); y++)
			{
				for(int x = 0; x < font.getWidths()[unicode]; x++)
				{
					if (font.getFontImage().getP()[(x + font.getOffsets()[unicode])+ y* font.getFontImage().getW()] == 0xffffffff)
					{
						setPixel(x + offX + offset,y + offY,color);
					}
				}
			}
			
			offset += font.getWidths()[unicode];
		}
	}
	
	public void drawImageTile(ImageTile image, int offX, int offY, int tileX, int tileY)
	{
		if(image.isAlpha() && !processing)
		{
			imageRequest.add(new ImageRequest(image.getTileImage(tileX, tileY),zDepth,offX,offY));
			return;
		}
		
		//DONT RENDER UNNECESSARY PIXELS
			if(offX < -image.getTileW()) return; //if the image is completely off screen don't not render it
			if(offY < -image.getTileH()) return;
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
	public void drawRect(int offX, int offY, int width, int height, int color)
	{
		for(int y = 0; y<= width;y++)
		{
			setPixel(offX, y+offY,color);
			setPixel(offX+width, y+offY,color);
		}
		for(int x = 0; x<= height;x++)
		{
			setPixel(x + offX,offY,color);
			setPixel(x + offX,offY+height,color);
		}
	
	}
	public void drawFillRect(int offX, int offY, int width, int height, int color)
	{
		//DONT RENDER UNNECESSARY PIXELS
		if(offX < -width) return; //if the image is completely off screen don't not render it
		if(offY < -height) return;
		if(offX >= pW) return;
		if(offY >= pH) return;
		
		
		//not render any pixels which are not on the screen
		int newX = 0;
		int newY = 0;
		int newWidth = width;
		int newHeight = height;
		
	
		//CLIP OFFSCREEN PIXELS
		if( offX < 0 ) // if the image is going off screen
			newX -= offX;
		
		if( offY < 0 ) // if the image is going off screen
			newY -= offY;
		if(newWidth +  offX >= pW) newWidth = newWidth - (newWidth + offX - pW);
			
		if(newHeight +  offY >= pH)newHeight = newHeight - (newHeight + offY - pH);
		for(int y = newY; y<= newHeight;y++)
		{
			for(int x = newX; x<= newWidth;x++)
			{
				setPixel(x + offX,y + offY,color);
			}
		
		}
	
	}

	public int getzDepth() {
		return zDepth;
	}

	public void setzDepth(int zDepth) {
		this.zDepth = zDepth;
	}
	
}
