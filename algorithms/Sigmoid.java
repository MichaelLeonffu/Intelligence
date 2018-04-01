/**
*	
*	Sigmoid. https://en.wikipedia.org/wiki/Sigmoid_function
*	
*	@author Michael Leonffu
*	@version v0.3.5-alpha
*	@since v0.3.1-alpha
*
*/
package intelligence.algorithms;

import java.lang.Math;

public class Sigmoid extends Algorithms{

	public Sigmoid() throws Exception{
		super("sigmoid", "STUFF", 
			new Algorithm("sigmoid", "sigmoids"){
				public double algorithm(double[] input) throws Exception{
					if(input.length != 1) //this algorithm only accepts one input
						throw new Exception("sigmoid only accepts one input");

					//S(x) = e^x/(e^x + 1) logisic sigmoid

					return Math.pow(Math.E, input[0])/(Math.pow(Math.E, input[0]) + 1);
				}
			},
			new Algorithm("sigmoidPrime", "sigmoidsPrime"){
				public double algorithm(double[] input) throws Exception{
					if(input.length != 1) //this algorithm only accepts one input
						throw new Exception("sigmoidPrime only accepts one input");

					//S'(x) = e^x/(e^x + 1)^2 logisic sigmoid

					return Math.pow(Math.E, input[0])/(Math.pow((Math.pow(Math.E, input[0]) + 1), 2));
				}
			},
			new Algorithm("tanh", "hyperbolic tangent"){
				public double algorithm(double[] input) throws Exception{
					if(input.length != 1) //this algorithm only accepts one input
						throw new Exception("tanh only accepts one input");

					//A type of sigmoid function hyperbolic tangent
					//(e^x - e^-x)/(e^x - e^-x) or tanhx

					return Math.tanh(input[0]);
				}
			}
		);
	}

	public static void main(String[] args) throws Exception{
		//Testing Sigmoid
		Sigmoid mySigmoid = new Sigmoid();
		System.out.println(mySigmoid.algorithm("sigmoid", 6));
		System.out.println(mySigmoid.algorithm("sigmoidPrime", 0));
	}
}
