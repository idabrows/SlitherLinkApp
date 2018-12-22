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
    Calculator calculator;
    Map M;

    public SolverController(Map M) {
        this.M=M;
        calculator = new Calculator(M);
    }



    public void solveMe() throws IloException{
        int counter=0;
        HashSet<GameVariables> S = new HashSet<>();

        IloCplex cplex = new IloCplex();
        GameVariables gameVariables = new GameVariables(M);
        List<IloRange> constraints = new ArrayList<>();
        notConnectedYet(gameVariables,cplex,constraints);
        calculator.printSolution(gameVariables,cplex);
        if(calculator.isConnected(gameVariables,cplex)) return;
        gameVariables.setIntGameVariables(cplex);
        for (int i = 0; i < gameVariables.getVerticalLineInt().length; i++) {
            for (int j = 0; j < gameVariables.getVerticalLineInt()[0].length; j++) {
                IloCplex cplex2 = new IloCplex();
                notConnectedYet(gameVariables,cplex2,constraints);
                constraints.add(cplex2.addEq(gameVariables.getVerticalLineSolver()[i][j],1-gameVariables.getVerticalLineInt()[i][j]));
                if(cplex2.solve()){
                    counter++;
                    GameVariables gameVariables1 = new GameVariables(gameVariables);
                    gameVariables1.setIntGameVariables(cplex2);
                    S.add(gameVariables1);
                }
                calculator.printSolution(gameVariables,cplex2);
                constraints.remove(constraints.size()-1);
            }
        }
        System.out.println(counter);
        System.out.println(S.size());

        for (GameVariables s:S) {
            if(calculator.isConnected(s)){
                System.out.println("Connected"); return;
            }
        }

    }

 //   public void notConnectedYet(IloIntVar[][] VerticalLine, IloIntVar[][] HorizontalLine, IloIntVar[][] Node, IloCplex cplex, List<IloRange> constraints) throws IloException {
 public void notConnectedYet(GameVariables gameVariables, IloCplex cplex, List<IloRange> constraints) throws IloException {
        calculator.makeBool(gameVariables,cplex);
        calculator.addPrimaryConstraints(gameVariables,cplex,constraints);
        calculator.addGameConstraints(gameVariables,cplex,constraints);
        calculator.addCycleConstraints(gameVariables,cplex,constraints);

    }

}