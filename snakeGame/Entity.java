/*
*	@author Michael Leonffu
*	@version 03-22-2018
*
*	Snake Game!
*	Entity interacts with other entities in the game space.
*
*/

public abstract class Entity{
	//Identification
	private String name;		//Name of the entity; entities with same behavoir should be same name.
	private char symbol;		//Each entity has a symbol
	private Point point;		//Each entity has a position
	//Behavoirs/propertys
	private boolean animate;	//Does this entity every move? (Does not change.)
	private int precedence;		//Higher precedence means that it'll "destory" low precedence
	private int view;			//Higher views will be shown over low views on the poistion 
	//Situations
	private int fitness;		//Each entity has a fitness; -1 for N/A
	private boolean exist;		//If the entiity is existing;

	public Entity(String name, char symbol, Point point, boolean animate, int precedence, int view, int fitness){
		this.name = name;					//Optional DEFAULT: "DEFAULT_NAME"
		this.symbol = symbol;				//Required
		this.point = new Point(point);		//Required (even if abritrary)
		
		this.animate = animate;				//Optional DEFAULT: true
		this.precedence = precedence;		//Required
		this.view = view;					//Required
		
		this.fitness = fitness;				//Optional? DEFAULT: ???????
		this.exist = true;					//Optional? DEFAULT: true
	}

	public Entity(char symbol, Point point, int precedence, int view, int fitness){
		this("DEFAULT_NAME", symbol, point, true, precedence, view, fitness);
	}

	// public Entity(char symbol, int x, int y, int precedence, int view, int fitness){
	// 	this(symbol, new Point(x, y), precedence, view, fitness);
	// }

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
	public abstract boolean spawn(Game field);	//Setting up game, more for relocating spawn positions via method.
	public abstract boolean upkeep(Game field);	//Game phase to check current entity situation and update
	public abstract boolean action(Game field);	//Game phase where each entity makes a move in the game space

	//Misc; not really applicable?
	// public boolean equals(){
	// 	return false;
	// }

	public String toString(){
		return this.name + ": " + this.symbol + ": " + this.point + " Animate: " + this.animate + " Prec: " + this.precedence + " view: " + this.view + " fit: " + this.fitness + " exist: " + this.exist;
	}

	public void print(){
		System.out.printf("name: %10s symbol: %1s point: (%3d,%3d) animate: %5s precedence: %3d view: %3d fitness: %5d exist: %5s%n", this.name, this.symbol, this.point.getX(), this.point.getY(), this.animate, this.precedence, this.view, this.fitness, this.exist);
	}

	public void printHeader(){
		System.out.printf("%-15s %-6s %-9s %-7s %-10s %-4s %-7s %-5s%n", "name", "symbol", "point", "animate", "precedence", "view", "fitness", "exist");
	}

	public void printBody(){
		System.out.printf("%-15s %-6s (%3d,%3d) %-7s %10d %4d %7d %-5s%n", this.name, this.symbol, this.point.getX(), this.point.getY(), this.animate, this.precedence, this.view, this.fitness, this.exist);
	}

}