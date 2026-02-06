package simple.tetris;

import java.awt.Color;

public class LPiece extends Pieces{
    
    private static final int[][] L_SHAPE = {{1, 0},
                                            {1, 0}, 
                                            {1, 1}};
    
    public LPiece(int x, int y){
            super(x,y,L_SHAPE);
    }
    
    @Override
    public int getClearBonus(int lines) {
        switch(lines) {
            case 1: return 100;   // Single
            case 2: return 300;   // Double
            default: return 0;
        }
    }
    @Override
    public Color getPieceColor() {
        return Color.CYAN; 
    }
}

