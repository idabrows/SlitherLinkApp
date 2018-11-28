package view;
import model.Calculator;
import model.Map;


import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        Map M = new Map();
        M.readMapFromFile(new File("//home//idabrows//Documents//SlitherLink//Coefficients"));
        Calculator calculator=new Calculator(M);
        calculator.solveMe();
    }
}
