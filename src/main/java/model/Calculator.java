package model;

import ilog.concert.IloException;
import ilog.concert.IloIntVar;
import ilog.concert.IloRange;
import ilog.cplex.IloCplex;
import model.Map;

import java.util.ArrayList;
import java.util.List;

public class Calculator {
    int counter=0;
    Map M;
    public Calculator(Map M){
        this.M=M;

    }



    public void printSolution(ArrayList<Coordinates> coordinates, IloIntVar[][] VerticalLine, IloIntVar[][] HorizontalLine, IloIntVar[][] Node, IloCplex cplex) throws IloException {
        if(!coordinates.isEmpty()) System.out.println(coordinates.get(0).getX()+" "+coordinates.get(0).getY()+" "+coordinates.get(0).getValue());
        if (cplex.solve()) {
            counter++;
            boolean f = isConnected(Node, VerticalLine, HorizontalLine, cplex);
            System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            System.out.println("Connectivity: "+f);
            System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            if(f) return;
            if(coordinates.size()<=0){
                for (int i = coordinates.size()==0 ? 0 : coordinates.get(coordinates.size()-1).getX(); i < VerticalLine.length; i++) {
                    for (int j = coordinates.size()==0 ? 0 : coordinates.get(coordinates.size()-1).getY(); j < VerticalLine[0].length; j++) {
                        ArrayList<Coordinates> a = new ArrayList<>(coordinates);
                        a.add(new Coordinates(i,j,1-(int)cplex.getValue(VerticalLine[i][j])));
         //                solveMe(a);
                    }
                }
            }
        }
        else{
            System.out.println("Model not solved");
            //      cplex.exportModel("//home//idabrows//Documents//SlitherLink//logs.lp");
        }
        System.out.println(counter);

    }


    public void makeBool(IloIntVar[][] VerticalLine, IloIntVar[][] HorizontalLine, IloIntVar[][] Node, IloCplex cplex) throws IloException {
        for (int i = 0; i < VerticalLine.length; i++)
            for (int j = 0; j < VerticalLine[0].length; j++)
                VerticalLine[i][j] = cplex.boolVar();
        for (int i = 0; i < HorizontalLine.length; i++)
            for (int j = 0; j < HorizontalLine[0].length; j++)
                HorizontalLine[i][j] = cplex.boolVar();
        for (int i = 0; i < Node.length; i++)
            for (int j = 0; j < Node[0].length; j++)
                Node[i][j] = cplex.boolVar();
    }


    public void addPrimaryConstraints(IloIntVar[][] VerticalLine, IloIntVar[][] HorizontalLine, IloIntVar[][] Node, IloCplex cplex, List<IloRange> constraints) throws IloException {
        for (int i = 0; i < VerticalLine.length; i++)
            for (int j = 0; j < VerticalLine[0].length; j++)
                constraints.add(
                        cplex.addGe(VerticalLine[i][j], 0));

        for (int i = 0; i < HorizontalLine.length; i++)
            for (int j = 0; j < HorizontalLine[0].length; j++)
                constraints.add(
                        cplex.addGe(HorizontalLine[i][j], 0));

        for (int i = 0; i < Node.length; i++)
            for (int j = 0; j < Node[0].length; j++)
                constraints.add(
                        cplex.addGe(Node[i][j], 0));
    }


    public void addGameConstraints(IloIntVar[][] VerticalLine, IloIntVar[][] HorizontalLine, IloCplex cplex, List<IloRange> constraints) throws IloException {
        for (int i = 0; i < M.getRows(); i++)
            for (int j = 0; j < M.getCols(); j++)
                if (M.getCoefficients()[i][j] != -1)
                    constraints.add(
                            cplex.addEq(cplex.sum(VerticalLine[i][j], VerticalLine[i][j + 1], HorizontalLine[i][j], HorizontalLine[i + 1][j]),
                                    M.getCoefficients()[i][j]));
    }



