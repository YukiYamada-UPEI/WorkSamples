import java.util.Random;

/**
 * Mutate a population at a possibility of 1/5.
 *
 */
public class GAMutator {

    /**
     * Mutates one generation
     */
    public QTBPopulation[] mutateGeneration(QTBPopulation[] generation) {
        for (QTBPopulation p : generation) {
            mutateOneFifth(p);
            mutateOneFifth(p);
        }

        return generation;
    }



    /**
     * Mutate one population
     */
    public void mutateOneFifth(QTBPopulation p) {
        int n = p.getN();
        Random r = new Random();


        int i1 = r.nextInt(p.getnPiece());

        //System.out.println("mutated at " + i1);
        if (0 == r.nextInt(5)) {
            p.getX()[i1] = r.nextInt(n);
            p.getY()[i1] = r.nextInt(n);
        }
    }



}