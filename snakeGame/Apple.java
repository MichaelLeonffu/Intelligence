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
		super.setExist(true);
	}

	public boolean spawn(Game game){	//Both spawns and destorys apples
		if(!super.getExist()){	//if the apple is not-existing
			// if(!game.getEntities().remove(this)){	//remove this apple, it doesnt exsist
			// 	//If it coult not find the thing and remove
			// 	System.out.println("FAILEd");
			// 	System.exit(0);
			// }
			relocate(game);	//This apple was recently spawned so relocate it.
			super.setExist(true);
		}else{
			System.out.println("FAILEd3");
			System.exit(0);
			relocate(game);	//This apple was recently spawned so relocate it.
		}
		return true;
	}

	public boolean relocate(Game game){
		//Moves apple to "vaid" location then makes apple exist again
		ArrayList<Entity> validSpace = new ArrayList<Entity>();
		for(Entity[] manyE: game.toEntitySpace())
			for(Entity e: manyE)
				if(e.getPrecedence() <= super.getPrecedence())	//Can relocate without dying
					validSpace.add(e);
		if(validSpace.isEmpty())
			return false;
		Collections.shuffle(validSpace);
		// if(!super.setExist(true))	//if the method returns false that means an apple exists and there is a problem
		// 	return false;
		super.setPoint(new Point(validSpace.get(0).getPoint()));	//move apple
		return true;
	}

	public boolean upkeep(Game game){
		//Its action is to move to correct location if it does not exist
		if(!super.getExist())	//if the apple is not-existing
			game.addSpawnQueue(this);	//queue this apple for spawn checking
			//if(!relocate(game))	//Something is awrong with spawning
				//return false;
		return true;
	}

	public boolean action(Game game){
		//Does nothing;
		return true;
	}

}