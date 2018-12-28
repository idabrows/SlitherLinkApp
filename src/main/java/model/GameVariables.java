package model;

import ilog.concert.IloException;
import ilog.concert.IloIntVar;
import ilog.cplex.IloCplex;

import java.util.ArrayList;
import java.util.Arrays;

public class GameVariables {
    private IloIntVar[][] VerticalLineSolver;
    private IloIntVar[][] HorizontalLineSolver;
    private IloIntVar[][] NodeSolver;
    private int[][] VerticalLineInt;
    private int[][] HorizontalLineInt;
    private int[][] NodeInt;
    private int[][] SureVertical;
    private int[][] SureHorizontal;

    public GameVariables(Map M){
        VerticalLineSolver = new IloIntVar[M.getRows()][M.getCols() + 1];
        HorizontalLineSolver = new IloIntVar[M.getRows() + 1][M.getCols()];
        NodeSolver = new IloIntVar[M.getRows() + 1][M.getCols() + 1];
        SureVertical = new int[M.getRows()][M.getCols() + 1];
        SureHorizontal = new int[M.getRows() + 1][M.getCols()];
        for (int i = 0; i < SureVertical.length; i++) {
            for (int j = 0; j < SureVertical[0].length; j++) {
                SureVertical[i][j]=-1;
            }
        }
        for (int i = 0; i < SureHorizontal.length; i++) {
            for (int j = 0; j < SureHorizontal[0].length; j++) {
                SureHorizontal[i][j]=-1;
            }
        }

    }

    public GameVariables(GameVariables gameVariables){
        this.VerticalLineSolver = gameVariables.getHorizontalLineSolver().clone();
        this.HorizontalLineSolver = gameVariables.getHorizontalLineSolver().clone();
        this.NodeSolver = gameVariables.getNodeSolver().clone();
        this.VerticalLineInt = gameVariables.getVerticalLineInt().clone();
        this.HorizontalLineInt = gameVariables.getHorizontalLineInt().clone();
        this.NodeInt = gameVariables.getNodeInt().clone();
    }

    public void setIntGameVariables(IloCplex cplex) throws IloException {
        VerticalLineInt = new int[VerticalLineSolver.length][VerticalLineSolver[0].length];
        for (int i = 0; i < VerticalLineInt.length; i++) {
            for (int j = 0; j < VerticalLineInt[0].length; j++) {
                VerticalLineInt[i][j]= (int) cplex.getValue(VerticalLineSolver[i][j]);
            }
        }
        HorizontalLineInt = new int[HorizontalLineSolver.length][HorizontalLineSolver[0].length];
        for (int i = 0; i < HorizontalLineInt.length; i++) {
            for (int j = 0; j < HorizontalLineInt[0].length; j++) {
                getHorizontalLineInt()[i][j]= (int) cplex.getValue(getHorizontalLineSolver()[i][j]);
            }
        }
        NodeInt = new int[NodeSolver.length][NodeSolver[0].length];
        for (int i = 0; i < NodeInt.length; i++) {
            for (int j = 0; j < NodeInt[0].length; j++) {
                NodeInt[i][j]= (int) cplex.getValue(NodeSolver[i][j]);
            }
        }
    }


    public IloIntVar[][] getVerticalLineSolver() {
        return VerticalLineSolver;
    }

    public void setVerticalLineSolver(IloIntVar[][] verticalLineSolver) {
        VerticalLineSolver = verticalLineSolver;
    }

    public void setVerticalLineSolver(IloIntVar verticalLineSolver,int i, int j) {
        VerticalLineSolver[i][j] = verticalLineSolver;
    }

    public IloIntVar[][] getHorizontalLineSolver() {
        return HorizontalLineSolver;
    }

    public void setHorizontalLineSolver(IloIntVar[][] horizontalLineSolver) {
        HorizontalLineSolver = horizontalLineSolver;
    }

    public void setHorizontalLineSolver(IloIntVar verticalLineSolver,int i, int j) {
        HorizontalLineSolver[i][j] = verticalLineSolver;
    }

    public IloIntVar[][] getNodeSolver() {
        return NodeSolver;
    }

    public void setNodeSolver(IloIntVar[][] nodeSolver) {
        NodeSolver = nodeSolver;
    }

    public void setNodeSolver(IloIntVar verticalLineSolver,int i, int j) {
        NodeSolver[i][j] = verticalLineSolver;
    }

    public int[][] getVerticalLineInt() {
        return VerticalLineInt;
    }

    public void setVerticalLineInt(int[][] verticalLineInt) {
        VerticalLineInt = verticalLineInt;
    }

    public int[][] getHorizontalLineInt() {
        return HorizontalLineInt;
    }

    public void setHorizontalLineInt(int[][] horizontalLineInt) {
        HorizontalLineInt = horizontalLineInt;
    }

    public int[][] getNodeInt() {
        return NodeInt;
    }

    public void setNodeInt(int[][] nodeInt) {
        NodeInt = nodeInt;
    }



    public void printVetricalLines(){
        for (int i = 0; i < VerticalLineInt.length; i++) {
            System.out.println(Arrays.toString(VerticalLineInt[i]));
        }
        System.out.println("");

    }

    public int[][] getSureVertical() {
        return SureVertical;
    }

    public void setSureVertical(int[][] sureVertical) {
        SureVertical = sureVertical;
    }

    public int[][] getSureHorizontal() {
        return SureHorizontal;
    }

    public void setSureHorizontal(int[][] sureHorizontal) {
        SureHorizontal = sureHorizontal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GameVariables)) return false;
        GameVariables that = (GameVariables) o;
        for (int i = 0; i < getVerticalLineInt().length; i++) {
            for (int j = 0; j < getVerticalLineInt()[0].length; j++) {
                if(getVerticalLineInt()[i][j]!=that.getVerticalLineInt()[i][j]) return false;
            }
        }
        for (int i = 0; i < getHorizontalLineInt().length; i++) {
            for (int j = 0; j < getHorizontalLineInt()[0].length; j++) {
                if(getHorizontalLineInt()[i][j]!=that.getHorizontalLineInt()[i][j]) return false;
            }
        }
        for (int i = 0; i < getNodeInt().length; i++) {
            for (int j = 0; j < getNodeInt()[0].length; j++) {
                if(getNodeInt()[i][j]!=that.getNodeInt()[i][j]) return false;
            }
        }
        return true;


    }

    public void printSureVertical(){
        for (int i = 0; i < SureVertical.length; i++) {
            System.out.println(Arrays.toString(SureVertical[i]));
        }
    }

    public void printSureHorizontal(){
        for (int i = 0; i < SureHorizontal.length; i++) {
            System.out.println(Arrays.toString(SureHorizontal[i]));
        }
    }

    public int sumSureVertical(){
        int sum=0;
        for (int i = 0; i < SureVertical.length; i++) {
            for (int j = 0; j < SureVertical[0].length; j++) {
                if(SureVertical[i][j]!=-1) sum++;
            }
        }
        return sum;
    }

    public int sumSureHorizontal(){
        int sum=0;
        for (int i = 0; i < SureHorizontal.length; i++) {
            for (int j = 0; j < SureHorizontal[0].length; j++) {
                if(SureHorizontal[i][j]!=-1) sum++;
            }
        }
        return sum;
    }

    @Override
    public int hashCode() {
        int result = Arrays.hashCode(getVerticalLineSolver());
        result = 31 * result + Arrays.hashCode(getHorizontalLineSolver());
        result = 31 * result + Arrays.hashCode(getNodeSolver());
        return result;
    }
}
