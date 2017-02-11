import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

import javax.imageio.ImageIO;

public class PlainTile extends TileObject{
	
	

	private ID id;
	private Random r;
	
	private int wabisabi[][];
	private Game game;

	public PlainTile(int x, int y, int coordinateX, int coordinateY, Building building, ID player_id, Game game) {
		super(x, y, coordinateX, coordinateY, building);
		this.id = player_id;
		this.game = game;
		
		r = new Random();
		
		
		int tempSize = 110;
		//uses random to give it imperfection/transience
		wabisabi = new int [tempSize][tempSize];
		for (int i = 0; i < tempSize; i++){
			for (int j = 0; j < tempSize; j++){
				wabisabi[i][j] = r.nextInt(2);
			}
		}

		
		
	}

	@Override
	public void tick() {
		
		
	}

	@Override
	public void render(Graphics g) {
		Graphics2D g2d = (Graphics2D) g; //has the method that we need that graphics doesn't
		int tileSize = game.getTileSize();
		
		
		int zoom = game.getZoomitude();
		
		/*
		 * There WAS a bizzare error here I cannot figure out. Sometimes when using the zoom key, it will randomly render the 
		 * last or second two tiles in a 2X2 board so that they will become slightly bigger. This never happens at the same 
		 * coordinates and can take varying amounts of time to occur. I can't fathom what it might be since it occurs in
		 * random places and I have turned off all the randomness in the program. BUT I KICKED ITS ASS!!!!
		 */
		
		if (zoom != 0){	
			x = game.getxMove() + (coordinateX * tileSize);
			y = game.getyMove() + (coordinateY * tileSize);
			//System.out.println(x + "   " + coordinateX + "   "+ y + "   " + coordinateY + "  \n");
		}

		
		
		if (id == ID.Green) g.setColor(Color.green);
		else if (id == ID.Blue) g.setColor(Color.blue);
		else if (id == ID.Red) g.setColor(Color.red);
		else if (id == ID.Yellow) g.setColor(Color.yellow);
		else g.setColor(Color.white);
		
		g.fillRect(x, y, tileSize,tileSize);
		
		
		

		//creates grass graphics
		g.setColor(new Color(1,166,17)); //Blade of Grass http://www.colourlovers.com/palette/264688/Grass_Green
		int chagaStart = 1;
		int chagaX = chagaStart; //wondering about this oddly named variable. You're probably not alone. Take the time to learn about chaga mushroms now!
		int chagaY = chagaStart;
		for (int j = 0; j < tileSize/3; j++){
			for (int i = 0; i < tileSize/2; i++){
				if (wabisabi[i][j] != 0){
					g.drawLine((int)x + chagaX, (int)y + chagaY, (int) x + chagaX, (int)y + chagaY + 1);
				}
				
				chagaX += 2;
			}
			chagaX = chagaStart;
			chagaY += 3;
		}

		
		
		
		if(building == Building.Housing){
			g.setColor(new Color(83, 49, 24)); //tree trunk brown   http://rgb.to/color/17783/tree-bark-brown
			g.drawLine((int)x + 6, (int)y + 4,(int)x + 7, (int)y + 4);
			g.drawLine((int)x + 5, (int)y + 5,(int)x + 8, (int)y + 5); 
			g.drawLine((int)x + 4, (int)y + 6,(int)x + 9, (int)y + 6); 
			g.drawLine((int)x + 3, (int)y + 7,(int)x + 10, (int)y + 7); 
			g.drawLine((int)x + 3, (int)y + 8,(int)x + 10, (int)y + 8); 
			g.drawLine((int)x + 3, (int)y + 9,(int)x + 10, (int)y + 9); 
			
			g.setColor(new Color(149, 148, 148)); //sherwin-williams grey shingle  http://encycolorpedia.com/959494
			g.drawLine((int)x + 6, (int)y + 3,(int)x + 3, (int)y + 6 );
			g.drawLine((int)x + 6, (int)y + 3,(int)x + 10, (int)y + 6 );
			 
		}
	
		
	}

	@Override
	public Rectangle getBounds() {
		// TODO Auto-generated method stub
		return null;
	}

	
	
	

	public ID getId() {
		return id;
	}

	public void setId(ID id) {
		this.id = id;
	}

	//returns the resource generated for this tile. What type of resource it is will be specified in Handler until altered.
	public int getFood(){
		return 200;
	}

	@Override
	public int getWood() {
		return 0;
	}



}
