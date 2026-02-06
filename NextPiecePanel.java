package simple.tetris;

import javax.swing.*;
import java.awt.*;

public class NextPiecePanel extends JPanel {
    private TetrisGame game;
    private final int PREVIEW_TILE_SIZE = 25; // Slightly smaller for the preview box

    public NextPiecePanel(TetrisGame game) {
        this.game = game;
        this.setPreferredSize(new Dimension(120, 120));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // 1. IMPROVE QUALITY
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // 2. MATCH THE INDIE GRADIENT
        GradientPaint gp = new GradientPaint(0, 0, new Color(44, 62, 80), 0, getHeight(), new Color(20, 24, 35));
        g2d.setPaint(gp);
        g2d.fillRect(0, 0, getWidth(), getHeight());

        if (game == null || game.getNextPiece() == null) return;

        Pieces next = game.getNextPiece();
        int[][] shape = next.getShape();
        Color pColor = next.getPieceColor();

        // 3. CENTER THE PIECE IN THE PANEL
        // Calculate the total width and height of the shape to center it
        int shapeWidth = shape[0].length * PREVIEW_TILE_SIZE;
        int shapeHeight = shape.length * PREVIEW_TILE_SIZE;
        int offsetX = (getWidth() - shapeWidth) / 2;
        int offsetY = (getHeight() - shapeHeight) / 2;

        // 4. DRAW THE PIECE
        for (int r = 0; r < shape.length; r++) {
            for (int c = 0; c < shape[r].length; c++) {
                if (shape[r][c] == 1) {
                    drawProfessionalBlock(g2d, offsetX + (c * PREVIEW_TILE_SIZE), 
                                          offsetY + (r * PREVIEW_TILE_SIZE), pColor);
                }
            }
        }
    }

    private void drawProfessionalBlock(Graphics2D g2d, int x, int y, Color color) {
        // Main block body
        g2d.setColor(color);
        g2d.fillRoundRect(x + 1, y + 1, PREVIEW_TILE_SIZE - 2, PREVIEW_TILE_SIZE - 2, 6, 6);

        // Glassy Shine (Top half)
        g2d.setColor(new Color(255, 255, 255, 50));
        g2d.fillRoundRect(x + 3, y + 3, PREVIEW_TILE_SIZE - 6, PREVIEW_TILE_SIZE / 2 - 3, 4, 4);
        
        // Subtle dark border
        g2d.setColor(new Color(0, 0, 0, 80));
        g2d.drawRoundRect(x + 1, y + 1, PREVIEW_TILE_SIZE - 2, PREVIEW_TILE_SIZE - 2, 6, 6);
    }
}