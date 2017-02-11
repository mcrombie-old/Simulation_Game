
import java.awt.Graphics;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;

public class Handler {

	LinkedList<PlayerObject> players;
	
	TileObject[][] tiles;
	
	private int size;
	private Random r;

	private Game game;
	
	public Handler(int size, Game game){
		this.size = size;
		this.game = game;
		
		tiles = new TileObject[size][size];
		players = new LinkedList<PlayerObject>();
		
		r = new Random();
	
	}
	
	
	
	public void tick(){
		//Loops through all of the players
		for (int i = 0; i < players.size(); i++){
			
			PlayerObject playerObject = players.get(i);
			int numberOfTiles = playerObject.getTilesOwned().size();
			playerObject.housingCapacity = 0; //set to 0 each time so it doesn't recount old houses...well it does but ya know
			
			
			populationGrowth(playerObject);
			tileIterator(playerObject, numberOfTiles); //updates resources
			//housingCapacity(playerObject);
			//eudaimonia(playerObject);

			playerObject.tick(); //wjere player makes actions
			
		}
		
	}

	//DEALS WITH POPULATION GROWTH AND STARVATION
	private void populationGrowth(PlayerObject playerObject) {
		
		double r = 0.01;//(playerObject.food - playerObject.population) * 0.1 + 0.000000001; //FIGURE OUT HOW I WANT THIS
		double t = 1.0;
		playerObject.population = playerObject.population * Math.pow(Math.E, (r * t));
		
		//STARVATION
		if (playerObject.food < 0){
			if (playerObject.population >= 2.0) { //make sure you don't have population less than one
				System.out.println("Starvation!");
				playerObject.setStarving(true); //NOT SURE THIS IS NEEDED
				playerObject.population = playerObject.population + playerObject.food;
				playerObject.eudaimonia--; //lowers happiness
			}
			playerObject.food = 0; //reset food to 0	
		}	
		else{
			//resources used
			playerObject.food -= (int) playerObject.population;
		}
	}
	
	private void tileIterator(PlayerObject playerObject, int numberOfTiles) {
		//Loops through all of the tiles owned by the current player
		for (int j = 0; j < numberOfTiles; j++){
			int[] playersTileLocation = playerObject.getTilesOwned().get(j);
			TileObject playersTile = tiles[(int)playersTileLocation[0]][(int)playersTileLocation[1]];
			playersTile.setId(playerObject.getId()); //needed?
			
			//updates player's counts
			updateResources(playerObject, playersTile);	
		}	
	}

	
	public void updateResources(PlayerObject playerObject, TileObject playersTile){
		
		playerObject.prevFood = playerObject.food;
		playerObject.prevPopulation = playerObject.population;
		playerObject.prevWood = playerObject.wood;
		
		//resources gained
		if (playersTile.getClass() == PlainTile.class){
			playerObject.food += playersTile.getFood();
		}

		else if (playersTile.getClass() == TreeTile.class){
			playerObject.wood += playersTile.getWood();
			playerObject.food += playersTile.getFood();
		}

		
		//buildings
		if (playersTile.building == Building.Housing) playerObject.housingCapacity+=10;
		
	}
	
	private void eudaimonia(PlayerObject playerObject) {
		if(!playerObject.isOvercrowding() && !playerObject.isStarving()){
			playerObject.eudaimonia++;
		}
		
		//lower the eudaimonia the more the chance of revolt
		if(playerObject.eudaimonia < 0 && !playerObject.isRevolted()){
			System.out.println("Revolution has occured!");
			playerObject.setRevolted(true);
			LinkedList<int[]> list = new LinkedList<int[]>();
			int[] playersTileLocation = playerObject.getTilesOwned().getLast();
			playerObject.getTilesOwned().removeLast();
			list.add(playersTileLocation);
			players.add(new Player(0, 0, ID.Blue, list, this));
			System.out.println(players.getLast().tilesOwned.size());
		}
	}



	private void housingCapacity(PlayerObject playerObject) {
		if (playerObject.housingCapacity < playerObject.population){
			System.out.println("Overcrowding!");
			playerObject.setOvercrowding(true);
			playerObject.eudaimonia--;
		}
	}



	public void render(Graphics g){

		//renders each tile
		for (int i = 0; i < size; i++){

			for (int j = 0; j < size; j++){
				TileObject tempObject = tiles[i][j];
				tempObject.render(g);
				
			}
		}
		

		//renders HUD
		for (int i = 0; i < players.size(); i++){
			PlayerObject tempObject = players.get(i);
			tempObject.render(g);
		}
		
		game.setZoomitude(0);
	}
	
	public void addPlayer(PlayerObject player){
		this.players.add(player);
	}
	

	
	
}
