package model;

import com.sun.org.apache.xerces.internal.impl.xs.SubstitutionGroupHandler;
import ilog.concert.*;
import ilog.concert.IloIntVar.*;
import ilog.cplex.IloCplex;

import javax.security.auth.Subject;
import java.awt.*;
import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Calculator {
    Map M;
    ArrayList<Solution> solutions = new ArrayList<>();
    public Calculator(Map M) {
        this.M = M;
    }


    public void solveMe() throws IloException {
            int vrows = M.getRows();
            int vcols = M.getCols() + 1;
            int hrows = M.getRows() + 1;
            int hcols = M.getCols();
            int nrows = M.getRows() + 1;
            int ncols = M.getCols() + 1;

            IloCplex cplex = new IloCplex();
            // variables
            IloIntVar[][] VerticalLine = new IloIntVar[vrows][vcols];
            IloIntVar[][] HorizontalLine = new IloIntVar[hrows][hcols];
            IloIntVar[][] Node = new IloIntVar[nrows][ncols];

            for (int i = 0; i < vrows; i++)
                for (int j = 0; j < vcols; j++)
                    VerticalLine[i][j] = cplex.boolVar();
            for (int i = 0; i < hrows; i++)
                for (int j = 0; j < hcols; j++)
                    HorizontalLine[i][j] = cplex.boolVar();
            for (int i = 0; i < nrows; i++)
                for (int j = 0; j < ncols; j++)
                    Node[i][j] = cplex.boolVar();


            List<IloRange> constraints = new ArrayList<IloRange>();

            //>=0
            for (int i = 0; i < vrows; i++)
                for (int j = 0; j < vcols; j++)
                    constraints.add(
                            cplex.addGe(VerticalLine[i][j], 0));

            for (int i = 0; i < hrows; i++)
                for (int j = 0; j < hcols; j++)
                    constraints.add(
                            cplex.addGe(HorizontalLine[i][j], 0));

            for (int i = 0; i < nrows; i++)
                for (int j = 0; j < ncols; j++)
                    constraints.add(
                            cplex.addGe(Node[i][j], 0));

            //load game constraints
            for (int i = 0; i < M.getRows(); i++)
                for (int j = 0; j < M.getCols(); j++)
                    if (M.getCoefficients()[i][j] != -1)
                        constraints.add(
                                cplex.addEq(cplex.sum(VerticalLine[i][j], VerticalLine[i][j + 1], HorizontalLine[i][j], HorizontalLine[i + 1][j]),
                                        M.getCoefficients()[i][j]));

//cycle conditions
//i=0
            //i=0, j=0
            constraints.add(
                    cplex.addEq(cplex.diff(cplex.sum(VerticalLine[0][0], HorizontalLine[0][0]), cplex.prod(2, Node[0][0])),
                            0));
            //i=0,j=1...ncols-2
            for (int j = 1; j < ncols - 1; j++)
                constraints.add(
                        cplex.addEq(cplex.diff(cplex.sum(HorizontalLine[0][j - 1], VerticalLine[0][j], HorizontalLine[0][j]), cplex.prod(2, Node[0][j])),
                                0));
            //i-0,j=ncols-1
            constraints.add(
                    cplex.addEq(cplex.diff(cplex.sum(HorizontalLine[0][ncols - 2], VerticalLine[0][ncols - 1]), cplex.prod(2, Node[0][ncols - 1])),
                            0));

//i=1..nrows-2
            //i=1.. nrows-2, j=0
            for (int i = 1; i < nrows - 1; i++) {
                constraints.add(
                        cplex.addEq(cplex.diff(cplex.sum(VerticalLine[i - 1][0], HorizontalLine[i][0], VerticalLine[i][0]), cplex.prod(2, Node[i][0])),
                                0));
                //i=1.. nrows-2, j=1..ncols-2
                for (int j = 1; j < ncols - 1; j++)
                    constraints.add(
                            cplex.addEq(cplex.diff(cplex.sum(VerticalLine[i - 1][j], HorizontalLine[i][j - 1], VerticalLine[i][j], HorizontalLine[i][j]), cplex.prod(2, Node[i][j])),
                                    0));
                //i=1...nrows-2, j=ncols-1
                constraints.add(
                        cplex.addEq(cplex.diff(cplex.sum(VerticalLine[i - 1][ncols - 1], HorizontalLine[i][ncols - 2], VerticalLine[i][ncols - 1]), cplex.prod(2, Node[i][ncols - 1])),
                                0));
            }
            //i=nrows-1
//j=0
            constraints.add(
                    cplex.addEq(cplex.diff(cplex.sum(VerticalLine[nrows - 2][0], HorizontalLine[nrows - 1][0]), cplex.prod(2, Node[nrows - 1][0])),
                            0));
//j=1...ncols-2
            for (int j = 1; j < ncols - 1; j++)
                constraints.add(
                        cplex.addEq(cplex.diff(cplex.sum(VerticalLine[nrows - 2][j], HorizontalLine[nrows - 1][j - 1], HorizontalLine[nrows - 1][j]), cplex.prod(2, Node[nrows - 1][j])),
                                0));
//j=ncols-1
            constraints.add(
                    cplex.addEq(cplex.diff(cplex.sum(VerticalLine[nrows - 2][ncols - 1], HorizontalLine[nrows - 1][ncols - 2]), cplex.prod(2, Node[nrows - 1][ncols - 1])),
                            0));

//the solution has to be connected
    if (isConnected(Node,VerticalLine,HorizontalLine,cplex)){
        printSolution(cplex,VerticalLine,HorizontalLine);
    }
    else{
        solutions.add(new Solution(cplex,VerticalLine,HorizontalLine,Node));
        solveMe();
    }




    }



    public void printSolution(IloCplex cplex, IloIntVar[][] VerticalLine, IloIntVar[][] HorizontalLine) throws IloException {
        if (cplex.solve()) {
            System.out.println("Vertical lines");
            for (int i = 0; i < VerticalLine.length; i++) {
                for (int j = 0; j < VerticalLine[0].length; j++)
                    System.out.print((int) cplex.getValue(VerticalLine[i][j]) + " ");
                System.out.println("");
            }
            System.out.println("Horizontal lines");
            for (int i = 0; i < HorizontalLine.length; i++) {
                for (int j = 0; j < HorizontalLine[0].length; j++)
                    System.out.print((int) cplex.getValue(HorizontalLine[i][j]) + " ");
                System.out.println("");
            }

        } else
            System.out.println("Model not solved");

        //end
        cplex.end();
    }










boolean isConnected(IloIntVar[][] Node,IloIntVar[][] V,IloIntVar[][] H,IloCplex cplex) throws IloException {
        int i=0; int j=0;
        //creating an array with visited vertexes, if are not in the subgraph visited = true
        boolean[][] Visited = new boolean[Node.length][Node[0].length];
        for(i=0;i<Visited.length;i++)
            for(j=0;j<Visited[0].length;j++) {
                if (cplex.getValue(Node[i][j]) == 0) Visited[i][j] = true;
                else Visited[i][j] = false;
            }

    //looking for a vertex from the subgraph
    i=0; j=0;
    while(Visited[i][j]==true){
        if(j==Node[0].length){
             j=0; i++;
        }
        else j++;
    }
    //i,j - coordinated of the vertex
    //goes through a cycle
    int c = myDFS(Node,V,H,cplex,Visited,i,j,1); //the first vertex has to be counted, too
    int subGraphSize=0;
    for(i=0;i<Node.length;i++)
        for (j=0;j<Node[0].length;j++)
            if(cplex.getValue(Node[i][j])==1) subGraphSize++;

    return subGraphSize==c;
}

//myDFS goes through the connected subgraph and counts the number of vertexes
int myDFS(IloIntVar[][] Node,IloIntVar[][] V,IloIntVar[][] H,IloCplex cplex,boolean[][] Visited,int i,int j,int counter) throws IloException {
        Visited[i][j]=true;
    boolean hasUnvisited = false;
    //checks if there are any unvisited neighbors;
    if (i>0)
        if(Visited[i-1][j]==false && cplex.getValue(V[i-1][j])==1) hasUnvisited=true;
    if(j>0)
        if(Visited[i][j-1]==false && cplex.getValue(H[i][j-1])==1) hasUnvisited=true;
    if(i<Visited.length-1)
        if (Visited[i+1][j]==false && cplex.getValue(V[i][j])==1) hasUnvisited=true;
    if(j<Visited[0].length-1)
        if (Visited[i][j+1]==false && cplex.getValue(H[i][j])==1) hasUnvisited=true;

    if(!hasUnvisited) {
        return counter;
    }

    if (i>0)
        if(Visited[i-1][j]==false && cplex.getValue(V[i-1][j])==1){
            return myDFS(Node,V,H,cplex,Visited,i-1,j,counter+1);
        }
    if(j>0)
        if(Visited[i][j-1]==false && cplex.getValue(H[i][j-1])==1){
            return myDFS(Node,V,H,cplex,Visited,i,j-1,counter+1);
        }
    if(i<Visited.length-1)
        if (Visited[i+1][j]==false && cplex.getValue(V[i][j])==1){
            return myDFS(Node,V,H,cplex,Visited,i+1,j,counter+1);
        }
    if(j<Visited[0].length-1)
        if (Visited[i][j+1]==false && cplex.getValue(H[i][j])==1){
            return myDFS(Node,V,H,cplex,Visited,i,j+1,counter+1);
        }
    return -1;
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

