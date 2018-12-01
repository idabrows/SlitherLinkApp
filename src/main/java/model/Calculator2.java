package model;

import ilog.concert.*;
import ilog.concert.IloIntVar.*;
import ilog.cplex.IloCplex;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Calculator2 {
    Map M;

    public Calculator2(Map M){
        this.M=M;
    }


    public void solveMe() {
        try {
            int vrows=M.getRows();
            int vcols=M.getCols()+1;
            int hrows=M.getRows()+1;
            int hcols=M.getCols();
            int nrows=M.getRows()+1;
            int ncols=M.getCols()+1;

            IloCplex cplex = new IloCplex();
            // variables
            IloIntVar[][] VerticalLine=new IloIntVar[vrows][vcols];
            IloIntVar[][] HorizontalLine=new IloIntVar[hrows][hcols];
            IloIntVar[][] Node=new IloIntVar[nrows][ncols];

            for(int i=0;i<vrows;i++)
                for(int j=0;j<vcols;j++)
                    VerticalLine[i][j]=cplex.boolVar();
            for(int i=0;i<hrows;i++)
                for(int j=0;j<hcols;j++)
                    HorizontalLine[i][j]=cplex.boolVar();
            for(int i=0;i<ncols;i++)
                for(int j=0;j<ncols;j++)
                    Node[i][j]=cplex.boolVar();


            //load game constraints
            List<IloRange> constraints = new ArrayList<IloRange>();

            for(int i=0;i<vrows;i++)
                for(int j=0;j<vcols;j++)
                        constraints.add(
                                cplex.addGe(VerticalLine[i][j],0));

            for(int i=0;i<hrows;i++)
                for(int j=0;j<hcols;j++)
                    constraints.add(
                            cplex.addGe(HorizontalLine[i][j],0));


            for(int i=0;i<M.getRows();i++)
                for(int j=0;j<M.getCols();j++)
                    if(M.getCoefficients()[i][j]!=-1)
                        constraints.add(
                                cplex.addEq(cplex.sum(VerticalLine[i][j], VerticalLine[i][j+1], HorizontalLine[i][j], HorizontalLine[i+1][j]),
                                        M.getCoefficients()[i][j]));

//cycle conditions
//i=0
            constraints.add(
                    cplex.addEq(cplex.diff(cplex.sum(VerticalLine[0][0],HorizontalLine[0][0]),cplex.prod(2,Node[0][0])),
                            0));


                for(int j=1;j<ncols-1;j++)
                    constraints.add(
                            cplex.addEq(cplex.diff(cplex.sum(HorizontalLine[0][j-1],VerticalLine[0][j],HorizontalLine[0][j]),cplex.prod(2,Node[0][j])),
                                    0));
            constraints.add(
                    cplex.addEq(cplex.diff(cplex.sum(HorizontalLine[0][ncols-2],VerticalLine[0][ncols-1]),cplex.prod(2,Node[0][ncols-1])),
                            0));


//
//            for(int i=0;i<nrows;i++)
//                for(int j=0;j<ncols;j++)
//                    constraints.add(
//                            cplex.addGe(HorizontalLine[i][j],0));
//

            //print solution
            if (cplex.solve()) {
                            System.out.println("");
            //1st row of horizontal lines
            for(int j=0;j<M.getCols();j++){
                if(cplex.getValue(HorizontalLine[0][j])==1)
                          System.out.print("_");
                else System.out.print(" ");
            }
            System.out.println("");

            for(int i=0;i<M.getRows();i++) {
                //row of vertical lines
                for (int j = 0; j < vcols; j++) {
                    if (cplex.getValue(VerticalLine[i][j]) == 1)
                        System.out.print("|");
                    else
                        System.out.print(" ");
                }
                System.out.println("");
                //row of horizontal lines
                for (int j = 0; j < hcols; j++) {
                    if (cplex.getValue(HorizontalLine[i][j]) == 1)
                        System.out.print("_");
                    else
                        System.out.print(" ");
                }
                System.out.println("");
            }
            }
            else
                System.out.println("Model not solved");

            //end
            cplex.end();
        }

        catch (IloException exc) {
            exc.printStackTrace();
        }
    }


















}


        /*public static void solveMe() {
            try {
                IloCplex cplex = new IloCplex();

                // variables
                IloNumVar x = cplex.numVar(0, Double.MAX_VALUE, "x");
                IloNumVar y = cplex.numVar(0, Double.MAX_VALUE, "y");

                // expressions
                IloLinearNumExpr objective = cplex.linearNumExpr();
                objective.addTerm(0.12, x);
                objective.addTerm(0.15, y);

                // define objective
                cplex.addMinimize(objective);

                // define constraints
                List<IloRange> constraints = new ArrayList<IloRange>();
                constraints.add(cplex.addGe(cplex.sum(cplex.prod(60, x),cplex.prod(60, y)), 300));
                constraints.add(cplex.addGe(cplex.sum(cplex.prod(12, x),cplex.prod(6, y)), 36));
                constraints.add(cplex.addGe(cplex.sum(cplex.prod(10, x),cplex.prod(30, y)), 90));
                IloLinearNumExpr num_expr = cplex.linearNumExpr();
                num_expr.addTerm(2, x);
                num_expr.addTerm(-1,y);
                constraints.add(cplex.addEq(num_expr, 0));
                num_expr = cplex.linearNumExpr();
                num_expr.addTerm(1,y);
                num_expr.addTerm(-1,x);
                constraints.add(cplex.addLe(num_expr,8));

                // display option
                cplex.setParam(IloCplex.Param.Simplex.Display, 0);

                // solve
                if (cplex.solve()) {
                    System.out.println("obj = "+cplex.getObjValue());
                    System.out.println("x   = "+cplex.getValue(x));
                    System.out.println("y   = "+cplex.getValue(y));
                    for (int i=0;i<constraints.size();i++) {
                        System.out.println("dual constraint "+(i+1)+"  = "+cplex.getDual(constraints.get(i)));
                        System.out.println("slack constraint "+(i+1)+" = "+cplex.getSlack(constraints.get(i)));
                    }
                }
                else {
                    System.out.println("Model not solved");
                }

                cplex.end();
            }
            catch (IloException exc) {
                exc.printStackTrace();
            }
        }*/

