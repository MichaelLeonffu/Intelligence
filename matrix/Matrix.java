/**
*	
*	Matrix.
*	
*	@author Michael Leonffu
*	@version v0.2.1-alpha
*	@since v0.2.1-alpha
*
*/
package intelligence.matrix;

import java.util.ArrayList;
import java.util.Arrays;

//Notes:
// [0][1][2]
// [3][4][5]
// as row size x column size: 2 x 3
// IN INDEX NOTATION, NOT CONVENTIONAL
// such that matrix m[0][0] is 0
// and matrix m[0][1] is 1
// and matrix m[1][0] is 3
// and matrix m[2][0] is OUT OF BOUNDS

public class Matrix{
	/** All the data */
	private double[][] data;

	private String matrixName = "DEFAULT_MATRIX_NAME";

	private ArrayList<String[]> opHistory = new ArrayList<String[]>();

	//I dont like this solution but for now it'll do

	private final String[] OP_DONE = {"OP_DONE"};

	//Constructor
	/** init with 0.0 */
	@Deprecated
	public Matrix(int rows, int columns) throws Exception{
		if(rows <= 0 || columns <= 0)
			throw new Exception("invalid size of matrix");
		this.data = new double[rows][columns];
		for(int i = 0; i < rows; i++)
			for(int j = 0; j < columns; j++)
				this.data[i][j] = 0;
		this.opHistory.add(this.toStringLine());
		this.opHistory.add(this.OP_DONE);
	}

	/** Generating a matrix using a 2D array */
	public Matrix(double[][] data) throws Exception{
		//Determine if there is any data
		if(data.length == 0)
			throw new Exception("no rows found");
		if(data[0].length == 0)
			throw new Exception("no columns found");
		//Determine if matrix is regular, all rows have same number of columns
		int columnSize = data[0].length;
		for(int i = 0; i < data.length; i++)
			if(data[i].length != columnSize)
				throw new Exception("column size varies by row");
		//Other wise it should be fine.
		//Generate our empty data arrray
		this.data = new double[data.length][columnSize];
		for(int i = 0; i < data.length; i++)
			for(int j = 0; j < data[i].length; j++)
				this.data[i][j] = data[i][j];
		this.opHistory.add(this.toStringLine());
		this.opHistory.add(this.OP_DONE);
	}

	/** Copy Matrix data to make this Matrix. */
	public Matrix(Matrix matrix) throws Exception{
		this(matrix.data);
		this.opHistory.add(this.toStringLine());
		this.opHistory.add(this.OP_DONE);
	}

	//Mutator
	/** 
	*	This should be moved latter or changed. 
	*	@return true if name was changed.
	*/
	@Deprecated
	public boolean setName(String newName){
		if(this.matrixName.equals(newName))
			return false;
		this.matrixName = newName;
		return true;
	}

	//Accessor
	/** Returns all data from this Matrix as double[][] */
	public double[][] getData(){
		double[][] temp = new double[this.data.length][this.data[0].length];
		for(int i = 0; i < this.data.length; i++)
			for(int j = 0; j < this.data[i].length; j++)
				temp[i][j] = this.data[i][j];
		return temp;
	}

	/** For convenince */
	public int getRows(){
		return this.data.length;
	}

	public int getColumns(){
		return this.data[0].length;
	}

	//Methods
	public boolean sameSize(Matrix matrix){
		return this.sameSize(this, matrix);
	}

	/** I may want to remove thses extra methods because APi can be called to get the values afterwards anyways */
	@Deprecated
	public double[][] add(Matrix matrix) throws Exception{
		return this.add(this, matrix);
	}

	public void addToThis(Matrix matrix) throws Exception{
		// String[] addSign = {" + "};
		// this.opHistory.add(addSign);
		// this.opHistory.add(matrix.toStringLine());
		this.data = this.add(matrix);
		this.addOp("+", matrix.toStringLine(), this.toStringLine());
		// String[] eqSign = {" = "};
		// this.opHistory.add(eqSign);
		// this.opHistory.add(this.toStringLine());
	}

	/** I may want to remove thses extra methods because APi can be called to get the values afterwards anyways */
	@Deprecated
	public double[][] scalarMultiplication(int num){
		return this.scalarMultiplication(num, this);
	}

