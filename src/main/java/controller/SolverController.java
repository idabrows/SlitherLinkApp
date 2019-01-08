package controller;
import ilog.concert.*;
import ilog.cplex.IloCplex;
import model.Calculator;
import model.GameVariables;
import model.Map;
import java.util.ArrayList;
import java.util.List;


public class SolverController {
    private boolean toSolve = true;
    private IloCplex cplex = new IloCplex();
    private final Calculator calculator;
    private final Map M;
    private GameVariables gameVariables;

    public SolverController(Map M) throws IloException {
        this.M=M;
        calculator = new Calculator(M);
        solveMe();
    }

    private void solveMe() throws IloException {
        gameVariables = new GameVariables(M);
        List<IloRange> constraints = new ArrayList<>();
        notConnectedYet(gameVariables, cplex, constraints);
        if(!cplex.solve()){
            toSolve=false;
            return;
        }
        gameVariables.setIntGameVariables(cplex);

        while (!calculator.isConnected(gameVariables, cplex)) {
            calculator.divide(gameVariables, cplex);
            cplex = new IloCplex();
            gameVariables = new GameVariables(M);
            notConnectedYet(gameVariables, cplex, constraints);

            for (int i = 0; i < calculator.getPartition().size(); i++) {
                IloLinearIntExpr iloIntExpr = cplex.linearIntExpr();
                for (IloIntVar iloIntVar : calculator.getPartition().get(i))
                    iloIntExpr.addTerm(iloIntVar, 1);
                constraints.add(cplex.addLe(cplex.sum(iloIntExpr, 1), calculator.getPartition().get(i).size()));
            }
            if(!cplex.solve()){
             toSolve=false;
             return;
            }
            gameVariables.setIntGameVariables(cplex);
        }
    }

    private void notConnectedYet(GameVariables gameVariables, IloCplex cplex, List<IloRange> constraints) throws IloException {
        Calculator.makeBool(gameVariables,cplex);
        calculator.addPrimaryConstraints(gameVariables,cplex,constraints);
        calculator.addGameConstraints(gameVariables,cplex,constraints);
        calculator.addCycleConstraints(gameVariables,cplex,constraints);
        calculator.notEmpty(gameVariables,cplex,constraints);
  }

    public GameVariables getGameVariables() {
        return gameVariables;
    }

    public boolean isToSolve() {
        return toSolve;
    }

}