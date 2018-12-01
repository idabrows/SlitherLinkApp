package controller;

import ilog.concert.IloException;
import ilog.concert.IloIntVar;
import ilog.concert.IloRange;
import ilog.cplex.IloCplex;
import model.GameBinaryVariables;
import model.Map;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;


public class MapController{
    int rows;
    int cols;
    Map M;
    GameBinaryVariables gameBinaryVariables;
    IloCplex cplex;
    List<IloRange> constraints;
    public MapController(Map M) throws IloException {
        this.M=M;
        gameBinaryVariables = new GameBinaryVariables(M);
        constraints = new ArrayList<IloRange>();
        cplex = new IloCplex();
    }

    public void createVariables() throws IloException {
        this.rows=M.getRows();
        this.cols=M.getCols();


        for(int i=0;i<gameBinaryVariables.getVrows();i++)
            for(int j=0;j<gameBinaryVariables.getVcols();j++)
                gameBinaryVariables.setVerticalLine(cplex.boolVar(),i,j);

        //gameBinaryVariables.setHorizontalLine(new IloIntVar[rows][cols-1]);
        for(int i=0;i<gameBinaryVariables.getHrows();i++)
            for(int j=0;j<gameBinaryVariables.getHcols();j++)
                gameBinaryVariables.setHorizontalLine(cplex.boolVar(),i,j);
    }

    public void loadGameConstraints() throws IloException {
        for(int i=0;i<rows;i++)
            for(int j=0;j<cols;j++)
                if(M.getCoefficients()[i][j]!=-1)
                    constraints.add(
                            cplex.addEq(cplex.sum(gameBinaryVariables.getVerticalLine(i,j),
                            gameBinaryVariables.getVerticalLine(i,j+1),
                                    gameBinaryVariables.getHorizontalLine(i,j),
                                      gameBinaryVariables.getHorizontalLine(i+1,j)),
                              M.getCoefficients()[i][j]));

    }

    public void printSolution() throws IloException {
        if (cplex.solve()) {
            System.out.print(cplex.getValue(gameBinaryVariables.getHorizontalLine(0,0)));
        //    System.out.println("obj = "+cplex.getObjValue());
//            System.out.println("");
//            //1st row of horizontal lines
//            for(int j=0;j<cols;j++){
//                if(cplex.getValue(gameBinaryVariables.getHorizontalLine(0,j))==1)
//                          System.out.print("_");
//                else System.out.print(" ");
//            }
//            System.out.println("");
//
//            for(int i=0;i<rows;i++) {
//                //row of vertical lines
//                for (int j = 0; j < gameBinaryVariables.getVcols(); j++) {
//                    if (cplex.getValue(gameBinaryVariables.getVerticalLine(i, j)) == 1)
//                        System.out.print("|");
//                    else
//                        System.out.print(" ");
//                }
//                System.out.println("");
//                //row of horizontal lines
//                for (int j = 0; j < gameBinaryVariables.getHcols(); j++) {
//                    if (cplex.getValue(gameBinaryVariables.getHorizontalLine(i, j)) == 1)
//                        System.out.print("_");
//                    else
//                        System.out.print(" ");
//                }
//                System.out.println("");
//            }
        }
        else
            System.out.println("Model not solved");

    }

    public void end(){
        cplex.end();
    }

}