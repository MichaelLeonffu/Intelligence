/**
*	
*	Neural Network.
*	
*	@author Michael Leonffu
*	@version v0.3.5-alpha
*	@since v0.3.5-alpha
*
*/
package intelligence.neuralNetwork;

import intelligence.matrix.Matrix;
import intelligence.matrix.Transform;
import intelligence.algorithms.Sigmoid;
import intelligence.algorithms.Algorithms;

import java.util.Arrays;

public class NeuralNetwork{
	/** Name of the network */
	private String name;

	/** Activation functions. */
	private String activationName;
	private Algorithms activationAlgorithm;

	/** This is the weights and bias for the network. It also defines the size and shape of the network! */
	private Network network;
	/*
		basic network example:
		[a]	[c]
			[d]	[f]
		[b]	[e]
		 0   1   2 layers
		 2   3   1 nodes
		   6   3   weights
		 -   3   1 biases

		There are only biases for NON input layers. There is a bias for each node.
		Realize that weights are the product of the two layers around that weight.

		Between layer 1 amd 2;
			c   d   e   
		a [w1][w2][w3]
		b [w4][w5][w6]
		
		all layers in this.network will be rectangular/square, and will have at least one cell.
		this.network.length = layers of weight; this.network.length+1 = layers of nodes.
		OLD this.network[i].length = INPUT nodes in layer i.
		OLD this.network[i][0].length = OUTPUT nodes in laryer i+1
	*/

	//Constructors
	/** Such that 5, 4, 1 would result in a 3 layers: 5 Inputs, 4 Hidden nodes, and 1 output. */
	public NeuralNetwork(int[] layers, Algorithms activationAlgorithm, String activationName) throws Exception{
		if(layers.length < 3) //Must have atleast 3 layers
			throw new Exception("must have at least 3 layers");
		for(int nodeCount: layers)
			if(nodeCount <= 0)	//Cannot have any node count of 0
				throw new Exception("must have at least 1 node per layer");
		this.network = new Network(new Matrix[layers.length - 1], new Matrix[layers.length - 1]);	//Weights and bias are layers -1
		for(int i = 0; i < this.network.weights.length; i++)
			this.network.weights[i] = new Matrix(new double[layers[i]][layers[i + 1]], String.format("weights: %3d", i));
		for(int i = 0; i < this.network.bias.length; i++)
			this.network.bias[i] = new Matrix(new double[1][layers[i + 1]], String.format("bias: %3d", i));
		this.activationAlgorithm = activationAlgorithm;
		this.activationName = activationName;
	}

	public NeuralNetwork(double[][][] data, Algorithms activationAlgorithm, String activationName) throws Exception{
		if(data.length < 2) //Must have atleast 3 layers of nodes; meaning two data(weight) layers
			throw new Exception("must have at least 3 layers");
		for(double[][] layer: data)
			for(double[] rowOfLayer: layer)
				if(rowOfLayer.length <= 0)	//Cannot have any node count of 0
					throw new Exception("must have at least 1 node per layer");
		this.network = new Network(new Matrix[data.length], new Matrix[data.length]);	//Weights and bias are data.length
		for(int i = 0; i < this.network.weights.length; i++)
			this.network.weights[i] = new Matrix(data[i], String.format("layer: %3d", i));
		for(int i = 0; i < this.network.bias.length; i++)
			this.network.bias[i] = new Matrix(new double[1][data[i][0].length], String.format("bias: %3d", i));
		this.activationAlgorithm = activationAlgorithm;
		this.activationName = activationName;
	}

	public NeuralNetwork(double[][][] dataWeights, double[][][] dataBias, Algorithms activationAlgorithm, String activationName) throws Exception{
		if(dataWeights.length < 2) //Must have atleast 3 layers of nodes; meaning two data(weight) layers
			throw new Exception("must have at least 3 layers");
		if(dataWeights.length != dataBias.length)
			throw new Exception("must have equal amount of weight layers and bias layers");
		for(double[][] layer: dataWeights)
			for(double[] rowOfLayer: layer)
				if(rowOfLayer.length <= 0)	//Cannot have any node count of 0
					throw new Exception("must have at least 1 node per layer");
		for(double[][] layer: dataBias)
			for(int i = 0; i < layer.length; i++)
				if(i > 0)
					throw new Exception("bias data must be only one row");
		for(int i = 0; i < dataWeights.length; i++)
			if(dataWeights[i][0].length != dataBias[i][0].length)
				throw new Exception("there must be a bias for every node on every layer after input");
		this.network = new Network(new Matrix[dataWeights.length], new Matrix[dataBias.length]);	//Weights and bias are data.length
		for(int i = 0; i < this.network.weights.length; i++)
			this.network.weights[i] = new Matrix(dataWeights[i], String.format("layer: %3d", i));
		for(int i = 0; i < this.network.bias.length; i++)
			this.network.bias[i] = new Matrix(dataBias[i], String.format("bias: %3d", i));
		this.activationAlgorithm = activationAlgorithm;
		this.activationName = activationName;
	}

