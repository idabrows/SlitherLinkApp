package model;
import ilog.concert.*;
import ilog.cplex.*;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;


public class Calculator {
    Map M;

    public Calculator(Map M){
        this.M=M;
    }


    public void solveMe() {
        try {
            IloCplex cplex = new IloCplex();

            // variables
            IloIntVar X[][]=loadVariables(cplex);

            // expressions
      //      IloLinearNumExpr objective = cplex.linearNumExpr();
          //  objective.addTerm(0.12, X[0][0]);
            //objective.addTerm(0.15, X[1]);

            // define objective
     //       cplex.addMaximize(objective);

            // define constraints
            List<IloRange> constraints = new ArrayList<IloRange>();
            loadGameConstraints(cplex, X, constraints);

            // solve
            if (cplex.solve()) {
                System.out.println("obj = "+cplex.getObjValue());
                System.out.println("x1   = "+cplex.getValue(X[0][0]));
                System.out.println("x2   = "+cplex.getValue(X[1][1]));
            }
            else {
                System.out.println("Model not solved");
            }

            cplex.end();
        }
        catch (IloException exc) {
            exc.printStackTrace();
        }
    }

    public void loadGameConstraints(IloCplex cplex,IloIntVar[][] X,List<IloRange> l) throws IloException {
        for(int i=0;i<M.rows-1;i++)
            for(int j=0;j<M.cols-1;j++)
                 l.add(cplex.addLe(cplex.sum(cplex.prod(1, X[0][0]),cplex.prod(1, X[1][1])), 3));


    }

    public IloIntVar[][] loadVariables(IloCplex IC) throws IloException {
        IloIntVar[][] X = new IloIntVar[M.rows-1][M.cols-1];
        for(int i=0;i<M.rows-1;i++)
            for(int j=0;j<M.cols-1;j++)
                X[i][j]=IC.boolVar();
        return X;
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

