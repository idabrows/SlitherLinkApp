package model;
//tu powstanie liczba zmiennych w zależności od wymiarów siatki
//
import java.util.ArrayList;
import java.util.List;

/**
 * @author idabrows
 */
public class Equations{
    /**
     * rows number of rows of matrix X
     */
    int rows=0;
    /**
      * cols number of columns of matrix X
      */
    int cols=0;
    /**
     * X[][] matrix of boolean values
     */
    boolean X[][];

    int nvars=cols*(rows+1)+rows*(cols+1);
    int nconstraints=0;
    public Equations() {

    }

//    public ArrayList<Equation> Constraints(){

//    }


}
