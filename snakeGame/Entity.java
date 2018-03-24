/*
*	@author Michael Leonffu
*	@version 03-22-2018
*
*	Snake Game!
*	Entity interacts with other entities in the game space.
*
*/

public abstract class Entity{
	private char symbol;		//Each entity has a symbol
	private Point point;		//Each entity has a position
	private int precedence;		//Higher precedence means that it'll "destory" low precedence
	private int view;			//Higher views will be shown over low views on the poistion 
	private int fitness;		//Each entity has a fitness; -1 for N/A
	private boolean exist;		//If the entiity is existing;

	public Entity(char symbol, Point point, int precedence, int view, int fitness){
		this.symbol = symbol;				//Required
		this.point = new Point(point);		//Required (even if abritrary)
		this.precedence = precedence;		//Required
		this.view = view;					//Required
		this.fitness = fitness;				//Optional?
		this.exist = true;					//Optional?
	}

	// public Entity(char symbol, int x, int y, int precedence, int view, int fitness){
	// 	this(symbol, new Point(x, y), precedence, view, fitness);
	// }

	//Accessor
	public char getSymbol(){
		return this.symbol;
	}

	public Point getPoint(){
		return new Point(this.point);
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
		return this.symbol + ": " + this.point + " Prec: " + this.precedence + " view: " + this.view + " fit: " + this.fitness + " exist: " + this.exist;
	}

}