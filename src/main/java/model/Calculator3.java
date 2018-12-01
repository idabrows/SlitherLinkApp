package model;

import ilog.concert.*;
import ilog.cplex.IloCplex;

import java.util.ArrayList;
import java.util.List;

public class Calculator3 {
    public static void solveMe() {
        try {
            IloCplex cplex = new IloCplex();

            // variables
            IloIntVar x = cplex.boolVar();
            IloIntVar y = cplex.boolVar();
            IloIntVar[][] A = new IloIntVar[2][2];
            IloIntVar[] B = new IloIntVar[2];
            for(int i=0;i<2;i++)
                for(int j=0;j<2;j++)
                    A[i][j]=cplex.boolVar();
            for(int i=0;i<2;i++)
                B[i]=cplex.boolVar();

            // define constraints
            List<IloRange> constraints = new ArrayList<IloRange>();
            constraints.add(cplex.addEq(cplex.sum(A[0][0], A[0][1],A[1][0],A[1][1],B[0],B[1]), 5));


            // solve
            if (cplex.solve()) {
                System.out.println(cplex.getValue(A[0][0]));
                System.out.println(cplex.getValue(A[0][1]));
                System.out.println(cplex.getValue(A[1][0]));
                System.out.println(cplex.getValue(A[1][1]));
                System.out.println(cplex.getValue(B[0]));
                System.out.println(cplex.getValue(B[1]));
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
}
