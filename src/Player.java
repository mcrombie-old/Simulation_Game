import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;

public class Player extends PlayerObject{
	
	private Random r;
	
	private HashMap m1 = new HashMap();

	public Player(float x, float y, ID id, LinkedList<int []> tilesOwned, Handler handler) {
		super(x,y, id, tilesOwned, handler);
		
		r = new Random();
		
	}

	@Override
	public void tick() {
		hud.setPopulation(population);
		hud.setFood(food);
		hud.setWood(wood);
		hud.setPrevPopulation(prevPopulation);
		hud.setPrevFood(prevFood);
		hud.setPrevWood(prevWood);
		
		hud.setHousingCapacity(housingCapacity);
		hud.setEudaimonia(eudaimonia);
		hud.tick();

		
		//EXPANDS WHEN IT CAN
		/*if (population > 1 + getNumberOfTilesOwned() && food > 0){
			food -= 10;
			expandRightOrDown();
		}*/
		
		//PUTS HOUSING INTO THE FIRST UNUSED TILE.
		/*if (numberOfUnusedTiles > 0 && food >= 50 && wood >= 50){
			food -= 50;
			wood -= 50;
			
		
			for (int i = 0; i < tilesOwned.size(); i++){
				int [] tempLocation = tilesOwned.get(i);
				TileObject tempTile = handler.tiles[tempLocation[0]][tempLocation[1]];
				
				//put some housing in
				if (tempTile.building == Building.None){
					tempTile.setBuilding(Building.Housing);
					break;
				}
			}
			
		}*/
		
		
		

	}

	@Override
	public void render(Graphics g) {
		hud.render(g);

	}

	@Override
	public Rectangle getBounds() {
		// TODO Auto-generated method stub
		return null;
	}

}
