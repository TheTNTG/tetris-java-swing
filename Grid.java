package simple.tetris;
public class Grid <T> implements java.io.Serializable{
    
     private T[][] matrix;

     
     Grid(int width, int height){
        matrix = (T[][])(new Object[width][height]);
     }
     
     
     public void setT(int x, int y, T value){ matrix[x][y] = value; } 
     public T getT(int x, int y){ return matrix[x][y]; }
   
}
