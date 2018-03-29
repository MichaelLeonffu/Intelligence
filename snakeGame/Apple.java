/**
*	
*	Apple game! Wall! Apple does nothing except spawn! It's an entity, doesn't move.
*	
*	@author Michael Leonffu
*	@version v0.2.0-alpha
*	@since v0.1.0-alpha
*
*/
// package intelligence.snakeGame;

import java.util.ArrayList;
import java.util.Collections;

public class Apple extends Entity{
	public final char SYMBOL_APPLE = '';

	public Apple(){
		this(new Point());
	}

	public Apple(Point point){
		super("Apple", '', new Point(point), true, true, -1, 0, -1);
		super.setExist(true);
	}

	public boolean spawn(Game game){	//Both spawns and destorys apples
		if(!super.getExist()){	//if the apple is not-existing, relocate apple then re-exist it.
			relocate(game);
			super.setExist(true);
		}else{
			System.out.println(this);
			System.out.println("Apple should not be in spawn queue, it is already exist");
			System.exit(0);
		}
		return true;
	}

	public boolean relocate(Game game){
		//Moves apple to "vaid" location
		ArrayList<Entity> validSpace = new ArrayList<Entity>();
		for(Entity[] manyE: game.toEntitySpace())
			for(Entity e: manyE)
				if(e.getPrecedence() <= super.getPrecedence())	//Can relocate without dying
					validSpace.add(e);
		if(validSpace.isEmpty())	//Problem, cant find any suitable spawn location
			return false;
		Collections.shuffle(validSpace);
		if(super.getExist())	//Returns true that means an apple exists; apple should only relocate when not-exist
			return false;
		super.setPoint(new Point(validSpace.get(0).getPoint()));	//move apple
		return true;
	}

	public boolean upkeep(Game game){
		if(!super.getExist())	//if the apple is not-existing
			game.addSpawnQueue(this);	//queue this apple for spawn
		return true;
	}

	public boolean action(Game game){
		//Do nothing
		return true;
	}

}