package model;

import ilog.concert.IloException;
import ilog.concert.IloIntVar;
import ilog.concert.IloLinearNumExpr;
import ilog.cplex.IloCplex;

private class Constraints {
    int rows;
    int cols;
    int cycleConditions[][] = new int[rows][cols];
    int gameConditions[][];

    private Constraints(Map M){
        this.rows=M.rows;
        this.cols=M.cols;
        this.gameConditions=M.coefficients;
    }

    public void setEquations() throws IloException {
        IloIntVar[][] X = new IloIntVar[rows-1][cols-1];
        IloCplex cplex = new IloCplex();

        for (int i=0;i<rows-1;i++){
            for(int j=0;j<cols-1;j++)
                  X[i][j]=cplex.boolVar();
        }

        IloLinearNumExpr objective = cplex.linearNumExpr();
        objective.addTerm(0.12, X[0][0]);
        cplex.addMaximize(objective);

    }




    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getCols() {
        return cols;
    }

    public void setCols(int cols) {
        this.cols = cols;
    }

    public int[][] getCycleConditions() {
        return cycleConditions;
    }

    public void setCycleConditions(int[][] cycleConditions) {
        this.cycleConditions = cycleConditions;
    }

    public int[][] getGameContitions() {
        return gameContitions;
    }

    public void setGameContitions(int[][] gameContitions) {
        this.gameContitions = gameContitions;
    }
}
