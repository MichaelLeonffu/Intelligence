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
		super("Snake_Head", 's', new Point(point), true, 0, 1, 0);
		super.setExist(false);
		//this.position = new Point(point);
	}

	public boolean action(Game field){
		this.path.add(0, super.getPoint());	//Add to front of the list.
		if(move(snakeBrain(field))){
			//move tail if any, has tail if path.size() is fitness + 2 since one size is ghost tail;
			//fitness 0; means no tail; but means 1 ghost; so size should be 1
			//if(this.path.size() - super.getFitness() > 1)	//if there are any tails to move.
			for(int i = 0; i < this.tail.size(); i++)//For as many tails that are there, move them
				this.tail.get(i).setPoint(this.path.get(i));
			//I could have just made "path" into tail, and spawn a new tail for every turn
			//But for now i want each tail to survie as if it was a real snake
			return true;
		}
		return false;
	}

	public boolean upkeep(Game field){
		//I dont like this solution:
		tempFit = super.getFitness();
		for(Entity e: field.getEntities())
			if(e.getPoint().equals(this.getPoint()))	//Getting all things on the same spot
				if(e instanceof Apple)	//if it is an apple
					super.setFitness(super.getFitness() + 1);
		if(tempFit == super.getFitness()){	//same fitness, not eat apple
			if(this.path.size() >= 1)	//first move
				this.path.remove(this.path.size() - 1);
		}else{	//Eats an apple
			//Snake ate apple; that means it gains a tail, at furthest position.
			Tail tempTailName = new Tail(this.path.get(this.path.size() - 1));
			this.tail.add(this.tail.size(), tempTailName);	//Add this to the end of the list though.
			field.addSpawnQueue(tempTailName);	//Adds to the game.
		}
		return true;
	}

	public boolean spawn(Game game){
		//do nothing
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
		if(!super.setExist(true))	//if the method returns false that means an apple exists and there is a problem
			return false;
		super.setPoint(new Point(validSpace.get(0).getPoint()));	//move apple
		return true;
	}

	private int snakeBrainSmart(Game field){
		Point apple = new Point();
		for(Entity e: field.getEntities())
			if(e instanceof Apple)	//if it is an apple
				apple = new Point(e.getPoint());
		if(apple.equals(new Point()))	//apple seems to not exist
			return snakeBrain(field);
		if(apple.getY() > super.getPoint().getY())
			return 0;
		if(apple.getX() > super.getPoint().getX())
			return 1;
		if(apple.getY() < super.getPoint().getY())
			return 2;
		if(apple.getX() < super.getPoint().getX())
			return 3;
		return snakeBrain(field);	//incase there is no apple (snake is on apple?)
	}

	//This is where the algorithms go; for now it'll be a random stay alive.
	private int snakeBrain(Game field){
		Integer[] choices = choices();
		for(int i = 0; i < choices.length; i++){
			//Should check if things are in bounds!
			switch(choices[i].intValue()){
				case 0:
					if(field.toEntitySpace()[super.getPoint().getY() + 1][super.getPoint().getX()].getPrecedence() <= super.getPrecedence()){
						return 0;
					}
				case 1:
					if(field.toEntitySpace()[super.getPoint().getY()][super.getPoint().getX() + 1].getPrecedence() <= super.getPrecedence()){
						return 1;
					}
				case 2:
					if(field.toEntitySpace()[super.getPoint().getY() - 1][super.getPoint().getX()].getPrecedence() <= super.getPrecedence()){
						return 2;
					}
				case 3:
					if(field.toEntitySpace()[super.getPoint().getY()][super.getPoint().getX() - 1].getPrecedence() <= super.getPrecedence()){
						return 3;
					}
			}
		}
		return 0;
		// Random random = new Random();
		// return random.nextInt(4);
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
				// super.setY(super.getPoint().getY() + 1);
				super.setPoint(new Point(super.getPoint().getX(), super.getPoint().getY()+1));
				break;
			case 1:
				super.setPoint(new Point(super.getPoint().getX()+1, super.getPoint().getY()));
				// super.setX(super.getPoint().getX() + 1);
				break;
			case 2:
				super.setPoint(new Point(super.getPoint().getX(), super.getPoint().getY()-1));
				// super.setY(super.getPoint().getY() - 1);
				break;
			case 3:
				super.setPoint(new Point(super.getPoint().getX()-1, super.getPoint().getY()));
				// super.setX(super.getPoint().getX() - 1);
				break;
			default:
				return false;
		}
		return true;
	}

	class Tail extends Entity{
	public final char SYMBOL_TAIL = 't';

	public Tail(Point point){
		super("Snake_Tail", 't', new Point(point), true, 1, 1, 0);
		super.setExist(true);	//Spawns
	}

	public boolean spawn(Game game){
		//Snake spawns in when created.
		game.addEntity(this);	//Add this tail to the game
		return true;
	}

	public boolean relocate(Game game){
		//Do nothing
		return true;
	}

	public boolean action(Game field){
		//I think its best if we dont use the game field.
		return true;
	}

	public boolean upkeep(Game field){
		//Doesn't really upkeep, its more of a child entity.
		return true;
	}

}
}