
/**
 * The ObjectiveFunction has two methods, one for evaluating a solution to the problem.
 * And another for checking if it is a valid solution.
 * @author (your name)
 * @version (a version number or a date)
 */
public class ObjectiveFunction
{
    /**
     * This method evaluates the objective function
     *
     * @param   int[] x : x coordinates of the pieces 
     *          int[] y : y coordinates of the pieces 
     *          int[] type : type of the pieces (0-queen; 1-tower; 2-bishop)
     *          
     * @return  the number of pieces being attacked
     * 
     */
    static public int Evaluate(int[] x, int[] y, int[] type)
    {
        int result=0;
        //For each piece
        for(int i = 0; i<x.length; i++)
        {
            //We check if it is attacked by another piece
            for(int j = 0; j<x.length; j++)
            {       
                if((i!=j) && (type[j] <= 1) && (x[j]==x[i] || y[j]==y[i]))  //Checking for rows and columns of Queens and Towers
                {
                    result++;
                    break;
                }
                if((i!=j) && (type[j]%2==0) && (Math.abs(x[j]-x[i])==Math.abs(y[j]-y[i]))) //Checking for diagonals Queens and Bishops
                {
                    result++;
                    break;
                }
            }
        }
        return  result;
    }
    
    /**
     * This method checks whether the solution is valid
     *
     * @param   int n : nxn size of the board 
     *          int nQueens : numbers of Queens that should be placed on the board 
     *          int nTowers : numbers of Towers that should be placed on the board 
     *          int nBishops : numbers of Bishops that should be placed on the board 
     *          int[] x : x coordinates of the pieces 
     *          int[] y : y coordinates of the pieces 
     *          int[] type : type of the pieces (0-queen; 1-tower; 2-bishop)
     *          
     * @return  true if it is a valid solution
     *          false if the constraints are violated
     */
        static public Boolean CheckConstraints(int n, int nQueens, int nTowers, int nBishops, int[] x, int[] y, int[] type)
    {
        int[] cPieces = new int[3];
        try{ 
            for(int i = 0; i<x.length; i++) 
            {
                if (x[i]>n || x[i]<1 || y[i]>n || y[i]<1) return false;    //If some piece is outside the board
                cPieces[type[i]]++;                                     //Counting the type of each piece
                
                for(int j=i+1; j<x.length; j++)
                {
                    if(x[i]==x[j] && y[i]==y[j]) return false;             //If two pieces are in the same square
                }
            }
        
        }
        catch (Exception e) {return false;} //If something is wrong, e.g. a piece type different from 0,1,2
    
        if(cPieces[0]!=nQueens || cPieces[1]!=nTowers || cPieces[2]!=nBishops) return false; //If the number of pieces do not match
        
        return true;
    }
}
