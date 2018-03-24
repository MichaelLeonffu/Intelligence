/*
*	@author Michael Leonffu
*	@version 03-22-2018
*
*	Snake game! 
*
*/

public class Point{
	private int x;
	private int y;

	public Point(){
		this.x = 0;
		this.y = 0;
	}

	public Point(int x, int y){
		this.x = x;
		this.y = y;
	}

	public Point(Point point){
		this.x = point.x;
		this.y = point.y;
	}

	public int getX(){
		return this.x;
	}

	public int getY(){
		return this.y;
	}

	public void setX(int x){
		this.x = x;
	}

	public void setY(int y){
		this.y = y;
	}

	public boolean equals(/*Object*/Point point){
		// if(point == null){
		// 	System.out.println("Point is Null");
		// 	System.exit(0);
		// }else{

		// }
		return point.x == this.x && point.y == this.y;
	}

	// public Point clone(){
	// 	return new Point();
	// }

	public String toString(){
		return "(" + this.x + "," + this.y + ")";
	}
}