package model;
import java.io.*;

public class Map {
    int rows=100;
    int cols=100;
    int coefficients[][]=new int[rows][cols];


    public void readMapFromFile(File file) throws IOException {
//        File file = new File("//home//idabrows//Documents//SlitherLink//Coefficients");
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
            for(int j=0;j<cols;j++)
            s[i][j]= String.valueOf(coefficients[i][j]);
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

}
