//package model;
//import ilog.concert.*;
//import ilog.cplex.*;
//
//import javax.swing.*;
//import java.util.ArrayList;
//import java.util.List;
//import ilog.concert.IloIntVar;
//import static javafx.scene.input.KeyCode.X;
//
//
//public class Calculator {
//    Map M;
//
//    public Calculator(Map M){
//        this.M=M;
//    }
//
//
//    public void solveMe() {
//        try {
//            GameBinaryVariables gameBinaryVariables = new GameBinaryVariables(M);
//            IloCplex cplex = new IloCplex();
//            List<IloRange> constraints = new ArrayList<IloRange>();
//            // variables
//            for(int i=0;i<gameBinaryVariables.getVrows();i++)
//                for(int j=0;j<gameBinaryVariables.getVcols();j++)
//                    gameBinaryVariables.setVerticalLine(cplex.boolVar(),i,j);
//
//            for(int i=0;i<gameBinaryVariables.getHrows();i++)
//                for(int j=0;j<gameBinaryVariables.getHcols();j++)
//                    gameBinaryVariables.setHorizontalLine(cplex.boolVar(),i,j);
//            //load game constraints
//            for(int i=0;i<M.rows;i++)
//                for(int j=0;j<M.cols;j++)
//                    if(M.getCoefficients()[i][j]!=-1)
//                        constraints.add(
//                                cplex.addEq(cplex.sum(gameBinaryVariables.getVerticalLine(i,j),
//                                        gameBinaryVariables.getVerticalLine(i,j+1),
//                                        gameBinaryVariables.getHorizontalLine(i,j),
//                                        gameBinaryVariables.getHorizontalLine(i+1,j)),
//                                        M.getCoefficients()[i][j]));
//
//
//            IloLinearNumExpr objective = cplex.linearNumExpr();
//
//            // define objective
//            cplex.addMinimize(objective);
//
//
//
//
//            //print solution
//            if (cplex.solve()) {
//                System.out.print(cplex.getValue(gameBinaryVariables.getHorizontalLine(0,0)));
//                //    System.out.println("obj = "+cplex.getObjValue());
////            System.out.println("");
////            //1st row of horizontal lines
////            for(int j=0;j<cols;j++){
////                if(cplex.getValue(gameBinaryVariables.getHorizontalLine(0,j))==1)
////                          System.out.print("_");
////                else System.out.print(" ");
////            }
////            System.out.println("");
////
////            for(int i=0;i<rows;i++) {
////                //row of vertical lines
////                for (int j = 0; j < gameBinaryVariables.getVcols(); j++) {
////                    if (cplex.getValue(gameBinaryVariables.getVerticalLine(i, j)) == 1)
////                        System.out.print("|");
////                    else
////                        System.out.print(" ");
////                }
////                System.out.println("");
////                //row of horizontal lines
////                for (int j = 0; j < gameBinaryVariables.getHcols(); j++) {
////                    if (cplex.getValue(gameBinaryVariables.getHorizontalLine(i, j)) == 1)
////                        System.out.print("_");
////                    else
////                        System.out.print(" ");
////                }
////                System.out.println("");
////            }
//            }
//            else
//                System.out.println("Model not solved");
//
//            //end
//            cplex.end();
//        }
//
//        catch (IloException exc) {
//            exc.printStackTrace();
//        }
//    }
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//}
//
//
//        /*public static void solveMe() {
//            try {
//                IloCplex cplex = new IloCplex();
//
//                // variables
//                IloNumVar x = cplex.numVar(0, Double.MAX_VALUE, "x");
//                IloNumVar y = cplex.numVar(0, Double.MAX_VALUE, "y");
//
//                // expressions
//                IloLinearNumExpr objective = cplex.linearNumExpr();
//                objective.addTerm(0.12, x);
//                objective.addTerm(0.15, y);
//
//                // define objective
//                cplex.addMinimize(objective);
//
//                // define constraints
//                List<IloRange> constraints = new ArrayList<IloRange>();
//                constraints.add(cplex.addGe(cplex.sum(cplex.prod(60, x),cplex.prod(60, y)), 300));
//                constraints.add(cplex.addGe(cplex.sum(cplex.prod(12, x),cplex.prod(6, y)), 36));
//                constraints.add(cplex.addGe(cplex.sum(cplex.prod(10, x),cplex.prod(30, y)), 90));
//                IloLinearNumExpr num_expr = cplex.linearNumExpr();
//                num_expr.addTerm(2, x);
//                num_expr.addTerm(-1,y);
//                constraints.add(cplex.addEq(num_expr, 0));
//                num_expr = cplex.linearNumExpr();
//                num_expr.addTerm(1,y);
//                num_expr.addTerm(-1,x);
//                constraints.add(cplex.addLe(num_expr,8));
//
//                // display option
//                cplex.setParam(IloCplex.Param.Simplex.Display, 0);
//
//                // solve
//                if (cplex.solve()) {
//                    System.out.println("obj = "+cplex.getObjValue());
//                    System.out.println("x   = "+cplex.getValue(x));
//                    System.out.println("y   = "+cplex.getValue(y));
//                    for (int i=0;i<constraints.size();i++) {
//                        System.out.println("dual constraint "+(i+1)+"  = "+cplex.getDual(constraints.get(i)));
//                        System.out.println("slack constraint "+(i+1)+" = "+cplex.getSlack(constraints.get(i)));
//                    }
//                }
//                else {
//                    System.out.println("Model not solved");
//                }
//
//                cplex.end();
//            }
//            catch (IloException exc) {
//                exc.printStackTrace();
//            }
//        }*/
//
