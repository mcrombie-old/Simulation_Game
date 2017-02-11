
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInput extends KeyAdapter{
	
	private Game game;
	private Handler handler;
	private boolean[] keyDown = new boolean[4];
	
	private int speed = 10;
	private int zoom = 1;
	
	public KeyInput(Game game, Handler handler){
		this.handler = handler;
		this.game = game;
		
		keyDown[0] = false; //w key
		keyDown[1] = false; //s key
		keyDown[2] = false; //d key
		keyDown[3] = false; //a key
		
	}

	public void keyPressed(KeyEvent e){
		int key = e.getKeyCode();
		
		//stores how much we have moved from the initial position for zooming purposes
		if(key == KeyEvent.VK_UP) game.setyMove(game.getyMove() + speed);
		if(key == KeyEvent.VK_DOWN) game.setyMove(game.getyMove() - speed);
		if(key == KeyEvent.VK_RIGHT) game.setxMove(game.getxMove() - speed);
		if(key == KeyEvent.VK_LEFT) game.setxMove(game.getxMove() + speed);
		
		for(int i = 0; i < handler.tiles.length; i++){
			for (int j = 0; j < handler.tiles.length;j++){
				TileObject tempTile = handler.tiles[i][j];
				if(key == KeyEvent.VK_UP) { tempTile.setY((int) (tempTile.getY() + speed)); keyDown[0] = true;}
				if(key == KeyEvent.VK_DOWN) { tempTile.setY((int) (tempTile.getY() - speed)); keyDown[1] = true;}
				if(key == KeyEvent.VK_RIGHT) {tempTile.setX((int) (tempTile.getX() - speed)); keyDown[2] = true;}
				if(key == KeyEvent.VK_LEFT) { tempTile.setX((int) (tempTile.getX() + speed)); keyDown[3] = true;}
			}
		}
		
		if (key == KeyEvent.VK_Z){
			game.setTileSize(game.getTileSize() + zoom); //zoom in
			game.setZoomitude(zoom);
		}
		else if (key == KeyEvent.VK_X){
			game.setTileSize(game.getTileSize() - zoom); //zoom out
			game.setZoomitude(zoom);
		}
		
		if (key == KeyEvent.VK_ESCAPE) System.exit(1);
	}
	
	public void keyReleased(KeyEvent e){
		int key = e.getKeyCode();
		
		for(int i = 0; i < handler.tiles.length; i++){
			for(int j = 0; j < handler.tiles.length; j++){
				TileObject tempTile = handler.tiles[i][j];
				
				if(key == KeyEvent.VK_W) keyDown[0] = false; //tempObject.setVelY(0);
				if(key == KeyEvent.VK_S) keyDown[1] = false; //tempObject.setVelY(0);
				if(key == KeyEvent.VK_D) keyDown[2] = false; //tempObject.setVelX(0);
				if(key == KeyEvent.VK_A) keyDown[3] = false; //tempObject.setVelX(0);
				
				//vertical movement
				//if (!keyDown[0] && !keyDown[1]) tempTile.setVelY(0);
					
				//horizontal movement
				//if (!keyDown[2] && !keyDown[3]) tempTile.setVelX(0);
			}
		}
		
		
		
		
	}
}
		
		
		

