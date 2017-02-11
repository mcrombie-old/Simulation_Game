

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.LinkedList;
import java.util.Random;
import java.util.Timer;


/**
 * @author      Michael Crombie <mdcrombie@email.wm.edu>
 * @version     1.1                 (current version number of program)
 * @since       1.1         (the version of the package this class was first added to)
 */

/*
 * TO DO:
 * Arrow movement while holding two buttons
 * zooming
 * HOUSING CAPACITY TO REMAIN CONSTANT WITHOUT HAVING TO RECOUNT
 * SPENDING FOOD
 * POPULATION
 * CAPPING POPULATION WITH HOUSING CAPACITY
 * ART FOR MILLS 
 * EVENT HUD 
 * REVOLUTION EVENTS WHEN POPULATIUON GOES OVER HOUSING CAPACITY
 * CREATE CLASS THAT STORES ALL THE MAPS
 * FIGURE OUT HOW TO CALCULATE GROWTH RATE
 * USE A HASHMAP TO SHOW POPULATIONS OF TILES
 * GET START BUTTON TO WORK
 * ADD GREEN/RED INCREMENTORS TO SIDES TO SHOW RESOURCE INCREASE/DECREASES
 */

public class Game extends Canvas implements Runnable {

	private static final long serialVersionUID = 1550691097823471818L;
	
	public static final int WIDTH = 1000, HEIGHT = WIDTH / 12 * 9;
	private Thread thread; 
	private Thread thread2; 
	protected boolean running = false;
	
	private Random r;
	
	private Handler handler;
	//private HUD hud;
	
	
	
	private int time = 0;

	private String type; 
	
	private int size = 2;
	private int tileSize = 100;
	private int zoomitude = 0;
	private int xMove = 200, yMove = 100;
	

	
	public Game(String type){
		handler = new Handler(size, this);
		
		this.type = type;
		
		this.addMouseListener(new MouseInput(this, handler, tileSize));
		this.addKeyListener(new KeyInput(this, handler));

		new Map(WIDTH, HEIGHT, "Map", this);
		
		//hud = new HUD();
		
		r = new Random();
		
		makePlainMap();
		
		LinkedList<int[]> list = new LinkedList<int[]>();
		
		int[] array = new int[2];
		array[0] = 0;
		array[1] = 0;
		list.add(array);
		
		/*LinkedList<int[]> list2 = new LinkedList<int[]>();
		
		int[] array2 = new int[2];
		array2[0] = 10;
		array2[1] = 10;
		list2.add(array2);*/
		
		handler.addPlayer(new Player(0, 0, ID.Red, list, handler));
		handler.tiles[0][0].setBuilding(Building.Housing); //sets up house in first tile
		
		//handler.addPlayer(new Player2(0, 0, ID.Blue, list2));
		

	}


	public synchronized void start(){
		thread = new Thread(this);
		thread.start();
		running = true;
	}
	
	public synchronized void stop(){
		try{
			thread.join();
			running = false;
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void run(){
		
		if (type == "Play"){
			this.requestFocus(); //keyboard control input happens without clikcing
			long lastTime = System.nanoTime();
			double amountOfTicks = 1; //sets the time of each tick to be in one second
			double ns = 1000000000 / amountOfTicks; 
			double delta = 0;
			long timer = System.currentTimeMillis();
			int frames = 0;
			while(running){
				long now = System.nanoTime();
				delta += (now - lastTime)/ ns;
				lastTime = now;
				while(delta >= 1){
					tick();
					delta--;
				}
				if (running)
					render();
				frames++;
				
				if (System.currentTimeMillis() - timer > 1000){
					timer +=1000;
					//System.out.println("FPS: " + frames);
					frames = 0;
				}
				
			}
			stop();
		}else if (type == "Simulate"){
			for (int i = 0; i < 400; i++){
				tick();
				render();
			}
		}

	}
	
	private void tick(){
		handler.tick();
		//hud.tick();

	}

	private void render(){
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null){
			this.createBufferStrategy(3);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		
		g.setColor(new Color(7, 0, 153)); //ocean blue color
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		handler.render(g);
		//hud.render(g);
		
		g.dispose();
		bs.show();
		

	}
	
	//constrains to board
	public static float clamp(float var, float min, float max){
		if(var >= max)
			return var = max;
		else if (var <= min)
			return var = min;
		else
			return var;
	}
	
	public void makeRandomMap(){
		for (int i = 0; i < size; i++){
			
			for (int j = 0; j < size; j++){
				int n = r.nextInt(3) + 1;
				if (n == 1)handler.tiles[i][j] = new TreeTile(0 + (i * tileSize),0 + (j * tileSize), i, j, Building.None, ID.None, this);
				//else if (n == 2) handler.tiles[i][j] = new PlainTile(0 + (i * tileSize),0 + (j * tileSize), Building.None,ID.None, tileSize);
				else if (n == 3) handler.tiles[i][j] = new PlainTile(0 + (i * tileSize),0 + (j * tileSize), i, j, Building.None, ID.None, this);
			}
		}
		
	}
	
	public void makePlainMap(){
		for (int i = 0; i < size; i++){
			for (int j = 0; j < size; j++){
				handler.tiles[i][j] = new PlainTile(xMove + (i * tileSize),yMove + (j * tileSize), i, j, Building.None,ID.None, this);
			}
		}
	}
	
	public void makePlainWithWoodMap(){

		int n = 0;
		for (int i = 0; i < size; i++){
			for (int j = 0; j < size; j++){
				n = r.nextInt(3) + 1;
				if (n <= 2)handler.tiles[i][j] = new PlainTile(200 + (i * tileSize),100 + (j * tileSize),i, j, Building.None,ID.None, this);
				else if (n == 3) handler.tiles[i][j] = new TreeTile(200 + (i * tileSize),100 + (j * tileSize),i,j, Building.None,ID.None, this);
			}
		}
	}
	
	public void makeTrellisMap(){

		int n = 0;
		for (int i = 0; i < size; i++){
			for (int j = 0; j < size; j++){
				n = r.nextInt(10) + 1;
				if (n <= 8)handler.tiles[i][j] = new PlainTile(100 + (i * tileSize),100 + (j * tileSize), i, j, Building.None,ID.None, this);
				//else if (n == 9) handler.tiles[i][j] = new MountainTile(100 + (i * tileSize),100 + (j * tileSize), Building.None,ID.None, tileSize);
				else if (n == 10) handler.tiles[i][j] = new TreeTile(100 + (i * tileSize),100 + (j * tileSize), i, j, Building.None,ID.None, this);
			}
		}
	}
		
	/**
	 * @return the tileSize
	 */
	public int getTileSize() {
		return tileSize;
	}

	/**
	 * @param tileSize the tileSize to set
	 */
	public void setTileSize(int tileSize) {
		this.tileSize = tileSize;
	}

	public int getZoomitude() {
		return zoomitude;
	}


	public void setZoomitude(int d) {
		this.zoomitude = d;
	}


	/**
	 * @return the xMove
	 */
	public int getxMove() {
		return xMove;
	}


	/**
	 * @return the yMove
	 */
	public int getyMove() {
		return yMove;
	}


	/**
	 * @param xMove the xMove to set
	 */
	public void setxMove(int xMove) {
		this.xMove = xMove;
	}


	/**
	 * @param yMove the yMove to set
	 */
	public void setyMove(int yMove) {
		this.yMove = yMove;
	}


	public static void main(String[] args) {
		
		for (int i = 0; i < 1; i++)
			new Game("Play");
	}
}
