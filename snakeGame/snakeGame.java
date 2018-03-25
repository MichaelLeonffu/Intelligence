/*
*	@author Michael Leonffu
*	@version 03-22-2018
*
*	Snake game! 
*
*/

import java.util.ArrayList;

public class snakeGame{

	public static final int WIDTH = 7;
	public static final int HEIGHT = 7;
	public static final int TIME = 500;

	public static double averageTrial(ArrayList<Double> trials){
		if(trials.size() == 0)
			return -1;
		double sum = 0.0;
		for(Double d: trials)
			sum += d.doubleValue();
		return sum/trials.size();
	}

	public static void main(String[] args){

		ArrayList<Double> trials = new ArrayList<Double>();

		int count = 0;

		while(count < 10){

			count++;
			Game game = new Game(WIDTH, HEIGHT);

			game.run(TIME);
			// System.out.println("Cycle: " + game.getCycle() + " Fit: " + game.getFitness() + " Cycle/Fit: " + game.getCycle()/(game.getFitness() + 0.0));
			if(trials.size() > 10){
				trials.remove(0);
			}

			if(game.getFitness() != 0){
				trials.add(game.getCycle()/(game.getFitness() + 0.0));
			}

			System.out.printf("Time: %5dms Width: %2d Height: %2d Cycle: %7d Max Cycle: %3d Fit: %7d Cycle/Fit: %2.2f Average: %2.2f%n", TIME, WIDTH, HEIGHT,  game.getCycle(), game.getMaxCycle(), game.getFitness(), game.getCycle()/(game.getFitness() + 0.0), averageTrial(trials));
		}

		// // Entities entities = new Entities();
		// // System.out.println(entities);

		// for(String s: args)
		// 	System.out.println(s);

		// Snake snake = new Snake(new Point(1, 1));
		// //Snake snake2 = new Snake(new Point(15, 12));
		// Apple apple = new Apple(new Point(1, 1));
		// //Apple apple2 = new Apple(new Point(21, 11));
		// System.out.println(snake);
		// Game field = new Game(5, 5);

		// field.addEntity(snake);
		// field.addEntity(apple);

		// snake.spawn(field.toEntitySpace());
		// apple.spawn(field.toEntitySpace());

		// // Wall wall = new Wall(new Point(1, 2));
		// // Wall wall1 = new Wall(new Point(1, 2));
		// // Wall wall2 = new Wall(new Point(2, 2));
		// // Wall wall3 = new Wall(new Point(3, 2));
		// // Wall wall4 = new Wall(new Point(4, 2));

		// // Wall wall5 = new Wall(new Point(4, 3));
		// // Wall wall6 = new Wall(new Point(4, 4));
		// // Wall wall7 = new Wall(new Point(4, 5));
		// // field.addEntities(wall, wall1, wall2, wall3, wall4, wall5, wall6, wall7);

		// //Make an add entitiiyiysisisyisi arry
		// System.out.println(field);
		// System.out.println(snake);

		// long startTime = System.currentTimeMillis();  //startTime + 10000 > System.currentTimeMillis()

		// System.out.println("\n\n" + intro());

		// try{
		// 	Thread.sleep(1500);
		// }catch(Exception e){
		// 	System.out.println(e.getMessage());
		// }

		// int cycles = 0;

		// while(field.gameRunning() && startTime + 5000 > System.currentTimeMillis()){
		// 	try{
		// 		Thread.sleep(1);
		// 	}catch(Exception e){
		// 		System.out.println(e.getMessage());
		// 	}
		// 	System.out.println("cycles:" + cycles++);
		// 	System.out.println(field);
		// 	System.out.println(snake);
		// 	//UPKEEP1
		// 	field.upkeep();
		// 	//ACTION:
		// 	field.action();
		// }

		// //Game loop:
		// 	//While snake is alive;
		// 		//Each entitiy then checks if it is alive	UPKEEP
		// 			//Checks if snake is still alive etc
		// 		//Each enitity takes an action				ACTION

	}

	public static String intro(){
		return 	"+--------------------------+\n" +
				"|     Snake Game v0.1      |\n" +
				"|                          |\n" +
				"|       Width:  5          |\n" +
				"|       Height: 5          |\n" +
				"+--------------------------+";
	}
}