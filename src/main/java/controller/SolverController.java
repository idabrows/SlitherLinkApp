package controller;
import ilog.concert.*;
import ilog.cplex.IloCplex;
import model.Calculator;
import model.Coordinates;
import model.Map;

import java.util.ArrayList;
import java.util.List;


public class SolverController {
    Calculator calculator;
    Map M;

    public SolverController(Map M) {
        this.M=M;
        calculator = new Calculator(M);
        checkedValues = new ArrayList<>();
    }

    public ArrayList<Coordinates> checkedValues;

    public void solveMe(ArrayList<Coordinates> coordinates) throws IloException{
            //looking for the variables that can be constants
            IloCplex cplex = new IloCplex();
            IloIntVar[][] VerticalLine = new IloIntVar[M.getRows()][M.getCols() + 1];
            IloIntVar[][] HorizontalLine = new IloIntVar[M.getRows() + 1][M.getCols()];
            IloIntVar[][] Node = new IloIntVar[M.getRows() + 1][M.getCols() + 1];
            List<IloRange> constraints = new ArrayList<IloRange>();
            notConnectedYet(VerticalLine,HorizontalLine,Node,cplex,constraints);

    }

    public void notConnectedYet(IloIntVar[][] VerticalLine, IloIntVar[][] HorizontalLine, IloIntVar[][] Node, IloCplex cplex, List<IloRange> constraints) throws IloException {
        calculator.makeBool(VerticalLine,HorizontalLine,Node,cplex);
        calculator.addPrimaryConstraints(VerticalLine,HorizontalLine,Node,cplex,constraints);
        calculator.addGameConstraints(VerticalLine,HorizontalLine,cplex,constraints);
        calculator.addCycleConstraints(VerticalLine,HorizontalLine,Node,cplex,constraints);

    }

}