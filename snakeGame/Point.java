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

	//Constructor
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

	//Methods
	public double distance(Point point){	//should it check for null?!
		return this.distance(this, point);
	}

	public double distance(Point point1, Point point2){
		return this.distance(point1.x, point1.y, point2.x, point2.y);
	}

	public static double distance(int x1, int y1, int x2, int y2){
		return Math.pow(((x1 - x2)*(x1 - x2) + (y1 - y2)*(y1 - y2)), 0.5);
	}

	public int distanceInt(Point point){
		return this.distanceInt(this, point);
	}

	public int distanceInt(Point point1, Point point2){
		return this.distanceInt(point1.x, point1.y, point2.x, point2.y);
	}

	public static int distanceInt(int x1, int y1, int x2, int y2){
		return Math.abs(x1 - x2) + Math.abs(y1 - y2);
	}

	//Accessor
	public int getX(){
		return this.x;
	}

	public int getY(){
		return this.y;
	}

	//Mutator
	public boolean setPoint(Point point){
		return this.setPoint(point.x, point.y);
	}

	public boolean setPoint(int x, int y){
		return this.setX(x) && this.setY(y);
	}

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

	//Misc
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