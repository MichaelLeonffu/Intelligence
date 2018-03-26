/*
*	@author Michael Leonffu
*	@version 03-22-2018
*
*	Snake game! Wall!
*	Wall does nothing! It's an entity, doesn't move.
*
*/
// package intelligence.snakeGame;

public class Wall extends Entity{
	public final char SYMBOL_WALL = '■';

	public Wall(Point point){
		super("Wall", '■', new Point(point), false, false, 2, -1, -1);
	}

	public boolean spawn(Game game){
		//Do nothing
		return true;
	}

	public boolean relocate(Game game){
		//Do nothing
		return true;
	}

	public boolean upkeep(Game game){
		//take no action
		return true;
	}

	public boolean action(Game game){
		//take no action
		return true;
	}
}