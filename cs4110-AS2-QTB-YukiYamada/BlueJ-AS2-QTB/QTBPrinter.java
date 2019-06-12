import javax.swing.*;

/**
 * This class is only for printing solutions
 */
public class QTBPrinter {

    /*Instance variables*/
    private static final int QUEEN = 0;
    private static final int TOWER = 1;
    private static final int BISHOP = 2;


    /*Printing methods for Solution*/
    public static void printNumValues(Solution sol) {
        int[] x = sol.getX();
        int[] y = sol.getY();
        int[] type = sol.getType();

        System.out.print("\nx = ");
        for (int i : x) {
            if (i < 10) System.out.print(" " + i + ",");
            else System.out.print(i + ",");
        }

        System.out.print("\ny = ");
        for (int i : y) {
            if (i < 10) System.out.print(" " + i + ",");
            else System.out.print(i + ",");
        }

        System.out.print("\nT = ");
        for (int t : type) {
            if (t < 10) System.out.print(" " + t + ",");
            else System.out.print(t + ",");
        }
        System.out.println();

    }

    public static void printIndex(Solution sol) {
        int[] x = sol.getX();
        int[] y = sol.getY();
        int[] type = sol.getType();

        System.out.print("\ni = ");
        for (int i = 0; i < x.length; i++) {
            if (i < 10) System.out.print(" " + i + " ");
            else System.out.print(i + " ");
        }
        System.out.println();
    }

    public static void printIndexXY(Solution sol) {
        int[] x = sol.getX();

        System.out.print("\ni = ");
        for (int i = 0; i < x.length; i++) {
            if (i < 10) System.out.print(" " + i + " ");
            else System.out.print(i + " ");
        }
        System.out.print("  ");
        System.out.print("i = ");
        for (int i = 0; i < x.length; i++) {
            if (i < 10) System.out.print(" " + i + " ");
            else System.out.print(i + " ");
        }
        System.out.println();
        System.out.println();

    }

    public static void printValues(Solution sol) {
        int[] x = sol.getX();
        int[] y = sol.getY();
        int[] type = sol.getType();

        System.out.print("\nx = ");
        for (int i : x) {
            if (i < 10) System.out.print(" " + i + " ");
            else System.out.print(" " + (char) (i + 55) + " ");
        }

        System.out.print("\ny = ");
        for (int i : y) {
            if (i < 10) System.out.print(" " + i + " ");
            else System.out.print(" " + (char) (i + 55) + " ");
        }

        System.out.print("\nT = ");
        for (int t : type) {
            if (t == QUEEN) System.out.print(" Q" + " ");
            else if (t == TOWER) System.out.print(" T" + " ");
            else if (t == BISHOP) System.out.print(" B" + " ");
        }
        System.out.println();

        System.out.println();
    }

    public static void printSolution(Solution sol) {
        int[] x = sol.getX();
        int[] y = sol.getY();
        int[] type = sol.getType();

        int length = x.length;
        boolean exist = false;
        int index = 0;
        System.out.print("\n   ");
        for (int i = 0; i < length; i++)
            if (i < 10)
                System.out.print(i + " ");
            else
                System.out.print((char) (i + 55) + " ");
        System.out.print("\n   ");
        for (int i = 0; i < length; i++)
            System.out.print("- ");

        System.out.println();
        for (int py = 0; py < length; py++) {
            if (py < 10)
                System.out.print(py + "| ");
            else
                System.out.print((char) (py + 55) + "| ");
            for (int px = 0; px < length; px++) {
                for (int i = 0; i < length; i++) {
                    if (px == x[i] && py == y[i]) {
                        exist = true;
                        index = i;
                    }
                }
                if (!exist) System.out.print(". ");
                else {
                    if (type[index] == QUEEN) System.out.print("Q" + " ");
                    else if (type[index] == TOWER) System.out.print("T" + " ");
                    else if (type[index] == BISHOP) System.out.print("B" + " ");
                }
                exist = false;
            }
            System.out.println();

        }
    }

    public static void printBoard(Solution sol) {
        int[] x = sol.getX();
        int[] y = sol.getY();
        int[] type = sol.getType();
        int nBoard = sol.getN();

        int length = x.length;
        length = nBoard;
        System.out.println("Printer: nBoard: " + nBoard);
        boolean exist = false;
        int index = 0;
        System.out.print("\n   ");
        for (int i = 0; i < length; i++)
            if (i < 10)
                System.out.print(i + " ");
            else
                System.out.print((char) (i + 55) + " ");
        System.out.print("\n   ");
        for (int i = 0; i < length; i++)
            System.out.print("- ");

        System.out.println();
        for (int py = 0; py < length; py++) {
            if (py < 10)
                System.out.print(py + "| ");
            else
                System.out.print((char) (py + 55) + "| ");
            for (int px = 0; px < length; px++) {
                for (int i = 0; i < length; i++) {
                    if (px == x[i] && py == y[i]) {
                        exist = true;
                        index = i;
                    }
                }
                if (!exist) System.out.print(". ");
                else {
                    if (type[index] == QUEEN) System.out.print("Q" + " ");
                    else if (type[index] == TOWER) System.out.print("T" + " ");
                    else if (type[index] == BISHOP) System.out.print("B" + " ");
                }
                exist = false;
            }
            System.out.println();

        }
    }


    public static void printValuesOfX(Solution sol) {
        int[] x = sol.getX();
        System.out.print("x = ");
        for (int i : x) {
            if (i < 10) System.out.print(" " + i + " ");
            else System.out.print(" " + (char) (i + 55) + " ");
        }
        System.out.println();
    }

    public static void printValueXY(Solution sol) {
        int[] x = sol.getX();
        System.out.print("x = ");
        for (int i : x) {
            if (i < 10) System.out.print(" " + i + " ");
            else System.out.print(" " + (char) (i + 55) + " ");
        }
        System.out.print("  ");

        int[] y = sol.getY();
        System.out.print("y = ");
        for (int i : y) {
            if (i < 10) System.out.print(" " + i + " ");
            else System.out.print(" " + (char) (i + 55) + " ");
        }
        System.out.println();
    }

    public static void printAll(Solution sol) {
        printNumValues(sol);
        printIndex(sol);
        printValues(sol);
        printSolution(sol);
    }


    /*Overloaded methods for QTBPopulation*/
    public static void printNumValues(QTBPopulation p) {
        printNumValues(convertSolution(p));
    }

    public static void printIndex(QTBPopulation p) {
        printIndex(convertSolution(p));
    }

    public static void printIndexXY(QTBPopulation p) {
        printIndexXY(convertSolution(p));
    }

    public static void printValues(QTBPopulation p) {
        printValues(convertSolution(p));
    }

    public static void printSolution(QTBPopulation p) {
        printSolution(convertSolution(p));
    }

    public static void printValueOfX(QTBPopulation p) {
        printValuesOfX(convertSolution(p));

    }

    public static void printValueXY(QTBPopulation p) {
        printValueXY(convertSolution(p));
    }

    public static void printAll(QTBPopulation p) {
        printAll(convertSolution(p));
    }


    /*Convert QTBPopulation into Solution */
    public static Solution convertSolution(QTBPopulation p) {
        return new Solution(p.getN(), p.getnQ(), p.getnT(), p.getnB(), p.getX(), p.getY(), p.getType());
    }
}
