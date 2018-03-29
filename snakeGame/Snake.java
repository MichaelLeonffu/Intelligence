/*
*	@author Michael Leonffu
*	@version 03-22-2018
*
*	Snake game! 
*
*/
// package intelligence.snakeGame;

import java.util.Random;
import java.util.ArrayList;
import java.util.Collections;

public class Snake extends Entity{
	public final char SYMBOL_HEAD = 's';
	public final char SYMBOL_TAIL = 't';

	private ArrayList<Point> path = new ArrayList<Point>();
	private ArrayList<Entity> tail = new ArrayList<Entity>();

	private int tempFit;

	public Snake(){
		this(new Point());
	}

	public Snake(Point point){
		super("Snake_Head", 's', new Point(point), true, true, 0, 1, 0);
		super.setExist(false);
	}

	public boolean spawn(Game game){
		//Do nothing
		return true;
	}

	public boolean relocate(Game game){
		//Moves snake to "vaid" location then makes snake exist again
		ArrayList<Entity> validSpace = new ArrayList<Entity>();
		for(Entity[] manyE: game.toEntitySpace())
			for(Entity e: manyE)
				if(e.getPrecedence() <= super.getPrecedence())	//Can relocate without dying
					validSpace.add(e);
		if(validSpace.isEmpty())
			return false;
		Collections.shuffle(validSpace);
		if(!super.setExist(true))	//if the method returns false that means an apple exists and there is a problem
			return false;
		super.setPoint(new Point(validSpace.get(0).getPoint()));
		return true;
	}

	public boolean upkeep(Game game){
		//I dont like this solution:
		tempFit = super.getFitness();
		for(Entity e: game.getEntities())
			if(e.getPoint().equals(this.getPoint()))	//Getting all things on the same spot
				if(e instanceof Apple)	//if it is an apple
					super.setFitness(super.getFitness() + 1);
		if(tempFit == super.getFitness()){	//same fitness, not eat apple
			if(this.path.size() >= 1)	//first move
				this.path.remove(this.path.size() - 1);
		}else{	//Ate an apple
			//Snake ate apple; that means it gains a tail, at furthest position.
			Tail tempTailName = new Tail(this.path.get(this.path.size() - 1));
			this.tail.add(this.tail.size(), tempTailName);	//Add this to the end of the list though.
			game.addSpawnQueue(tempTailName);	//Sets snake to spawn
		}
		return true;
	}

	public boolean action(Game game){
		this.path.add(0, super.getPoint());	//Add current position to front of the path list.
		if(move(snakeBrainSafe(game))){
			for(int i = 0; i < this.tail.size(); i++)//For as many tails that are there, move them
				this.tail.get(i).setPoint(this.path.get(i));
			return true;
		}
		return false;
	}

