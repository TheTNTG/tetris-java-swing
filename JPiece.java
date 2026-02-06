package simple.tetris;

import java.awt.Color;

public class JPiece extends Pieces{
    //3x3
    private static final int[][] J_SHAPE = {
                                            {0, 0, 1},
                                            {0, 0, 1}, 
                                            {0, 1, 1}
                                            };
    
    public JPiece(int x, int y){
            super(x,y,J_SHAPE);
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
        return Color.BLUE; 
    }
}

