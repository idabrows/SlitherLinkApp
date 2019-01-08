package model;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class MapTest {

    @Test
    void readMapFromFile() throws IOException {
        Map map = new Map();
        map.readMapFromFile(new File("ExampleMaps//6x8VeryEasy1.txt"));
        int[][]A={
                {-1,3,2,1,2,-1},
                {-1,0,1,-1,-1,1},
                {-1,2,-1,-1,-1,3},
                {-1,-1,-1,-1,2,-1},
                {-1,2,-1,-1,-1,-1},
                {3,-1,-1,-1,2,-1},
                {3,-1,-1,2,3,-1},
                {-1,3,3,2,2,-1}
        };
        assertEquals(map.getRows(),8);
        assertEquals(map.getCols(),6);
        for (int i = 0; i < map.getRows(); i++) {
            for (int j = 0; j < map.getCols(); j++) {
                assertEquals(map.getCoefficients()[i][j],A[i][j]);
            }
        }
    }

    @Test
    void saveUsersMap() throws IOException {
        Map map1 = new Map();
         String[][]A={
                {"-1","3","2","1","2","-1"},
                {"-1","0","1","-1","-1","1"},
                {"-1","2","-1","-1","-1","3"},
                {"-1","-1","-1","-1","2","-1"},
                {"-1","2","-1","-1","-1","-1"},
                {"3","-1","-1","-1","2","-1"},
                {"3","-1","-1","2","3","-1"},
                {"-1","3","3","2","2","-1"}
        };
        int[][]B={
                {-1,3,2,1,2,-1},
                {-1,0,1,-1,-1,1},
                {-1,2,-1,-1,-1,3},
                {-1,-1,-1,-1,2,-1},
                {-1,2,-1,-1,-1,-1},
                {3,-1,-1,-1,2,-1},
                {3,-1,-1,2,3,-1},
                {-1,3,3,2,2,-1}
        };
        map1.setCoefficients(A);
        map1.saveUsersMap(new File("ExampleMaps//6x8VeryEasy1Test.txt"));
        Map map2 = new Map();
        map2.readMapFromFile(new File("ExampleMaps//6x8VeryEasy1Test.txt"));
        assertEquals(map2.getRows(),8);
        assertEquals(map2.getCols(),6);
        for (int i = 0; i < map2.getRows(); i++) {
            for (int j = 0; j < map2.getCols(); j++) {
                assertEquals(map2.getCoefficients()[i][j],B[i][j]);
            }
        }
    }

    @Test
    void stringCols() {
        Map map = new Map();
        String[][]A={
                {"-1","3","2","1","2","-1"},
                {"-1","0","1","-1","-1","1"},
                {"-1","2","-1","-1","-1","3"},
                {"-1","-1","-1","-1","2","-1"},
                {"-1","2","-1","-1","-1","-1"},
                {"3","-1","-1","-1","2","-1"},
                {"3","-1","-1","2","3","-1"},
                {"-1","3","3","2","2","-1"}
        };
        map.setCoefficients(A);
        String[] s = map.stringCols();
        for (int i = 0; i < map.getCols(); i++) {
            assertEquals(s[i],"col"+i);
        }
    }

    @Test
    void stringCoefficients() {
        Map map = new Map();
        String[][]A={
                {"44","3","2","1","2","-1"},
                {"-1","0","1","-1","-1","1"},
                {"-23","2","-1","43","-1","3"},
                {"-1","-1","55","-1","2","-1"},
                {"-1","2","-1","-1","-1","-1"},
                {"3","-1","-1","-1","2","-1"},
                {"3","-1","-1","2","3","-1"},
                {"-1","3","3","2","2","-1"}
        };
        int[][] B = {
                {-1,3,2,1,2,-1},
                {-1,0,1,-1,-1,1},
                {-1,2,-1,-1,-1,3},
                {-1,-1,-1,-1,2,-1},
                {-1,2,-1,-1,-1,-1},
                {3,-1,-1,-1,2,-1},
                {3,-1,-1,2,3,-1},
                {-1,3,3,2,2,-1}
        };
        map.setCoefficients(A);
        map.stringCoefficients();
        for (int i = 0; i < map.getRows(); i++) {
            for (int j = 0; j < map.getCols(); j++) {
                assertEquals(map.getCoefficients()[i][j],B[i][j]);
            }
        }
    }

    @Test
    void setCoefficients() {
        Map map = new Map();
        String[][]A={
                {"-1","3","2","1","2","-1"},
                {"-1","0","1","-1","-1","1"},
                {"-1","2","-1","-1","-1","3"},
                {"-1","-1","-1","-1","2","-1"},
                {"-1","2","-1","-1","-1","-1"},
                {"3","-1","-1","-1","2","-1"},
                {"3","-1","-1","2","3","-1"},
                {"-1","3","3","2","2","-1"}
        };
        int[][]B={
                {-1,3,2,1,2,-1},
                {-1,0,1,-1,-1,1},
                {-1,2,-1,-1,-1,3},
                {-1,-1,-1,-1,2,-1},
                {-1,2,-1,-1,-1,-1},
                {3,-1,-1,-1,2,-1},
                {3,-1,-1,2,3,-1},
                {-1,3,3,2,2,-1}
        };
        map.setCoefficients(A);
        assertEquals(map.getRows(),8);
        assertEquals(map.getCols(),6);
        for (int i = 0; i < map.getRows(); i++) {
            for (int j = 0; j < map.getCols(); j++) {
                assertEquals(map.getCoefficients()[i][j],B[i][j]);
            }
        }
    }

    @Test
    void stringCoefficientToInt() {
        assertEquals(Map.stringCoefficientToInt("0"),0);
        assertEquals(Map.stringCoefficientToInt("1"),1);
        assertEquals(Map.stringCoefficientToInt("2"),2);
        assertEquals(Map.stringCoefficientToInt("3"),3);
        assertEquals(Map.stringCoefficientToInt(null),-1);
        assertEquals(Map.stringCoefficientToInt("33"),-1);
        assertEquals(Map.stringCoefficientToInt("-25"),-1);
    }

    @Test
    void setCoefficient() {
        Map map = new Map();
        String[][]A={
                {"-1","3","2","1","2","-1"},
                {"-1","0","1","-1","-1","1"},
                {"-1","2","-1","-1","-1","3"},
                {"-1","-1","-1","-1","2","-1"},
                {"-1","2","-1","-1","-1","-1"},
                {"3","-1","-1","-1","2","-1"},
                {"3","-1","-1","2","3","-1"},
                {"-1","3","3","2","2","-1"}
        };

        map.setCoefficients(A);
        for (int i = 0; i < map.getRows(); i++) {
            for (int j = 0; j < map.getCols(); j++) {
                map.setCoefficient(20,i,j);
            }
        }
        for (int i = 0; i < map.getRows(); i++) {
            for (int j = 0; j < map.getCols(); j++) {
                assertEquals(map.getCoefficients()[i][j],20);
            }
        }
    }

}