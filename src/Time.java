


public class Time implements Runnable{

	
	/**
	 * Time follows a second thread that keep track of the time elapsed.
	 * 
	 * Tick is used to update things
	 * 
	 * I don't think render is necessary
	 */
	
	
	Thread thread2;
	private boolean running = false; //set to true to start
	
	private int time = 0;
	
	public Time(){
	}
	
	public void run() {
	
		long lastTime = System.nanoTime();
		double amountOfTicks = 1.0; //sets the time of each tick to be in one second
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
		
	}
	
	
	public synchronized void stop(){
		try{
			thread2.join();
			running = false;
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private void tick(){
		time++;
		System.out.println(time);
	}
	
	private void render(){
		
	}
	
	

}