	//Mutator
	/** Should take a seed as an input */
	public void randomize(){
		Transform makeRandom = new Transform("randomize"){
			public double transform(double value){
				return Math.random();	// 0 -> 1 random; maybe use Gaussian distrib?
			}
		};
		for(int i = 0; i < this.network.weights.length; i++)
			this.network.weights[i].transformThis(makeRandom);
		for(int i = 0; i < this.network.bias.length; i++)
			this.network.bias[i].transformThis(makeRandom);
	}

	//Accessor
	public int[] getLayers(){
		int[] layers = new int[this.network.weights.length+1];
		for(int i = 0; i < this.network.weights.length; i++)
			layers[i] = this.network.weights[i].getRows();
		layers[this.network.weights.length] = this.network.weights[this.network.weights.length-1].getColumns();
		return layers;
	}

	// public Matrix[] getNetwork() throws Exception{
	// 	Matrix[] temp = new Matrix[this.network.length];
	// 	for(int i = 0; i < this.network.length; i++)
	// 		temp[i] = new Matrix(this.network[i]);
	// 	return temp;
	// }

	//Methods
	public double[] simulate(double[] inputs) throws Exception{
		for(double input: inputs)
			if(input < 0 || input > 1)
				throw new Exception("inputs must be in range of 0 to 1");
		if(inputs.length != this.network.weights[0].getRows())
			throw new Exception("must have exact number of inputs this network can accept");
		Transform sigmoid = new Transform(this.activationName){
			public double transform(double value){
				try{
					return activationAlgorithm.algorithm(activationName, value);
				}catch(Exception e){
					System.out.println("failed to use " + activationName);
					return 0.0;
				}
			}
		};
		double[][] inputData = new double[1][inputs.length];	//Its a matrix with 1 row and input.length columns
		for(int i = 0; i < inputs.length; i++)
			inputData[0][i] = inputs[i];
		Matrix matrixData = new Matrix(inputData);
		for(int i = 0; i < this.network.weights.length; i++){		//Then use the "hidden values" on the next set of weights.
			matrixData.multiplyThis(this.network.weights[i]);		//Input * weights = Hidden sum
			// matrixData.multiplyThis(this.network.bias[i]);
			
			double[][] biasFormatData = new double[matrixData.getRows()][1];
			for(int j = 0; j < biasFormatData.length; j++)
				biasFormatData[j][0] = 1;
			Matrix biasFormat = new Matrix(biasFormatData);
			biasFormat.multiplyThis(this.network.bias[i]);
			matrixData.addToThis(biasFormat);

			matrixData.transformThis(sigmoid);	//S(Hidden sum) -> Hidden values
		}
		return matrixData.getData()[0];
	}

	public Matrix simulate(Matrix inputs) throws Exception{
		double[][] inputsData = inputs.getData();
		for(double[] inputRow: inputsData)
			for(double input: inputRow)
				if(input < 0 || input > 1)
					throw new Exception("inputs must be in range of 0 to 1");
		if(inputs.getColumns() != this.network.weights[0].getRows())
			throw new Exception("must have exact number of inputs this network can accept");
		Transform transform = new Transform(this.activationName){
			public double transform(double value){
				try{
					return activationAlgorithm.algorithm(activationName, value);
				}catch(Exception e){
					System.out.println("failed to use " + activationName);
					return 0.0;
				}
			}
		};
		Matrix output = new Matrix(inputs, "inputs");
		for(int i = 0; i < this.network.weights.length; i++){		//Then use the "hidden values" on the next set of weights.
			output.setName(String.format("Hidden Sum: %3d", i));
			output.multiplyThis(this.network.weights[i]);		//Input * weights = Hidden sum
			output.setName(String.format("Hidden Values: %3d", i));

			double[][] biasFormatData = new double[output.getRows()][1];
			for(int j = 0; j < biasFormatData.length; j++)
				biasFormatData[j][0] = 1;
			Matrix biasFormat = new Matrix(biasFormatData);
			biasFormat.multiplyThis(this.network.bias[i]);
			output.addToThis(biasFormat);

			output.setName(String.format("Hidden Values Bias: %3d", i));
			if(i == this.network.weights.length - 1)
				output.setName("output");
			output.transformThis(transform);	//S(Hidden sum) -> Hidden values
		}
		return output;
	}

