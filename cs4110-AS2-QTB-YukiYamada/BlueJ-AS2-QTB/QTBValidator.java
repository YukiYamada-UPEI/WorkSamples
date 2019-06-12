public class QTBValidator {
    private int maxEvals, n, nQ, nT, nB, nPieces;


    /**
     * Constructor for objects of class RandomSearch
     */
    public QTBValidator(int boardSize, int nQueens, int nTowers, int nBishops, int maxEvaluations) {
        maxEvals = maxEvaluations;

        n = boardSize;
        nQ = nQueens;
        nT = nTowers;
        nB = nBishops;
        nPieces = nQ + nT + nB;
    }

    /**
     * Converts a QTBPopulation into a Solution
     *
     * @param p
     * @return
     */
    public Solution convertSolution(QTBPopulation p) {
        return new Solution(p.getN(), p.getnQ(), p.getnT(), p.getnB(), p.getX(), p.getY(), p.getType());
    }

    /**
     * Pick the best population amoung the generation
     *
     * @param populations
     * @return
     */
    public QTBPopulation pickBestPopulation(QTBPopulation[] populations) {
        /*Check the best population amoung the current generation*/
        int bestIndex = 0;
        for (int i = 0; i < populations.length; i++) {
            //System.out.println(i);
            if (populations[i].getFitness() < populations[bestIndex].getFitness())
                bestIndex = i;
        }
        return populations[bestIndex];
    }

    /**
     * Checks if the positions of pieces are valid
     * (no two pieces are a the same spot)
     * (correct number of each type of pieces are placed)
     */
    public boolean isPopulationValid(QTBPopulation p) {
        return ObjectiveFunction.CheckConstraints(n, nQ, nT, nB, p.getX(), p.getY(), p.getType());
    }

    /**
     * Checks the constraints and fitness values of a generation
     * and builds a new generation with valid populations
     *
     * @param candidateP
     * @param currentP
     * @return
     */
    public QTBPopulation[] checkConstraintsAndEvaluates(QTBPopulation[] candidateP, QTBPopulation[] currentP) {
        int nPopulation = currentP.length;
        QTBPopulation[] nextP = new QTBPopulation[nPopulation];

        for (int i = 0; i < nPopulation; i++) {
            int f;
            if ((f = evaluateFitness(candidateP[i])) != -1) {
                nextP[i] = candidateP[i]; //valid population will be a child
                nextP[i].setFitness(f); //fitness value is set
            } else {
                nextP[i] = currentP[i];
            }
        }
        return nextP;
    }

    /**
     * Calls checkConstraints and evaluate functions of ObjectiveFunction class
     * Checks if a population violates constraints and evaluate a fitness value of a population.
     *
     * @return -1: if a population violates constraints
     * fitness value: otherwise
     */
    public int evaluateFitness(QTBPopulation candidate) {
        int[] x = candidate.getX();
        int[] y = candidate.getY();
        int[] type = candidate.getType();

        if (ObjectiveFunction.CheckConstraints(n, nQ, nT, nB, x, y, type)) {
            return ObjectiveFunction.Evaluate(x, y, type);
        } else {
            return -1;
        }
    }

    /**
     * Prints the fitness values of the current generation
     *
     * @param populations
     */
    public void printFitnesses(QTBPopulation[] populations) {
        for (int i = 0; i < populations.length; i++) {
            System.out.println("Fitness[" + i + "] = " + populations[i].getFitness());
        }
    }


}
