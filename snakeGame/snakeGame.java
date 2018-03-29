/*
*	@author Michael Leonffu
*	@version 03-22-2018
*
*	Snake game! 
*
*/
// package intelligence.snakeGame;

import java.util.ArrayList;

public class snakeGame{

	public static final int WIDTH = 8;
	public static final int HEIGHT = 8;
	public static final int TIME = 150000;

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

		int count = 9;

		while(count < 10){

			count++;
			Game game = new Game(WIDTH, HEIGHT);

			if(true){
				System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
				System.out.println(intro());
				System.out.println("\n\n\n\n\n");
				try{
					Thread.sleep(1000);
				}catch(Exception e){

				}
			}



			game.run(TIME);
			// System.out.println("Cycle: " + game.getCycle() + " Fit: " + game.getFitness() + " Cycle/Fit: " + game.getCycle()/(game.getFitness() + 0.0));
			if(trials.size() > 10){
				trials.remove(0);
			}

			if(game.getFitness() != 0){
				trials.add(game.getCycle()/(game.getFitness() + 0.0));
			}

			// System.out.printf("Time: %5dms Width: %2d Height: %2d Cycle: %7d Max Cycle: %3d Fit: %4d Cycle/Fit: %4.2f Average: %4.2f%n", TIME, WIDTH, HEIGHT,  game.getCycle(), game.getMaxCycle(), game.getFitness(), game.getCycle()/(game.getFitness() + 0.0), averageTrial(trials));
		}

	}

	public static String intro(){
		return 	"┏━━━━━━━━━━━━━━━━━━━━━━━━━━┓\n" +
				"┃     Snake Game v0.2.1    ┃\n" +
				"┃                          ┃\n" +
				"┃       Width:  8          ┃\n" +
				"┃       Height: 8          ┃\n" +
				"┗━━━━━━━━━━━━━━━━━━━━━━━━━━┛";
	}
}