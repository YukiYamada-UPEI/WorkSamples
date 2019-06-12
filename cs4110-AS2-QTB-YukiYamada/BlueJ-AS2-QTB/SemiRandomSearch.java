import java.util.Arrays;
import java.util.Random;

/**
 * Random search
 *
 * 
 */
public class SemiRandomSearch implements SearchAlgorithm
{
    // Maximum number of solutions to be evaluated during the search
    private int maxEvals, n, nQ, nT, nB, nPieces;
    Random r;

    /**
     * Constructor for objects of class RandomSearch
     */
    public SemiRandomSearch(int boardSize, int nQueens, int nTowers, int nBishops, int maxEvaluations)
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
        
        int nMutations = nPieces;
        int resetInterval = maxEvals/nMutations;
        int fEvals = 0;
        int[] bestSolutionX = new int[nPieces], bestSolutionY = new int[nPieces], bestSolutionType = new int[nPieces];
        int[] x = new int[nPieces], y = new int[nPieces], type = new int[nPieces];
        int[] xNew = new int[nPieces], yNew = new int[nPieces];
        int bestSolutionFitness=Integer.MAX_VALUE;
        int currentFitness;
        int fitness =Integer.MAX_VALUE;
        
        //Generating the first random solution
        do
        {
            //Fixing the pieces' type in the solution
            for(int i=0; i<nPieces; i++)
            {
                if(i<nQ) type[i] = 0;
                else if(i<nQ+nT) type[i] = 1;
                else type[i] = 2;
            }
            //Generating the positions
            for(int i=0; i<nPieces; i++)
            {
                x[i] = r.nextInt(n)+1;
                y[i] = r.nextInt(n)+1;                
            }
        }while(!ObjectiveFunction.CheckConstraints(n, nQ, nT, nB, x, y, type));
        currentFitness = ObjectiveFunction.Evaluate(x, y, type);
        
        while(fEvals < maxEvals)
        {
            xNew = Arrays.copyOf(x, x.length);
            yNew = Arrays.copyOf(y, y.length);
            
            //Randomly modifying the position of nMutations pieces
            for(int i=0; i<nMutations; i++)
            {
                int idx = r.nextInt(nPieces);
                xNew[idx] = r.nextInt(n)+1;
                yNew[idx] = r.nextInt(n)+1;
            }
            
            if(ObjectiveFunction.CheckConstraints(n, nQ, nT, nB, xNew, yNew, type))
            {
                fitness = ObjectiveFunction.Evaluate(xNew, yNew, type);
                fEvals++;
                
                if(fitness < currentFitness)
                {        
                   //Updating current solution 
                   x = Arrays.copyOf(xNew, xNew.length);
                   y = Arrays.copyOf(yNew, yNew.length);
                   currentFitness = fitness;
                }
                
                if(currentFitness < bestSolutionFitness)
                {        
                   //Updating best found solution
                   bestSolutionX = Arrays.copyOf(x, x.length);
                   bestSolutionY = Arrays.copyOf(y, y.length);
                   bestSolutionType = type;
                   bestSolutionFitness = currentFitness;
                }
            }
            
            if((nPieces-nMutations)!=fEvals/resetInterval) 
            {
                nMutations--;
            }
            
        }
        
        return new Solution(n, nQ, nT, nB, bestSolutionX, bestSolutionY, bestSolutionType);
    }
}
