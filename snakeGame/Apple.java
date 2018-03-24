/*
*	@author Michael Leonffu
*	@version 03-22-2018
*
*	Snake game! 
*
*/
import java.util.Random;
import java.util.ArrayList;
import java.util.Collections;

public class Apple extends Entity{
	public final char SYMBOL_APPLE = '';

	public Apple(){
		this(new Point());
	}

	public Apple(Point point){
		super("Apple", '', new Point(point), true, -1, 0, -1);
		super.setExist(false);
	}

	public boolean spawn(Game game){
		//Moves apple to "vaid" location then makes apple exist again
		ArrayList<Entity> validSpace = new ArrayList<Entity>();
		for(Entity[] manyE: game.toEntitySpace())
			for(Entity e: manyE)
				if(e.getPrecedence() <= super.getPrecedence())	//Can spawn without dying
					validSpace.add(e);
		if(validSpace.isEmpty())
			return false;
		Collections.shuffle(validSpace);
		if(!super.setExist(true))	//if the method returns false that means an apple exists and there is a problem
			return false;
		super.setPoint(new Point(validSpace.get(3).getPoint()));	//move apple
		return true;
	}

	public boolean upkeep(Game game){
		//Its action is to move to correct location and spawn if it does not exist
		if(!super.getExist())	//if the apple is not-existing
			if(!spawn(game))	//Something is awrong with spawning
				return false;
		return true;
	}

	public boolean action(Game game){
		//Does nothing;
		return true;
	}

}