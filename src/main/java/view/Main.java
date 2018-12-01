package view;
import model.Calculator2;
import model.Calculator3;
import model.Map;


import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) throws IOException {
        Map M = new Map();
        M.readMapFromFile(new File("//home//idabrows//Documents//SlitherLink//Coefficients"));
          Calculator2 calculator=new Calculator2(M);
        calculator.solveMe();
//        Calculator3 calculator=new Calculator3();
//        calculator.solveMe();
    }
}