	/** Asume field is only made up of walls and nothing inside.*/
	private int snakeBrainSafe(Game game){
		Entity[][] entitySpace = game.toEntitySpace();
		//If at bottom right side
		if(entitySpace.length > super.getPoint().y - 1 && super.getPoint().y >= 0)										//If in range of Y-1
			if(entitySpace[super.getPoint().y - 1].length > super.getPoint().x + 0 && super.getPoint().x >= 0)			//If in range of x
				if(entitySpace[super.getPoint().y - 1][super.getPoint().x + 0].getPrecedence() > super.getPrecedence())	//If bottom side is high prec
					if(!(entitySpace[super.getPoint().y - 1][super.getPoint().x + 0] instanceof Tail))					//If bottom side is NOT tail
						if(entitySpace.length > super.getPoint().y + 0 && super.getPoint().y >= 0)										//If in range of Y
							if(entitySpace[super.getPoint().y + 0].length > super.getPoint().x + 1 && super.getPoint().x >= 0)			//If in range of x+1
								if(entitySpace[super.getPoint().y + 0][super.getPoint().x + 1].getPrecedence() > super.getPrecedence())	//If bottom right side is high prec
									return 0; //move up
		//If at bottom side
		if(entitySpace.length > super.getPoint().y - 1 && super.getPoint().y >= 0)										//If in range of Y-1
			if(entitySpace[super.getPoint().y - 1].length > super.getPoint().x + 0 && super.getPoint().x >= 0)			//If in range of x
				if(!(entitySpace[super.getPoint().y - 1][super.getPoint().x + 0] instanceof Tail))					//If bottom side is NOT tail
					if(entitySpace[super.getPoint().y - 1][super.getPoint().x + 0].getPrecedence() > super.getPrecedence())	//If bottom side is high prec
						return 1;	//Move rightward.
		//If at top side and even
		if(entitySpace.length > super.getPoint().y + 0 && super.getPoint().y >= 0)										//If in range of Y
			if(entitySpace[super.getPoint().y + 0].length > super.getPoint().x + 0 && super.getPoint().x >= 0)			//If in range of x
				if(super.getPoint().x % 2 == 0)
					if(entitySpace.length > super.getPoint().y + 1 && super.getPoint().y >= 0)										//If in range of Y+1
						if(entitySpace[super.getPoint().y + 1].length > super.getPoint().x + 0 && super.getPoint().x >= 0)			//If in range of x
							if(!(entitySpace[super.getPoint().y + 1][super.getPoint().x + 0] instanceof Tail))					//If bottom side is NOT tail	
								if(entitySpace[super.getPoint().y + 1][super.getPoint().x + 0].getPrecedence() > super.getPrecedence())	//If top side is high prec
									return 3;	//Move leftward.
		//If on even
		if(entitySpace.length > super.getPoint().y + 0 && super.getPoint().y >= 0)										//If in range of Y
			if(entitySpace[super.getPoint().y + 0].length > super.getPoint().x + 0 && super.getPoint().x >= 0)			//If in range of x
				if(super.getPoint().x % 2 == 0)																			//If even
					return 0;	//Move upward.
		//If on odd and "NEAR" bottom side and not on left wall
		if(entitySpace.length > super.getPoint().y - 2 && super.getPoint().y >= 0)										//If in range of Y-2
			if(entitySpace[super.getPoint().y - 2].length > super.getPoint().x + 0 && super.getPoint().x >= 0)			//If in range of x
				if(entitySpace[super.getPoint().y - 2][super.getPoint().x + 0].getPrecedence() > super.getPrecedence() && !(entitySpace[super.getPoint().y - 2][super.getPoint().x + 0] instanceof Tail))	//If bottom side is high prec and not tail
					if(entitySpace.length > super.getPoint().y + 0 && super.getPoint().y >= 0)										//If in range of Y
						if(entitySpace[super.getPoint().y + 0].length > super.getPoint().x + 0 && super.getPoint().x >= 0)			//If in range of x
							if(super.getPoint().x % 2 == 1)																			//If Odd
								if(entitySpace.length > super.getPoint().y + 0 && super.getPoint().y >= 0)										//If in range of Y-1
									if(entitySpace[super.getPoint().y + 0].length > super.getPoint().x - 1 && super.getPoint().x >= 0)			//If in range of x
										if(!(entitySpace[super.getPoint().y + 0][super.getPoint().x - 1].getPrecedence() > super.getPrecedence()))	//If bottom side is high prec
											return 3;	//Move left.
		//If on odd
		if(entitySpace.length > super.getPoint().y + 0 && super.getPoint().y >= 0)										//If in range of Y
			if(entitySpace[super.getPoint().y + 0].length > super.getPoint().x + 0 && super.getPoint().x >= 0)			//If in range of x
				if(super.getPoint().x % 2 == 1)																			//If Odd
					return 2;	//Move downward.
		return -1;	//Dont move.
	}

