package model;

import controller.MapController;
import ilog.concert.IloException;
import ilog.concert.IloIntVar;
import ilog.cplex.IloCplex;

public class GameBinaryVariables {
    int rows;
    int cols;
    int vrows;
    int vcols;
    int hrows;
    int hcols;
    private IloIntVar[][] VerticalLine;
    private IloIntVar[][] HorizontalLine;

    public GameBinaryVariables(Map M){
        this.rows=M.getRows();
        this.cols=M.getCols();
        vrows=M.getRows();
        vcols=M.getCols()+1;
        hrows=M.getRows()+1;
        hcols=M.getCols();
        VerticalLine=new IloIntVar[vrows][vcols];
        HorizontalLine=new IloIntVar[hrows][hcols];

    }

    public int getVrows() {
        return vrows;
    }

    public void setVrows(int vrows) {
        this.vrows = vrows;
    }

    public int getVcols() {
        return vcols;
    }

    public void setVcols(int vcols) {
        this.vcols = vcols;
    }

    public int getHrows() {
        return hrows;
    }

    public void setHrows(int hrows) {
        this.hrows = hrows;
    }

    public int getHcols() {
        return hcols;
    }

    public void setHcols(int hcols) {
        this.hcols = hcols;
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

    public IloIntVar[][] getVerticalLine() {
        return VerticalLine;
    }

    public IloIntVar getVerticalLine(int i,int j) {
        return VerticalLine[i][j];
    }

    public void setVerticalLine(IloIntVar[][] verticalLine) {
        VerticalLine = verticalLine;
    }

    public void setVerticalLine(IloIntVar verticalLine,int i,int j) {
        VerticalLine[i][j] = verticalLine;
    }


    public IloIntVar[][] getHorizontalLine() {
        return HorizontalLine;
    }

    public IloIntVar getHorizontalLine(int i,int j) {
        return HorizontalLine[i][j];
    }


    public void setHorizontalLine(IloIntVar[][] horizontalLine) {
        HorizontalLine = horizontalLine;
    }

    public void setHorizontalLine(IloIntVar horizontalLine,int i,int j) {
        HorizontalLine[i][j] = horizontalLine;
    }


}
