import java.util.Random;


/**
 * Crossover Operations.
 * Randomly choose two indexes and crossover
 *
 */
public class GACrossover {
    /**
     * Cross over operations is conducted
     *
     * @param populations
     * @return
     */
    public QTBPopulation[] crossover(QTBPopulation[] populations) {

        for (int i = 0; i < populations.length; i += 2) {
            if (i+1 < populations.length)
                crossTwo(populations[i], populations[i + 1]);
        }

        return populations;
    }

    /**
     * Cross over two populations
     *
     * @return
     */
    public void crossTwo(QTBPopulation next1, QTBPopulation next2) {
        int geneLen = next1.getX().length;
        int nPieces = next1.getnPiece();
        Random r = new Random();
        int crossLine = r.nextInt(nPieces); //which index to crossover
        int crossLine2 = r.nextInt(nPieces);

        if (crossLine > crossLine2) {
            int temp = crossLine;
            crossLine = crossLine2;
            crossLine2 = temp;
        }
        //System.out.println("crossline:" + crossLine);
        //System.out.println("crossline2:" + crossLine2);

        QTBPopulation p1 = next1.clone();
        QTBPopulation p2 = next2.clone();


        /*GACrossover operation*/
        for (int i = crossLine; i < crossLine2 + 1; i++) {
            next1.getX()[i] = p2.getX()[i];
            next1.getY()[i] = p2.getY()[i];
            next1.getType()[i] = p2.getType()[i];

            next2.getX()[i] = p1.getX()[i];
            next2.getY()[i] = p1.getY()[i];
            next2.getType()[i] = p1.getType()[i];

        }


    }

}
