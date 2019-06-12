public class GeneticAlgorithm implements SearchAlgorithm {
    // Maximum number of solutions to be evaluated during the search
    private int maxEvals, n, nQ, nT, nB, nPieces;
    private int remainingEvals;

    private QTBValidator mValidator;
    private QTBPopulationFactory mPFactory;

    private GASelector mSelector = new GASelector();
    private GACrossover mCrossover = new GACrossover();
    private GAMutator mMutator = new GAMutator();


    /**
     * Constructor for objects of class RandomSearch
     */
    public GeneticAlgorithm(int boardSize, int nQueens, int nTowers, int nBishops, int maxEvaluations) {
        maxEvals = maxEvaluations;

        n = boardSize;
        nQ = nQueens;
        nT = nTowers;
        nB = nBishops;
        nPieces = nQ + nT + nB;
        maxEvals = maxEvaluations;

        mValidator = new QTBValidator(boardSize, nQueens, nTowers, nBishops, maxEvaluations);
        mPFactory = new QTBPopulationFactory(boardSize, nQueens, nTowers, nBishops);
    }

    /**
     * Starts the algorithm
     *
     * @return The best found solution
     */
    @Override
    public Solution Start() {

        int nPopulation = 100;  //number of initial populations
        QTBPopulation[] currentGeneration = mPFactory.createGeneration(nPopulation);
        int count = 0;

        remainingEvals = maxEvals;
        while (0 < remainingEvals) {
            QTBPopulation[] selectedGeneration = mSelector.select(currentGeneration);
            QTBPopulation[] crossoverGeneration = mCrossover.crossover(selectedGeneration);
            QTBPopulation[] mutatedGeneration = mMutator.mutateGeneration(crossoverGeneration);
            QTBPopulation[] candidateGeneration = mutatedGeneration;

            nextBecomesCurrent(currentGeneration, candidateGeneration);
            if (count % 100 == 0)
                System.out.println("f=" + currentGeneration[0].getFitness());
            count++;


        }

        /*The best population is returned as a result*/
        QTBPopulation best = mValidator.pickBestPopulation(currentGeneration);
        if (mValidator.isPopulationValid(best))
            return mValidator.convertSolution(mValidator.pickBestPopulation(currentGeneration));
        else {
            System.out.println("Invalid Result\n");
            return null;
        }
    }


    /**
     * @param current
     * @param candidate
     */
    public void nextBecomesCurrent(QTBPopulation[] current, QTBPopulation[] candidate) {
        int l = current.length;
        for (int i = 0; i < l; i++) {
            int f;
            if ((f = mValidator.evaluateFitness(candidate[i])) != -1) {
                current[i] = candidate[i].clone(); //valid population will be a child
                current[i].setFitness(f); //fitness value is set
                remainingEvals--;
            }
        }
    }


}