	public void scalarMultiplicationThis(int num){
		// String[] multiplySign = {" x "};
		// this.opHistory.add(multiplySign);
		String[] value = {String.valueOf(num)};
		// this.opHistory.add(value);
		this.data = this.scalarMultiplication(num);
		this.addOp("x", value, this.toStringLine());
		// String[] eqSign = {" = "};
		// this.opHistory.add(eqSign);
		// this.opHistory.add(this.toStringLine());
	}

	/** I may want to remove thses extra methods because APi can be called to get the values afterwards anyways */
	@Deprecated
	public double[][] transpose(){
		return this.transpose(this);
	}

	public void transposeThis(){
		// String[] transposeSign = {" T "};
		// this.opHistory.add(transposeSign);
		// this.opHistory.add(this.toStringLine());
		this.data = this.transpose();
		this.addOp("T", this.toStringLine());
		// String[] eqSign = {" = "};
		// this.opHistory.add(eqSign);
		// this.opHistory.add(this.toStringLine());
	}

	//Static methods
	public static double[][] add(Matrix matrix1, Matrix matrix2) throws Exception{
		if(!matrix1.sameSize(matrix2))	//not same size
			throw new Exception("sizes do not match, cannot add");
		double[][] temp = new double[matrix1.getRows()][matrix2.getColumns()];
		double[][] matrix1Data = matrix1.getData();
		double[][] matrix2Data = matrix2.getData();
		for(int i = 0; i < temp.length; i++)
			for(int j = 0; j < temp[i].length; j++)
				temp[i][j] = matrix1Data[i][j] + matrix2Data[i][j];
		return temp;
	}

	public static double[][] scalarMultiplication(int num, Matrix matrix){
		double[][] temp = new double[matrix.getRows()][matrix.getColumns()];
		double[][] matrixData = matrix.getData();
		for(int i = 0; i < temp.length; i++)
			for(int j = 0; j < temp[i].length; j++)
				temp[i][j] = num * matrixData[i][j];
		return temp;
	}

	public static double[][] transpose(Matrix matrix){
		double[][] temp = new double[matrix.getColumns()][matrix.getRows()];
		double[][] matrixData = matrix.getData();
		for(int i = 0; i < matrixData.length; i++)
			for(int j = 0; j < matrixData[i].length; j++)
				temp[j][i] = matrixData[i][j];
		return temp;
	}

	public static boolean sameSize(Matrix matrix1, Matrix matrix2){
		return matrix1.getRows() == matrix2.getRows() && matrix1.getColumns() == matrix2.getColumns();
	}

	//Misc
	public void addOp(String operator, String[] value, String[] result){
		String[] operatorSign = {" " + operator + " "};
		this.opHistory.add(operatorSign);
		this.opHistory.add(value);
		String[] eqSign = {" = "};
		this.opHistory.add(eqSign);
		this.opHistory.add(result);
		this.opHistory.add(this.OP_DONE);
	}

	public void addOp(String operator, String[] result){
		String[] operatorSign = {" " + operator + " "};
		this.opHistory.add(operatorSign);
		String[] eqSign = {" = "};
		this.opHistory.add(eqSign);
		this.opHistory.add(result);
		this.opHistory.add(this.OP_DONE);
	}

	public String[] toStringLine(){
		String[] finalString = new String[this.getRows() + 1];
		double[][] data = this.getData();
		finalString[0] = this.matrixName + ": " + this.data.length + "x" + this.data[0].length;
		for(int i = 0; i < this.getRows(); i++){
			finalString[i+1] = "[";
			for(double value: data[i]){
				finalString[i+1] += String.format("%8.2f,", value);
				//finalString += value + ",";	//requires formatting...
			}
			finalString[i+1] = finalString[i+1].substring(0, finalString[i+1].length()-1) + "]";
		}
		return finalString;
	}

