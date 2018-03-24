/*
*	@author Michael Leonffu
*	@version 03-22-2018
*
*	Empty game! 
*
*/

public class Empty extends Entity{
	public final char SYMBOL_EMPTY = '□';

	// public Empty(){
	// 	super('□', new Point());
	// }

	public Empty(Point point){
		super(' ', new Point(point), -2, -2, -1);
		//this.position = new Point(point);
	}

	public boolean upkeep(Game field){
		//take no action
		return true;
	}

	public boolean action(Game field){
		//take no action
		return true;
	}

	public boolean setExist(boolean newExist){
		if(super.getExist() == newExist)
			return false; //nothing changed
		super.setExist(true);	//ALWAYS EXSISTS;
		return true;
	}

}