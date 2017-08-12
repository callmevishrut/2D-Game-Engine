package com.minor.engine;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

public class Window {
	
	
	private JFrame frame;
	private BufferedImage image;
	private Canvas canvas;
	private BufferStrategy bs;
	private Graphics graphics;
	
	public Window(GameContainer gc) {
		
		image = new BufferedImage(gc.getWidth(), gc.getHeight(), BufferedImage.TYPE_INT_RGB);
		// to get the image in a int form in RGB and store it into the ram
		canvas = new Canvas();
		// to prepare the canvas we need Dimension input 
		Dimension s = new Dimension((int)(gc.getWidth() * gc.getScale()),(int) (gc.getHeight() * gc.getScale()));
		canvas.setPreferredSize(s);
		canvas.setMaximumSize(s);
		canvas.setMinimumSize(s);
		
		frame = new JFrame(gc.getTitle());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		frame.add(canvas, BorderLayout.CENTER);
		frame.pack(); //set the frame to the size of the canvas
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);;
		frame.setVisible(true);
		
		canvas.createBufferStrategy(2); // to buffers
		bs = canvas.getBufferStrategy();
		graphics = bs.getDrawGraphics();
	}
	
	public void update()
	{
		
		graphics.drawImage(image, 0, 0, canvas.getWidth(), canvas.getHeight(),null);
		bs.show(); // graphics were being drawn in the buffered strategy , we need to show it
				
		
	}

	public BufferedImage getImage() {
		return image;
	}

	public Canvas getCanvas() {
		return canvas;
	}

}
