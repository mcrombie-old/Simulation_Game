import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.LinkedList;
import java.util.Random;

public abstract class PlayerObject {
	
	protected float x, y;
	protected ID id;
	protected LinkedList<int[]> tilesOwned;
	protected Handler handler;
	protected HUD hud;
	
	protected boolean starving = false;
	protected boolean overcrowding = false;
	protected boolean revolted = false;
	
	Random r;
	
	//resources
	protected double population = 100, housingCapacity, food = 100, wood;
	protected double prevPopulation, prevFood, prevWood; //tracks what these were the previous turn
	//other
	protected int numberOfUnusedTiles = 0, numberOfHouses = 1, eudaimonia = 0;
	


	public PlayerObject(float x, float y, ID id, LinkedList<int[]> tilesOwned, Handler handler){
		this.x=x;
		this.y=y;
		this.id = id; 
		this.tilesOwned = tilesOwned;
		this.handler = handler;
		
		r = new Random();
		
		hud = new HUD();
	
		
	}
	

	
	//simple expansion algorithm that chooses an owned tile at random and will expand to the right, down, 
	//or diagonally downright
	//STILL DOES NOT ACCOUNT FOR IF THE TILE IT SELECTS
	public void expandRightOrDown(){
		
		boolean foundTile = false;
		while(!foundTile){
			System.out.println("size is " + tilesOwned.size());
			int  n = r.nextInt(tilesOwned.size());
			
			//ensures it doesn't select the current tile
			int  xRandom = 0, yRandom = 0;
			while (xRandom == 0 && yRandom ==0){
				xRandom = r.nextInt(2);
				yRandom = r.nextInt(2);
			}

			
			int[] expandTile = new int[2];
			expandTile[0] = tilesOwned.get(n)[0] + xRandom;
			expandTile[1] = tilesOwned.get(n)[1] + yRandom;
			
			boolean containsCheck = false;
			for (int i = 0; i < tilesOwned.size(); i++){
				if (tilesOwned.get(i)[0] == expandTile[0] && tilesOwned.get(i)[1] == expandTile[1])
					containsCheck = true;
			}
			if (!containsCheck){
				tilesOwned.add(expandTile);
				foundTile = true;
				
			}
				
		}
		
		//shows there is a new tile not in use
		numberOfUnusedTiles++;
	}


	public void expandFromLast(){
		int  n = r.nextInt(tilesOwned.size());
		int  xRandom = r.nextInt(2) - r.nextInt(2);
		int  yRandom = r.nextInt(2) - r.nextInt(2);
		
		int[] expandTile = new int[2];
		expandTile[0] = tilesOwned.getLast()[0] + xRandom;
		expandTile[1] = tilesOwned.getLast()[1] + yRandom;
		
		
		
		boolean containsCheck = false;
		for (int i = 0; i < tilesOwned.size(); i++){
			if (tilesOwned.get(i)[0] == expandTile[0] && tilesOwned.get(i)[1] == expandTile[1])
				containsCheck = true;
		}
		
		if (!containsCheck)
			tilesOwned.add(expandTile);
		
	}
	

	public double getPopulation() {
		return population;
	}


	public void setPopulation(double population) {
		this.population = population;
	}

	public double getFood() {
		return food;
	}


	public void setFood(double food) {
		this.food = food;
	}



	public double getWood() {
		return wood;
	}


	public void setWood(double wood) {
		this.wood = wood;
	}
	
	public double getHousingCapacity() {
		return housingCapacity;
	}


	public void setHousingCapacity(double housingCapacity) {
		this.housingCapacity = housingCapacity;
	}
	

	public LinkedList<int[]> getTilesOwned() {
		return tilesOwned;
	}
	
	public int getNumberOfTilesOwned() {
		return tilesOwned.size();
	}


	public void setTilesOwned(LinkedList<int[]> tilesOwned) {
		this.tilesOwned = tilesOwned;
	}


	public float getX() {
		return x;
	}


	public void setX(float x) {
		this.x = x;
	}


	public float getY() {
		return y;
	}


	public void setY(float y) {
		this.y = y;
	}


	public ID getId() {
		return id;
	}


	public void setId(ID id) {
		this.id = id;
	}
	


	public boolean isStarving() {
		return starving;
	}



	public void setStarving(boolean starving) {
		this.starving = starving;
	}



	public boolean isOvercrowding() {
		return overcrowding;
	}



	public boolean isRevolted() {
		return revolted;
	}



	public void setOvercrowding(boolean overcrowding) {
		this.overcrowding = overcrowding;
	}



	public void setRevolted(boolean revolted) {
		this.revolted = revolted;
	}



	public abstract void tick();
	public abstract void render(Graphics g);
	public abstract Rectangle getBounds();

}
