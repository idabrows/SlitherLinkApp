package controller;
import ilog.concert.*;
import ilog.cplex.IloCplex;
import model.Calculator;
import model.Coordinates;
import model.GameVariables;
import model.Map;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class SolverController {
    private boolean toSolve = true;
    IloCplex cplex = new IloCplex();
    Calculator calculator;
    private Map M;
    private GameVariables gameVariables;

    public SolverController(Map M) throws IloException {
        this.M=M;
        calculator = new Calculator(M);
        solveMe();
    }



    public void solveMe() throws IloException {
        int counter = 0;
        HashSet<GameVariables> S = new HashSet<>();

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
                IloIntExpr iloIntExpr = cplex.linearIntExpr();
                for (IloIntVar iloIntVar : calculator.getPartition().get(i))
                    ((IloLinearIntExpr) iloIntExpr).addTerm(iloIntVar, 1);
                constraints.add(cplex.addLe(cplex.sum(iloIntExpr, 1), calculator.getPartition().get(i).size()));
            }
            if(!cplex.solve()){
             toSolve=false;
             return;
            }
            gameVariables.setIntGameVariables(cplex);

        }

    }

 public void notConnectedYet(GameVariables gameVariables, IloCplex cplex, List<IloRange> constraints) throws IloException {
        calculator.makeBool(gameVariables,cplex);
        calculator.addPrimaryConstraints(gameVariables,cplex,constraints);
        calculator.addGameConstraints(gameVariables,cplex,constraints);
        calculator.addCycleConstraints(gameVariables,cplex,constraints);
        calculator.notEmpty(gameVariables,cplex,constraints);
  }


    public Calculator getCalculator() {
        return calculator;
    }

    public void setCalculator(Calculator calculator) {
        this.calculator = calculator;
    }

    public Map getM() {
        return M;
    }

    public void setM(Map m) {
        M = m;
    }

    public GameVariables getGameVariables() {
        return gameVariables;
    }

    public void setGameVariables(GameVariables gameVariables) {
        this.gameVariables = gameVariables;
    }

    public boolean isToSolve() {
        return toSolve;
    }

    public void setToSolve(boolean toSolve) {
        this.toSolve = toSolve;
    }

    public IloCplex getCplex() {
        return cplex;
    }

    public void setCplex(IloCplex cplex) {
        this.cplex = cplex;
    }
}