
import java.awt.Graphics;
import java.awt.Rectangle;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Random;
import java.util.stream.Stream;

public abstract class TileObject {

	protected int x, y;
	protected int coordinateX, coordinateY;
	protected Building building;
	
	
	
	private Random random;
	
	//climate stuff
	private double[] a = new double[0];
	private double[] b,c,savedOutputs = new double[0];
	private double[] equationWeights;
	
	
	
	public TileObject(int x, int y, int coordinateX, int coordinateY, Building building){
		this.x=x;
		this.y=y;
		this.coordinateX = coordinateX;
		this.coordinateY = coordinateY;
		this.building = building;
		
	}
	
	/**
	 * @return the building
	 */
	public Building getBuilding() {
		return building;
	}

	/**
	 * @param building the building to set
	 */
	public void setBuilding(Building building) {
		this.building = building;
	}

	public double Climate(){
		
		

		double modifier = 0;
		
		double[] d = new double[1];
		double[] e = new double[1];
		double[] f = new double[1];
		double[] outputs = new double[1];
		
		if(randomNumber(0,1) < 0.5){
			d[0] = randomNumber(-1 , 3);
			e[0]= randomNumber(-4 , 2);
			f[0] = randomNumber(-3 , 5);
			outputs[0] = 0;
		}else{
			d[0] = randomNumber(5 , 10);
			e[0]= randomNumber(4 , 8);
			f[0] = randomNumber(2 , 9);
			outputs[0] = 1;
		}
		
		if (a.length > 2){
			modifier = (equationWeights[0]*d[0]) + (equationWeights[1]*e[0]) + (equationWeights[2]*f[0]) + equationWeights[3];
		}
		
		
		
		a = concat(a,d);
		b = concat(a,d);
		c = concat(a,d);
		savedOutputs = concat(savedOutputs,outputs);
		
		//recalculated optimization
		equationWeights = percept(a,b,c, savedOutputs, .1, "Random");
		
		System.out.println(modifier);
		return 10.0 + (30 - Math.abs(modifier));
		
		
		
	}


