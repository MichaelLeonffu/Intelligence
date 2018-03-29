/*
*	@author Michael Leonffu
*	@version 03-22-2018
*
*	This is the board.
*
*/
// package intelligence.snakeGame;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Game{
	private ArrayList<Entity> entities = new ArrayList<Entity>();
	private ArrayList<Entity> spawnQueue = new ArrayList<Entity>();

	public static final int MIN_HEIGHT = 3;
	public static final int MIN_WIDTH = 3;
	public static final int DEFAULT_HEIGHT = 10;
	public static final int DEFAULT_WIDTH = 10;

	private int width;
	private int height;

	private int cycle;
	private int fitness;

	private int maxCycle;

	//Game loop:
		//While snake is alive;
			//Each entitiy then checks if it is alive		UPKEEP
				//Checks if snake is still alive etc			-Entity.upkeep()
				//Also allows entities to spawn via entity
			//Print the field 								PRINT
			//Each enitity takes an action					ACTION


	//Very simple for now
	public void run(int time){
		Snake snake = new Snake();
		Apple apple = new Apple();
		Game field = new Game(this.width, this.height);

		//Spawn and relocate entityes in order from highest to lowest precendence
		field.addEntitiesRelocate(snake, apple);

		long startTime = System.currentTimeMillis();  //startTime + 10000 > System.currentTimeMillis()

		this.fitness = 0;
		this.cycle = 0;
		this.maxCycle = 0;
		int tempCycle = 0;
		int tempFit = 0;

		while(field.gameRunning() && startTime + time > System.currentTimeMillis()){
			tempFit = snake.getFitness();
			//UPKEEP
				field.upkeep();
			//Display
				System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
				System.out.println("cycle:" + this.cycle);
				System.out.println(field);
				//snake.print();
				snake.printHeader();
				for(Entity e: field.getEntities())
					if(e.getAnimate())	//If it moves then put it on the log
						e.printBody();
			if(!field.gameRunning())
					break;
			//ACTION:
				field.action();
			this.cycle++;
			if(tempFit < snake.getFitness()){	//If it incressed in fitness; it took x cycles
				this.maxCycle = (this.cycle - tempCycle) > this.maxCycle? (this.cycle - tempCycle): this.maxCycle;
				tempCycle = this.cycle;
			}
			try{
				Thread.sleep(field.gameRunning()? 50: 5000);
			}catch(Exception e){
				System.out.println(e.getMessage());
			}
		}

		this.fitness = snake.getFitness();
	}

	//Mutator
		public int getCycle(){
			return this.cycle;
		}

		public int getFitness(){
			return this.fitness;
		}

		public int getMaxCycle(){
			return this.maxCycle;
		}

	//Constructor
		public Game(){
			this(10, 10);
		}

		public Game(int x, int y){	//Should throw execption if field too small!!!!
			if(x < this.MIN_WIDTH || y < this.MIN_HEIGHT){
				x = this.DEFAULT_WIDTH;
				y = this.DEFAULT_HEIGHT;
			}
			this.width = x;
			this.height = y;
			this.cycle = -1;
			this.fitness = -1;
			generateEmptyField(x,y);
			generateWalls();
			removeExtraEmpty();
		}

	//Methods
	public boolean addEntity(Entity e){
		entities.add(e);
		return true;
	}

	public boolean addSpawnQueue(Entity e){
		this.spawnQueue.add(e);
		return true;
	}

	public boolean addEntities(Entity...es){
		for(Entity e: es)
			entities.add(e);
		return true;
	}

	public boolean addEntitiesRelocate(Entity...es){
		for(Entity e: es){
			//unsafe.
			e.relocate(this);
			this.entities.add(e);
		}
		return true;
	}

	private boolean generateEmptyField(int x, int y){ //This should be an exception! if too small; SHOULD remove this method; intergrate to its caller.
		if(x < 1 || y < 1){
			x = 1;
			y = 1;
		}
		for(int i = 0; i < x; i++)
			for(int j = 0; j < y; j++)
				if(!addEntity(new Empty(new Point(i, j))))
					return false;
		return true;
	}

	private boolean removeExtraEmpty(){
		//can be improved!!!!!!
		ArrayList<Entity> toRemove = new ArrayList<Entity>();
		for(int i = 0; i < this.entities.size(); i++)
			if(this.entities.get(i) instanceof Empty)			//If it is a wall
				for(Entity ec: this.entities)	//comapre to
					if(!(ec instanceof Empty))	//and not compared to another empty
						if(this.entities.get(i).getPoint().equals(ec.getPoint()))	//same location
							if(!ec.getAnimate())		//if its not animate
								toRemove.add(this.entities.get(i));
								//entities.remove(i);	//might be bad logic.
		for(Entity er: toRemove)
			this.entities.remove(er);
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
		//THIS IS KINDA UPKEEP Y; but like arguably this needs to happen before the other upkeeps
		//This is the main game method I suppose; adding apples + checking if stuff is still existing
		for(Entity e: this.entities)		//To Change
			if(e.getMortal())				//Can its exist change?
				for(Entity e1: this.entities)	//To Compare to
					if(e.getPoint().equals(e1.getPoint()))	//If both are located at same spot
						if(e.getPrecedence() < e1.getPrecedence())	//If the change one has less of a pressence
							e.setExist(false);	//Then it'll not exist
		//Sort by precedence so that higher goes first.
		Collections.sort(this.entities, new SortByPrecedenceDec()); //can be more effiencent and test for any changes in list before sorting.
		// Collections.reverse(this.entities);
		this.spawnQueue.clear();
		for(Entity e: this.entities)
			e.upkeep(this);
		//Sort by precedence so that higher goes first.
		Collections.sort(this.spawnQueue, new SortByPrecedenceDec());
		// Collections.reverse(this.spawnQueue);
		for(Entity e: this.spawnQueue)
			e.spawn(this);
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

	//Should make it [][][] so that stacked entities are accounted for.
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
		Entity[][] space = toEntitySpace();
		String finalStr = "";
		for(int column = 0; column < space.length; column++){
			for(int row = 0; row < space[column].length; row++){
				finalStr += space[column][row].getSymbol() + " ";
			}
			finalStr += "\n";
		}
		return finalStr;
	}

	public class SortByPrecedence implements Comparator<Entity>{
		//to sort by precedence ACCENDING
		public int compare(Entity a, Entity b){
			return a.getPrecedence() - b.getPrecedence();
		}
	}

	public class SortByPrecedenceDec implements Comparator<Entity>{
		//to sort by precedence DECENDING
		public int compare(Entity a, Entity b){
			return b.getPrecedence() - a.getPrecedence();
		}
	}
}