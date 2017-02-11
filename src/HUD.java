

import java.awt.Color;
import java.awt.Graphics;
import java.text.DecimalFormat;

//Heads Up Display
public class HUD {
	
	public static float HEALTH = 100; 
	
	private float greenValue = 255; 
	
	private double population, food, wood, housingCapacity;
	private double prevPopulation, prevFood, prevWood;
	private int eudaimonia;

	public void tick(){
		
		

	}
	
	public void render(Graphics g){
		
		DecimalFormat df = new DecimalFormat("#.##"); //restricts output to two decimal places
		DecimalFormat df2 = new DecimalFormat("#.#####");
		
		//unnecessary health bar
		g.setColor(Color.gray);
		g.fillRect(15, 15, 200, 32);
		g.setColor(new Color(75, (int)greenValue, 0));
		g.fillRect(15, 15, (int)HEALTH * 2, 32);
		g.setColor(Color.white);
		g.drawRect(15, 15, 200, 32);
		
		//PAUSE BUTTON
		g.setColor(Color.gray);
		g.drawRect(300, 15, 100, 32);
		g.setColor(Color.black);
		g.fillRect(301, 16, 100 - 1, 32 - 1);
		g.setColor(Color.white);
		g.drawString("Start", 310, 38);
		
		//START BUTTON
		g.setColor(Color.gray);
		g.drawRect(400, 15, 100, 32);
		g.setColor(Color.black);
		g.fillRect(401, 16, 100 - 1, 32 - 1);
		g.setColor(Color.white);
		g.drawString("Pause", 410, 38);
		
		//PLAYER DETAILS
		g.setColor(Color.white);
		g.drawString("Player", 15, 64); 
		g.drawString("Population: " + df.format(population), 15, 80); //g.setColor(Color.green); g.drawString(" + " + df2.format(population - prevPopulation), 110, 80);
		g.setColor(Color.white);
		g.drawString("Food: " + df.format(food), 15, 96);
		g.drawString("Wood: " + df.format(wood), 15, 112);
		g.drawString("Housing Capacity: " + df.format(population) + " / " + housingCapacity, 15, 128);
		g.drawString("Eudaimonia: " + df.format(eudaimonia) + " / " + 100, 15, 144);
		
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


	public double getWood() {
		return wood;
	}


	public double getHousingCapacity() {
		return housingCapacity;
	}


	public void setFood(double food) {
		this.food = food;
	}


	public void setWood(double wood) {
		this.wood = wood;
	}

	public void setHousingCapacity(double housingCapacity) {
		this.housingCapacity = housingCapacity;
	}

	public int getEudaimonia() {
		return eudaimonia;
	}

	public void setEudaimonia(int eudaimonia) {
		this.eudaimonia = eudaimonia;
	}

	/**
	 * @return the prevPopulation
	 */
	public double getPrevPopulation() {
		return prevPopulation;
	}

	/**
	 * @return the prevFood
	 */
	public double getPrevFood() {
		return prevFood;
	}

	/**
	 * @return the prevWood
	 */
	public double getPrevWood() {
		return prevWood;
	}

	/**
	 * @param prevPopulation the prevPopulation to set
	 */
	public void setPrevPopulation(double prevPopulation) {
		this.prevPopulation = prevPopulation;
	}

	/**
	 * @param prevFood the prevFood to set
	 */
	public void setPrevFood(double prevFood) {
		this.prevFood = prevFood;
	}

	/**
	 * @param prevWood the prevWood to set
	 */
	public void setPrevWood(double prevWood) {
		this.prevWood = prevWood;
	}
	
	

}
