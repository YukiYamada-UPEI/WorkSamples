import java.util.Scanner;

/**
 * Given a problem description (specific parameters) calls an algorithm to solve the problem.
 *
 */
public class Solver
{
    // Parameters of some problems
    //private int maxEvals = 10000, n = 20, nQ = 10, nT = 7, nB = 5; //Start with this one
    //private int maxEvals = 1000000, n = 20, nQ = 10, nT = 7, nB = 5;      //The same but with much more Function evaluations to perform, the global optimum in this problem has fitness 0.
    //private int maxEvals = 1000000, n = 100, nQ = 20, nT = 20, nB = 20;   //This one should be hard to solve, but there should be solutions of fitness 0
    private int maxEvals = 1000000, n = 100, nQ = 60, nT = 20, nB = 60;   //Very hard to solve, there shouldn't be any solution with fitness 0.

    //private int maxEvals = 100 , n = 20, nQ = 10, nT = 7, nB = 5; //My test

    // Algorithm used
    private SearchAlgorithm algorithm;
    private Solution sol;

    /**
     * Constructs the solver using the manually specified parameters
     */
    public Solver()
    {
        //algorithm = new RandomSearch(n, nQ, nT, nB, maxEvals);
        //algorithm = new SemiRandomSearch(n, nQ, nT, nB, maxEvals);
       algorithm = new GeneticAlgorithm(n, nQ, nT, nB, maxEvals);
    }

    /**
     * Runs the algorithm and returns the best solution found by the algorithm
     */
    public void Solve()
    {
        sol = algorithm.Start();
    }
    
    /**
     * Runs the algorithm a specified number of times and returns the average fitness of those runs
     * @return    The average fitness of all the runs
    */
    public Statistics Test(int runs)
    {
        int max = 0, min = Integer.MAX_VALUE;   
        double sumFitness = 0;
        for(int i=0; i<runs; i++)
        {
            sol = algorithm.Start();
            QTBPrinter.printNumValues(sol);//
            sumFitness = sumFitness+sol.GetFitness();
            if(sol.GetFitness() < min) min=sol.GetFitness();
            if(sol.GetFitness() > max) max=sol.GetFitness();
        }
        return new Statistics(min, max, sumFitness/runs);
    }

    public static void main(String[]args){
        Solver solver = new Solver();
        System.out.print("How many repeats?: ");
        Scanner s = new Scanner(System.in);
        int repeat = s.nextInt();
        s.nextLine();
        System.out.println(solver.Test(repeat));
    }


    /**These getters are added only for debugging purposes**/
    public Solution getSol() {
        return sol;
    }
}
