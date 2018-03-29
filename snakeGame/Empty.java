/**
*	
*	Empty game! Wall! Empty does nothing! It's an entity, doesn't move.
*	
*	@author Michael Leonffu
*	@version v0.2.0-alpha
*	@since v0.1.0-alpha
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