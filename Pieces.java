package simple.tetris;

import java.awt.Color;

public abstract class Pieces implements GameEntity, java.io.Serializable{
    

    //ENCAPSULATION
    private int[][] shape;
    private int posX, posY;

    public Pieces(int x, int y, int[][] p){
        posX = x;
        posY = y;
        shape = p;
    }
    
    public abstract int getClearBonus(int linesCleared);
    public abstract Color getPieceColor();
    
    @Override
    public void movePiece(int dx, int dy){
        posX += dx;
        posY += dy;
    }
    
   
    
    @Override
    public void rotate() {
        int rows = shape.length;
        int cols = shape[0].length;
        int[][] rotatedShape = new int[cols][rows];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                // Clockwise rotation formula
                rotatedShape[j][rows - 1 - i] = shape[i][j];
            }
        }
        this.shape = rotatedShape;
    }
    
    
    //ACCESSOR METHODS
    public int getX(){return posX;}
    public int getY(){return posY;}
    public int[][] getShape(){ return shape; } 
    //MUTATOR
    public void setShape(int[][] newShape) {
            shape = newShape;
    }
}
