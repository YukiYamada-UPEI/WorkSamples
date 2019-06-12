import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class QTBPopulationFactory {
    private int  n, nQ, nT, nB, nPieces;
    private int[] typeO;
    private Random r;


    public QTBPopulationFactory(int boardSize, int nQueens, int nTowers, int nBishops) {

            n = boardSize;
            nQ = nQueens;
            nT = nTowers;
            nB = nBishops;
            r = new Random();
            nPieces = nQ + nT + nB;
    }

    /**
     * Create a random generation with given n populations
     *
     * @return
     */
    public QTBPopulation[] createGeneration(int nPopulation) {
        QTBPopulation[] generation = new QTBPopulation[nPopulation];

        for (int i = 0; i < nPopulation; i++) {
            generation[i] = createPopulation();//original generation to start
        }
        return generation;
    }

    /**
     * Creates a new population randomly.
     *
     * @return
     */
    public QTBPopulation createPopulation() {

        int[] x = new int[nPieces], y = new int[nPieces], type = new int[nPieces];

        //Fixing the pieces' type in the solution
        for (int i = 0; i < nPieces; i++) {
            if (i < nQ) type[i] = 0;
            else if (i < nQ + nT) type[i] = 1;
            else type[i] = 2;
        }


        while (true) { //creates four populatioins
            for (int i = 0; i < nPieces; i++) {
                x[i] = r.nextInt(n) + 1;
                y[i] = r.nextInt(n) + 1;
            }
            if (ObjectiveFunction.CheckConstraints(n, nQ, nT, nB, x, y, type)) {
                int fitness = ObjectiveFunction.Evaluate(x, y, type);
                return new QTBPopulation(n, nQ, nT, nB, fitness, x, y, type);
            }
        }
    }

    /**
     *
     */
    public void decideTypeOrder() {
        /*Build shuffled types*/
        typeO = new int[nPieces];
        Integer[] rtype = new Integer[nPieces];
        for (int i = 0; i < nPieces; i++) {
            if (i < nQ) rtype[i] = 0;
            else if (i < nQ + nT) rtype[i] = 1;
            else rtype[i] = 2;
        }

        List<Integer> list = Arrays.asList(rtype);
        Collections.shuffle(list);
        rtype = list.toArray(new Integer[list.size()]);

        for (int i = 0; i < nPieces; i++) {
            typeO[i] = rtype[i];
            System.out.print(typeO[i]);
        }
        System.out.println();
    }

    /**
     * Creates a new population randomly.
     *
     * @return
     */
    public QTBPopulation createPopulationTypeRandom() {

        int[] x = new int[nPieces], y = new int[nPieces];

        while (true) { //creates four populatioins
            for (int i = 0; i < nPieces; i++) {
                x[i] = r.nextInt(n) + 1;
                y[i] = r.nextInt(n) + 1;
            }
            if (ObjectiveFunction.CheckConstraints(n, nQ, nT, nB, x, y, typeO)) {
                int fitness = ObjectiveFunction.Evaluate(x, y, typeO);
                return new QTBPopulation(n, nQ, nT, nB, fitness, x, y, typeO);
            }
        }
    }



}
