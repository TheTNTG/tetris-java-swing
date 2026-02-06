package simple.tetris;

import java.awt.Color;

public class ZPiece extends Pieces {
    private static final int[][] Z_SHAPE = {{1, 1, 0},
                                            {0, 1, 1}};
    public ZPiece(int x, int y) {
        super(x, y, Z_SHAPE);
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
        return Color.GREEN; 
    }
}