    public void addCycleConstraints(IloIntVar[][] VerticalLine,IloIntVar[][] HorizontalLine,IloIntVar[][] Node,IloCplex cplex,List<IloRange> constraints) throws IloException {
        //i=0
        //i=0, j=0
        constraints.add(
                cplex.addEq(cplex.diff(cplex.sum(VerticalLine[0][0], HorizontalLine[0][0]), cplex.prod(2, Node[0][0])),
                        0));
        //i=0,j=1...ncols-2
        for (int j = 1; j < Node[0].length - 1; j++)
            constraints.add(
                    cplex.addEq(cplex.diff(cplex.sum(HorizontalLine[0][j - 1], VerticalLine[0][j], HorizontalLine[0][j]), cplex.prod(2, Node[0][j])),
                            0));
        //i-0,j=ncols-1
        constraints.add(
                cplex.addEq(cplex.diff(cplex.sum(HorizontalLine[0][Node[0].length - 2], VerticalLine[0][Node[0].length - 1]), cplex.prod(2, Node[0][Node[0].length - 1])),
                        0));

        //i=1..nrows-2
        //i=1.. nrows-2, j=0
        for (int i = 1; i < Node.length - 1; i++) {
            constraints.add(
                    cplex.addEq(cplex.diff(cplex.sum(VerticalLine[i - 1][0], HorizontalLine[i][0], VerticalLine[i][0]), cplex.prod(2, Node[i][0])),
                            0));
            //i=1.. nrows-2, j=1..ncols-2
            for (int j = 1; j < Node[0].length - 1; j++)
                constraints.add(
                        cplex.addEq(cplex.diff(cplex.sum(VerticalLine[i - 1][j], HorizontalLine[i][j - 1], VerticalLine[i][j], HorizontalLine[i][j]), cplex.prod(2, Node[i][j])),
                                0));
            //i=1...nrows-2, j=ncols-1
            constraints.add(
                    cplex.addEq(cplex.diff(cplex.sum(VerticalLine[i - 1][Node[0].length - 1], HorizontalLine[i][Node[0].length - 2], VerticalLine[i][Node[0].length - 1]), cplex.prod(2, Node[i][Node[0].length - 1])),
                            0));
        }
        //i=nrows-1
        //j=0
        constraints.add(
                cplex.addEq(cplex.diff(cplex.sum(VerticalLine[Node.length - 2][0], HorizontalLine[Node.length - 1][0]), cplex.prod(2, Node[Node.length - 1][0])),
                        0));
        //j=1...ncols-2
        for (int j = 1; j < Node[0].length - 1; j++)
            constraints.add(
                    cplex.addEq(cplex.diff(cplex.sum(VerticalLine[Node.length - 2][j], HorizontalLine[Node.length - 1][j - 1], HorizontalLine[Node.length - 1][j]), cplex.prod(2, Node[Node.length - 1][j])),
                            0));
        //j=ncols-1
        constraints.add(
                cplex.addEq(cplex.diff(cplex.sum(VerticalLine[Node.length - 2][Node[0].length - 1], HorizontalLine[Node.length - 1][Node[0].length - 2]), cplex.prod(2, Node[Node.length - 1][Node[0].length - 1])),
                        0));

    }


    public boolean isConnected(IloIntVar[][] Node,IloIntVar[][] V,IloIntVar[][] H,IloCplex cplex) throws IloException {
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

        if (i>0 && Visited[i-1][j]==false && cplex.getValue(V[i-1][j])==1) hasUnvisited=true;
        if(j>0 && Visited[i][j-1]==false && cplex.getValue(H[i][j-1])==1) hasUnvisited=true;
        if(i<Visited.length-1 && Visited[i+1][j]==false && cplex.getValue(V[i][j])==1) hasUnvisited=true;
        if(j<Visited[0].length-1 && Visited[i][j+1]==false && cplex.getValue(H[i][j])==1) hasUnvisited=true;

        if(!hasUnvisited) return counter;

        if (i>0 && Visited[i-1][j]==false && cplex.getValue(V[i-1][j])==1)
                return myDFS(Node,V,H,cplex,Visited,i-1,j,counter+1);
        if(j>0 && Visited[i][j-1]==false && cplex.getValue(H[i][j-1])==1)
                return myDFS(Node,V,H,cplex,Visited,i,j-1,counter+1);
        if(i<Visited.length-1 && Visited[i+1][j]==false && cplex.getValue(V[i][j])==1)
                return myDFS(Node,V,H,cplex,Visited,i+1,j,counter+1);
        if(j<Visited[0].length-1 && Visited[i][j+1]==false && cplex.getValue(H[i][j])==1)
                return myDFS(Node,V,H,cplex,Visited,i,j+1,counter+1);

        return -1;
    }


}