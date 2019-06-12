import java.util.Arrays;

/**
 * A solution of the problem. This class stores the problem definition as well as the values of a specific solutions
 * After creating a solution it verifies its feasibility and calculates its own fitness. If the solution is not feasible the fitness is set to -1.
 * This class should only be used to return the best found solution of an algorithm. 
 * For efficiency, new solutions inside a search algorithm should be created by directly manipulating the solutions array. 
 */
public class Solution
{
    // instance variables - replace the example below with your own
    private int n, nQ, nT, nB, fitness;
    private int[] x, y, type;
    

    /**
     * Constructor for objects of class Solution
     */
    public Solution(int boardSize, int nQueens, int nTowers, int nBishops, int[] xValues, int[] yValues, int[] typeValues)
    {
        n = boardSize;
        nQ = nQueens;
        nT = nTowers;
        nB = nBishops;        
        x = Arrays.copyOf(xValues, xValues.length);
        y = Arrays.copyOf(yValues, yValues.length);
        type = Arrays.copyOf(typeValues, typeValues.length);
        
        if(ObjectiveFunction.CheckConstraints(n, nQueens, nTowers, nBishops, x, y, type))
        {
            fitness = ObjectiveFunction.Evaluate(x, y, type);
        }
        else
        {
            fitness = -1;
        }
    }
    
    public int GetFitness()
    {
        return fitness;
    }


    /**These getters below are used for only debugging purposes**/
    public int getN() {
        return n;
    }

    public int getnQ() {
        return nQ;
    }

    public int getnT() {
        return nT;
    }

    public int getnB() {
        return nB;
    }

    public int getFitness() {
        return fitness;
    }

    public int[] getX() {
        return x;
    }

    public int[] getY() {
        return y;
    }

    public int[] getType() {
        return type;
    }
}
