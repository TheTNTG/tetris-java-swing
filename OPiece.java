package simple.tetris;

import java.awt.Color;

public class OPiece extends Pieces {
    //2x2
    private static final int[][] O_SHAPE = {
                                            {1, 1},
                                            {1, 1}};
    public OPiece(int x, int y) {
        super(x, y, O_SHAPE);
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
        return Color.YELLOW; 
    }
}
