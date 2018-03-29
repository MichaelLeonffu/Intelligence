/**
*	
*	The Point class defines a point with a cordinate location (x, y).
*	
*	@author Michael Leonffu
*	@version v0.2.0-alpha
*	@since v0.1.0-alpha
*
*/
package intelligence.snakeGame;

public class Point{
	/** Codinate X location. */
	public int x;
	/** Codinate Y location. */
	public int y;

	//Constructor
	/** 
	*	Creates a Point object with (0, 0) cordinates.
	*	@version	v0.1.0-alpha
	*	@since 		v0.1.0-alpha
	*/
	public Point(){
		this(0, 0);
	}

	/** 
	* 	Creates a Point object using the cordinates of a Point object.
	* 	@param 		point the specified Point to set the cordinates of this Point
	*	@version	v0.1.0-alpha
	*	@since 		v0.1.0-alpha
	*/
	public Point(Point point){
		this(point.x, point.y);
	}

	/** 
	* 	Creates a Point object using the cordinates of a X and Y.
	* 	@param 		x the specified X cordinate to set for this Point
	*	@param 		y the specified Y cordinate to set for this Point
	*	@version	v0.1.0-alpha
	*	@since 		v0.1.0-alpha
	*/
	public Point(int x, int y){
		this.x = x;
		this.y = y;
	}

	//Methods
	/** 
	* 	Computes the distance between point and this Point.
	* 	@param 		point the specified Point to compare this Point to compute distance
	* 	@return 	the distance between Point and this Point.
	* 	@version	v0.2.0-alpha
	*	@since 		v0.2.0-alpha
	*/
	public double distance(Point point){	//should it check for null?!
		return this.distance(this, point);
	}

	/** 
	* 	Computes the distance between point1 and this point2.
	* 	@param 		point1 the specified Point to compare to point2 to compute distance
	*	@param 		point2 the specified Point to compare to point1 to compute distance
	* 	@return 	the distance between point1 and this point2.
	* 	@version	v0.2.0-alpha
	*	@since 		v0.2.0-alpha
	*/
	public double distance(Point point1, Point point2){
		return this.distance(point1.x, point1.y, point2.x, point2.y);
	}

	/** 
	* 	Computes the distance between cordinate (x1, y1) and this cordinate (x2, y2).
	* 	@param 		x1 the specified X cordinate of the first cordinate
	*	@param 		y1 the specified Y cordinate of the first cordinate
	*	@param 		x2 the specified X cordinate of the second cordinate
	*	@param 		y2 the specified Y cordinate of the second cordinate
	* 	@return 	the distance between first cordinate and second cordinate.
	* 	@version	v0.2.0-alpha
	*	@since 		v0.2.0-alpha
	*/
	public double distance(int x1, int y1, int x2, int y2){
		return Math.pow(((x1 - x2)*(x1 - x2) + (y1 - y2)*(y1 - y2)), 0.5);
	}

	/** 
	* 	Computes the integer distance between point and this Point in whole number intervals.
	* 	@param 		point the specified Point to compare this Point to compute integer distance
	* 	@return 	the integer distance between Point and this Point.
	* 	@version	v0.2.0-alpha
	*	@since 		v0.2.0-alpha
	*/
	public int distanceInt(Point point){
		return this.distanceInt(this, point);
	}

	/** 
	* 	Computes the integer distance between point1 and this point2 in whole number intervals.
	* 	@param 		point1 the specified Point to compare to point2 to compute integer distance
	*	@param 		point2 the specified Point to compare to point1 to compute integer distance
	* 	@return 	the integer distance between point1 and this point2.
	* 	@version	v0.2.0-alpha
	*	@since 		v0.2.0-alpha
	*/
	public int distanceInt(Point point1, Point point2){
		return this.distanceInt(point1.x, point1.y, point2.x, point2.y);
	}

