package model;

import ilog.concert.IloException;
import ilog.concert.IloIntVar;
import ilog.concert.IloLinearIntExpr;
import ilog.concert.IloRange;
import ilog.cplex.IloCplex;
import model.Map;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class Calculator {
    int counter;
    Map M;
    ArrayList<HashSet<IloIntVar>> Partition = new ArrayList<>();


    public Calculator(Map M){
        this.M=M;
    }



    public void printSolution(GameVariables gameVariables, IloCplex cplex) throws IloException {
        if (cplex.solve()) {
            counter++;
            boolean f = isConnected(gameVariables,cplex);
            System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            System.out.println("Connectivity: "+f);
            System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            if(f) return;

        }
        else{
            System.out.println("Model not solved");
            //      cplex.exportModel("//home//idabrows//Documents//SlitherLink//logs.lp");
        }

    }


    public void makeBool(GameVariables gameVariables, IloCplex cplex) throws IloException {
        for (int i = 0; i < gameVariables.getVerticalLineSolver().length; i++)
            for (int j = 0; j < gameVariables.getVerticalLineSolver()[0].length; j++)
                gameVariables.setVerticalLineSolver(cplex.boolVar(),i,j);
        for (int i = 0; i < gameVariables.getHorizontalLineSolver().length; i++)
            for (int j = 0; j < gameVariables.getHorizontalLineSolver()[0].length; j++)
                gameVariables.setHorizontalLineSolver(cplex.boolVar(),i,j);
        for (int i = 0; i < gameVariables.getNodeSolver().length; i++)
            for (int j = 0; j < gameVariables.getNodeSolver()[0].length; j++)
                gameVariables.setNodeSolver(cplex.boolVar(),i,j);
    }


    public void addPrimaryConstraints(GameVariables gameVariables, IloCplex cplex, List<IloRange> constraints) throws IloException {
        for (int i = 0; i < gameVariables.getVerticalLineSolver().length; i++)
            for (int j = 0; j < gameVariables.getVerticalLineSolver()[0].length; j++)
                constraints.add(
                        cplex.addGe(gameVariables.getVerticalLineSolver()[i][j], 0));

        for (int i = 0; i < gameVariables.getHorizontalLineSolver().length; i++)
            for (int j = 0; j < gameVariables.getHorizontalLineSolver()[0].length; j++)
                constraints.add(
                        cplex.addGe(gameVariables.getHorizontalLineSolver()[i][j], 0));

        for (int i = 0; i < gameVariables.getNodeSolver().length; i++)
            for (int j = 0; j < gameVariables.getNodeSolver()[0].length; j++)
                constraints.add(
                        cplex.addGe(gameVariables.getNodeSolver()[i][j], 0));
    }


    public void addGameConstraints(GameVariables gameVariables, IloCplex cplex, List<IloRange> constraints) throws IloException {
        for (int i = 0; i < M.getRows(); i++)
            for (int j = 0; j < M.getCols(); j++)
                if (M.getCoefficients()[i][j] != -1)
                    constraints.add(
                            cplex.addEq(cplex.sum(gameVariables.getVerticalLineSolver()[i][j], gameVariables.getVerticalLineSolver()[i][j + 1], gameVariables.getHorizontalLineSolver()[i][j], gameVariables.getHorizontalLineSolver()[i + 1][j]),
                                    M.getCoefficients()[i][j]));

    }


    public void notEmpty(GameVariables gameVariables,IloCplex cplex,List<IloRange> constraints) throws IloException {
        IloLinearIntExpr sum = cplex.linearIntExpr();
        for (int i = 0; i < M.getRows(); i++) {
            for (int j = 0; j < M.getCols()+1; j++) {
                sum.addTerm(1,gameVariables.getVerticalLineSolver()[i][j]);
            }
        }
        constraints.add(cplex.addGe(sum,1));
    }

    public void addCycleConstraints(GameVariables gameVariables,IloCplex cplex,List<IloRange> constraints) throws IloException {
        //i=0
        //i=0, j=0
        constraints.add(
                cplex.addEq(cplex.diff(cplex.sum(gameVariables.getVerticalLineSolver()[0][0], gameVariables.getHorizontalLineSolver()[0][0]), cplex.prod(2, gameVariables.getNodeSolver()[0][0])),
                        0));
        //i=0,j=1...ncols-2
        for (int j = 1; j < gameVariables.getNodeSolver()[0].length - 1; j++)
             constraints.add(
                    cplex.addEq(cplex.diff(cplex.sum(gameVariables.getHorizontalLineSolver()[0][j - 1], gameVariables.getVerticalLineSolver()[0][j], gameVariables.getHorizontalLineSolver()[0][j]), cplex.prod(2, gameVariables.getNodeSolver()[0][j])),
                            0));
        //i-0,j=ncols-1
        constraints.add(
                cplex.addEq(cplex.diff(cplex.sum(gameVariables.getHorizontalLineSolver()[0][gameVariables.getNodeSolver()[0].length - 2], gameVariables.getNodeSolver()[0][gameVariables.getNodeSolver()[0].length - 1]), cplex.prod(2, gameVariables.getNodeSolver()[0][gameVariables.getNodeSolver()[0].length - 1])),
                        0));

        //i=1..nrows-2
        //i=1.. nrows-2, j=0
        for (int i = 1; i < gameVariables.getNodeSolver().length - 1; i++) {
            constraints.add(
                    cplex.addEq(cplex.diff(cplex.sum(gameVariables.getVerticalLineSolver()[i - 1][0], gameVariables.getHorizontalLineSolver()[i][0], gameVariables.getVerticalLineSolver()[i][0]), cplex.prod(2, gameVariables.getNodeSolver()[i][0])),
                            0));
            //i=1.. nrows-2, j=1..ncols-2
            for (int j = 1; j < gameVariables.getNodeSolver()[0].length - 1; j++)
                constraints.add(
                        cplex.addEq(cplex.diff(cplex.sum(gameVariables.getVerticalLineSolver()[i - 1][j], gameVariables.getHorizontalLineSolver()[i][j - 1], gameVariables.getVerticalLineSolver()[i][j], gameVariables.getHorizontalLineSolver()[i][j]), cplex.prod(2, gameVariables.getNodeSolver()[i][j])),
                                0));
            //i=1...nrows-2, j=ncols-1
            constraints.add(
                    cplex.addEq(cplex.diff(cplex.sum(gameVariables.getVerticalLineSolver()[i - 1][gameVariables.getNodeSolver()[0].length - 1], gameVariables.getHorizontalLineSolver()[i][gameVariables.getNodeSolver()[0].length - 2], gameVariables.getVerticalLineSolver()[i][gameVariables.getNodeSolver()[0].length - 1]), cplex.prod(2, gameVariables.getNodeSolver()[i][gameVariables.getNodeSolver()[0].length - 1])),
                            0));
        }
        //i=nrows-1
        //j=0
        constraints.add(
                cplex.addEq(cplex.diff(cplex.sum(gameVariables.getVerticalLineSolver()[gameVariables.getNodeSolver().length - 2][0], gameVariables.getHorizontalLineSolver()[gameVariables.getNodeSolver().length - 1][0]), cplex.prod(2, gameVariables.getNodeSolver()[gameVariables.getNodeSolver().length - 1][0])),
                        0));
        //j=1...ncols-2
        for (int j = 1; j < gameVariables.getNodeSolver()[0].length - 1; j++)
            constraints.add(
                    cplex.addEq(cplex.diff(cplex.sum(gameVariables.getVerticalLineSolver()[gameVariables.getNodeSolver().length - 2][j], gameVariables.getHorizontalLineSolver()[gameVariables.getNodeSolver().length - 1][j - 1], gameVariables.getHorizontalLineSolver()[gameVariables.getNodeSolver().length - 1][j]), cplex.prod(2, gameVariables.getNodeSolver()[gameVariables.getNodeSolver().length - 1][j])),
                            0));
        //j=ncols-1
        constraints.add(
                cplex.addEq(cplex.diff(cplex.sum(gameVariables.getVerticalLineSolver()[gameVariables.getNodeSolver().length - 2][gameVariables.getNodeSolver()[0].length - 1], gameVariables.getHorizontalLineSolver()[gameVariables.getNodeSolver().length - 1][gameVariables.getNodeSolver()[0].length - 2]), cplex.prod(2, gameVariables.getNodeSolver()[gameVariables.getNodeSolver().length - 1][gameVariables.getNodeSolver()[0].length - 1])),
                        0));

    }


    public boolean isConnected(GameVariables gameVariables,IloCplex cplex) throws IloException {
        int i; int j;
        //creating an array with visited vertices, if are not in the subgraph, visited = true
        boolean[][] Visited = new boolean[gameVariables.getNodeSolver().length][gameVariables.getNodeSolver()[0].length];
        for(i=0;i<Visited.length;i++)
            for(j=0;j<Visited[0].length;j++) {
                if (cplex.getValue(gameVariables.getNodeSolver()[i][j]) == 0) Visited[i][j] = true;
                else {
                    Visited[i][j] = false;
                }
            }

        //looking for a vertex from the subgraph
        i=0; j=0;
        while(Visited[i][j]==true){
            if(j==Visited[0].length-1){
                j=0; i++;
            }
            else j++;
        }

        int c = myDFS(gameVariables,cplex,Visited,i,j,1); //the first vertex has to be counted, too
        int subGraphSize=0;

        for(i=0;i<gameVariables.getNodeSolver().length;i++)
            for (j=0;j<gameVariables.getNodeSolver()[0].length;j++)
                if(cplex.getValue(gameVariables.getNodeSolver()[i][j])==1) subGraphSize++;
        return subGraphSize==c;
    }

    public void divide(GameVariables gameVariables,IloCplex cplex) throws IloException {
        int i; int j;
        //creating an array with visited vertices, if are not in the subgraph visited = true
        boolean[][] Visited = new boolean[gameVariables.getNodeSolver().length][gameVariables.getNodeSolver()[0].length];
        for(i=0;i<Visited.length;i++)
            for(j=0;j<Visited[0].length;j++) {
                if (cplex.getValue(gameVariables.getNodeSolver()[i][j]) == 0) Visited[i][j] = true;
                else Visited[i][j] = false;
            }

        //looking for a vertex from the subgraph
        for (int k = 0; k < gameVariables.getNodeSolver().length; k++) {
            for (int l = 0; l < gameVariables.getNodeSolver()[0].length; l++) {
                if(Visited[k][l]==false){
                    Partition.add(new HashSet<IloIntVar>());
                    myDFS(gameVariables,cplex,Visited,k,l);

                }
            }
        }



    }



    private void myDFS(GameVariables gameVariables,IloCplex cplex,boolean[][] Visited,int i,int j) throws IloException {
        Visited[i][j]=true;
         if(i<gameVariables.getVerticalLineSolver().length &&
                j<gameVariables.getVerticalLineSolver()[0].length &&
                    cplex.getValue(gameVariables.getVerticalLineSolver()[i][j])==1)
                       Partition.get(Partition.size()-1).add(gameVariables.getVerticalLineSolver()[i][j]);
        if(i>=1 && i<gameVariables.getVerticalLineSolver().length+1 &&
                j<gameVariables.getVerticalLineSolver()[0].length &&
                    cplex.getValue(gameVariables.getVerticalLineSolver()[i-1][j])==1)
                       Partition.get(Partition.size()-1).add(gameVariables.getVerticalLineSolver()[i-1][j]);
        if(i<gameVariables.getHorizontalLineSolver().length &&
                j<gameVariables.getHorizontalLineSolver()[0].length &&
                cplex.getValue(gameVariables.getHorizontalLineSolver()[i][j])==1)
                    Partition.get(Partition.size()-1).add(gameVariables.getHorizontalLineSolver()[i][j]);
        if(j>=1 && i<gameVariables.getHorizontalLineSolver().length &&
                j<gameVariables.getHorizontalLineSolver()[0].length+1 &&
                cplex.getValue(gameVariables.getHorizontalLineSolver()[i][j-1])==1)
                    Partition.get(Partition.size()-1).add(gameVariables.getHorizontalLineSolver()[i][j-1]);
        boolean hasUnvisited = false;

        if (i>0 && Visited[i-1][j]==false && cplex.getValue(gameVariables.getVerticalLineSolver()[i-1][j])==1) hasUnvisited=true;
        if(j>0 && Visited[i][j-1]==false && cplex.getValue(gameVariables.getHorizontalLineSolver()[i][j-1])==1) hasUnvisited=true;
        if(i<Visited.length-1 && Visited[i+1][j]==false && cplex.getValue(gameVariables.getVerticalLineSolver()[i][j])==1) hasUnvisited=true;
        if(j<Visited[0].length-1 && Visited[i][j+1]==false && cplex.getValue(gameVariables.getHorizontalLineSolver()[i][j])==1) hasUnvisited=true;

        if(!hasUnvisited) return;

        if (i>0 && Visited[i-1][j]==false && cplex.getValue(gameVariables.getVerticalLineSolver()[i-1][j])==1)
            myDFS(gameVariables,cplex,Visited,i-1,j);
        if(j>0 && Visited[i][j-1]==false && cplex.getValue(gameVariables.getHorizontalLineSolver()[i][j-1])==1)
            myDFS(gameVariables,cplex,Visited,i,j-1);
        if(i<Visited.length-1 && Visited[i+1][j]==false && cplex.getValue(gameVariables.getVerticalLineSolver()[i][j])==1)
            myDFS(gameVariables,cplex,Visited,i+1,j);
        if(j<Visited[0].length-1 && Visited[i][j+1]==false && cplex.getValue(gameVariables.getHorizontalLineSolver()[i][j])==1)
            myDFS(gameVariables,cplex,Visited,i,j+1);

    }


    //myDFS goes through the connected subgraph and counts the number of vertices
    private int myDFS(GameVariables gameVariables,IloCplex cplex,boolean[][] Visited,int i,int j,int counter) throws IloException {
        Visited[i][j]=true;
        boolean hasUnvisited = false;

        if (i>0 && Visited[i-1][j]==false && cplex.getValue(gameVariables.getVerticalLineSolver()[i-1][j])==1) hasUnvisited=true;
        if(j>0 && Visited[i][j-1]==false && cplex.getValue(gameVariables.getHorizontalLineSolver()[i][j-1])==1) hasUnvisited=true;
        if(i<Visited.length-1 && Visited[i+1][j]==false && cplex.getValue(gameVariables.getVerticalLineSolver()[i][j])==1) hasUnvisited=true;
        if(j<Visited[0].length-1 && Visited[i][j+1]==false && cplex.getValue(gameVariables.getHorizontalLineSolver()[i][j])==1) hasUnvisited=true;

        if(!hasUnvisited) return counter;

        if (i>0 && Visited[i-1][j]==false && cplex.getValue(gameVariables.getVerticalLineSolver()[i-1][j])==1)
                return myDFS(gameVariables,cplex,Visited,i-1,j,counter+1);
        if(j>0 && Visited[i][j-1]==false && cplex.getValue(gameVariables.getHorizontalLineSolver()[i][j-1])==1)
                return myDFS(gameVariables,cplex,Visited,i,j-1,counter+1);
        if(i<Visited.length-1 && Visited[i+1][j]==false && cplex.getValue(gameVariables.getVerticalLineSolver()[i][j])==1)
                return myDFS(gameVariables,cplex,Visited,i+1,j,counter+1);
        if(j<Visited[0].length-1 && Visited[i][j+1]==false && cplex.getValue(gameVariables.getHorizontalLineSolver()[i][j])==1)
                return myDFS(gameVariables,cplex,Visited,i,j+1,counter+1);

        return -1;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public Map getM() {
        return M;
    }

    public void setM(Map m) {
        M = m;
    }

    public ArrayList<HashSet<IloIntVar>> getPartition() {
        return Partition;
    }

    public void setPartition(ArrayList<HashSet<IloIntVar>> partition) {
        Partition = partition;
    }

    public void printSet(HashSet<IloIntVar> hashSet,IloCplex cplex,GameVariables gameVariables) throws IloException {
        ArrayList<Coordinates> vertical = new ArrayList<>();
        ArrayList<Coordinates> horizontal = new ArrayList<>();
        for (int i = 0; i < gameVariables.getVerticalLineSolver().length; i++) {
            for (int j = 0; j < gameVariables.getVerticalLineSolver()[0].length; j++) {
                if (hashSet.contains(gameVariables.getVerticalLineSolver()[i][j])){
                    vertical.add(new Coordinates(i, j, 0));
                }
            }
        }
        for (int i = 0; i < gameVariables.getHorizontalLineSolver().length; i++) {
            for (int j = 0; j < gameVariables.getHorizontalLineSolver()[0].length; j++) {
                if (hashSet.contains(gameVariables.getHorizontalLineSolver()[i][j]))
                    horizontal.add(new Coordinates(i,j,0));
            }
        }

        System.out.println("Vertical: ");
        for (int i = 0; i < vertical.size(); i++) System.out.print("("+vertical.get(i).getX()+","+vertical.get(i).getY()+") ");
        System.out.println();
        System.out.println("Horizontal: ");
        for (int i = 0; i < horizontal.size(); i++) System.out.print("("+horizontal.get(i).getX()+","+horizontal.get(i).getY()+") ");
        System.out.println("\n");
    }


}