	public float getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}


	public void setY(int y) {
		this.y = y;
	}
	



	/**
	 * @return the coordinateX
	 */
	public int getCoordinateX() {
		return coordinateX;
	}

	/**
	 * @return the coordinateY
	 */
	public int getCoordinateY() {
		return coordinateY;
	}

	/**
	 * @param coordinateX the coordinateX to set
	 */
	public void setCoordinateX(int coordinateX) {
		this.coordinateX = coordinateX;
	}

	/**
	 * @param coordinateY the coordinateY to set
	 */
	public void setCoordinateY(int coordinateY) {
		this.coordinateY = coordinateY;
	}

	public abstract void tick();
	public abstract void render(Graphics g);
	public abstract Rectangle getBounds();
	public abstract ID getId();
	public abstract void setId(ID id);
	public abstract int getFood();
	public abstract int getWood();
	
	
	static int MAX_ITER = 100;
	//static double LEARNING_RATE = 0.1;           
	//static int NUM_INSTANCES = 4;
	static int theta = 0;  
	
	
	public double[] percept(double[] x, double[] y, double[] z, double[] outputs, double LEARNING_RATE, String style){ 

		//makes sure there is the same amount of data for each
		assert x.length == y.length && y.length == z.length && z.length == outputs.length;
		
		int NUM_INSTANCES = x.length;
		
		

		//fifty random points of class 1
		/*for(int i = 0; i < NUM_INSTANCES/2; i++){
			x[i] = randomNumber(5 , 10);
			y[i] = randomNumber(4 , 8); 
			z[i] = randomNumber(2 , 9);        
			outputs[i] = 1;         
			System.out.println(x[i]+"\t"+y[i]+"\t"+z[i]+"\t"+outputs[i]);
		}

		//fifty random points of class 0
		for(int i = NUM_INSTANCES/2; i < NUM_INSTANCES; i++){
			x[i] = randomNumber(-1 , 3);
			y[i] = randomNumber(-4 , 2);   
			z[i] = randomNumber(-3 , 5);       
			outputs[i] = 0;        
			System.out.println(x[i]+"\t"+y[i]+"\t"+z[i]+"\t"+outputs[i]);
		}*/

		double[] weights = new double[4];// 3 for input variables and one for bias
		double localError, globalError;
		int i, p, iteration, output;
		
		if (style == "Random"){
			weights[0] = randomNumber(0,1);// w1
			weights[1] = randomNumber(0,1);// w2
			weights[2] = randomNumber(0,1);// w3
			weights[3] = randomNumber(0,1);// this is the bias
		}else if (style == "Low"){
			weights[0] = 0.1;
			weights[1] = 0.1;
			weights[2] = 0.1;
			weights[3] = 0.1;
		}



		iteration = 0;
		do {
			iteration++;
			globalError = 0;
			//loop through all instances (complete one epoch)
			for (p = 0; p < NUM_INSTANCES; p++) {
				// calculate predicted class
				output = calculateOutput(theta,weights, x[p], y[p], z[p]);
				// difference between predicted and actual class values
				localError = outputs[p] - output;
				//update weights and bias
				weights[0] += LEARNING_RATE * localError * x[p];
				weights[1] += LEARNING_RATE * localError * y[p];
				weights[2] += LEARNING_RATE * localError * z[p];
				weights[3] += LEARNING_RATE * localError;
				//summation of squared error (error value for all instances)
				globalError += (localError*localError);
			}

			/* Root Mean Squared Error */
			//System.out.println("Iteration "+iteration+" : RMSE = "+Math.sqrt(globalError/NUM_INSTANCES));
		} while (globalError != 0 && iteration<=MAX_ITER);

		//System.out.println("\n=======\nDecision boundary equation:");
		//System.out.println(weights[0] +"*x + "+weights[1]+"*y +  "+weights[2]+"*z + "+weights[3]+" = 0");

		//generate 10 new random points and check their classes
		//notice the range of -10 and 10 means the new point could be of class 1 or 0
		//-10 to 10 covers all the ranges we used in generating the 50 classes of 1's and 0's above
		for(int j = 0; j < 10; j++){
			double x1 = randomNumber(-10 , 10);
			double y1 = randomNumber(-10 , 10);   
			double z1 = randomNumber(-10 , 10); 

			output = calculateOutput(theta,weights, x1, y1, z1);
			//System.out.println("\n=======\nNew Random Point:");
			//System.out.println("x = "+x1+",y = "+y1+ ",z = "+z1);
			//System.out.println("class = "+output);
		}
		
		return weights;
	}//end main  

	/**
	 * returns a random double value within a given range
	 * @param min the minimum value of the required range (int)
	 * @param max the maximum value of the required range (int)
	 * @return a random double value between min and max
	 */ 
	public static double randomNumber(int min , int max) {
		DecimalFormat df = new DecimalFormat("#.####");
		double d = min + Math.random() * (max - min);
		String s = df.format(d);
		double x = Double.parseDouble(s);
		return x;
	}

	/**
	 * returns either 1 or 0 using a threshold function
	 * theta is 0range
	 * @param theta an integer value for the threshold
	 * @param weights[] the array of weights
	 * @param x the x input value
	 * @param y the y input value
	 * @param z the z input value
	 * @return 1 or 0
	 */ 
	static int calculateOutput(int theta, double weights[], double x, double y, double z)
	{
		double sum = x * weights[0] + y * weights[1] + z * weights[2] + weights[3];
		return (sum >= theta) ? 1 : 0;
	}
	
	public double[] concat(double[] a, double[] b) {
		   int aLen = a.length;
		   int bLen = b.length;
		   double[] c= new double[aLen+bLen];
		   System.arraycopy(a, 0, c, 0, aLen);
		   System.arraycopy(b, 0, c, aLen, bLen);
		   return c;
		}
	
}
