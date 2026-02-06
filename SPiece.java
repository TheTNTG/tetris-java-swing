package simple.tetris;

import java.awt.Color;

public class SPiece extends Pieces {
    private static final int[][] S_SHAPE = {{0, 1, 1}, 
                                            {1, 1, 0}};
    public SPiece(int x, int y) {
        super(x, y, S_SHAPE);
    }
    @Override
    public int getClearBonus(int lines) {
        switch(lines) {
            case 1: return 100;   // Single
            case 2: return 300;   // Double
            case 3: return 500;   // Triple
            default: return 0;
        }
    }
    @Override
    public Color getPieceColor() {
        return Color.RED; 
    }
}
