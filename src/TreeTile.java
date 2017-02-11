import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class TreeTile extends TileObject{
	
	

	private ID id;
	private Game game;


	public TreeTile(int x, int y, int coordinateX, int coordinateY, Building building, ID player_id, Game game) {
		super(x, y, coordinateX, coordinateY, building);
		this.id = player_id;
		this.game = game;
		
	}

	@Override
	public void tick() {

		
	}

	@Override
	public void render(Graphics g) {
		Graphics2D g2d = (Graphics2D) g; //has the method that we need that graphics doesn't
		int tileSize = game.getTileSize();
		System.out.println(x + "   " + coordinateX + "   "+ y + "   " + coordinateY + "  \n");
		
		int oldTileSize = (int) (game.getTileSize() * (1 / game.getZoomitude()));
		if (game.getZoomitude() < 1){
			
			x -= (int) (coordinateX * oldTileSize * game.getZoomitude());
			y -= (int) (coordinateY * oldTileSize * game.getZoomitude());
		}else if (game.getZoomitude() > 1){
			
			x += (int) (coordinateX * oldTileSize);
			y += (int) (coordinateY * oldTileSize);
		}

		
		if (id == ID.Green) g.setColor(Color.green);
		else if (id == ID.Blue) g.setColor(Color.blue);
		else if (id == ID.Red) g.setColor(Color.red);
		else if (id == ID.Yellow) g.setColor(Color.yellow);
		else g.setColor(Color.white);
		
		g.fillRect((int)x + 1, (int)y + 1, tileSize,tileSize);
		
		
		
		//tree
		/*g.setColor(new Color(77, 168, 59)); //tree leaf green  http://rgb.to/color/17792/tree-leaf-green
		g.fillOval((int)x + 5, (int)y + 5, 10, 5);
		g.setColor(new Color(83, 49, 24)); //tree trunk brown   http://rgb.to/color/17783/tree-bark-brown
		g.drawLine((int)x + 10, (int)y + 10,(int)x + 10, (int)y + 15);
		*/
		
		int chagaStart = 1;
		int chagaX = chagaStart; //wondering about this oddly named variable. You're probably not alone. Take the time to learn about chaga mushroms now!
		int chagaY = chagaStart;
		
		for (int i = 0; i < tileSize / 11; i++){
			for (int j = 0; j < tileSize / 11; j++){
				g.setColor(new Color(77, 168, 59)); //tree leaf green  http://rgb.to/color/17792/tree-leaf-green
				g.drawLine((int)x + chagaX, (int)y + chagaY, (int)x + chagaX + 7, (int)y + chagaY);
				g.drawLine((int)x + chagaX, (int)y + chagaY + 1, (int)x + chagaX + 7, (int)y + chagaY + 1);
				g.drawLine((int)x + chagaX, (int)y + chagaY + 2, (int)x + chagaX + 7, (int)y + chagaY + 2);
				g.drawLine((int)x + chagaX, (int)y + chagaY + 3, (int)x + chagaX + 7, (int)y + chagaY + 3);
				
				g.setColor(new Color(83, 49, 24)); //tree trunk brown   http://rgb.to/color/17783/tree-bark-brown
				g.drawLine((int)x + chagaX + 3, (int)y + chagaY + 4, (int)x + chagaX + 3, (int)y + chagaY + 8);
				
				chagaY += 11;
			}

			chagaY = chagaStart;
			chagaX += 11; //wabisab?
		}
		

		
		
		if(building == Building.Housing){
			g.setColor(new Color(83, 49, 24)); //tree trunk brown   http://rgb.to/color/17783/tree-bark-brown
			g.drawLine((int)x + 6, (int)y + 4,(int)x + 7, (int)y + 4);
			g.drawLine((int)x + 5, (int)y + 5,(int)x + 8, (int)y + 5); 
			g.drawLine((int)x + 5, (int)y + 6,(int)x + 8, (int)y + 6); 
			
		}
		
		
		
	}

	@Override
	public Rectangle getBounds() {
		// TODO Auto-generated method stub
		return null;
	}

	
	
	
	/**
	 * @return the id
	 */
	public ID getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(ID id) {
		this.id = id;
	}
	
	public int getFood(){
		return 100;
	}
	
	public int getWood(){
		return 100;
	}




}
