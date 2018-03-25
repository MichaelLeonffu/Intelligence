/*
*	@author Michael Leonffu
*	@version 03-22-2018
*
*	Snake game! Point in 2D space.
*
*/
// package intelligence.snakeGame;

public class Point{
	private int x;
	private int y;

	public Point(){
		this(0, 0);
	}

	public Point(Point point){
		this(point.x, point.y);
	}

	public Point(int x, int y){
		this.x = x;
		this.y = y;
	}

	//Accessor
	public int getX(){
		return this.x;
	}

	public int getY(){
		return this.y;
	}

	//Mutator
	public boolean setX(int x){
		if(this.x == x)			//If not changed then return false
			return false;
		this.x = x;
		return true;
	}

	public boolean setY(int y){
		if(this.y == y)
			return false;
		this.y = y;
		return true;
	}

	public boolean equals(Object pointObject){
		if(pointObject == null)						//If null then its false
			return false;
		if(getClass() != pointObject.getClass())	//If its not of type Point then it is false
			return false;
		Point point = (Point)pointObject;			//Safe to cast since it is of type Point
		return point.x == this.x && point.y == this.y;
	}

	public boolean equals(Point point){
		if(point == null)						//If null then its false
			return false;
		return point.x == this.x && point.y == this.y;
	}

	public String toString(){
		return "(" + this.x + "," + this.y + ")";
	}
}