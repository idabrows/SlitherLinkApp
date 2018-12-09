package model;

import ilog.concert.IloException;
import ilog.concert.IloIntVar;
import ilog.cplex.IloCplex;

public class Solution {
    private IloCplex cplex;
    private IloIntVar[][] V;
    private IloIntVar[][] H;
    private IloIntVar[][] N;

    public Solution(IloCplex cplex, IloIntVar[][] V, IloIntVar[][] H, IloIntVar[][] N){
        this.cplex=cplex;
        this.H=H;
        this.N=N;
        this.V=V;
    }

    public IloCplex getCplex() {
        return cplex;
    }

    public void setCplex(IloCplex cplex) {
        this.cplex = cplex;
    }

    public double[][] getV() throws IloException {
        double Vd[][] = new double[V.length][V[0].length];
        for (int i = 0; i <V.length; i++) {
            for (int j = 0; j <V[0].length; j++) {
                Vd[i][j]=cplex.getValue(V[i][j]);
            }
        }
        return Vd;
    }

    public void setV(IloIntVar[][] v) {
        V = v;
    }

    public double[][] getH() throws IloException {
        double Hd[][] = new double[H.length][H[0].length];
        for (int i = 0; i <H.length; i++) {
            for (int j = 0; j <H[0].length; j++) {
                Hd[i][j]=cplex.getValue(H[i][j]);
            }
        }
        return Hd;
    }


    public void setH(IloIntVar[][] h) {
        H = h;
    }

    public IloIntVar[][] getN() {
        return N;
    }

    public void setN(IloIntVar[][] n) {
        N = n;
    }
}