	/** 
	* 	Computes the integer distance between cordinate (x1, y1) and this cordinate (x2, y2).
	* 	@param 		x1 the specified X cordinate of the first cordinate
	*	@param 		y1 the specified Y cordinate of the first cordinate
	*	@param 		x2 the specified X cordinate of the second cordinate
	*	@param 		y2 the specified Y cordinate of the second cordinate
	* 	@return 	the integer distance between first cordinate and second cordinate.
	* 	@version	v0.2.0-alpha
	*	@since 		v0.2.0-alpha
	*/
	public int distanceInt(int x1, int y1, int x2, int y2){
		return Math.abs(x1 - x2) + Math.abs(y1 - y2);
	}

	//Accessor
	/**
	*	Returns X cordinate of this Point.
	*	@return 	the X cordinate of this Point.
	*	@deprecated
	*	@version	v0.2.0-alpha
	*	@since 		v0.1.0-alpha
	*/
	@Deprecated
	public int getX(){
		return this.x;
	}

	/**
	*	Returns Y cordinate of this Point.
	*	@return 	the Y cordinate of this Point.
	*	@deprecated
	*	@version	v0.2.0-alpha
	*	@since 		v0.1.0-alpha
	*/
	@Deprecated
	public int getY(){
		return this.y;
	}

	//Mutator
	/**
	*	Sets the cordinate location of this Point to be of point.
	*	@param 		point the point to compare this Point to
	*	@return 	true if both X or Y cordinate was changed.
	*	@deprecated
	*	@version	v0.2.0-alpha
	*	@since 		v0.1.0-alpha
	*/
	@Deprecated
	public boolean setPoint(Point point){
		return this.setPoint(point.x, point.y);
	}

	/**
	*	Sets the cordinate location of this Point to be of (x, y).
	*	@param 		x the X cordinate to compare this Point to
	*	@param 		y the Y cordinate to compare this Point to
	*	@return 	true if both X or Y cordinate was changed.
	*	@deprecated
	*	@version	v0.2.0-alpha
	*	@since 		v0.2.0-alpha
	*/
	@Deprecated
	public boolean setPoint(int x, int y){
		return this.setX(x) && this.setY(y);
	}

	/**
	*	Sets the X cordinate of this point.
	*	@param 		x the X cordinate to set this Point to
	*	@return 	true if X cordinate was changed.
	*	@version	v0.2.0-alpha
	*	@since 		v0.1.0-alpha
	*/
	public boolean setX(int x){
		if(this.x == x)			//If not changed then return false
			return false;
		this.x = x;
		return true;
	}

	/**
	*	Sets the Y cordinate of this point.
	*	@param 		y the Y cordinate to set this Point to
	*	@return 	true if Y cordinate was changed.
	*	@version	v0.2.0-alpha
	*	@since 		v0.1.0-alpha
	*/
	public boolean setY(int y){
		if(this.y == y)
			return false;
		this.y = y;
		return true;
	}

	//Misc
	/** 
	* 	Determines if point is equal to Point. Points are equal if they are both of type Point and if both their X and Y cordinates are the same value.
	* 	@param 		obj an object to compare to this Point
	* 	@return 	true if determined that both Points are equal.
	* 	@version	v0.2.0-alpha
	*	@since 		v0.2.0-alpha
	*/
	public boolean equals(Object obj){
		if(obj == null)						//If null then its false
			return false;
		if(getClass() != obj.getClass())	//If its not of type Point then it is false
			return false;
		Point point = (Point)obj;			//Safe to cast since it is of type Point
		return point.x == this.x && point.y == this.y;
	}

	/** 
	* 	Determines if point is equal to Point. Points are equal if both their X and Y cordinates are the same value.
	* 	@param 		point a Point to compare to this Point
	* 	@return 	true if determined that both Points are equal.
	* 	@version	v0.1.0-alpha
	*	@since 		v0.1.0-alpha
	*/
	public boolean equals(Point point){
		if(point == null)						//If null then its false
			return false;
		return point.x == this.x && point.y == this.y;
	}

	/** 
	* 	Returns a string of the cordinate location of this Point as (x, y).
	* 	@return 	a string representing the codinate location of this Point as (x, y).
	* 	@version	v0.1.0-alpha
	*	@since 		v0.1.0-alpha
	*/
	public String toString(){
		return "(" + this.x + "," + this.y + ")";
	}
}