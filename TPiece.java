package simple.tetris;

import java.awt.Color;

public class TPiece extends Pieces{
    //2x3
    private static final int[][] T_SHAPE = {{0, 1, 0},
                                            {1, 1, 1}};
    
    public TPiece(int x, int y){
            super(x,y,T_SHAPE);
    }
    
    @Override
    public Color getPieceColor() {
        return Color.MAGENTA; // T-pieces are purple
    }
    @Override
    public int getClearBonus(int lines) {
            switch(lines) {
                case 1: return 100;   // Single
                case 2: return 300;   // Double
                default: return 0;
            }
        }
}