	private int snakeBrainSmart(Game game){
		Point apple = new Point();
		for(Entity e: game.getEntities())
			if(e instanceof Apple)	//if it is an apple
				apple = new Point(e.getPoint());
		if(apple.equals(new Point()))	//apple seems to not exist
			return snakeBrain(game);
		if(apple.y > super.getPoint().y)
			return 0;
		if(apple.x > super.getPoint().x)
			return 1;
		if(apple.y < super.getPoint().y)
			return 2;
		if(apple.x < super.getPoint().x)
			return 3;
		return snakeBrain(game);	//incase there is no apple (snake is on apple?)
	}

	//This is where the algorithms go; for now it'll be a random stay alive.
	private int snakeBrain(Game game){
		Entity[][] entitySpace = game.toEntitySpace();
		Integer[] choices = choices();
		for(int i = 0; i < choices.length; i++){
			//Should check if things are in bounds!
			switch(choices[i].intValue()){
				case 0:
					if(entitySpace.length > super.getPoint().y + 1 && super.getPoint().y >= 0)
						if(entitySpace[super.getPoint().y + 1].length > super.getPoint().x + 0 && super.getPoint().x >= 0)
							if(entitySpace[super.getPoint().y + 1][super.getPoint().x + 0].getPrecedence() <= super.getPrecedence())
								return 0;
				case 1:
					if(entitySpace.length > super.getPoint().y + 0 && super.getPoint().y >= 0)
						if(entitySpace[super.getPoint().y + 0].length > super.getPoint().x + 1 && super.getPoint().x >= 0)
							if(entitySpace[super.getPoint().y + 0][super.getPoint().x + 1].getPrecedence() <= super.getPrecedence())
								return 1;
				case 2:
					if(entitySpace.length > super.getPoint().y - 1 && super.getPoint().y >= 0)
						if(entitySpace[super.getPoint().y - 1].length > super.getPoint().x + 0 && super.getPoint().x >= 0)
							if(entitySpace[super.getPoint().y - 1][super.getPoint().x + 0].getPrecedence() <= super.getPrecedence())
								return 2;
				case 3:
					if(entitySpace.length > super.getPoint().y + 0 && super.getPoint().y >= 0)
						if(entitySpace[super.getPoint().y + 0].length > super.getPoint().x - 1 && super.getPoint().x >= 0)
							if(entitySpace[super.getPoint().y + 0][super.getPoint().x - 1].getPrecedence() <= super.getPrecedence())
								return 3;
			}
		}
		return 0;
	}

	//THIS IS TEMP METHOD
	private Integer[] choices(){
		ArrayList<Integer> choices = new ArrayList<Integer>();
		choices.add(0);
		choices.add(1);
		choices.add(2);
		choices.add(3);
		Collections.shuffle(choices);
		return choices.toArray(new Integer[choices.size()]);
	}

	private boolean move(int direction){
		if(direction < 0 || direction > 3)
			return false;
		switch(direction){
			case 0:
				super.setPoint(new Point(super.getPoint().x+0, super.getPoint().y+1));
				break;
			case 1:
				super.setPoint(new Point(super.getPoint().x+1, super.getPoint().y+0));
				break;
			case 2:
				super.setPoint(new Point(super.getPoint().x+0, super.getPoint().y-1));
				break;
			case 3:
				super.setPoint(new Point(super.getPoint().x-1, super.getPoint().y+0));
				break;
			case -1:
				super.setPoint(new Point(super.getPoint().x+0, super.getPoint().y+0));
				break;
			default:
				return false;
		}
		return true;
	}

	class Tail extends Entity{
		public final char SYMBOL_TAIL = 't';

		public Tail(Point point){
			super("Snake_Tail", 't', new Point(point), true, true, 1, 1, 0);
			super.setExist(true);	//Spawns
		}

		public boolean spawn(Game game){
			//Tail spawns in when created.
			game.addEntity(this);	//Add this tail to the game
			return true;
		}

		public boolean relocate(Game game){
			//Do nothing
			return true;
		}

		public boolean action(Game game){
			//I think its best if we dont use the action for each snake.
			return true;
		}

		public boolean upkeep(Game game){
			//Doesn't really upkeep.
			return true;
		}

	}
}