import java.util.Random;

/**
 * Roulette Wheel Selection
 */
public class GASelector {


    /*This is for minimization*/
    public QTBPopulation[] select(QTBPopulation[] current) {
        int[] weights = calcWeights(current);
        return select(current, weights);
    }

    public int[] calcWeights(QTBPopulation[] current) {
        int lop = current.length;
        int[] fitValues = new int[lop];
        int[] weights = new int[lop];
        int maxFit = 0;

        for (int i = 0; i < lop; i++)
            fitValues[i] = current[i].getFitness();
        for (int f : fitValues)
            if (f > maxFit) maxFit = f;

        for (int i = 0; i < lop; i++) {
            //weights[i] = maxFit - fitValues[i];//
            weights[i] = (int)Math.pow(maxFit - fitValues[i], 3);

        }

        return weights;
    }

    public QTBPopulation[] select(QTBPopulation[] current, int[] weights) {


        /*calculates the sum of all the fitness values*/
        int totalWeight = 0;
        for (int w : weights) totalWeight += w;

        /*Creates a roulette and fill each spot with a QTBPopulation*/
        QTBPopulation[] roulette = new QTBPopulation[totalWeight];
        int i = 0;
        int nx = 0;
        while (i < weights.length) {
            if (0 < weights[i]) {
                weights[i]--;
                roulette[nx] = current[i]; //fill each spot of roulette
                nx++;
            } else {
                i++;
            }
        }

        /*Play roulette and select next generation*/
        QTBPopulation[] selected = new QTBPopulation[current.length];
        Random rn = new Random(); //randomly select populations
        if (totalWeight > 0) {
            for (int j = 0; j < selected.length; j++) {
                selected[j] = roulette[rn.nextInt(totalWeight)].clone();
            }
        } else {
            return current;
        }

        return selected;
    }


}
