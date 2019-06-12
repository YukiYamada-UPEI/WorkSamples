import java.util.Arrays;

/**
 * This class models a one state, population in Genetic Algorithm
 * The content is almost same as solution, but this class is used by GeneticAlgorithm.java
 * <p>
 * Cloneable Interface is referenced by the website below
 * https://qiita.com/SUZUKI_Masaya/items/8da8c0038797f143f5d3
 */
public class QTBPopulation implements Cloneable {
    private int n, nQ, nT, nB, fitness;
    private int[] x, y, type;
    private int nPiece;
    private int id;

    public static int popId = 0;


    /*Constructors*/
    public QTBPopulation(int fitness) {
        this.fitness = fitness;

    }

    public QTBPopulation(int n, int nQ, int nT, int nB, int fitness, int[] x, int[] y, int[] type) {
        this.n = n;
        this.nQ = nQ;
        this.nT = nT;
        this.nB = nB;
        this.fitness = fitness;
        this.x = x;
        this.y = y;
        this.type = type;

        this.nPiece = nQ + nT + nB;
        id = popId;
        popId++;

    }


    /*Getters and setters*/
    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }

    public int getnQ() {
        return nQ;
    }

    public void setnQ(int nQ) {
        this.nQ = nQ;
    }

    public int getnT() {
        return nT;
    }

    public void setnT(int nT) {
        this.nT = nT;
    }

    public int getnB() {
        return nB;
    }

    public void setnB(int nB) {
        this.nB = nB;
    }

    public int getFitness() {
        return fitness;
    }

    public void setFitness(int fitness) {
        this.fitness = fitness;
    }

    public int[] getX() {
        return x;
    }

    public void setX(int[] x) {
        this.x = x;
    }

    public int[] getY() {
        return y;
    }

    public void setY(int[] y) {
        this.y = y;
    }

    public int[] getType() {
        return type;
    }

    public void setType(int[] type) {
        this.type = type;
    }

    public int getnPiece() {
        return nPiece;
    }

    public void setnPiece(int nPiece) {
        this.nPiece = nPiece;
    }

    /*toString*/
    @Override
    public String toString() {
        return "popId: " + id + " f=" + fitness + " ";
    }

    /*clone*/
    @Override
    public QTBPopulation clone() {
        QTBPopulation copy = null;

        try {
            copy = (QTBPopulation) super.clone();
            copy.n = this.n;
            copy.nB = this.nB;
            copy.nQ = this.nQ;
            copy.nT = this.nT;
            copy.fitness = this.fitness;
            copy.x = Arrays.copyOf(x, x.length);
            copy.y = Arrays.copyOf(y, y.length);
            copy.type = Arrays.copyOf(type, type.length);
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return copy;

    }

}
