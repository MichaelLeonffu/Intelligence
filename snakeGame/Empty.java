/*
*	@author Michael Leonffu
*	@version 03-22-2018
*
*	Empty game!
*	Empty does nothing! It's an entity, doesn't move.
*
*/
// package intelligence.snakeGame;

public class Empty extends Entity{
	public final char SYMBOL_EMPTY = '□';

	public Empty(Point point){
		super("Empty", ' ', new Point(point), false, false, -2, -2, -1);
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
		//Do nothing
		return true;
	}

	public boolean action(Game game){
		//Do nothing
		return true;
	}
}