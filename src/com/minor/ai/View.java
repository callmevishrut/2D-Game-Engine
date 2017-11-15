package com.minor.ai;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
public class View extends JFrame 
{

	           //  maze[row][col]
	           //   values: 0 = not-visited node
	           //   1 = wall (blocked)
	           //  2 = visited node
	            //  9 = target node

	     	// borders are filled with "1" to show wall .

      private int [][] maze = 

	        { {1,1,1,1,1,1,1,1,1,1,1,1,1},
	          {1,0,1,0,1,0,1,0,0,0,0,0,1},
	          {1,0,1,0,0,0,1,0,1,1,1,0,1},
	          {1,0,0,0,1,1,1,0,0,0,0,0,1},
	          {1,0,1,0,0,0,0,0,1,1,1,0,1},
	          {1,0,1,0,1,1,1,0,1,0,0,0,1},
	          {1,0,1,0,1,0,0,0,1,1,1,0,1},
	          {1,0,1,0,1,1,1,0,1,0,1,0,1},
	          {1,0,0,0,0,0,0,0,0,0,1,9,1},
	          {1,1,1,1,1,1,1,1,1,1,1,1,1}
	 };

      private final List<Integer> path = new ArrayList<Integer>();
      private int pathIndex;
      public View() 
      {

	        setTitle("Simple Maze Solver");
	        setSize(640, 480);
	        setLocationRelativeTo(null);
	        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        DFS.searchPath(maze, 1, 1, path);             // 1,1 is our starting position
	        pathIndex = path.size() - 2;

      }
      public void paint(Graphics g) 
      {

	    	 //    super.paint(g);
	    	 g.translate(50,50);//relative post. of a window on screen.

	    	 // draw the maze

	    	 for (int row = 0; row < maze.length; row++) 
	    	 {
	    		 for (int col = 0; col < maze[0].length; col++) 
	    		 {
	                Color color;
	                switch (maze[row][col]) 
	                {
	                    case 1 : color = Color.BLACK; break;
	                    case 9 : color = Color.RED; break;
	                    default : color = Color.WHITE;

	                }
	                g.setColor(color); 
	                //for setting the colors to the maze
	                g.fillRect(30 * col, 30 * row, 30, 30);
	                						//this means that we are setting the size of 
	                                        //rows and columns t0 30 and the other two thirty means that
	                						//it is setting the color inside the maze to be thirty
	                                      	//which must be equal to 30 otherwise the size of coloured and 
	                						//non colored will be different...
	               g.setColor(Color.BLACK);
	               g.drawRect(30 * col, 30 * row, 30, 30);

	            }

	        }      
	        // draw the path list
	        for (int p = 0; p < path.size(); p +=2) 
	        {
	            int pathX = path.get(p);
	            int pathY = path.get(p + 1);
	            g.setColor(Color.GREEN);
	            g.fillRect(pathX * 30, pathY * 30, 30, 30);

	        }
	        // draw the ball on path
	        int pathX = path.get(pathIndex);
	        int pathY = path.get(pathIndex + 1);
	        g.setColor(Color.RED);
	        g.fillOval(pathX * 30, pathY * 30, 30, 30);

      }
      protected void processKeyEvent(KeyEvent ke) 
      {
	    	if (ke.getID() != KeyEvent.KEY_PRESSED)
	        {
	            return;
	        }
	        if (ke.getKeyCode() == KeyEvent.VK_RIGHT)
	        {
	            pathIndex -= 2;
	            if (pathIndex < 0) 
	            {
	                pathIndex = 0;
	            }
	        }
	        else if (ke.getKeyCode() == KeyEvent.VK_LEFT)
	        {
	            pathIndex += 2;
	            if (pathIndex > path.size() - 2) 
	            {
	                pathIndex = path.size() - 2;
	            }
	        }
	        repaint(); 

      }

}

