package model;
import java.io.*;

public class Map {
    private int rows=100;
    private int cols=100;
    private int coefficients[][]=new int[rows][cols];


    public void readMapFromFile(File file) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(file));
        String st;
        int i = 0;
        String[] pom=null;
        while ((st = br.readLine()) != null) {
            pom = st.split(" ");
            for (int j = 0; j < pom.length; j++)
                coefficients[i][j] = Integer.parseInt(pom[j]);
            i++;
        }
        cols=pom.length;
        rows=i;

    }


    public void print(){
        for(int i=0;i<rows;i++){
            for (int j = 0; j < cols; j++)
                System.out.print(coefficients[i][j] + " ");
            System.out.println("");
        }
    }

    public String[] stringCols(){
        String[] s = new String[cols];
        for(int i=0;i<cols;i++)
            s[i]="col"+i;
        return s;
    }

    public String[][] stringCoefficients(){
        String[][] s = new String[rows][cols];
        for(int i=0;i<rows;i++)
            for(int j=0;j<cols;j++){
               String s1=String.valueOf(coefficients[i][j]);
               s[i][j] = s1.equals("-1") ? "" : s1;
            }
        return s;
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

    public int[][] getCoefficients() {
        return coefficients;
    }

    public void setCoefficients(int[][] coefficients) {
        this.coefficients = coefficients;
    }

    public void setCoefficient(int coefficient,int i,int j) {
        this.coefficients[i][j] = coefficient;
    }

    public void setCoefficients(String[][] coefficients){
        rows=coefficients.length;
        cols=coefficients[0].length;
        for (int i = 0; i < coefficients.length; i++) {
            for (int j = 0; j < coefficients[0].length; j++) {
                if (coefficients[i][j].isEmpty()) setCoefficient(-1,i,j);
                else setCoefficient(Integer.parseInt(coefficients[i][j]),i,j);
            }
        }
    }
}
