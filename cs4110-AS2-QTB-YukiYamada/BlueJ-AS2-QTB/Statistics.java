
/**
 * An object to store the statistics of an algorithm's test
 */
public class Statistics
{
    // instance variables - replace the example below with your own
    private double average;
    private int minimum, maximum; 

    /**
     * Constructor for objects of class Statistics
     */
    public Statistics(int min, int max, double ave)
    {
        average = ave;
        minimum = min;
        maximum = max;
    }


    /**Getters are added for only a debugging reason**/
    public double getAverage() {
        return average;
    }

    public int getMinimum() {
        return minimum;
    }

    public int getMaximum() {
        return maximum;
    }

    public String toString() {
        return "-Result-\n" + "Ave: " + average + "\nMin: " + minimum+ "\nMax: " + maximum +"\n\n";
    }

}
