package com.minor.engine.gfx;

public class Font {
	
	
	public static final Font STANDARD = new Font("/Fonts/standard.png");
	
	private Image fontImage;
	private int[] offsets;
	private int[] widths;
	// Some letters take up less space on the screen and some letters take more , we could prepare a 
	// a font we takes up the same am. of space , but we take offsets and widths of 
	// each letter so that we can display each letter with a little more accuracy
	
	
	public Font(String path)
	{
		fontImage = new Image(path);
		// now the sample image which we have prepared , is a series of pixels 
		// which we traverse using a for loop (blue) marks the beginning of the character and 
		// yellow marks the end of the character, thats how we divide the characters in the 
		// font image.
		
		
		offsets = new int[59];
		widths = new int[59];
		int unicode = 0;
		
		for(int i = 0; i < fontImage.getW();i++)
		{
			if(fontImage.getP()[i] == 0xff0000ff)
			{
				offsets[unicode] = i;
				
			}
			if(fontImage.getP()[i] == 0xffffff00)
			{
				widths[unicode] = i - offsets[unicode];
				unicode++;
			} 
		}
	}


	public Image getFontImage() {
		return fontImage;
	}


	public void setFontImage(Image fontImage) {
		this.fontImage = fontImage;
	}


	public int[] getOffsets() {
		return offsets;
	}


	public void setOffsets(int[] offsets) {
		this.offsets = offsets;
	}


	public int[] getWidths() {
		return widths;
	}


	public void setWidths(int[] widths) {
		this.widths = widths;
	}

}
