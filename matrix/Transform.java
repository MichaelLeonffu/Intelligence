/**
*	
*	Matrix Transform?
*	
*	@author Michael Leonffu
*	@version v0.3.5-alpha
*	@since v0.3.5-alpha
*
*/
package intelligence.matrix;

public abstract class Transform{
	private String name;
	
	public Transform(String name){
		this.name = name;
	}

	public String getName(){
		return this.name;
	}

	public abstract double transform(double value);
}