import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;


public class MouseInput implements MouseListener {

	Game game;
	Handler handler;
	InfoTable table;
	int tileSize;
	
	public MouseInput(Game game, Handler handler, int tileSize){
		this.game = game; 
		this.handler = handler;
		this.tileSize = tileSize;
	}
	
	public void mousePressed(MouseEvent event) {
		int mx = event.getX();
		int my = event.getY();
		
		if (mouseOver(mx, my, 300, 15, 100, 32)){
			System.out.println("Started");
			game.running = true;
		}
		
		if (mouseOver(mx, my, 400, 15, 100, 32)){
			System.out.println("Paused");
			game.running = false;
		}
		
		
		for (int i = 0; i < handler.tiles.length; i++){
			for (int j = 0; j < handler.tiles.length; j++){
				int x = (int) i * 20;
				int y = (int) j * 20;
				
				if (mouseOver(mx, my, x, y, 20, 20)){
					System.out.println("yope");
					int[] array = new int[2];
					//array[0] = i;
					//array[1] = j;
					
					//handler.players.get(0).getTilesOwned().add(array); //only allows this to happen once on simulate
				}
			}
		}
		
		

		
	}


	public void mouseReleased(MouseEvent event) {


	}
	

	public void mouseClicked(MouseEvent event) {

	}


	public void mouseEntered(MouseEvent event) {

	}


	public void mouseExited(MouseEvent event) {

	}
	
	private boolean mouseOver(int mx, int my, int x, int y, int width, int height){
		if (mx > x && mx < x + width){
			if (my > y && my < y + height){
				return true;
			}else{
				return false;
			}
		}else {
			return false;
		}
	}

}
