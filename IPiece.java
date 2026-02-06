package simple.tetris;

import java.awt.Color;

public class IPiece extends Pieces {
    private static final int[][] I_SHAPE = {{1},{1},{1},{1}};  
    
    public IPiece(int x, int y) {
        super(x, y, I_SHAPE);
    }
    
    @Override
    public int getClearBonus(int lines) {
        switch(lines) {
            case 1: return 100;   // Single
            case 2: return 300;   // Double
            case 3: return 500;   // Triple
            case 4: return 1200;  // TETRIS! (Big reward for the I-Piece)
            default: return 0;
        }
    }
    
    @Override
    public Color getPieceColor() {
        return Color.CYAN; 
    }
    
    
}
