package model;

import ilog.concert.IloException;
import ilog.concert.IloIntVar;
import ilog.cplex.IloCplex;

public class GameVariables {
    private final IloIntVar[][] VerticalLineSolver;
    private final IloIntVar[][] HorizontalLineSolver;
    private final IloIntVar[][] NodeSolver;
    private int[][] VerticalLineInt;
    private int[][] HorizontalLineInt;


    public GameVariables(Map M){
        VerticalLineSolver = new IloIntVar[M.getRows()][M.getCols() + 1];
        HorizontalLineSolver = new IloIntVar[M.getRows() + 1][M.getCols()];
        NodeSolver = new IloIntVar[M.getRows() + 1][M.getCols() + 1];
        int[][] sureVertical = new int[M.getRows()][M.getCols() + 1];
        int[][] sureHorizontal = new int[M.getRows() + 1][M.getCols()];
        for (int i = 0; i < sureVertical.length; i++) {
            for (int j = 0; j < sureVertical[0].length; j++) {
                sureVertical[i][j]=-1;
            }
        }
        for (int i = 0; i < sureHorizontal.length; i++) {
            for (int j = 0; j < sureHorizontal[0].length; j++) {
                sureHorizontal[i][j]=-1;
            }
        }
    }

    public void setIntGameVariables(IloCplex cplex) throws IloException {
        VerticalLineInt = new int[VerticalLineSolver.length][VerticalLineSolver[0].length];
        for (int i = 0; i < VerticalLineInt.length; i++)
            for (int j = 0; j < VerticalLineInt[0].length; j++)
                VerticalLineInt[i][j]= (int) cplex.getValue(VerticalLineSolver[i][j]);

        HorizontalLineInt = new int[HorizontalLineSolver.length][HorizontalLineSolver[0].length];
        for (int i = 0; i < HorizontalLineInt.length; i++)
            for (int j = 0; j < HorizontalLineInt[0].length; j++)
                getHorizontalLineInt()[i][j]= (int) cplex.getValue(getHorizontalLineSolver()[i][j]);

        int[][] nodeInt = new int[NodeSolver.length][NodeSolver[0].length];
        for (int i = 0; i < nodeInt.length; i++)
            for (int j = 0; j < nodeInt[0].length; j++)
                nodeInt[i][j]= (int) cplex.getValue(NodeSolver[i][j]);
    }

    IloIntVar[][] getVerticalLineSolver() {
        return VerticalLineSolver;
    }

    void setVerticalLineSolver(IloIntVar verticalLineSolver,int i, int j) {
        VerticalLineSolver[i][j] = verticalLineSolver;
    }

    IloIntVar[][] getHorizontalLineSolver() {
        return HorizontalLineSolver;
    }

    void setHorizontalLineSolver(IloIntVar verticalLineSolver,int i, int j) {
        HorizontalLineSolver[i][j] = verticalLineSolver;
    }

    IloIntVar[][] getNodeSolver() {
        return NodeSolver;
    }

    void setNodeSolver(IloIntVar verticalLineSolver,int i, int j) {
        NodeSolver[i][j] = verticalLineSolver;
    }

    public int[][] getVerticalLineInt() {
        return VerticalLineInt;
    }

    public int[][] getHorizontalLineInt() {
        return HorizontalLineInt;
    }

}