	public String opHistoryToString(){
		ArrayList<ArrayList<String[]>> opHistoryByLine = new ArrayList<ArrayList<String[]>>();
		ArrayList<String[]> aLine = new ArrayList<String[]>();

		for(int i = 0; i < this.opHistory.size(); i++){
			if(this.opHistory.get(i)[0].equals(this.OP_DONE[0])){ //if it is a OP_DONE
				opHistoryByLine.add(new ArrayList<String[]>());
				for(String[] str: aLine)
					opHistoryByLine.get(opHistoryByLine.size() - 1).add(str);
				String[] tempLast;
				tempLast = aLine.get(aLine.size() - 1);
				aLine.clear();
				aLine.add(tempLast);
			}else{
				aLine.add(this.opHistory.get(i));
			}
		}

		String finalString = "-----Operation History-----\n";
		int largestRow = 0;

		// group   1   2  3
		// 		[][][] + [][]
		// 		[][][]   [][]
		//		[][][]

		for(ArrayList<String[]> aLineOut: opHistoryByLine){
			for(String[] group: aLineOut)
				if(largestRow < group.length)
					largestRow = group.length; //largestRow = row.length > largestRow? row.length : largestRow
			for(int i = 0; i < largestRow; i++){
				for(String[] group: aLineOut){
					String groupValue = "";
					int groupWidth = 0;
					for(String content: group)
						if(groupWidth < content.length())
							groupWidth = content.length();
					if(group.length > i)
						groupValue = group[i];
					finalString += String.format("%-" + groupWidth + "s", groupValue);
				}
				finalString += "\n";
			}
			finalString += "\n";
		}
		return finalString;
	}

	public String toString(){
		String finalString = "";
		finalString += this.matrixName + ": " + this.data.length + "x" + this.data[0].length + "\n";
		for(double[] row: this.data){
			finalString += "[";
			for(double value: row){
				finalString += String.format("%8.2f,", value);
			}
			finalString = finalString.substring(0, finalString.length()-1) + "]\n";
		}
		return finalString;
	}

	/** Comapres the data only. */ //!!! Makes no sense to compare data of two double[][]!!!!
	// public boolean equals(Matrix matrix){
	// 	if(!this.sameSize(matrix))	//if not same size
	// 		return false;	//Maybe should be exception
	// 	double[][] thisMatrixData = this.getData();
	// 	double[][] matrixData = matrix.getData();
	// 	for(int i = 0; i < this.getRows(); i++)
	// 		for(int j = 0; j < this.getColumns(); j++)
	// 			if(thisMatrixData[i][j] != matrixData[i][j]) //if data not same
	// 				return false;
	// 	return true;
	// }

	public static void main(String[] args) throws Exception{
		// Matrix myMatrix = new Matrix(2, 2);
		// System.out.print(myMatrix);

		// myMatrix.setName("Anna Li");
		// System.out.print(myMatrix);

		double[][] sampleData = {{1.0,0.0},{9.0,8.0},{0.0, 9.1}};
		Matrix sampleMatrix = new Matrix(sampleData);
		sampleMatrix.setName("Sample Matrix");
		System.out.print(sampleMatrix);

		double[][] sampleData2 = {{1.0,-0.1},{9.0,8.0},{1.0, 1.0}};
		Matrix sampleMatrix2 = new Matrix(sampleData2);
		sampleMatrix2.setName("Sample Matrix2");
		System.out.print(sampleMatrix2);

		double[][] sampleData3 = Matrix.add(sampleMatrix, sampleMatrix2);
		Matrix sampleMatrix3 = new Matrix(sampleData3);
		sampleMatrix3.setName("Sample Matrix3");
		System.out.print(sampleMatrix3);


		sampleMatrix3.addToThis(sampleMatrix);
		sampleMatrix3.scalarMultiplicationThis(3);
		sampleMatrix3.transposeThis();
		sampleMatrix3.transposeThis();

		System.out.println(sampleMatrix3.opHistoryToString());

		// double[][] sampleData = {{1.0,0.0},{9.0,8.0}};
		// Matrix sampleMatrix = new Matrix(sampleData);
		// sampleMatrix.setName("Sample Matrix");
		// System.out.print(sampleMatrix);

		// double[][] sampleData2 = {{1.0,-0.1},{9.0,8.0}};
		// Matrix sampleMatrix2 = new Matrix(sampleData2);
		// sampleMatrix2.setName("Sample Matrix2");
		// System.out.print(sampleMatrix2);


	}
}