import java.util.Arrays;
import java.util.Random;

/**
 * Random search
 *
 * 
 */
public class RandomSearch implements SearchAlgorithm
{
    // Maximum number of solutions to be evaluated during the search
    private int maxEvals, n, nQ, nT, nB, nPieces;
    Random r;

    /**
     * Constructor for objects of class RandomSearch
     */
    public RandomSearch(int boardSize, int nQueens, int nTowers, int nBishops, int maxEvaluations)
    {
        maxEvals = maxEvaluations;
        n = boardSize;
        nQ = nQueens;
        nT = nTowers;
        nB = nBishops;
        r = new Random();
        nPieces = nQ+nT+nB;
    }

    /**
     * Starts the algorithm
     *
     * 
     * @return    The best found solution
     */
    public Solution Start()
    {
        int fEvals = 0;
        int[] bestSolutionX = new int[nPieces], bestSolutionY = new int[nPieces], bestSolutionType = new int[nPieces];
        int[] x = new int[nPieces], y = new int[nPieces], type = new int[nPieces];
        int bestSolutionFitness=Integer.MAX_VALUE;
        
        //Fixing the pieces' type in the solution
        for(int i=0; i<nPieces; i++)
        {
            if(i<nQ) type[i] = 0;
            else if(i<nQ+nT) type[i] = 1;
            else type[i] = 2;
        }
        
        while(fEvals < maxEvals)
        {
            for(int i=0; i<nPieces; i++)
            {
                x[i] = r.nextInt(n)+1;
                y[i] = r.nextInt(n)+1;                
            }
            
            if(ObjectiveFunction.CheckConstraints(n, nQ, nT, nB, x, y, type))
            {
                int fitness = ObjectiveFunction.Evaluate(x, y, type);
                fEvals++;
                if(fitness < bestSolutionFitness)
                {        
                    bestSolutionX = Arrays.copyOf(x, x.length);
                    bestSolutionY = Arrays.copyOf(y, y.length);
                    bestSolutionType = Arrays.copyOf(type, type.length);
                    bestSolutionFitness = fitness;
                }
            }
        }
        
        return new Solution(n, nQ, nT, nB, bestSolutionX, bestSolutionY, bestSolutionType);
    }
}
