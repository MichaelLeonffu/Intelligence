/*
*	@author Michael Leonffu
*	@version 03-22-2018
*
*	Snake game! 
*
*/

public abstract class Entity{
	private char symbol;
	private Point point;
	private boolean exist;		//If the entiity is existing;
	private int precedence;		//Higher precedence means that it'll "destory" low precedence
	private int view;			//Higher views will be shown over low views on the poistion 
	private int fitness;

	public Entity(char symbol, Point point, int precedence, int view, int fitness){
		this.symbol = symbol;
		this.point = new Point(point);
		this.exist = true;
		this.precedence = precedence;
		this.view = view;
		this.fitness = fitness;
	}

	public Entity(char symbol, int x, int y, int precedence, int view, int fitness){
		this(symbol, new Point(x, y), precedence, view, fitness);
	}

	public Point getPoint(){
		return new Point(this.point);
	}

	public boolean getExist(){
		return this.exist;
	}

	public int getFitness(){
		return this.fitness;
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

	public boolean setPoint(Point point){
		this.point = new Point(point);
		return true;
	}

	public char getSymbol(){
		return this.symbol;
	}

	public int getPrecedence(){
		return this.precedence;
	}

	public int getView(){
		return this.view;
	}

	public abstract boolean action(Game field);
	public abstract boolean upkeep(Game field);

	// public boolean equals(){
	// 	return false;
	// }

	public String toString(){
		return this.symbol + ": " + this.point + " Prec: " + this.precedence + " view: " + this.view + " fit: " + this.fitness;
	}

}