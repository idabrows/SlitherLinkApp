package model;

import ilog.concert.IloException;
import ilog.concert.IloIntVar;
import ilog.cplex.IloCplex;

public class GameBinaryVariables {
    int rows;
    int cols;
    public Map M;
    public IloIntVar[][] xVertical;
    public IloIntVar[][] xHorizontal;

    public GameBinaryVariables(Map M){
        this.rows=M.rows;
        this.cols=M.cols;
        this.M=M;
    }

    public GameBinaryVariables loadVariables(IloCplex IC) throws IloException {
        IloIntVar[][] X = new IloIntVar[M.rows-1][M.cols-1];
        for(int i=0;i<M.rows-1;i++)
            for(int j=0;j<M.cols-1;j++)
                X[i][j]=IC.boolVar();
        return X;
    }

}
