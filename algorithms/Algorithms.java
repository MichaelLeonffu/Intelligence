/**
*	
*	Algorithms.
*	
*	@author Michael Leonffu
*	@version v0.3.0-alpha
*	@since v0.3.0-alpha
*
*/
package intelligence.algorithms;

public class Algorithms{
	String name;
	String description;
	Algorithm[] algorithmCollection;

	//Constrcutors
	/** Must have algorithms! */
	public Algorithms(Algorithm...algorithms) throws Exception{
		this.algorithmCollection = new Algorithm[algorithms.length];
		for(int i = 0; i < this.algorithmCollection.length; i++)
			this.algorithmCollection[i] = algorithms[i];
		if(!this.uniqueNames(this.algorithmCollection))
			throw new Exception("Not a unqiue set of names");
		this.name = "DEFAULT_ALGORITHMS_NAME";
		this.description = "DEFAULT_ALGORITHMS_DESCRIPTION";
	}

	public Algorithms(String name, String description, Algorithm...algorithms) throws Exception{
		this.algorithmCollection = new Algorithm[algorithms.length];
		for(int i = 0; i < this.algorithmCollection.length; i++)
			this.algorithmCollection[i] = algorithms[i];
		if(!this.uniqueNames(this.algorithmCollection))
			throw new Exception("Not a unqiue set of names");
		this.name = name;
		this.description = description;
	}

	//Accessor
	public String[] getAlgorithmNames(){
		String[] names = new String[this.algorithmCollection.length];
		for(int i = 0; i < this.algorithmCollection.length; i++)
			names[i] = this.algorithmCollection[i].getName();
		return names;
	}

	//Algorithm
	public double algorithm(String algorithmName, double...input) throws Exception{
		for(Algorithm thisAlgorithm: this.algorithmCollection)
			if(thisAlgorithm.getName().equals(algorithmName))
				return thisAlgorithm.algorithm(input);
		throw new Exception("algorithm name not found");
	}

	//Static
	/** Checks if all names in the algorithms are unique. */
	private static boolean uniqueNames(Algorithm[] algorithms){
		for(int i = 0; i < algorithms.length; i++)
			for(int j = i + 1; j < algorithms.length; j++)
				if(algorithms[i].getName().equals(algorithms[j].getName()))
					return false;
		return true;
	}

	//Misc
	public String toString(){
		return "Name: " + this.name + ": " + this.description;
	}

	public abstract static class Algorithm{
		private String algorithmName;
		private String function;

		/** Shouldn't have no name.... */
		@Deprecated
		public Algorithm(){
			this.algorithmName = "DEFAULT_ALGORITHM_NAME";
			this.function = "DEFAULT_ALGORITHM_FUNCTION";
		}

		public Algorithm(String algorithmName, String function){
			this.algorithmName = algorithmName;
			this.function = function;
		}

		//Accessor
		public String getName(){
			return this.algorithmName;
		}

		public String getFunction(){
			return this.function;
		}

		//Algorithm
		/** Should throw exception if input is not allowed for that type of algorithm */
		public abstract double algorithm(double[] input) throws Exception;

		public String toString(){
			return "Name: " + this.algorithmName + ": " + this.function;
		}
	}

	public static void main(String[] args) throws Exception{
		//Testing the library
		Algorithm addOne = new Algorithm("addOne", "Adds one to a input"){
			public double algorithm(double[] input) throws Exception{
				if(input.length != 1) //this algorithm only accepts one input
					throw new Exception("addOne only accepts one input");
				return input[0] + 1;
			}
		};
		System.out.println(addOne);

		Algorithm timesFive = new Algorithm("timesFive", "Multipilies one input by 5"){
			public double algorithm(double[] input) throws Exception{
				if(input.length != 1) //this algorithm only accepts one input
					throw new Exception("timesFive only accepts one input");
				return input[0] * 5;
			}
		};
		System.out.println(timesFive);

		Algorithms myAlgorithms = new Algorithms("SimpleTest", "addOne, timesFive", addOne, timesFive);
		System.out.println(myAlgorithms);

		System.out.println(myAlgorithms.algorithm("addOne", 5));	//6
		System.out.println(myAlgorithms.algorithm("timesFive", 5));	//25

		System.out.println(myAlgorithms.algorithm("addOne", -5));		//-4
		System.out.println(myAlgorithms.algorithm("timesFive", -5));	//-25
	}
}