	//Misc
	public String toString(){
		String finalStr = "Nodes: " + Arrays.toString(this.getLayers()) + "\n";
		for(int i = 0; i < this.network.weights.length; i++){
			double[][] weights = this.network.weights[i].getData();
			double[][] bias = this.network.bias[i].getData();
			for(int j = 0; j < weights.length; j++){
				finalStr += String.format("weights: %3d->%3d ", i, i + 1) + "[";
				for(int k = 0; k < weights[j].length; k++)
					finalStr += String.format("%8.2f", weights[j][k]) + ",";
				finalStr = finalStr.substring(0, finalStr.length() - 1) + "]\n";
			}
			for(int j = 0; j < bias.length; j++){
				finalStr += String.format("bias:    %3d->%3d ", i, i + 1) + "[";
				for(int k = 0; k < bias[j].length; k++)
					finalStr += String.format("%8.2f", bias[j][k]) + ",";
				finalStr = finalStr.substring(0, finalStr.length() - 1) + "]\n";
			}
			finalStr += "\n";
		}
		return finalStr;
	}

	//This is to store network data between neural networks so that their data can be easyly duplicated and transfered
	private class Network{
		public Matrix[] weights;
		public Matrix[] bias;

		public Network(Matrix[] weights, Matrix[] bias){
			this.weights = weights;
			this.bias = bias;
		}

		//Make a clone method to clone the data in this network so that it can be used in other networks.
	}

	public static void main(String[] args) throws Exception{
		System.out.println("In the neural network!");

		/*
		double[][][] setWeight = {
			{	//Weight layer 1; between layers 0 and 1
				{0.03, 0.71, 0.07},
				{0.19, 0.41, 0.14}
			},
			{	//Weights between layers 1 and 2; between Hidden and output
				{0.15},
				{0.75},
				{0.13}
			}
		};

		double[][][] setBias = {
			{	//Bias for layer 1 nodes 
				{-4.0, 9.0, 0.03}
			},
			{	//Biases must only be one row, bias for layer 2 (also result node)
				{0.04}
			}
		};

		// double[] inputData1 = {1.0, 0.0};
		// System.out.println("Output:  \n" + Arrays.toString(brain.simulate(inputData1)));
		double[][] inputData = {{1.0, 0.0}};
		Matrix input = new Matrix(inputData, "Input data");

		// int[] sizes = {2,3,1};
		// NeuralNetwork brain = new NeuralNetwork(sizes);

		NeuralNetwork brain = new NeuralNetwork(setWeight, setBias);
		System.out.println(brain);

		// System.out.println("Randomize network");
		// brain.randomize();
		// System.out.println(brain);

		Matrix output = brain.simulate(input);
		System.out.println("Input:  \n" + input);
		System.out.println("Output: \n" + output.opHistoryToString());
		System.out.println(output.getData()[0][0]);
		*/

		double[][][] setWeight = {
			{	//Weight layer 1; between layers 0 and 1
				{3.87, -3.11},
				{3.87, -3.11}
			},
			{	//Weights between layers 1 and 2; between Hidden and output
				{5.19},
				{5.19}
			}
		};

		double[][][] setBias = {
			{	//Bias for layer 1 nodes 
				{-1.82, 4.58}
			},
			{	//Biases must only be one row, bias for layer 2 (also result node)
				{-4.87}
			}
		};

		double[][] inputData = {{1.0, 1.0}};
		Matrix input = new Matrix(inputData, "Input data");

		NeuralNetwork brain = new NeuralNetwork(setWeight, setBias, new Sigmoid(), "tanh");
		System.out.println(brain);

		Matrix output = brain.simulate(input);
		System.out.println("Input:  \n" + input);
		System.out.println("Output: \n" + output.opHistoryToString());

		System.out.println(Arrays.toString(inputData[0]));
		System.out.println(output.getData()[0][0]);
	}
}