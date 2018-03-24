/*
*	@author Michael Leonffu
*	@version 03-22-2018
*
*	Snake game! Wall!
*
*/

public class Wall extends Entity{
	public final char SYMBOL_WALL = '■';

	public Wall(Point point){
		super('■', new Point(point), 1, -1, -1);
	}

	public boolean upkeep(Game field){
		//take no action
		return true;
	}

	public boolean action(Game field){
		//take no action
		return true;
	}
}