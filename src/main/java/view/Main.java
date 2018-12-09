package view;
import ilog.concert.IloException;
import model.Calculator;
import model.Map;


import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException, IloException {
        Map M = new Map();
        M.readMapFromFile(new File("//home//idabrows//Documents//SlitherLink//Coefficients2"));
          Calculator calculator=new Calculator(M);
        calculator.solveMe();
    }
}
