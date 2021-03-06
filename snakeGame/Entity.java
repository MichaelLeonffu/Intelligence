/**
*	
*	Entity interacts with other entities in the game space.
*	
*	@author Michael Leonffu
*	@version v0.2.0-alpha
*	@since v0.1.0-alpha
*
*/
package intelligence.snakeGame;

public abstract class Entity{
	//Identification
	private String name;		//Name of the entity; entities with same behavoir should be same name.
	private char symbol;		//Each entity has a symbol
	private Point point;		//Each entity has a position
	//Behavoirs/propertys
	private boolean animate;	//Does this entity every move? (Should not change.)
	private boolean mortal;		//Does this entity ever not-exist? (Should not change.); use immune if can change.
	private int precedence;		//Higher precedence means that it'll "destory" low precedence
	private int view;			//Higher views will be shown over low views on the poistion 
	//Situations
	private int fitness;		//Each entity has a fitness; -1 for N/A
	private boolean exist;		//If the entiity is existing;

	public Entity(String name, char symbol, Point point, boolean animate, boolean mortal, int precedence, int view, int fitness){
		this.name = name;					//Optional DEFAULT: "DEFAULT_NAME"
		this.symbol = symbol;				//Required
		this.point = new Point(point);		//Required (even if abritrary)
		
		this.animate = animate;				//Optional DEFAULT: true
		this.mortal = mortal;				//Required DEFAULT: true
		this.precedence = precedence;		//Required
		this.view = view;					//Required
		
		this.fitness = fitness;				//Optional? DEFAULT: ???????
		this.exist = true;					//Optional? DEFAULT: true
	}

	public Entity(char symbol, Point point, int precedence, int view, int fitness){
		this("DEFAULT_NAME", symbol, point, true, true, precedence, view, fitness);
	}

	//Accessor
	public String getName(){
		return this.name;
	}

	public char getSymbol(){
		return this.symbol;
	}

	public Point getPoint(){
		return new Point(this.point);
	}

	public boolean getAnimate(){
		return this.animate;
	}

	public boolean getMortal(){
		return this.mortal;
	}

	public int getPrecedence(){
		return this.precedence;
	}

	public int getView(){
		return this.view;
	}

	public int getFitness(){
		return this.fitness;
	}

	public boolean getExist(){
		return this.exist;
	}

	//Mutator
	public boolean setPoint(Point point){
		if(this.point.equals(new Point(point)))
			return false; //nothing changed
		this.point = new Point(point);
		return true;
	}

	public boolean setFitness(int newFitness){
		if(this.fitness == newFitness)
			return false; //nothing changed
		this.fitness = newFitness;
		return true;
	}

	public boolean setExist(boolean newExist){
		if(this.exist == newExist)
			return false; //nothing changed
		this.exist = newExist;
		return true;
	}

	//Behavoirs
	public abstract boolean spawn(Game game);		//Alows entites to spawn other entities
	public abstract boolean relocate(Game game); 	//For relocating positions via method; finding and moving to valid location
	public abstract boolean upkeep(Game game);		//Game phase to check current entity situation and update
	public abstract boolean action(Game game);		//Game phase where each entity makes a move in the game space

	public String toString(){
		return this.name + ": " + this.symbol + ": " + this.point + " Animate: " + this.animate + " Prec: " + this.precedence + " Mortal: " + this.mortal + " view: " + this.view + " fit: " + this.fitness + " exist: " + this.exist;
	}

	@Deprecated
	public void print(){
		System.out.printf("name: %10s symbol: %1s point: (%3d,%3d) animate: %5s precedence: %3d view: %3d fitness: %5d exist: %5s%n", this.name, this.symbol, this.point.getX(), this.point.getY(), this.animate, this.precedence, this.view, this.fitness, this.exist);
	}

	public void printHeader(){
		System.out.printf("%-15s %-6s %-9s %-7s %-10s %-6s %-4s %-7s %-5s%n", "name", "symbol", "point", "animate", "precedence", "mortal", "view", "fitness", "exist");
	}

	public void printBody(){
		System.out.printf("%-15s %-6s (%3d,%3d) %-7s %10d %-6s %4d %7d %-5s%n", this.name, this.symbol, this.point.getX(), this.point.getY(), this.animate, this.precedence, this.mortal, this.view, this.fitness, this.exist);
	}

}