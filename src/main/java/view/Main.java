package view;
import ilog.concert.IloException;
import controller.SolverController;
import model.Coordinates;
import model.Map;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws IOException, IloException {
        Map M = new Map();
        M.readMapFromFile(new File("//home//idabrows//Documents//SlitherLink//Coefficients2"));
        SolverController solverController=new SolverController(M);
        ArrayList<Coordinates> ch = new ArrayList<>();
        solverController.solveMe();
    }
}
