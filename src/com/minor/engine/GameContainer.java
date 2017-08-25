package com.minor.engine;


public class GameContainer implements Runnable 
{
	private Thread thread;
	
	private Input input;
	private AbstractGame game;
	
	private Window window;
	private Renderer renderer;
	
	
	private boolean running = false;
	private final double UPDATE_CAP = 1.0/60.0;
	
	private int width = 320 , height = 240 ; // pixel resolution
	private float scale = 4f;
	private String title = "Minor_Engine v0.1";

	public GameContainer(AbstractGame game){
		this.game = game;		
	}
	public void start() {
		//making our window 
		window = new Window(this); // make window on this thread
		renderer = new Renderer(this);
		input = new Input(this);
		
		
		thread = new Thread(this);
		thread.run(); // for implementing our main thread
	}
	public void stop() {
		
	}
	int fps = 0;
	public int getFPS(){
		return fps;
	}
	public void run() {
		
		running = true;
		
		boolean render = false;
		
		double firstTime = 0;
		double lastTime = System.nanoTime() / 1000000000.0;
		double passedTime = 0;
		double unprocessedTime = 0;
		
		
		double frameTime = 0;
		int frames = 0;
		
		
		while(running)
		{
			render = false;
			firstTime = System.nanoTime() / 1000000000.0;
			passedTime = firstTime - lastTime;
		    lastTime = firstTime;
		    
		    unprocessedTime += passedTime;
		    
		    frameTime += passedTime;
		    while(unprocessedTime >= UPDATE_CAP)
		    {
		    	unprocessedTime -= UPDATE_CAP;
		    	render = true;
		    	
		    	//System.out.println("UPDATE");
		    	
		    	//TODO: Update game
		    	
		    	game.update(this, (float)UPDATE_CAP);
		    	input.update();
		    	
		    	if(frameTime >= 1.0)
		    	{
		    		frameTime = 0;
		    		fps = frames;
		    		frames = 0;
		    	}
		    }
		    if(render) {
		    	
		    	renderer.clear();
		    	//TODO: Render Game here		    	
		    	game.render(this, renderer);
		    	//renderer.drawText("FPS: "+ fps, 0, 0, 0xff00ffff);
		    	window.update();
		    	
		    	
		    	frames++; // next frame
		    	
		    }
		    else {
		    	try
		    	{
			    	Thread.sleep(1);

		    	}
		    	catch (InterruptedException e) {
		    		// TODO Auto-generated catch block
		    		e.printStackTrace();
		    	
		    	}
		    }
		}
		
		//dispose() here 
		dispose();
	}
	private void dispose() {
		
	}
	
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public float getScale() {
		return scale;
	}
	public void setScale(float scale) {
		this.scale = scale;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Window getWindow() {
		return window;
	}
	public Input getInput() {
		return input;
	}
}
