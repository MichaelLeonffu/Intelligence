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

public class Snake extends Entity{
	public final char SYMBOL_HEAD = 's';
	public final char SYMBOL_TAIL = 't';

	public Snake(){
		this(new Point());
	}

	public Snake(Point point){
		super('s', new Point(point), 0, 1, 0);
		super.setExist(false);
		//this.position = new Point(point);
	}

	public boolean action(Game field){
		if(move(snakeBrainSmart(field)))
			return true;
		return false;
	}

	public boolean upkeep(Game field){
		for(Entity e: field.getEntities())
			if(e.getPoint().equals(this.getPoint()))	//Getting all things on the same spot
				if(e instanceof Apple)	//if it is an apple
					super.setFitness(super.getFitness() + 1);
		return true;
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
		super.setPoint(new Point(validSpace.get(0).getPoint()));	//move apple
		return true;

	}

	private int snakeBrainSmart(Game field){
		Point apple = new Point();
		for(Entity e: field.getEntities())
			if(e instanceof Apple)	//if it is an apple
				apple = new Point(e.getPoint());
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

}