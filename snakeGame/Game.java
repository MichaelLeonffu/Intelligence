/*
*	@author Michael Leonffu
*	@version 03-22-2018
*
*	This is the board.
*
*/
import java.util.ArrayList;

public class Game{
	// public final char WALL = 'w';
	// public final char APPLE = 'a';
	public final char EMPTY = '□';
	private ArrayList<Entity> entities = new ArrayList<Entity>();

	private int width;
	private int height;

	private int cycle;
	private int fitness;

	private int maxCycle;

	//Very simple for now
	public void run(int time){
		Snake snake = new Snake(new Point(1, 1));
		Apple apple = new Apple(new Point(1, 1));
		Game field = new Game(this.width, this.height);

		field.addEntities(snake, apple);
		snake.spawn(field.toEntitySpace());
		apple.spawn(field.toEntitySpace());

		long startTime = System.currentTimeMillis();  //startTime + 10000 > System.currentTimeMillis()

		this.fitness = 0;
		this.cycle = 0;
		this.maxCycle = 0;
		int tempCycle = 0;
		int tempFit = 0;

		while(field.gameRunning() && startTime + time > System.currentTimeMillis()){
			tempFit = snake.getFitness();
			// try{
			// 	Thread.sleep(1);
			// }catch(Exception e){
			// 	System.out.println(e.getMessage());
			// }
			// System.out.println("cycle:" + this.cycle);
			// System.out.println(field);
			// System.out.println(snake);
			//UPKEEP
			field.upkeep();
			//ACTION:
			field.action();
			this.cycle++;
			if(tempFit < snake.getFitness()){	//If it incressed in fitness; it took x cycles
				this.maxCycle = (this.cycle - tempCycle) > this.maxCycle? (this.cycle - tempCycle): this.maxCycle;
				tempCycle = this.cycle;
			}
		}

		this.fitness = snake.getFitness();
	}

	public int getCycle(){
		return this.cycle;
	}

	public int getFitness(){
		return this.fitness;
	}

	public int getMaxCycle(){
		return this.maxCycle;
	}

	public Game(){
		this(10, 10);
	}

	public Game(int x, int y){
		if(x < 1 || y < 1){
			x = 10;
			y = 10;
		}
		this.width = x;
		this.height = y;
		this.cycle = -1;
		this.fitness = -1;
		generateEmptyField(x,y);
		generateWalls();
	}

	// public Entity[] getField(){
	// 	Entity[] tempField = new Entity[this.entities.length];
	// 	for(int x = 0; x < field.length; x++){
	// 		tempField[x] = field[x];
	// 	}
	// 	return tempField;
	// }

	public boolean addEntity(Entity e){
		entities.add(e);
		return true;
	}

	public boolean addEntities(Entity...es){
		for(Entity e: es)
			entities.add(e);
		return true;
	}

	private boolean generateEmptyField(int x, int y){
		if(x < 1 || y < 1){
			x = 1;
			y = 1;
			//This should be an exception!
			System.out.println("ERROR BAD SIZE generateEmptyField");
		}
		for(int i = 0; i < x; i++)
			for(int j = 0; j < y; j++)
				if(!addEntity(new Empty(new Point(i, j))))
					return false;
		return true;
	}

	//Can be improved
	private boolean generateWalls(){
		for(int i = 0; i < this.height; i++)
			for(int j = 0; j < this.width; j++)
				if(i == 0 || j == 0 || i == this.height-1 || j == this.width-1)
					if(!addEntity(new Wall(new Point(j, i))))
						return false;
		return true;
	}

	public ArrayList<Entity> getEntities(){
		//IK THIS IS NOT SAFE BUT JUST FOR NOW 
		return this.entities;
	}

	public boolean upkeep(){
		for(Entity e: this.entities)
			e.upkeep(this);
		//This is the main game method I suppose; adding apples + checking if stuff is still existing
		for(Entity e: this.entities)		//To Change
			for(Entity e1: this.entities)	//To Compare to
				if(e.getPoint().equals(e1.getPoint()))	//If both are located at same spot
					if(e.getPrecedence() < e1.getPrecedence())	//If the change one has less of a pressence
						e.setExist(false);	//Then it'll not exist
		return true;
	}

	public boolean gameRunning(){
		//Is a snake alive?
		for(Entity e: this.entities) //To Change
			if(e instanceof Snake && e.getExist() == true)	//If it is a snake
				return true;
		return false;
	}

	public boolean action(){
		for(int i = 0; i < this.entities.size(); i++){
			this.entities.get(i).action(this);
		}
		return true;
	}

	public Entity[][] toEntitySpace(){
		Entity[][] space = new Entity[this.height][this.width];
			for(Entity e: this.entities)
				//Everything should be in bounds.
				if(!(e.getPoint().getY() < 0 || e.getPoint().getY() >= this.height || e.getPoint().getX() < 0 || e.getPoint().getX() >= this.width))
					if(space[e.getPoint().getY()][e.getPoint().getX()] == null){	//not set yet
						space[e.getPoint().getY()][e.getPoint().getX()] = e;
					}else if(space[e.getPoint().getY()][e.getPoint().getX()].getView() < e.getView()){	//If an entity is allready using this space; pick higher view
						//If e has a greater view then:
						space[e.getPoint().getY()][e.getPoint().getX()] = e;	//use e
					}
		return space;
	}

	public char[][] toCharSpace(){
		char[][] space = new char[this.height][this.width];
		// for(int column = 0; column < this.height; column++)
		// 	for(int row = 0; row < this.width; row++)
				for(Entity e: this.entities)
					// if(e.getPoint().getY() == column && e.getPoint().getX() == row)
						// space[column][row] = e.getSymbol();
						//IDEALY there shouldnt be a situation when any object leaves the size of the field
						if(!(e.getPoint().getY() < 0 || e.getPoint().getY() >= this.height || e.getPoint().getX() < 0 || e.getPoint().getX() >= this.width))
							space[e.getPoint().getY()][e.getPoint().getX()] = e.getSymbol();
		return space;
	}

	public String toString(){
		//char[][] space = toCharSpace();
		Entity[][] space = toEntitySpace();
		String finalStr = "";
		// for(int column = 0; column < space.length; column++){
		// 	for(int row = 0; row < space[column].length; row++){
		// 		finalStr += space[column][row] + " ";
		// 	}
		// 	finalStr += "\n";
		// }
		for(int column = 0; column < space.length; column++){
			for(int row = 0; row < space[column].length; row++){
				finalStr += space[column][row].getSymbol() + " ";
			}
			finalStr += "\n";
		}
		return finalStr;
	}
}